import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Choffe on 2016-12-09.
 */
public class ImageHandler {

    private ArrayList<Image> Images = new ArrayList<>();
    private ArrayList<Image> trainImages = new ArrayList<>();
    private ArrayList<Image> testImages = new ArrayList<>();

    public ImageHandler(){
    }

    public void setUpImages(ArrayList<ArrayList> images, ArrayList<Integer> facits){
        if(images.size() != facits.size()){
            System.out.println("Error, images and facit size is not the same");
        }
        for(int i = 0; i < images.size(); i++){
            Images.add(new Image(images.get(i), facits.get(i)));
        }
    }
    public ArrayList<Image> getImages(){
        return Images;
    }

    public ArrayList<Image> getTrainImages(){
        return trainImages;
    }

    public ArrayList<Image> getTestImages(){
        return testImages;
    }

    public void setUpTraingSet(){
        Collections.shuffle(Images);
        int i;
        for(i = 0; i < (Images.size() * 0.90);i++ ){
            trainImages.add(Images.get(i));
        }

        for(int j = i; j < Images.size(); j++){
            testImages.add(Images.get(j));
        }
    }

}
