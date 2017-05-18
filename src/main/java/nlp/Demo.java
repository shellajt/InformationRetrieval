package nlp;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.CoreNLPProtos;
import edu.stanford.nlp.simple.*;
import edu.stanford.nlp.trees.*;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        // Create a document. No computation is done yet.
        Document doc = new Document("add your text here! It can contain multiple sentences. Now is the time for all good men to come to the aid of their country");
        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
            // We're only asking for words -- no need to load any models yet
//            System.out.println("The second word of the sentence '" + sent + "' is " + sent.word(1));
//            // When we ask for the lemma, it will load and run the part of speech tagger
//            System.out.println("The third lemma of the sentence '" + sent + "' is " + sent.lemma(2));
//            // When we ask for the parse, it will load and run the parser
              System.out.println("The parse of the sentence '" + sent + "' is " + sent.parse());
            // ...
            String things = sent.dependencyGraph().toList();
            List<String> struct = Arrays.asList(things.split("\n"));

            for (String dep : struct) {
                System.out.println(dep);
            }

//            LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparse/englishPCFG.ser.gz", "-maxLength", "80", "-retrainTmpSubcategories");
//            TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//
////            Tree parse = lp.apply(sent.);
//
//            Tree tree = sent.parse();
//            EnglishGrammaticalStructure egs = new EnglishGrammaticalStructure(tree);
//            //EnglishGrammaticalStructure.g
        }
    }
}