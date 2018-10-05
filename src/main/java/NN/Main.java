package NN;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("1: Создание графа\n" +
                "2: Создание функции по графу \n"
                );

        while (true) {
            int n = Integer.parseInt(scanner.nextLine());
            switch (n) {
                case 1:
                    Step1();
                    break;
                case 2:
                    Step2();
                    break;
                default:
                    break;
            }

        }


    }

    public static void Step1() throws Exception {
        System.out.println("Введите имя файла с описанием графа без .txt :");
        String name = scanner.nextLine();
        Graph graph = (ReadFile(name + ".txt"));
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("firstSerialization.xml")));
        encoder.writeObject(graph);
        encoder.close();
        System.out.println("Сериализованная структура графа в формате XML в файле firstSerialization.xml");
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("firstSerialization.xml")));
        Graph graph1 = (Graph) decoder.readObject();
        graph1.writeGraph("firstSerializationOutput.txt");
        decoder.close();
        System.out.println("Результат десериализации в файле firstSerializationOutput.txt");
    }

    public static void Step2() throws Exception {
        System.out.print("Текстовый файл c ориентированным ациклическим графом с именованными вершинами и линейно упорядоченными дугами: ");
        String nameFile = scanner.nextLine();
        Graph graph = ReadFile(nameFile);
//        graph.sortGraph();
        GraphDfs functionsGeneratorOfGraph = new GraphDfs(graph);
        String result = functionsGeneratorOfGraph.generateFunction();
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("secondOutput.txt")))) {
            out.println(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Результат преобразования графа в имя функции в файле secondOutput.txt");
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
            if (!graph.containsKey(node2))
                graph.put(node2, new ArrayList<>());
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
