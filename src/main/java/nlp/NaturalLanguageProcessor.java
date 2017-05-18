package nlp;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jesse Shellabarger on 5/16/2017.
 */
public class NaturalLanguageProcessor {

    Document lincolnDoc;
    List<SentenceParse> parses = new LinkedList();

    public NaturalLanguageProcessor() throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(".\\Resources\\NlpDocs\\Lincoln.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        sb.append(line+"\n");

        while (line != null) {
            sb.append(line+"\n");
            line = reader.readLine();
        }

        lincolnDoc = new Document(sb.toString());
        for (Sentence sent : lincolnDoc.sentences()) {
            if (sent.toString().contains("-")) continue;

            SentenceParse parse = new SentenceParse();
            List<String> dependencies = Arrays.asList(sent.dependencyGraph().toList().split("\n"));
            for (String dep : dependencies) {
                System.out.println(dep);
                if (dep.startsWith("nsubj")) {
                    int lastDash = dep.lastIndexOf("-");
                    //System.out.println(lastDash);
                    int lastParen = dep.lastIndexOf(")");
                    //System.out.println(lastParen);
                    String numberString = dep.substring(lastDash+1, lastParen);
                    //System.out.println(numberString);
                    int num = Integer.parseInt(numberString)-1;
                    //System.out.println(num);
                    parse.subject = sent.lemma(num);
                    //System.out.println(parse.subject);

                    int firstDash = dep.indexOf("-");
                    int comma = dep.indexOf(",");
                    String numString = dep.substring(firstDash+1, comma);
                    int number = Integer.parseInt(numString)-1;
                    parse.verb = sent.lemma(number);

                } else if (dep.startsWith("dobj")) {
                    int lastDash = dep.lastIndexOf("-");
                    int lastParen = dep.lastIndexOf(")");
                    String numberString = dep.substring(lastDash+1, lastParen);
                    int num = Integer.parseInt(numberString)-1;
                    parse.object = sent.lemma(num);
                }
            }
            parses.add(parse);
        }
    }

    public boolean analyze(SentenceParse queryData) {
        for (SentenceParse parse : parses) {
            if (queryData.equals(parse)) return true;
        }

        return false;
    }
}
