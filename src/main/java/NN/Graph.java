package NN;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by NATA on 04.10.2018.
 */

@XmlRootElement(name = "graph")
public class Graph {
    private TreeMap<Integer, ArrayList<Node>> graph = new TreeMap<Integer, ArrayList<Node>>();

    Graph(TreeMap<Integer, ArrayList<Node>> graph) {
        this.graph = graph;
    }

    public Graph() {
    }

    public void setGraph(TreeMap<Integer, ArrayList<Node>> graph) {
        this.graph = graph;
    }


    public TreeMap<Integer, ArrayList<Node>> getGraph() {
        return graph;
    }

    public void add(Integer node1, ArrayList<Node> node23) {
        if (this.graph == null) {
            this.graph = new TreeMap<Integer, ArrayList<Node>>();
        }
        this.graph.put(node1, node23);
    }

    public void writeGraph(String nameOfFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nameOfFile)))) {
            for (Integer node : graph.keySet()) {
                StringBuilder resultLine = new StringBuilder();
                for (Node nodeWithEdge : graph.get(node)) {
                    resultLine.append(nodeWithEdge.getNode1()).append(" ").append(node).append(" ").append(nodeWithEdge.getNode2()).append('\n');
                }
                out.print(resultLine);
            }
            out.flush();
        }
    }
    public Graph getReverseGraph() {
        TreeMap<Integer, ArrayList<Node>> reverseList = new TreeMap<>();
        for (Integer node : graph.keySet()) {
            if (!reverseList.containsKey(node)) {
                reverseList.put(node, new ArrayList<Node>());
            }
            for (Node nodeWithEdge : graph.get(node)) {
                if (!reverseList.containsKey(nodeWithEdge.getNode1())) {
                    reverseList.put(nodeWithEdge.getNode1(), new ArrayList<Node>());
                }
                reverseList.get(nodeWithEdge.getNode1()).add(new Node(node, nodeWithEdge.getNode2()));
            }
        }
        for (Integer node : reverseList.keySet()) {
            Collections.sort(reverseList.get(node));
        }
        return new Graph(reverseList);
    }


}
