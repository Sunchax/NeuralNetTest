import java.util.ArrayList;

/**
 * Created by Choffe on 2016-12-09.
 */
public class Image {
    private ArrayList<Double> image = new ArrayList<>();
    private int facit;

    public Image(ArrayList<Double> image, int facit){
        this.image = image;
        this.facit = facit;
    }

    public ArrayList<Double> getImage(){
        return image;
    }

    public int getFacit(){
        return facit;
    }
}
