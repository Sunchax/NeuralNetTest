import java.util.ArrayList;

import static java.lang.Math.E;

/**
 * Created by Choffe on 2016-09-26.
 */
public class neuron {
    double value = 0;

    public neuron(){
    }

    public double returnValue(){
        return value;
    }

    public double getValue() {
        return (1/(1+(Math.pow( Math.E, (-value)))));
    }

    public void input(double value){
        this.value += value;
    }

    public void reset(){
        value = 0;
    }
}

