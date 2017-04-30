import serach.bm25;

import java.io.IOException;

/**
 * Created by Jesse Shellabarger on 4/30/2017.
 */
public class main {

    public static void main(String[] args) {
        bm25 bm = new bm25("Washington");
        try {
            System.out.println(String.format("Suggested document is: %s", bm.score()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
