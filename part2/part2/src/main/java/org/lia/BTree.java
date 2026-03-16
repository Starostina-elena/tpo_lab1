package org.lia;

public class BTree {

    private static class Node {
        Integer a, b, c;
        Node ch1, ch2, ch3, ch4;
        Node parent;

        private Node(Node parent) {
            this.parent = parent;
        }

        public void balanceValues() {
            if (a != null && b != null && a > b) {
                int temp = a;
                a = b;
                b = temp;
            }
            if (a != null && c != null && a > c) {
                int temp = a;
                a = c;
                c = temp;
            }
            if (b != null && c != null && b > c) {
                int temp = b;
                b = c;
                c = temp;
            }
        }

        public boolean isLeaf() {
            return ch1 == null && ch2 == null && ch3 == null && ch4 == null;
        }

        public boolean isFull() {
            return a != null && b != null && c != null;
        }

        @Override
        public String toString() {
            String sa = (a == null) ? "_" : a.toString();
            String sb = (b == null) ? "_" : b.toString();
            String sc = (c == null) ? "_" : c.toString();
            return "(" + sa + "," + sb + "," + sc + ")";
        }
    }

    private Node root;

    public BTree() {
        root = new org.lia.BTree.Node(null);
    }

    private Node chooseChild(Node node, int x) {
        if (node == null) return null;
        if (node.b == null && node.c == null) {
            return (node.a != null && x < node.a) ? node.ch1 : node.ch2;
        } else if (node.c == null) {
            if (x < node.a) return node.ch1;
            else if (x < node.b) return node.ch2;
            else return node.ch3;
        } else {
            if (x < node.a) return node.ch1;
            else if (x < node.b) return node.ch2;
            else if (x < node.c) return node.ch3;
            else return node.ch4;
        }
    }

    public boolean search(int x) {
        Node node = root;
        while (node != null) {
            if ((node.a != null && node.a == x) || (node.b != null && node.b == x) || (node.c != null && node.c == x)) {
                return true;
            }
            if (node.isLeaf()) return false;
            node = chooseChild(node, x);
        }
        return false;
    }

    public void insert(int x) {
        if (root.isFull()) {
            splitRoot();
        }
        insertNonFull(root, x);
    }

    private void insertNonFull(Node node, int x) {
        if (node.isLeaf()) {
            if (node.a == null) node.a = x;
            else if (node.b == null) node.b = x;
            else if (node.c == null) node.c = x;
            node.balanceValues();
            return;
        }

        Node child = chooseChild(node, x);

        if (child == null) {
            child = new org.lia.BTree.Node(node);
            if (node.b == null && node.c == null) {
                if (node.ch1 == null) node.ch1 = child; else node.ch2 = child;
            } else if (node.c == null) {
                if (node.ch1 == null) node.ch1 = child;
                else if (node.ch2 == null) node.ch2 = child;
                else node.ch3 = child;
            } else {
                if (node.ch1 == null) node.ch1 = child;
                else if (node.ch2 == null) node.ch2 = child;
                else if (node.ch3 == null) node.ch3 = child;
                else node.ch4 = child;
            }
            child.parent = node;
        }

        if (child.isFull()) {
            splitChild(node, child);
            child = chooseChild(node, x);
        }

        insertNonFull(child, x);
    }

    private void splitRoot() {
        root.balanceValues();
        Node left = new org.lia.BTree.Node(null);
        Node right = new org.lia.BTree.Node(null);
        left.a = root.a;
        right.a = root.c;

        if (root.ch1 != null) {
            left.ch1 = root.ch1; left.ch1.parent = left;
        }
        if (root.ch2 != null) {
            left.ch2 = root.ch2; left.ch2.parent = left;
        }
        if (root.ch3 != null) {
            right.ch1 = root.ch3; right.ch1.parent = right;
        }
        if (root.ch4 != null) {
            right.ch2 = root.ch4; right.ch2.parent = right;
        }

        int promoted = root.b;
        Node newRoot = new org.lia.BTree.Node(null);
        newRoot.a = promoted;
        newRoot.ch1 = left; left.parent = newRoot;
        newRoot.ch2 = right; right.parent = newRoot;
        root = newRoot;
    }

    private void splitChild(Node parent, Node child) {
        child.balanceValues();
        Node left = new org.lia.BTree.Node(parent);
        Node right = new org.lia.BTree.Node(parent);
        left.a = child.a;
        right.a = child.c;

        if (child.ch1 != null) { left.ch1 = child.ch1; left.ch1.parent = left; }
        if (child.ch2 != null) { left.ch2 = child.ch2; left.ch2.parent = left; }
        if (child.ch3 != null) { right.ch1 = child.ch3; right.ch1.parent = right; }
        if (child.ch4 != null) { right.ch2 = child.ch4; right.ch2.parent = right; }

        int promoted = child.b;

        java.util.List<Integer> keys = new java.util.ArrayList<>();
        if (parent.a != null) keys.add(parent.a);
        if (parent.b != null) keys.add(parent.b);
        if (parent.c != null) keys.add(parent.c);

        java.util.List<Node> children = new java.util.ArrayList<>();
        if (parent.ch1 != null) children.add(parent.ch1);
        if (parent.ch2 != null) children.add(parent.ch2);
        if (parent.ch3 != null) children.add(parent.ch3);
        if (parent.ch4 != null) children.add(parent.ch4);

        int idx = -1;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) == child) { idx = i; break; }
        }
        if (idx == -1) {
            idx = children.size();
            children.add(child);
        }

        children.remove(idx);
        children.add(idx, left);
        children.add(idx + 1, right);

        int keyPos = idx;
        if (keyPos < 0) keyPos = 0;
        if (keyPos > keys.size()) keyPos = keys.size();
        keys.add(keyPos, promoted);

        parent.a = (keys.size() > 0) ? keys.get(0) : null;
        parent.b = (keys.size() > 1) ? keys.get(1) : null;
        parent.c = (keys.size() > 2) ? keys.get(2) : null;

        parent.ch1 = parent.ch2 = parent.ch3 = parent.ch4 = null;
        for (int i = 0; i < children.size() && i < 4; i++) {
            Node nd = children.get(i);
            if (i == 0) parent.ch1 = nd;
            else if (i == 1) parent.ch2 = nd;
            else if (i == 2) parent.ch3 = nd;
            else if (i == 3) parent.ch4 = nd;
            if (nd != null) nd.parent = parent;
        }
    }

    public void delete(int x) {
        if (!search(x)) return;
        deleteInternal(root, x);
        if (root.a == null && !root.isLeaf()) {
            root = root.ch1;
            if (root != null) root.parent = null;
        }
    }

    private int keyCount(Node n) {
        int cnt = 0;
        if (n.a != null) cnt++;
        if (n.b != null) cnt++;
        if (n.c != null) cnt++;
        return cnt;
    }

    private Node getChildAt(Node p, int idx) {
        if (idx == 0) return p.ch1;
        if (idx == 1) return p.ch2;
        if (idx == 2) return p.ch3;
        return p.ch4;
    }

    private void setChildAt(Node p, int idx, Node ch) {
        if (idx == 0) p.ch1 = ch;
        else if (idx == 1) p.ch2 = ch;
        else if (idx == 2) p.ch3 = ch;
        else p.ch4 = ch;
        if (ch != null) ch.parent = p;
    }

    private Integer getKeyAt(Node p, int idx) {
        if (idx == 0) return p.a;
        if (idx == 1) return p.b;
        return p.c;
    }

    private void setKeyAt(Node p, int idx, Integer v) {
        if (idx == 0) p.a = v;
        else if (idx == 1) p.b = v;
        else p.c = v;
    }

    private void removeKeyFromNode(Node n, Integer key) {
        if (n.a != null && n.a.equals(key)) {
            n.a = n.b;
            n.b = n.c;
            n.c = null;
        } else if (n.b != null && n.b.equals(key)) {
            n.b = n.c;
            n.c = null;
        } else if (n.c != null && n.c.equals(key)) {
            n.c = null;
        }
    }

    private Node getMaxNode(Node n) {
        Node cur = n;
        while (!cur.isLeaf()) {
            if (cur.ch4 != null) cur = cur.ch4;
            else if (cur.ch3 != null) cur = cur.ch3;
            else if (cur.ch2 != null) cur = cur.ch2;
            else cur = cur.ch1;
        }
        return cur;
    }

    private Integer getMaxKey(Node n) {
        if (n.c != null) return n.c;
        if (n.b != null) return n.b;
        return n.a;
    }

    private void rotateLeft(Node parent, int childIdx) {
        Node child = getChildAt(parent, childIdx);
        Node right = getChildAt(parent, childIdx + 1);
        Integer parentKey = getKeyAt(parent, childIdx);
        if (child.a == null) child.a = parentKey;
        else if (child.b == null) child.b = parentKey;
        else child.c = parentKey;
        child.balanceValues();

        Integer rightLeft = right.a;
        setKeyAt(parent, childIdx, rightLeft);
        if (right.b != null) {
            right.a = right.b;
            right.b = right.c;
            right.c = null;
        } else {
            right.a = null;
            right.b = null;
            right.c = null;
        }

        Node move = right.ch1;
        right.ch1 = right.ch2;
        right.ch2 = right.ch3;
        right.ch3 = right.ch4;
        right.ch4 = null;

        if (child.ch4 == null) {
            if (child.ch3 == null) child.ch3 = move;
            else if (child.ch2 == null) child.ch2 = move;
            else child.ch4 = move;
        } else {
            child.ch4 = move;
        }
        if (move != null) move.parent = child;
    }

    private void rotateRight(Node parent, int childIdx) {
        Node child = getChildAt(parent, childIdx);
        Node left = getChildAt(parent, childIdx - 1);
        Integer parentKey = getKeyAt(parent, childIdx - 1);
        if (child.c == null) {
            child.c = child.b;
            child.b = child.a;
            child.a = parentKey;
        } else {
            child.c = child.b; child.b = child.a; child.a = parentKey;
        }
        child.balanceValues();

        Integer leftRight = (left.c != null) ? left.c : left.b;
        setKeyAt(parent, childIdx - 1, leftRight);
        if (left.c != null) left.c = null;
        else if (left.b != null) left.b = null;
        else left.a = null;

        Node move = left.ch4;
        left.ch4 = null;
        child.ch4 = child.ch3;
        child.ch3 = child.ch2;
        child.ch2 = child.ch1;
        child.ch1 = move;
        if (move != null) move.parent = child;
    }

    private void mergeWithRight(Node parent, int childIdx) {
        Node left = getChildAt(parent, childIdx);
        Node right = getChildAt(parent, childIdx + 1);
        Integer parentKey = getKeyAt(parent, childIdx);
        left.b = parentKey;
        left.c = right.a;
        Node c1 = left.ch1; Node c2 = left.ch2; Node c3 = right.ch1; Node c4 = right.ch2;
        left.ch1 = c1; left.ch2 = c2; left.ch3 = c3; left.ch4 = c4;
        if (c3 != null) c3.parent = left;
        if (c4 != null) c4.parent = left;

        removeKeyFromNode(parent, parentKey);
        java.util.List<Node> orig = new java.util.ArrayList<>();
        if (parent.ch1 != null) orig.add(parent.ch1);
        if (parent.ch2 != null) orig.add(parent.ch2);
        if (parent.ch3 != null) orig.add(parent.ch3);
        if (parent.ch4 != null) orig.add(parent.ch4);
        java.util.List<Node> newChildren = new java.util.ArrayList<>();
        boolean mergedPlaced = false;
        for (int i = 0; i < orig.size(); i++) {
            Node on = orig.get(i);
            if (!mergedPlaced && (on == left || on == right)) {
                newChildren.add(left);
                mergedPlaced = true;
                if (i + 1 < orig.size() && (orig.get(i + 1) == left || orig.get(i + 1) == right)) i++;
            } else {
                newChildren.add(on);
            }
        }
        parent.ch1 = parent.ch2 = parent.ch3 = parent.ch4 = null;
        for (int i = 0; i < newChildren.size() && i < 4; i++) setChildAt(parent, i, newChildren.get(i));
    }

    private void mergeWithLeft(Node parent, int leftIdx) {
        mergeWithRight(parent, leftIdx);
    }

    private void deleteInternal(Node node, int x) {
        if (node == null) return;
        if (node.a != null && node.a == x || node.b != null && node.b == x || node.c != null && node.c == x) {
            if (node.isLeaf()) {
                removeKeyFromNode(node, x);
                return;
            } else {
                int pos = (node.a != null && node.a == x) ? 0 : (node.b != null && node.b == x) ? 1 : 2;
                Node leftChild = getChildAt(node, pos);
                Node predNode = getMaxNode(leftChild);
                Integer pred = getMaxKey(predNode);
                if (pos == 0) node.a = pred;
                else if (pos == 1) node.b = pred;
                else node.c = pred;
                deleteInternal(leftChild, pred);
                return;
            }
        }

        if (node.isLeaf()) return;

        int idx;
        if (node.a != null && x < node.a) idx = 0;
        else if (node.b != null && x < node.b) idx = 1;
        else if (node.c != null && x < node.c) idx = 2;
        else idx = keyCount(node);

        Node child = getChildAt(node, idx);
        if (child == null) return;

        if (keyCount(child) == 1) {
            Node left = (idx - 1 >= 0) ? getChildAt(node, idx - 1) : null;
            Node right = (idx + 1 <= 3) ? getChildAt(node, idx + 1) : null;
            if (left != null && keyCount(left) >= 2) {
                rotateRight(node, idx);
            } else if (right != null && keyCount(right) >= 2) {
                rotateLeft(node, idx);
            } else {
                if (left != null) {
                    mergeWithLeft(node, idx - 1);
                    child = getChildAt(node, idx - 1);
                } else if (right != null) {
                    mergeWithRight(node, idx);
                    child = getChildAt(node, idx);
                }
            }
        }

        deleteInternal(child, x);
    }

    public void printTree() {
        if (root == null) {
            System.out.println("(empty)");
            return;
        }

        java.util.ArrayList<java.util.AbstractMap.SimpleEntry<Node, Integer>> list = new java.util.ArrayList<>();
        java.util.ArrayDeque<java.util.AbstractMap.SimpleEntry<Node, Integer>> q = new java.util.ArrayDeque<>();
        q.add(new java.util.AbstractMap.SimpleEntry<>(root, 0));

        while (!q.isEmpty()) {
            java.util.AbstractMap.SimpleEntry<Node, Integer> e = q.removeFirst();
            Node n = e.getKey();
            int lvl = e.getValue();
            list.add(e);

            if (n.ch1 != null) q.addLast(new java.util.AbstractMap.SimpleEntry<>(n.ch1, lvl + 1));
            if (n.ch2 != null) q.addLast(new java.util.AbstractMap.SimpleEntry<>(n.ch2, lvl + 1));
            if (n.ch3 != null) q.addLast(new java.util.AbstractMap.SimpleEntry<>(n.ch3, lvl + 1));
            if (n.ch4 != null) q.addLast(new java.util.AbstractMap.SimpleEntry<>(n.ch4, lvl + 1));
        }

        list.sort((o1, o2) -> Integer.compare(o1.getValue(), o2.getValue()));

        int currentLevel = -1;
        StringBuilder line = new StringBuilder();
        for (java.util.AbstractMap.SimpleEntry<Node, Integer> entry : list) {
            int lvl = entry.getValue();
            if (lvl != currentLevel) {
                if (currentLevel != -1) {
                    System.out.println(line.toString().trim());
                }
                currentLevel = lvl;
                line.setLength(0);
            }
            line.append(entry.getKey().toString()).append(" ");
        }
        if (line.length() > 0) {
            System.out.println(line.toString().trim());
        }
    }
}
