package neuralNetwork;

import java.io.Serializable;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class Neuron implements Serializable {
    private double value;
    private DoubleFunction<Double> function;

    public Neuron() {
    }

    public Neuron(double value, DoubleFunction<Double> function) {
        this.value = value;
        this.function = function;
    }

    public double applyFunction() {
        return this.function.apply(value);
    }

    public static Neuron linearNeuron() {
        return new Neuron(0.0, (DoubleFunction<Double> & Serializable) (value) -> value);
    }

    public static Neuron sigmoidNeuron() {
        return new Neuron(0.0, (DoubleFunction<Double> & Serializable) (value) -> 1.0 / (1.0 + Math.exp(-value)));
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Neuron neuron = (Neuron) o;

        if (Double.compare(neuron.value, value) != 0) return false;
        return function != null ? function.equals(neuron.function) : neuron.function == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (function != null ? function.hashCode() : 0);
        return result;
    }

    public DoubleFunction<Double> getFunction() {
        return function;
    }

    public void setFunction(DoubleFunction<Double> function) {
        this.function = function;
    }
}
