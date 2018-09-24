package ru.otus.mylist;

import java.util.Collections;

public class App {
    public static void main(String[] args) {
        MyArrayList<Integer> myArrayList = new MyArrayList<Integer>(11);
        MyArrayList<Integer> myArrayList1 = new MyArrayList<Integer>(22);
        MyArrayList<Integer> qwe = new MyArrayList<Integer>(10);
        System.out.println(qwe.isEmpty());
        Collections.sort(myArrayList);
        Collections.copy(myArrayList1, myArrayList);
        System.out.println(myArrayList.get(0));
        System.out.println(myArrayList1.get(0));
        for (Integer i : myArrayList) {
            System.out.println(i);
        }
        Collections.addAll(myArrayList1, 1, 2, 3, 4, 5);
        int[] first = {1, 2, 3, 4, 5};
        int[] second = {6, 7, 8, 9, 10};
        System.out.println();
        System.out.println();
        System.arraycopy(first, 0, second, 0, 5);
        for (int i : first) {
            System.out.println(i);
        }
        for (int i : second) {
            System.out.println(i);
        }
    }
}

}
