package org.lia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BTreeTest {

    private BTree tree;

    @BeforeEach
    public void setUp() {
        tree = new BTree();
    }

    @Test
    public void testInsert() {
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        tree.printTree();

        assert tree.search(3) : "3 should be found in the tree";
        assert tree.search(5) : "5 should be found in the tree";
        assert tree.search(7) : "7 should be found in the tree";
    }

    @Test
    public void testSearch() {
        tree.insert(3);
        tree.insert(5);

        assert tree.search(3) : "3 should be found in the tree";
        assert tree.search(5) : "5 should be found in the tree";
        assert !tree.search(10) : "10 should not be found in the tree";
    }

    @Test
    public void testDelete() {
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);

        tree.delete(3);
        tree.printTree();
        assert !tree.search(3) : "3 should not be found in the tree after deletion";
        assert tree.search(5) : "5 should be found in the tree after deletion";
        assert tree.search(7) : "7 should be found in the tree after deletion";
    }

    @Test
    public void testDeleteNonExistent() {
        tree.insert(3);
        tree.delete(10);
        tree.printTree();
        assert tree.search(3) : "3 should still be found in the tree after attempting deleting 10";
        assert !tree.search(10) : "10 should not be found in the tree";
    }

    @Test
    public void testBig() {
        for (int i = 0; i < 1000; i++) {
            tree.insert(i);
            assert tree.search(i) : "Value " + i + " should be found in the tree";
        }
        for (int i = 0; i < 1000; i++) {
            assert tree.search(i) : "Value " + i + " should be found in the tree";
        }
        for (int i = 0; i < 1000; i++) {
            tree.delete(i);
            assert !tree.search(i) : "Value " + i + " should not be found in the tree after deletion";
        }
        for (int i = 0; i < 1000; i++) {
            assert !tree.search(i) : "Value " + i + " should not be found in the tree after deletion";
        }

        tree.printTree();
    }
}
