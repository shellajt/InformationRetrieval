import serach.ArticleTitleSearch;
import serach.BM25;
import serach.SkipBigramSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class main {

    private static final File directory = new File(".\\Resources\\Curated");

    public static void main(String[] args) {
        try {
            System.out.println("Please enter a search query. Type exit to quit.");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String searchQuery = br.readLine();
            searchQuery = removePunctuation(searchQuery.toLowerCase());


            // REPL
            while (!searchQuery.equals("exit")) {
                irSearch(directory, searchQuery);

                System.out.println("Please enter a search query. Type exit to quit.");
                searchQuery = br.readLine();
                searchQuery = removePunctuation(searchQuery.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String removePunctuation(String line) {
        return line.replaceAll("\\.", "").replaceAll(",", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll(";", "")
                .replaceAll(":", "").replaceAll("!", "");
    }

    private static void irSearch(File directory, String searchQuery) {
        //Search the titles for each page
        ArticleTitleSearch headerSearch = new ArticleTitleSearch(searchQuery, directory);
        Map<String, Double> headerScores = headerSearch.score();

        //BM25 index search
        BM25 bm = new BM25(searchQuery, directory);
        Map<String, Double> bm25Scores = new HashMap();
        try {
            bm25Scores = bm.score();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Skip Bigram Search
        SkipBigramSearch bigram = new SkipBigramSearch(searchQuery, directory);
        Map<String, Double> bigramScores = new HashMap();
        try {
            bigramScores = bigram.score();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //Consolidate scores from our different methods
        Map<String, Double> finalScores = new HashMap();
        for (String filename : headerScores.keySet()) {
            Double score = headerScores.get(filename) * 3 + bm25Scores.get(filename) + 1.5 * bigramScores.get(filename);
            finalScores.put(filename, score);
        }

        System.out.println(getTopFiveResults(finalScores));
    }

    private static String getTopFiveResults(Map<String, Double> finalScores) {
        StringBuilder sb = new StringBuilder();
        sb.append("We suggest the following five documents, sorted in order of their relevance.");
        sb.append("\r\n\t");

        List<String> usedFiles = new LinkedList();

        for (int i = 0; i < 5; i++) {
            Double maxScore = -1.0;
            String maxFilename = "Error!";
            for (String filename : finalScores.keySet()) {
                Double score = finalScores.get(filename);
                if (score > maxScore && !usedFiles.contains(filename.split(" ")[0])) {
                    maxScore = score;
                    maxFilename = filename.split(" ")[0];
                }
            }
            if (maxFilename.equals("Error!")) return sb.toString();
            usedFiles.add(maxFilename);

            sb.append(maxFilename);
            sb.append(".txt with a score of ");
            sb.append(String.format("%.3f", maxScore));
            sb.append("\r\n\t");
        }
        return sb.toString();
    }
}
