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

    private boolean logContains(String substr) {
        for (String s : tree.getLog()) {
            if (s != null && s.contains(substr)) return true;
        }
        return false;
    }

    @Test
    public void testInsert() {
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        tree.printTree();

        assertTrue(logContains("insert:start:3"));
        assertTrue(logContains("insertNonFull:node:(_,_,_) x:3"));
        assertTrue(logContains("insertNonFull:placedInLeaf:(3,_,_)"));
        assertTrue(logContains("insert:done:3"));
        assertTrue(logContains("insert:start:5"));
        assertTrue(logContains("insertNonFull:node:(3,_,_) x:5"));
        assertTrue(logContains("insertNonFull:placedInLeaf:(3,5,_)"));
        assertTrue(logContains("insert:done:5"));
        assertTrue(logContains("insert:start:7"));
        assertTrue(logContains("insertNonFull:node:(3,5,_) x:7"));
        assertTrue(logContains("insertNonFull:placedInLeaf:(3,5,7)"));
        assertTrue(logContains("insert:done:7"));

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

        assertTrue(logContains("search:start:3"));
        assertTrue(logContains("search:at:(3,5,_)"));
        assertTrue(logContains("search:found:3"));
        assertTrue(logContains("search:start:5"));
        assertTrue(logContains("search:at:(3,5,_)"));
        assertTrue(logContains("search:found:5"));
        assertTrue(logContains("search:start:10"));
        assertTrue(logContains("search:at:(3,5,_)"));
        assertTrue(logContains("search:notfound:10"));
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

        System.out.println(tree.log);

        assertTrue(logContains("delete:start:3"));
        assertTrue(logContains("search:start:3"));
        assertTrue(logContains("search:at:(3,5,7)"));
        assertTrue(logContains("search:found:3"));
        assertTrue(logContains("deleteInternal:start:node:(3,5,7) x:3"));
        assertTrue(logContains("deleteInternal:foundInNode:(3,5,7) x:3"));
        assertTrue(logContains("deleteInternal:removedFromLeaf:(5,7,_)"));
        assertTrue(logContains("delete:done:3"));
    }

    @Test
    public void testDeleteNonExistent() {
        tree.insert(3);
        tree.delete(10);
        tree.printTree();
        assertTrue(logContains("insert:start:3"));
        assertTrue(logContains("insertNonFull:node:(_,_,_) x:3"));
        assertTrue(logContains("insertNonFull:placedInLeaf:(3,_,_)"));
        assertTrue(logContains("insert:done:3"));
        assertTrue(logContains("delete:start:10"));
        assertTrue(logContains("search:start:10"));
        assertTrue(logContains("search:at:(3,_,_)"));
        assertTrue(logContains("search:notfound:10"));
        assertTrue(logContains("delete:notFound:10"));
        assertTrue(tree.search(3), "3 should still be found in the tree after attempting deleting 10");
        assertFalse(tree.search(10), "10 should not be found in the tree");
    }

    @Test
    public void testFewNodes() {
        for (int i = 0; i < 5; i++) {
            tree.insert(i);
        }

        tree.search(4);
        tree.search(10);
        tree.delete(3);
        tree.delete(0);

        tree.printTree();

        assertTrue(logContains("insert:start:3"));
        assertTrue(logContains("insert:rootIsFull:splitRoot"));
        assertTrue(logContains("splitRoot:promoted:1"));
        assertTrue(logContains("insertNonFull:node:(1,_,_) x:3"));
        assertTrue(logContains("insertNonFull:node:(2,_,_) x:3"));
        assertTrue(logContains("insertNonFull:placedInLeaf:(2,3,_)"));
        assertTrue(logContains("insert:done:3"));

        assertTrue(logContains("search:start:4"));
        assertTrue(logContains("search:at:(1,_,_)"));
        assertTrue(logContains("search:at:(2,3,4)"));
        assertTrue(logContains("search:found:4"));

        assertTrue(logContains("search:start:10"));
        assertTrue(logContains("search:at:(1,_,_)"));
        assertTrue(logContains("search:at:(2,3,4)"));
        assertTrue(logContains("search:notfound:10"));

        assertTrue(logContains("delete:start:3"));
        assertTrue(logContains("search:start:3"));
        assertTrue(logContains("search:at:(1,_,_)"));
        assertTrue(logContains("search:at:(2,3,4)"));
        assertTrue(logContains("search:found:3"));
        assertTrue(logContains("deleteInternal:start:node:(1,_,_) x:3"));
        assertTrue(logContains("deleteInternal:start:node:(2,3,4) x:3"));
        assertTrue(logContains("deleteInternal:foundInNode:(2,3,4) x:3"));
        assertTrue(logContains("deleteInternal:removedFromLeaf:(2,4,_)"));
        assertTrue(logContains("delete:done:3"));

        assertTrue(logContains("delete:start:0"));
        assertTrue(logContains("search:start:0"));
        assertTrue(logContains("search:at:(1,_,_)"));
        assertTrue(logContains("search:at:(0,_,_)"));
        assertTrue(logContains("search:found:0"));
        assertTrue(logContains("deleteInternal:start:node:(1,_,_) x:0"));
        assertTrue(logContains("deleteInternal:borrowFromRight:parent:(1,_,_) childIdx:0"));
        assertTrue(logContains("rotateLeft:start:parent:(1,_,_) childIdx:0"));
        assertTrue(logContains("rotateLeft:done:parent:(2,_,_)"));
        assertTrue(logContains("child:(0,1,_) right:(4,_,_)"));
        assertTrue(logContains("deleteInternal:start:node:(0,1,_) x:0"));
        assertTrue(logContains("deleteInternal:foundInNode:(0,1,_) x:0"));
        assertTrue(logContains("deleteInternal:removedFromLeaf:(1,_,_)"));
        assertTrue(logContains("delete:done:0"));
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

        assertTrue(logContains("insert:start:41"));
        assertTrue(logContains("insertNonFull:node:(15,_,_) x:41"));
        assertTrue(logContains("insertNonFull:node:(23,_,_) x:41"));
        assertTrue(logContains("insertNonFull:childIsFull:splitChild parent:(23,_,_) child:(27,31,35)"));
        assertTrue(logContains("splitChild:parent:(23,_,_) child:(27,31,35) promoted:31"));
        assertTrue(logContains("insertNonFull:node:(35,_,_) x:41"));
        assertTrue(logContains("insertNonFull:node:(37,_,_) x:41"));
        assertTrue(logContains("insertNonFull:childIsFull:splitChild parent:(37,_,_) child:(38,39,40)"));
        assertTrue(logContains("splitChild:parent:(37,_,_) child:(38,39,40) promoted:39"));
        assertTrue(logContains("insertNonFull:node:(40,_,_) x:41"));
        assertTrue(logContains("insertNonFull:placedInLeaf:(40,41,_)"));
        assertTrue(logContains("insert:done:41"));

        tree.clearLog();

        for (int i = 0; i < 1000; i++) {
            tree.delete(i);
            assertFalse(tree.search(i), "Value " + i + " should not be found in the tree after deletion");
        }
        for (int i = 0; i < 1000; i++) {
            assertFalse(tree.search(i), "Value " + i + " should not be found in the tree after deletion");
        }

        System.out.println(tree.log);
        tree.printTree();

        assertTrue(logContains("delete:start:41"));
        assertTrue(logContains("search:at:(639,_,_)"));
        assertTrue(logContains("search:at:(383,511,_)"));
        assertTrue(logContains("search:at:(191,255,319)"));
        assertTrue(logContains("search:at:(127,159,_)"));
        assertTrue(logContains("search:at:(79,95,111)"));
        assertTrue(logContains("search:at:(55,63,71)"));
        assertTrue(logContains("search:at:(47,51,_)"));
        assertTrue(logContains("search:at:(43,45,_)"));
        assertTrue(logContains("search:at:(41,42,_)"));
        assertTrue(logContains("search:found:41"));
    }
}
