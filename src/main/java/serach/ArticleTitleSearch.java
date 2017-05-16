package serach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jesse Shellabarger on 5/1/2017.
 */
public class ArticleTitleSearch {

    String searchQuery;
    List<String> queryWords;
    Map<String, Double> scores;
    File[] filesInDirectory;

    public ArticleTitleSearch(String searchQuery, File directory) {
        this.searchQuery = searchQuery;
        queryWords = Arrays.asList(searchQuery.split(" "));
        this.scores = new HashMap();
        this.filesInDirectory = directory.listFiles();
    }

    public Map<String, Double> score() {
        for (File f : filesInDirectory) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = br.readLine();
                String[] wordsInLine = line.split(" ");
                Double count = 0.0;
                for (String word : wordsInLine) {
                    if (queryWords.contains(word)) count++;
                }
                scores.put(f.getName(), count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return scores;
    }
}
