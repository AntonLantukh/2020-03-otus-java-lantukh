package ru.otus.lantukh.cache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private final WeakHashMap<K, V> cache;
    private final List<WeakReference<HwListener<K, V>>> listeners;

    public MyCache() {
        this.cache = new WeakHashMap<>();
        this.listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, OperationType.PUT);
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        notifyListeners(key, value, OperationType.REMOVE);
    }

    public void notifyListeners(K key, V value, OperationType action) {
        listeners.forEach(reference -> {
            HwListener<K, V> listener = reference.get();

            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception err) {
                    throw new RuntimeException(err);
                }
            }
        });
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        for(int i = 0; i < listeners.size(); i++) {
            HwListener<K, V> listListener = listeners.get(i).get();
            if (listListener == null) {
                listeners.remove(i);
                return;
            }

            if (listListener.equals(listener)) {
                listeners.remove(i);
            }
        }
    }
}
