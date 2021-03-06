# Сравнение различных GC
**На примере G1 и Parallel GC**

## Параметры запуска JVM

```
-Xms4096m / 512m
-Xmx4096m / 512m
-XX:+UseParallelGC / -XX:+UseG1GC
-Xlog:gc*  
-verbose:gc
```

Два тестируемых кейса для различных размеров хипа: 512m 4096m.

## Характеристика приложения

В цикле создаются объекты Animal, которые добавляются в коллекцию ArrayList.
Каждые 2500 объектов ход приложения приостанавливается на 10 мс (для памяти 512m) / 5мс (для памяти 4096m). 
При этом половина из добавленных объектов удаляется из коллекции.

## Parallel GC
Для сборки Young поколения используется сборщик PS Scavenge.
Для Tenured (Old) поколения используется PS MarkSweep — многопоточная версия Mark-Sweep-Compact алгоритма (пометить выжившие объекты, очистить память от мертвых, уплотнить выжившие)

```
GC name: PS MarkSweep
GC name: PS Scavenge
```

Для Parallel GC характерна сборка мусора при временной полной остановке приложения. При этом в отличие от Serial GC задействуются несколько потоков.
Young поколение разделяется на 3 региона — Eden и 2 Surviver. При этом Tenured регион один.

Процессы сборки мусора разделяются на малую сборку (minor GC), затрагивающую только младшее поколение, старшую сборку (major GC). Бывает и полная сборку (full GC).

Если в процессе очистки памяти в куче для Young поколения не остается свободных регионов, в которые можно было бы копировать выжившие объекты, это приводит к возникновению ситуации Allocation Failure. 
В таком случае сборщик выполняет полную сборку мусора по всей куче при остановленных основных потоках приложения.

Пример такого лога с Allocation Failure. Стоит сказать: что в случае с Parallel GC, приложение будет приостановлено в любом случае:

```
[22.394s][info   ][gc     ] GC(0) Pause Young (Allocation Failure) 96M->25M(491M) 86.680ms
```

## G1 GC

Для сборки Young поколения используется G1 Young Generation. Для Tenured поколения используется G1 Old Generation.

```
GC name: G1 Young Generation
GC name: G1 Old Generation
```

G1 использует региональную сборку. Heap разделяется на множество регионов равного размера (0.5-2 мб).

```
[0.015s][info   ][gc,heap] Heap region size: 2M  
[0.039s][info   ][gc     ] Using G1
```

G1 оценивает размер живых объектов в регионах, это происходит параллельно без остановки потоков приложения.
При следующем цикле GC обрабатывает все Young регионы, а также несколько тех, в которых наименьший размер живых объектов.

Пример работы G1 с регионами:

```
[864.818s][info   ][gc,heap      ] GC(4) Eden regions: 2->0(100)
[864.818s][info   ][gc,heap      ] GC(4) Survivor regions: 2->2(13)
[864.818s][info   ][gc,heap      ] GC(4) Old regions: 0->0
[864.818s][info   ][gc,heap      ] GC(4) Archive regions: 2->2
[864.818s][info   ][gc,heap      ] GC(4) Humongous regions: 753->753
```

Разделение регионов на Eden, Survivor и Tenured в данном случае логическое, регионы одного поколения не обязаны идти подряд и даже могут менять свою принадлежность к тому или иному поколению.

G1 имеет возможность параллельно с работой самого приложения оценивать размеры регоинов, их загруженность мертвыми объектами, выбирать подходящие регионы, очистить которые было бы наиболее эффективно.
Таким образом G1 в отличие от Parallel GC работает параллельно с основным приложением, но паузы STW для него потенциально будут меньше, благодаря тому, что часть работы будет проделана во время работы основного приложения.

Важное уточнение, в G1 есть так называемые "Громадные регионы" (humongous regions). Любой объект размером больше половины региона считается громадным и обрабатывается особым образом.
В частности, такой объект не перемещается между регионами и к нему не подселяют другие объекты.

Это ситуация особенно актуальная для нашего приложения, посколько мы имеем дело с постоянно растущей коллекцией.

Пример такой сборки:

```
[862.204s][info   ][gc           ] GC(2) Pause Young (Concurrent Start) (G1 Humongous Allocation) 1543M->1509M(4096M) 2643.627ms
[571.283s][info   ][gc,task      ] GC(0) Using 8 workers of 8 for evacuation
```

Выделение спциальных регионов под громадные объекты:

```
[574.514s][info   ][gc,heap      ] GC(0) Humongous regions: 909->909
```

## Сравнение GC

### 512m

|                                   | Parallel GC | G1 GC         |
|-----------------------------------|-------------|---------------|
| Колличество сборок мусора (Young) | 3           | 23            |
| Колличество сборок мусора (Old)   | 6           | 6             |
| Среднее время сборки (Young)      | 120ms       | 28ms          |
| Среднее время сборки (Old)        | 949ms       | 470ms         |
| Время жизни приложения            | 4.35s       | 5.30s         |
| Максимальная загрузка процессора  | 100.0%      | 79.7%         |

Для Parallel GC после прошествия 5 минут приложение долгое время не крашится, но переходит в режим непрерывной сборки мусора.

Стоит дополнительно отметить, что в случае с G1 место имеют еще и так называемые Concurrent операции, маркирующие мертвые объекты.
Логи данной сборки:
```
[82.288s][info   ][gc,marking   ] GC(1) Concurrent Mark From Roots 198.840ms
[82.288s][info   ][gc,marking   ] GC(1) Concurrent Preclean
[82.288s][info   ][gc,marking   ] GC(1) Concurrent Preclean 0.030ms
[82.288s][info   ][gc,marking   ] GC(1) Concurrent Mark (82.089s, 82.288s) 198.901ms
[82.288s][info   ][gc,start     ] GC(1) Pause Remark
[82.289s][info   ][gc           ] GC(1) Pause Remark 246M->136M(512M) 1.050ms
```
В ходе них происходит разметка удаляемых объектов, выявление проблемных мест. 
Такие сборки тоже занимают определенное время, но не блокируют выполнение программы.
С ними общее число сборок доходит до 16.

### 4096m

|                                   | Parallel GC | G1 GC        |
|-----------------------------------|-------------|--------------|
| Колличество сборок мусора (Young) | 3           | 24           |
| Колличество сборок мусора (Old)   | 3           | 4            |
| Среднее время сборки (Young)      | 3194ms      | 192ms        |
| Среднее время сборки (Old)        | 9650ms      | 4168ms       |
| Время жизни приложения            | 5.31s       | 6.24s        |
| Максимальная загрузка процессора  | 100.0%      | 89.0%        |

Для Parallel GC после прошествия 5 минут приложение долгое время не крашится, но переходит в режим непрерывной сборки мусора.
Начиная с этого времени, приложение становится неактивным, процессор загружается до 100%, система гудит, но свободные ресурсы в нашем случае не высвобождаются.
Кажется, что лучшим поведением в данном случае стало бы падение приложение с OOM, которое позволилио быстро оживить приложение.

G1 GC после заполнения хипа тоже начинает непрерывно собирать мусор, но длится это меньше, ресурсы процессора не эксплуатируются на 100%.

## Общие выводы

С точки зрения потребляемых ресурсов, G1 чаще задействует мощности процессора для проведения анализа по мертвым / живым объектам. 
Parallel GC в этом плане ведет себя намного скромнее, но при этом при подходе к OOM наоборот практически полностью загружает процессор, останавливает программу и непрерывно собирает мусор.
Такая операция не имеет особого смысла в тех условиях, когда дополнительную память освободить не удается.

Использование G1 плодотворно сказывается на снижении времени STW.
В примере с работой на 512m и 4096m G1 GС обогнал Parallel GC, как по снижению времени Young сборок, так и времени Old сборкой.
Срок жизни приложения был чуть выше у G1 при объеме памяти как 512m, так и 4096m.

Вместе с тем, кажется, что G1 уязвимым к негативному влиянию больших объектов, из-за того что управляет больших количеством регионов, но при этом малого объема (в нашем случае 2m).

В общем и целом, сложно сделать однозначные выводы на данном примере, поскольку он скорее искуственный. В реальной ситуации при подобной утечке не спасет ни один GC.
В рассмотренном примере видно, что в целом лидерует G1 GC. G1, лучше и быстрее работает как с Old поколением, так и с Young поколением.
Parallel проседает по времени сборок, из-за того, что не имеет возможности анализировать загрузку хипа в рантайме.

Сказать можно наверняка, что G1 выглядит предпочтительным вариантом, там где нужно сократить время STW и там, где нет тех самых "humongous" объектов.
При этом надо быть готовым, что часть процессорных ресурсов будет постоянно использоваться самим G1.

Parallel хорошо подойдет для случаев, когда доступа GC к процессору при работе программы давать не нужно, а при этом определенное увеличение времени STW в моменте не выглядит критичным.
