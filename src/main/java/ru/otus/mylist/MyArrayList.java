package ru.otus.mylist;

import java.util.*;

public class MyArrayList<E> implements List<E> {

    private Object[] elementData;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
    }

    public MyArrayList(int size) {
        if (size < 0)
            throw new IllegalArgumentException("Illegal Argemnt" + size);
        else
            elementData = new Object[size];
    }

    public MyArrayList(Collection<? extends E> collection) {
        elementData = collection.toArray();
        size = elementData.length;
    }

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Iterator<E> iterator() {
        return listIterator();
    }

    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(elementData, 0, newArray, 0, size);
        return newArray;
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < size) return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        if (a.length > size)
            a[size] = null;
        return a;
    }

    public boolean add(E e) {
        add(size, e);
        return true;
    }

    public boolean remove(Object o) {
        int index = indexOf(0);
        if (index == 1)
            return false;
        Object object = remove(index);
        if (object.equals(o))
            return true;
        else return false;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (indexOf(o) != -1) {
                return true;
            }
        }
        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        for (E e : c)
            add(e);
        return true;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);
        for (E e : c) {
            add(index, e);
        }
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object o : c) {
            int index = indexOf(o);
            if (index != -1) {
                remove(index);
                result = true;
            }
        }
        return result;
    }

    public boolean retainAll(Collection<?> c) {
        boolean ret = false;

        int i = 0;
        do {
            i = 0;
            for (; i < size; i++) {
                Object o = elementData[i];
                if (!c.contains(o)) {
                    ret = true;
                    remove(o);
                    break;
                }
            }
        } while (i < size);
        return ret;
    }

    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public void add(int index, E element) {
        checkIndexForAdd(index);
        growResizingCheck();
        movingRight(index);
        elementData[index] = element;
        size++;
    }

    private void growResizingCheck() {
        if (size == elementData.length) {
            Object[] a = new Object[size + (size >> 1)];
            System.arraycopy(elementData, 0, a, 0, size);
            elementData = a;
        }
    }

    public E remove(int index) {
        checkIndex(index);
        E e = (E) elementData[index];
        movingLeft(index);
        size--;
        return e;
    }

    private void movingLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++)
            if (o == null ? get(i) == null : o.equals(get(i)))
                return i;
        return -1;
    }

    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--)
            if (o == null ? get(i) == null : o.equals(get(i)))
                return i;
        return -1;
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);

    }

    public ListIterator<E> listIterator(int index) {
        return new MyIterator(index);

    }

    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        List<E> newList = new MyArrayList<E>();
        if (fromIndex == toIndex)
            return newList;
        for (int i = fromIndex; i < toIndex; i++)
            newList.add((E) elementData[i]);
        return newList;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    private void checkIndexForAdd(int index) {
        if (index == 0)
            return;
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

    }

    private void movingRight(int index) {
        System.arraycopy(elementData, index,
                elementData, index + 1,
                size - index);
    }

    private class MyIterator implements ListIterator {
        int cursor;
        int lastRet = -1;

        public MyIterator(int cursor) {
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public Object next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastRet = cursor;
            Object o = MyArrayList.this.get(cursor);
            cursor++;
            return o;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public Object previous() {
            if (!hasNext())
                throw new NoSuchElementException();
            cursor--;
            lastRet = cursor;
            Object o = MyArrayList.this.get(cursor);
            return o;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            MyArrayList.this.remove(lastRet);
            if (lastRet < cursor)
                cursor--;
            lastRet--;
        }

        @Override
        public void set(Object o) {
            if (lastRet < 0) throw new IllegalStateException();
            try {
                MyArrayList.this.set(lastRet, (E) o);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(Object o) {
            MyArrayList.this.add(cursor, (E) o);
            cursor++;
        }
    }
}


