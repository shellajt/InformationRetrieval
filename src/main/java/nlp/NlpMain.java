package nlp;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jesse Shellabarger on 5/17/2017.
 */
public class NlpMain {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome! Please give the software a moment to parse the document.");
        NaturalLanguageProcessor nlp = new NaturalLanguageProcessor();
        System.out.println("Enter any queries about President Lincoln and this software will attempt to determine" +
                "if they are true. Enter `exit` to quit.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String query = reader.readLine();

        while (query != "exit") {
            Document doc = new Document(query);
            SentenceParse parse = new SentenceParse();

            Sentence sent = doc.sentences().get(0);
            List<String> dependencies = Arrays.asList(sent.dependencyGraph().toList().split("\n"));
            for (String dep : dependencies) {
                System.out.println(dep);
                if (dep.startsWith("nsubj")) {
                    int lastDash = dep.lastIndexOf("-");
                    System.out.println(lastDash);
                    int lastParen = dep.lastIndexOf(")");
                    System.out.println(lastParen);
                    String numberString = dep.substring(lastDash+1, lastParen);
                    System.out.println(numberString);
                    int num = Integer.parseInt(numberString)-1;
                    System.out.println(num);
                    parse.subject = sent.lemma(num);
                    System.out.println(parse.subject);

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

            System.out.println(parse.toString());
            Boolean result = nlp.analyze(parse);
            if (result) {
                System.out.println("We believe this statement to be true");
            } else System.out.println("We are unsure about the truth of the statement");
            System.out.println("Enter another query or enter `exit` to quit");
            query = reader.readLine();
        }
    }
}
