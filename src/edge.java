/**
 * Created by Choffe on 2016-09-26.
 */


public class edge {
    double weight;
    double changeRate = 0.2;

    neuron n1;
    neuron n2;

    public edge(neuron n1, neuron n2, double weight){
        this.weight = weight;
        this.n1 = n1;
        this.n2 = n2;
    }

    public void reset(){
        n1.reset();
        n2.reset();
    }

    public void propegate(){
      //  n1.input(input);
        n2.input(weight * n1.returnValue());
    }

    public double bPropegate(double expected){
        double error = expected - n2.getValue();
        weight += (changeRate * n1.returnValue() * error);
        return error;
    }

    public double getWeight(){
        return weight;
    }
}
