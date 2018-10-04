package neuralNetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Layer implements Serializable{
    private int neuronsSize;
    private List<Neuron> neurons;

    public Layer(List<Neuron> neurons){
        this.neuronsSize = neurons.size();
        this.neurons = neurons;
    }
    public Layer(){
        this.neurons = new ArrayList<>();
    }

    public void addNeuron(Neuron neuron){
        neurons.add(neuron);
        setNeuronsSize();
    }

    private void setNeuronsSize() {
        this.neuronsSize = neurons.size();
    }

    public int getNeuronsSize(){
        return this.neuronsSize;
    }

    public List<Neuron> getNeurons(){
        return this.neurons;
    }

    public void setNeuronsSize(int neuronsSize) {
        this.neuronsSize = neuronsSize;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }
}
