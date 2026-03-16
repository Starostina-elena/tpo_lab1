package org.lia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertTrue(tree.search(3), "3 should be found in the tree");
        assertTrue(tree.search(5), "5 should be found in the tree");
        assertTrue(tree.search(7), "7 should be found in the tree");
    }

    @Test
    public void testSearch() {
        tree.insert(3);
        tree.insert(5);

        assertTrue(tree.search(3), "3 should be found in the tree");
        assertTrue(tree.search(5), "5 should be found in the tree");
        assertFalse(tree.search(10), "10 should not be found in the tree");
    }

    @Test
    public void testDelete() {
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);

        tree.delete(3);
        tree.printTree();
        assertFalse(tree.search(3), "3 should not be found in the tree after deletion");
        assertTrue(tree.search(5), "5 should be found in the tree after deletion");
        assertTrue(tree.search(7), "7 should be found in the tree after deletion");
    }

    @Test
    public void testDeleteNonExistent() {
        tree.insert(3);
        tree.delete(10);
        tree.printTree();
        assertTrue(tree.search(3), "3 should still be found in the tree after attempting deleting 10");
        assertFalse(tree.search(10), "10 should not be found in the tree");
    }

    @Test
    public void testBig() {
        for (int i = 0; i < 1000; i++) {
            tree.insert(i);
            assertTrue(tree.search(i), "Value " + i + " should be found in the tree");
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue(tree.search(i), "Value " + i + " should be found in the tree");
        }
        for (int i = 0; i < 1000; i++) {
            tree.delete(i);
            assertFalse(tree.search(i), "Value " + i + " should not be found in the tree after deletion");
        }
        for (int i = 0; i < 1000; i++) {
            assertFalse(tree.search(i), "Value " + i + " should not be found in the tree after deletion");
        }
        // TODO: как проверить, что программа ведет себя как дерево а не как список
        // TODO: сделать дженериком
        // TODO: Для этого выбрать характерные точки внутри алгоритма, и для предложенных самостоятельно наборов исходных данных записать последовательность попадания в характерные точки.
        tree.printTree();
    }
}
