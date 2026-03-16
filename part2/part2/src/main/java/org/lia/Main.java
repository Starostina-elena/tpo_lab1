package org.lia;

public class Main {
    public static void main(String[] args) {
        BTree t = new BTree();

        System.out.println("Вставляем: 3,5,7");
        t.insert(3);
        t.insert(5);
        t.insert(7);
        t.printTree();
        System.out.println();

        System.out.println("Вставляем: 2");
        t.insert(2);
        t.printTree();
        System.out.println();

        System.out.println("Поиск 7: " + t.search(7));
        System.out.println("Поиск 10: " + t.search(10));
        System.out.println();

        System.out.println("Удаляем 3");
        t.delete(3);
        t.printTree();
        System.out.println();

        System.out.println("Удаляем 7");
        t.delete(7);
        t.printTree();
        System.out.println();

        System.out.println("Удаляем 2");
        t.delete(2);
        t.printTree();
    }
}