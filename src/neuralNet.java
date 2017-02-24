import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;

/**
 * Created by Choffe on 2016-09-26.
 */
public class neuralNet {

//    ArrayList<Double> images = new ArrayList<>();
    ArrayList<neuron> input = new ArrayList<>();
    ArrayList<neuron> hidden = new ArrayList<>();
    ArrayList<neuron> output = new ArrayList<>();
    ArrayList<edge> leftEdges = new ArrayList<>();
    ArrayList<edge> rightEdges = new ArrayList<>();
   // private int facit;
    private double weight = 0;

    public neuralNet() {
    }

    public void InputImage(ArrayList<Double> images){
  //      this.images = images;
        int i = 0;
        for(neuron n : input){
            n.input(images.get(i));
            i++;
        }
    }

    public void SetUp(int input, int output){
        setInputLevel(input);
        setOutput(output);
        setUpedges();
    }

    public void setInputLevel(int nrOfNeurons) {
        for (int i = 0; i < nrOfNeurons; i++) {
            neuron n = new neuron();
            input.add(n);
        }
    }

    public void setOutput(int nrOfNeurons) {
        for (int i = 0; i < nrOfNeurons; i++) {
            neuron n = new neuron();
            output.add(n);
        }
    }

    public void setUpedges(){
        int i= 0;
        int j = 0;
        for(neuron n2 : output) {
            j++;
            for( neuron n : input){
                edge e = new edge(n, n2, this.weight);
                leftEdges.add(e);
                i++;
            }
        }
 //       System.out.println("number of edges: " + i);
    }


   /* public void propegate(){
        for(edge e: leftEdges){
            e.propegate();
        }
    }*/
    public void reset(){
        for(neuron n: input){
            n.reset();
        }
        for(neuron n: output){
            n.reset();
        }
    }

    public void learn(double error){

    }

    public double train(Image image) {

        int lenght = image.getImage().size();
        int i = 0;
        for(neuron n : input){
            n.reset();
            n.input(image.getImage().get(i));
            i++;
        }

        for (edge e : leftEdges) {
            e.propegate();
        }
        int facit = image.getFacit();
        int a = 0;
        if(facit == 1) {
            a = 1;
        }
        int b = 0;
        if(facit == 2) {
            b = 1;
        }
        int c = 0;
        if(facit == 3) {
            c = 1;
        }
        int d = 0;
        if(facit == 4) {
            d = 1;
        }

        ArrayList<Double> stdErr = new ArrayList<>();
        for (i = 0; i < 400; i++) {
            stdErr.add(leftEdges.get(i).bPropegate(a));
        }
        for  (i = 400;i < 800; i++) {
            stdErr.add(leftEdges.get(i).bPropegate(b));
        }
        for (i = 800; i < 1200; i++){
            stdErr.add(leftEdges.get(i).bPropegate(c));
        }
        for(i = 1200; i < 1600; i++){
            stdErr.add(leftEdges.get(i).bPropegate(d));
        }
        this.reset();

        return medianStdErr(stdErr);
    }

    public int test(ArrayList<Double> image){
        int i = 0;

        for(neuron n: input){
            n.reset();
            n.input(image.get(i));
            i++;
        }
        for (edge e : leftEdges) {
            e.propegate();
        }

        return getResult();
    }

    public int getResult(){
        double value = 0;
        int i = 1;
        int j = 0;
        for(neuron e: output){
            if(value < e.getValue()){
                value = e.getValue();
                j = i;
            }
            i++;
            e.reset();
        }
        for(neuron n: input){
            n.reset();
        }
        return j;
    }

    /*
     * Used is one is to use hidden layers.
     */

    public void setHiddenLevel(int nrOfNeurons) {
        for (int i = 0; i < nrOfNeurons; i++) {
            neuron n = new neuron();
            hidden.add(n);
        }
    }

    public void setUpNetwork() {
        for (neuron n : input) {
            for (neuron n2 : hidden) {
                edge e = new edge(n, n2, this.weight);
                leftEdges.add(e);

            }
        }

        for (neuron n3 : hidden) {
            for (neuron n4 : output) {
                edge e2 = new edge(n3, n4, this.weight);
                rightEdges.add(e2);
            }
        }
    }

    public double medianStdErr(ArrayList<Double> stdErr){
        double sum = 0;
        for(int i = 0; i < stdErr.size(); i++){
            sum += stdErr.get(i);
        }
        return (sum / stdErr.size());
    }
}