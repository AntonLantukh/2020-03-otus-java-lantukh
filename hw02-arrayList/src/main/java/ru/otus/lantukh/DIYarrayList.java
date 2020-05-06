package ru.otus.lantukh;

import java.util.*;

class DIYArrayList<E> implements List<E> {
    private static final int DEFAULT_SIZE = 10;

    private static final int EMPTY_SIZE = 0;

    private Object[] currentList;

    private int size;

    public DIYArrayList(int size) {
        this.currentList = new Object[size];
        this.size = 0;
    }

    public DIYArrayList() {
        currentList = new Object[DEFAULT_SIZE];
    }

    @Override
    public boolean add(E e) {
        add(size, e);

        return true;
    }

    public void add(int index, E e) {
        int oldLength = currentList.length;

        if (size == oldLength) {
            currentList = Arrays.copyOf(currentList, DEFAULT_SIZE + oldLength);
        }

        System.arraycopy(
                currentList,
                index, currentList,
                index + 1,
                size - index
        );

        size++;
        currentList[index] = e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }

        E element = (E) currentList[index];
        System.arraycopy(
                currentList,
                index + 1,
                currentList, index,
                size - 1
        );
        size--;

        return element;
    }

    @Override
    public boolean remove(Object e) {
        for (int i = EMPTY_SIZE; i < this.size; i++) {
            if (currentList[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] elementsArray = c.toArray();
        int incomingSize = elementsArray.length;

        if (incomingSize == 0) {
            return false;
        }

        if (incomingSize > currentList.length - size) {
            int newSize = currentList.length - size + DEFAULT_SIZE;
            currentList = Arrays.copyOf(currentList, newSize);
        }

        System.arraycopy(
                elementsArray,
                0,
                currentList,
                size,
                incomingSize
        );
        size = size + incomingSize;

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] elementsArray = c.toArray();
        int incomingSize = elementsArray.length;

        if (incomingSize == 0) {
            return false;
        }

        if (incomingSize > currentList.length - size) {
            int newSize = currentList.length - size + DEFAULT_SIZE;
            currentList = Arrays.copyOf(currentList, newSize);
        }

        int elementsToCopySize = size - index;
        // Set place for new elements
        System.arraycopy(
                currentList,
                index,
                currentList,
                incomingSize + index,
                elementsToCopySize
        );
        System.arraycopy(
                elementsArray,
                0,
                currentList,
                index,
                incomingSize
        );
        size = size + incomingSize;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (int j = 0; j < size; j++)
            currentList[j] = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E) this.currentList[index];
    }

    @Override
    public E set(int index, E element) {
        this.currentList[index] = element;

        return element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == EMPTY_SIZE;
    }

    @Override
    public boolean contains(Object element) {
        boolean containsFlag = false;

        for (Object item : this.currentList) {
            if (item.equals(element)) {
                containsFlag = true;
            }
        }

        return containsFlag;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(currentList, size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(currentList, size, a.getClass());
        }

        System.arraycopy(currentList, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet;

        Itr() {
            this.cursor = 0;
            this.lastRet = -1;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            Object[] elementData = DIYArrayList.this.currentList;
            int i = cursor;

            if (cursor >= size) {
                throw new NoSuchElementException();
            } else {
                cursor++;
                lastRet = i;
                return (E) elementData[i];
            }
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            DIYArrayList.this.remove(lastRet);
            lastRet = -1;
            cursor = lastRet;
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            DIYArrayList.this.set(lastRet, e);
        }

        public void add(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            DIYArrayList.this.add(lastRet, e);
            lastRet = -1;
            cursor++;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E previous() {
            Object[] elementData = DIYArrayList.this.currentList;
            int i = cursor - 1;
            if (cursor < 0) {
                throw new NoSuchElementException();
            } else {
                cursor = lastRet = i;
                return (E) elementData[i];
            }
        }
    }
}
