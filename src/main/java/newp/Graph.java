package newp;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by NATA on 04.10.2018.
 */

@XmlRootElement(name = "graph")
public class Graph {
   private TreeMap<Integer,ArrayList<Node>> graph=new TreeMap<Integer,ArrayList<Node>>();

    public void setGraph(TreeMap<Integer, ArrayList<Node>> graph) {
        this.graph = graph;
    }

    public Graph(TreeMap<Integer, ArrayList<Node>> graph) {
        this.graph = graph;
    }
    public Graph(){}

    public TreeMap<Integer, ArrayList<Node>> getGraph() {
        return graph;
    }

    public void add(Integer node1,ArrayList<Node> node23) {
        if (this.graph == null) {
            this.graph = new TreeMap<Integer,ArrayList<Node>>();
        }
        this.graph.put(node1,node23);
    }
    public void putEntry(Integer key, ArrayList<Node> value){
        graph.put(key, value);
    }
    public void writeGraph(String nameOfFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nameOfFile)))) {
            for (Integer node : graph.keySet()) {
                StringBuilder resultLine = new StringBuilder();
                resultLine.append(node.toString()).append(" :");
                for (Node nodeWithEdge : graph.get(node)) {
                    resultLine.append(" " + nodeWithEdge.toString() + ",");
                }
                if (resultLine.charAt(resultLine.length() - 1) == ',') {
                    resultLine.deleteCharAt(resultLine.length() - 1);
                } else {
                    resultLine.append(" _");
                }
                out.println(resultLine);
            }
            out.flush();
        }
    }

}
