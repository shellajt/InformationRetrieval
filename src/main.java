import serach.ArticleTitleSearch;
import serach.bm25;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class main {

    public static void main(String[] args) {
        File directory = new File(".\\Resources\\TextConverted");

        while (true) {
            System.out.println("Please enter a search query. Type exit to quit.");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String searchQuery = br.readLine();
                if (searchQuery.equals("exit")) exit();
                irSearch(directory, searchQuery);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void exit() {
        System.out.println("Exiting.");
        System.exit(0);
    }

    private static void irSearch(File directory, String searchQuery) {
        //Search the titles for each page
        ArticleTitleSearch headerSearch = new ArticleTitleSearch(searchQuery, directory);
        Map<String, Double> headerScores = headerSearch.score();

        //BM25 index search
        bm25 bm = new bm25(searchQuery, directory);
        Map<String, Double> bm25Scores = new HashMap();
        try {
            bm25Scores = bm.score();
        } catch (IOException e){
            e.printStackTrace();
        }

        //Consolidate scores from our different methods
        Map<String, Double> finalScores = new HashMap();
        for (String filename : headerScores.keySet()) {
            Double score = headerScores.get(filename) * 2 + bm25Scores.get(filename);
            finalScores.put(filename, score);
        }

        //Find the max and print it out
        Double maxScore = 0.0;
        String maxFilename = "Error!";
        for (String filename : finalScores.keySet()) {
            Double score = finalScores.get(filename);
            if (score > maxScore) {
                maxScore = score;
                maxFilename = filename.split(" ")[0];
            }
        }
        String output = String.format("We suggest the file %s.txt. Our system gave it a score of %.3f.", maxFilename, maxScore);
        System.out.println(output);
    }
}
