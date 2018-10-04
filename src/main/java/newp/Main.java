package newp;

import javafx.util.Pair;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

/**
 * Created by NATA on 04.10.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Graph graph = (ReadFile("1.txt"));
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("firstSerialization.xml")));
        encoder.writeObject(graph);
        encoder.close();
        System.out.println("Сериализованная структура графа в формате XML в файле firstSerialization.xml");
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("firstSerialization.xml")));
        Graph graph1 = (Graph) decoder.readObject();
        graph1.writeGraph("firstSerializationOutput.txt");
        decoder.close();
    }

    static Graph ReadFile(String nameFile) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile)));
        String s;
        ArrayList<Node> nodes = new ArrayList<>();
        TreeMap<Integer, ArrayList<Node>> graph = new TreeMap<Integer, ArrayList<Node>>();
        List<Integer> orderOfNode = new ArrayList<>();
        int numberOfLine = 0;
        while ((s = in.readLine()) != null) {
            numberOfLine++;
            String[] m = s.split("\\s");
            if (m.length != 3) {
                throw new ExceptionInInitializerError("ne to in " + numberOfLine);
            }
            int node1 = Integer.parseInt(m[1]);
            int node2 = Integer.parseInt(m[0]);
            int node3 = Integer.parseInt(m[2]);
            Node nodeWithEdge = new Node(node2, node3);
            if (!graph.containsKey(node1)) {
                graph.put(node1, new ArrayList<>());
                graph.get(node1).add(nodeWithEdge);
                orderOfNode.add(node1);
            } else {
                nodes = graph.get(node1);
                if (nodes.contains(nodeWithEdge))
                    throw new Exception("povtor in " + numberOfLine);
                else {
                    nodes.add(nodeWithEdge);
                    Collections.sort(nodes);
                    graph.put(node1, nodes);
                }
            }

        }
        return new Graph(graph);
    }
}
