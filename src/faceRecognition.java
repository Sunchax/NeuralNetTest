/**
 * Created by Choffe on 2016-10-02.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class faceRecognition {
    /* 1: Happy, 2: Sad, 3: Mischievous, 4: Mad. */
    static ImageHandler imageHandler = new ImageHandler();
    static neuralNet net = new neuralNet();

    public static void main(String[] args) throws IOException {


        if(args.length < 2){
            System.out.println("Error, to few arguments");
            System.exit(0);
        }
        double normalisation = 31;
        //String str = "23 34 543 43 34 43521 1";

  //      for(int j = 0; j < args.length; j++){
 //           System.out.println(args[j]);
 //       }

        Scanner imageScan = new Scanner(new FileReader(args[0]));
        Scanner facitScan = new Scanner (new FileReader(args[1]));
        Scanner testScan = new Scanner(new FileReader(args[2]));


        int nrOfInputNeurons = 20*20;
        int nrOfOutputNeurons = 4;

        net.SetUp(nrOfInputNeurons,nrOfOutputNeurons);



        ArrayList<ArrayList> listOfImages = new ArrayList<>();

        //Makes a array with the training images.
        while (imageScan.hasNextLine()) {

            String data = imageScan.nextLine();
            if(!data.contains("#") && !(data.length() < 1)) {

                ArrayList<Double> image = new ArrayList<>();
                while (!data.contains("Image") && data.length() > 0) {
                    String d[] = data.split(" ", 0);
                    for (int i = 0; i < d.length; i++) {
                        double pixel = (double)Integer.parseInt(d[i]);
                        image.add((pixel / normalisation));
                    }
                    if (imageScan.hasNextLine()) {
                        data = imageScan.nextLine();
                    } else {
                        break;
                    }
                }
                if (image.size() > 0) {
                    listOfImages.add(image);
                }
            }
        }
        imageScan.close();
     //   System.out.println("image is scanned");

        ArrayList<Integer> facit = new ArrayList<>();

        //Makes a array with facit.
        while(facitScan.hasNext()){
            String data = facitScan.nextLine();

            if(!data.contains("#") && !(data.length() < 2)){

                String d[] = data.split(" ");

                int intData = Integer.parseInt(d[1]);
                facit.add(intData);
            }
        }
        facitScan.close();

        ArrayList<ArrayList> testImages = new ArrayList<>();
        while (testScan.hasNextLine()) {

            String data = testScan.nextLine();
            if(!data.contains("#") && !(data.length() < 1)) {

                ArrayList<Double> image = new ArrayList<>();
                while (!data.contains("Image") && data.length() > 0) {
                    String d[] = data.split(" ", 0);

                    for (int i = 0; i < d.length; i++) {
                        double pixel = (double)Integer.parseInt(d[i]);
                        image.add((pixel / normalisation));
                    }
                    if (testScan.hasNextLine()) {
                        data = testScan.nextLine();
                    } else {
                        break;
                    }
                }
                if (image.size() > 0) {
                    testImages.add(image);
                }
            }
        }

        imageHandler.setUpImages(listOfImages, facit);
        imageHandler.setUpTraingSet();

     //   System.out.printf("test is scanned\n");
/*
        trainNetwork(imageHandler.getImages());
        trainNetwork(imageHandler.getImages());
        trainNetwork(imageHandler.getImages());
        trainNetwork(imageHandler.getImages());
        */
        validate();
        faceRecognition.testNetwork(testImages);


    }

    static double trainNetwork(ArrayList<Image> images){

        double stdErr = 1;

        for(int i = 0; i < images.size(); i++ ) {
            stdErr = net.train(images.get(i));
        }
        return stdErr;
    }

    static void testNetwork(ArrayList<ArrayList> images){
        int result;
        for(int i = 0; i < images.size(); i++){
            result = net.test(images.get(i));
            System.out.println("Image" + (i+1) + ": " + result);
        }
    }

    static void validate(){
        double sucessRate = 0.60;
        int sucessesInARow = 0;
        double stdErr = 1;
        int i = 0;
        ArrayList<Image> images = imageHandler.getTrainImages();
        while((sucessesInARow < 7) || (Math.abs(stdErr) > 0.5)) {
            i++;
            Collections.shuffle(images);
            stdErr = trainNetwork(images);

             if((tester(imageHandler.getTestImages()) > sucessRate)){
                 sucessesInARow++;
             }else{
                 sucessesInARow = 0;

             }
            if(i >300){
                break;
            }
        }
    /*    System.out.println("Success: " +sucessesInARow);
        System.out.println("stdErr: " + stdErr);
        System.out.println("number of validations: " + i);
   */ }

    static double tester(ArrayList<Image> images){
        double successRate;

        double success = 0;

        Image image;

        for(int i = 0; i < images.size(); i++){
            image = images.get(i);
            if(net.test(image.getImage()) == image.getFacit()){
                success++;
            }
        }

        successRate = (success / images.size());
     //   System.out.println("success rate: " + successRate);
        return successRate;
    }
}
