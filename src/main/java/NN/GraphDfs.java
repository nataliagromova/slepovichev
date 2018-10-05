package NN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NATA on 05.10.2018.
 */
public class GraphDfs {
    private Graph graph;

    public GraphDfs(Graph graph) {
        this.graph = graph;
    }

    public String generateFunction() throws Exception {
        if (graph == null) {
            throw new Exception("Граф не найден");
        }
        if (cycleChecker()) {
            throw new Exception("Граф не ацикличен");
        }

        Graph reverseGraph = graph.getReverseGraph();
        List<Integer> sinks = findSinks(reverseGraph);

        List<String> result = new ArrayList<>();

        for (Integer sink : sinks) {
            result.add(generateFunctionFromNode(graph, sink));
        }
        StringBuilder str = new StringBuilder();
        str.append("graph - ");
        for (String s : result) {
            str.append(s);
            str.append(",");
        }
        str.deleteCharAt(str.length() - 1);
       // str.append(")");
        return str.toString();
    }

    private boolean cycleChecker() {
        boolean result = false;
        Map<Integer, Integer> label = new HashMap<>();

        for (Integer node : graph.getGraph().keySet()) {
            label.put(node, 0);
        }
        for (Integer node : graph.getGraph().keySet()) {
            if (label.get(node) == 0) {
                result = findCycle(node, label, graph);
                if (result) {
                    break;
                }
            }
        }
        return result;
    }

    private boolean findCycle(Integer node, Map<Integer, Integer> label, Graph graph) {
        boolean result = false;
        label.put(node, 1);
        for (Node nodeWithEdge : graph.getGraph().get(node)) {
            Integer labelOfNode = label.get(nodeWithEdge.getNode1());
            if (labelOfNode == 0) {
                result = findCycle(nodeWithEdge.getNode1(), label, graph);
                if (result) {
                    break;
                }
            }
            if (labelOfNode == 1) {
                result = true;
                break;
            }
        }
        label.put(node, 2);
        return result;
    }

    private List<Integer> findSinks(Graph reversegraph) {
        List<Integer> sinks = new ArrayList<>();
        for (Integer node : reversegraph.getGraph().keySet()) {
            if (reversegraph.getGraph().get(node).size() == 0) {
                sinks.add(node);
            }
        }
        return sinks;
    }

    private String generateFunctionFromNode(Graph graph, Integer sink) {
        StringBuilder result = new StringBuilder(sink.toString());
        if (graph.getGraph().get(sink).size() != 0) {
            result.append("(");
            for (Node nodeWithEdges : graph.getGraph().get(sink)) {
                result.append(generateFunctionFromNode(graph, nodeWithEdges.getNode1()));
                result.append(",");
            }
            result.deleteCharAt(result.length() - 1);
            result.append(")");
        } else {
            result.append("()");
        }
        return result.toString();
    }
}
