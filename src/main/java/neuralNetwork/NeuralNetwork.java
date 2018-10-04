package neuralNetwork;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralNetwork implements Serializable {
    private int n;
    private int m;
    private int layersSize;
    private List<Layer> layers;
    private List<Double[][]> weights;

    public NeuralNetwork(){

    }

    public NeuralNetwork(List<Layer> layers, List<Double[][]> weights) {
        this.layersSize = layers.size();
        this.n = layers.get(0).getNeuronsSize();
        this.m = layers.get(this.layersSize - 1).getNeuronsSize();
        this.layers = layers;
        this.weights = weights;
    }

    public void clean() {
        for (int i = 0; i < layersSize; i++) {
            for (Neuron neuron : layers.get(i).getNeurons()) {
                neuron.setValue(0.0);
            }
        }
    }

    public List<Double> apply(List<Double> inputValues) {
        List<Double> result = new ArrayList<>();
        clean();
        int id = 0;
        for (Neuron neuron : layers.get(0).getNeurons()) {
            neuron.setValue(inputValues.get(id));
            id++;
        }

        for (int i = 0; i < layersSize - 1; i++) {
            int idOfNeuron = 0;
            Double[][] weightsOfLayer = weights.get(i);
            for (Neuron neuron : layers.get(i).getNeurons()) {
                double value = neuron.applyFunction();
                for (int j = 0; j < weightsOfLayer[idOfNeuron].length; j++) {
                    Neuron tmpNeuron = layers.get(i + 1).getNeurons().get(j);
                    tmpNeuron.setValue(tmpNeuron.getValue() + value * weightsOfLayer[idOfNeuron][j]);
                }
                idOfNeuron++;
            }
        }
        List<Neuron> lastLayer = this.layers.get(layersSize - 1).getNeurons();
        for (Neuron neuron : lastLayer) {
            result.add(neuron.applyFunction());
        }
        return result;
    }

    public Double backPropagation(List<Pair<List<Double>, List<Double>>> values) {
        Double result = 0.0;
        Double ni = 0.7;
        for (Pair<List<Double>, List<Double>> value : values) {
            List<Double> resultOfNeuralNetwork = this.apply(value.getKey());

            int id = 0;
            Map<Neuron, Double> dels = new HashMap<>();

            for (Double val : resultOfNeuralNetwork) {
                double del = val * (1 - val) * (value.getValue().get(id) - val);
                dels.put(layers.get(layersSize - 1).getNeurons().get(id), del);
            }
            for (int j = layersSize - 2; j >= 0; j--) {
                List<Neuron> neurons = this.layers.get(j).getNeurons();
                Double[][] weightOfLayer = this.weights.get(j);
                int idOfNeuron = 0;
                for (Neuron neuron : neurons) {
                    double del = neuron.applyFunction() * (1 - neuron.applyFunction());
                    double sum = 0.0;
                    for (int jj = 0; jj < weightOfLayer[idOfNeuron].length; jj++) {
                        sum += weightOfLayer[idOfNeuron][jj] * dels.get(this.layers.get(j + 1).getNeurons().get(jj));
                    }
                    del *= sum;
                    dels.put(neuron, del);
                    for (int jj = 0; jj < weightOfLayer[idOfNeuron].length; jj++) {
                        double grad = neuron.applyFunction() * dels.get(this.layers.get(j + 1).getNeurons().get(jj));
                        weightOfLayer[idOfNeuron][jj] += ni * grad;
                    }
                    idOfNeuron++;
                }

            }
        }
        for (Pair<List<Double>, List<Double>> value : values) {
            List<Double> resultOfNeuralNetwork = this.apply(value.getKey());
            Double error = 0.0;
            int id = 0;
            for (Double val : resultOfNeuralNetwork) {
                error += (value.getValue().get(id) - val) * (value.getValue().get(id) - val);
                id++;
            }
            result += error / 2.0;
        }

        return result / values.size();
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getLayersSize() {
        return layersSize;
    }

    public void setLayersSize(int layersSize) {
        this.layersSize = layersSize;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public List<Double[][]> getWeights() {
        return weights;
    }

    public void setWeights(List<Double[][]> weights) {
        this.weights = weights;
    }
}
