package curators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jesse Shellabarger on 4/27/2017.
 * I did work on it too John Kirschenheiter 
 */
public class WikipediaCurator {
	
	File[] filesInDirectory;
	List<File> newFiles;

	public static void main(String[] args) {
		try {
			new WikipediaCurator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WikipediaCurator() throws IOException {
		String path = ".\\Resources\\NlpDocs";
		File directory = new File(path);
        filesInDirectory = directory.listFiles();
        
        newFiles = new LinkedList<File>();
    	for (File file : filesInDirectory) {
    		BufferedReader br = new BufferedReader(new FileReader(file));
    		
    		File outfile = new File(".\\Resources\\NlpDocs\\" + file.getName()+"(1)");
    		FileWriter fw = new FileWriter(outfile);
    		BufferedWriter bw = new BufferedWriter(fw);
          
    		String line = br.readLine();
    		System.out.println(line);

			//line = processFirstLine(line);
    		int count = 0;
    		while (line != null) {
    			count++;
    			line = removePunctuation(line);
    			//line = line.toLowerCase();
    			//line = removeWeirdLine(line);
    			//line = handleReferences(line, count);
    			
    			bw.write(line);
    			bw.newLine();
    			line = br.readLine();
          }
    		
          br.close();
          bw.flush();
          bw.close();
    	}
    	
	}
	
	private String handleReferences(String line, int count) {
		if (count > 150){
			return "";
		}
		return line;
	}

	private String removeWeirdLine(String line) {
		if (line.startsWith("                                            ")){
			return "";
		}
		return line;
	}

	private String processFirstLine(String line) {
		return line.substring(0, line.indexOf("From Wiki"));
	}

	private String removePunctuation(String line) {
        return line.replaceAll(",", "").replaceAll("\"", "").replaceAll(";", "")
                .replaceAll(":", "").replaceAll("-", " ").replaceAll("\\(", " ").replaceAll("\\)", " ");
    }
}
