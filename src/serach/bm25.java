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
 * Created by Jesse Shellabarger on 4/30/2017.
 */
public class bm25 {

    String searchQuery;
    List<String> queryWords;
    File[] filesInDirectory;
    Map<String, Map<String, Integer>> amountQueryWordsInDoc;
    Map<String, Integer> numDocsContainingWord;
    Map<String, Long> fileLength = new HashMap<>();
    Map<String, Double> scores = new HashMap<>();
    private static float k = 1.2f;
    private static float b = 2.0f;
    private double averageLength;
    int numDocs;

    public bm25(String searchQuery) {
        this.searchQuery = searchQuery;
        queryWords = Arrays.asList(searchQuery.split(" "));
        this.numDocsContainingWord = new HashMap<>();
        for (String word : queryWords) {
            numDocsContainingWord.put(word, 0);
        }

        this.amountQueryWordsInDoc = new HashMap<>();

        //TODO: Fix this
        File directory = new File("C:/Users/Jesse Shellabarger/Documents/Github/InformationRetrieval/Resources/TextConverted");
        filesInDirectory = directory.listFiles();
        for (File file : filesInDirectory) {
            Map<String, Integer> innerMap = new HashMap<>();
            for (String word : queryWords) {
                innerMap.put(word, 0);
            }
            this.amountQueryWordsInDoc.put(file.getName(), innerMap);
        }
    }

    public String score() throws IOException {

        this.numDocs = filesInDirectory.length;
        for (File file : filesInDirectory) {
            this.fileLength.put(file.getName(), file.length());

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                line = removePunctuation(line);

                String[] wordsInText = line.split(" ");

                for (String word : wordsInText) {
                    if (queryWords.contains(word)) {
                        Map<String, Integer> innerMap = this.amountQueryWordsInDoc.get(file.getName());

                        if (innerMap.get(word) == 0) {
                            numDocsContainingWord.put(word, numDocsContainingWord.get(word) + 1);
                        }
                        innerMap.put(word, innerMap.get(word) + 1);
                    }

                }
                line = br.readLine();
            }
        }
        long totalLength = 0L;
        for (String word : fileLength.keySet()) {
            totalLength += fileLength.get(word);
        }
        averageLength = totalLength / numDocs;

        for (File file : filesInDirectory) {
            double score = 0.0;
            for (String query : queryWords) {
                double idf = idf(query);

                int numTimesInDoc = this.amountQueryWordsInDoc.get(file.getName()).get(query);
                long lengthOfDoc = fileLength.get(file.getName());

                double secondPart = (numTimesInDoc * (k + 1)) / (numTimesInDoc + k * (1 - b + b * lengthOfDoc/averageLength));

                System.out.println(String.format("FileName: %s\tSecondpart: %f\tFrequency: %s\tDoc Length: %d", file.getName(), secondPart, numTimesInDoc, lengthOfDoc));

                score += Math.abs(idf) * secondPart;
            }
            this.scores.put(file.getName(), score);
        }

        Double max = -99999999.0;
        String maxFileName = "Query words not found in any documents / Something Exploded";
        for (String fileName: scores.keySet()) {
            Double score = scores.get(fileName);
            if (score > max) {
                max = score;
                maxFileName = fileName;
            }
        }

        return maxFileName.split(" ")[0];
    }

    private String removePunctuation(String line) {
        return line.replaceAll("\\.", "").replaceAll(",", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll(";", "")
                .replaceAll(":", "").replaceAll("!", "");
    }

    private double idf(String query) {
        int nOfQ = this.numDocsContainingWord.get(query);

        return Math.log10((numDocs - nOfQ + 0.5) / (nOfQ + 0.5));
    }
}
