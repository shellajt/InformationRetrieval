package serach;

import java.io.*;
import java.util.*;

/**
 * Created by Jesse Shellabarger on 5/2/2017.
 */
public class SkipBigramSearch {

    private Map<String, Double> scores;
    File[] filesInDirectory;
    List<String> queryWords;
    List<Bigram> searchBigrams;

    public SkipBigramSearch(String searchQuery, File directory) {
        filesInDirectory = directory.listFiles();
        scores = new HashMap();
        for (File file : filesInDirectory) {
            this.scores.put(file.getName(), 0.0);
        }

        searchBigrams = new LinkedList();
        queryWords = Arrays.asList(searchQuery.split(" "));
        for (int i = 0; i < queryWords.size() - 1; i++) {
            searchBigrams.add(new Bigram(queryWords.get(i), queryWords.get(i+1)));
        }
    }

    public Map<String, Double> score() throws IOException {
        if (queryWords.size() == 1 || queryWords.size() == 0) return scores;

        for (File file : filesInDirectory) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            String[] leftOverWords = new String[2];
            leftOverWords[0] = "";
            leftOverWords[1] = "";
            while (line != null) {
                String[] words = concat(leftOverWords, line.split(" "));
                for (int i = 0; i < words.length -2; i++) {
                    if (!queryWords.contains(words[i])) continue;
                    if (checkBigram(words[i], words[i+1]) || checkBigram(words[i], words[i+2])) {
                        scores.put(file.getName(), scores.get(file.getName())+1);
                    }
                }
                leftOverWords[0] = words[words.length-2];
                leftOverWords[1] = words[words.length-1];

                line = reader.readLine();
            }
        }

        return scores;
    }

    private boolean checkBigram(String firstWord, String secondWord) {
        for (Bigram b : searchBigrams) {
            if (b.firstWord.equals(firstWord) && b.secondWord.equals(secondWord)) return true;
        }
        return false;
    }

    private String[] concat(String[] a, String[] b) {
        int aLen = a.length;
        int bLen = b.length;
        String[] c = new String[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private class Bigram {
        public String firstWord;
        public String secondWord;

        public Bigram(String word1, String word2) {
            this.firstWord = word1;
            this.secondWord = word2;
        }

    }
}
