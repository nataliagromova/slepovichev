package NN;

/**
 * Created by NATA on 04.10.2018.
 */
public class Node implements Comparable<Node> {
    private int node1;
    private int node2;

    public Node() {
    }

    public Node(int node1, int node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public int getNode1() {
        return node1;
    }

    public void setNode1(int node1) {
        this.node1 = node1;
    }

    @Override
    public String toString() {
        return node1+" "+node2;
    }

    public int getNode2() {
        return node2;
    }

    public void setNode2(int node2) {
        this.node2 = node2;
    }

    @Override
    public int compareTo(Node o) {
        if (o.node2 < node2)
            return 1;
        else return -1;
    }
}
