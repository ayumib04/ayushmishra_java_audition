package Indexing;

import java.io.*;
import java.util.*;

import Classes.Path;

public class PreProcessedCorpusReader {

	private BufferedReader br = null;

	public PreProcessedCorpusReader() throws IOException {
		// This constructor opens the pre-processed corpus file, Path.ResultHM1 + type
		// You can use your own version, or download from http://crystal.exp.sis.pitt.edu:8080/iris/resource.jsp
		// Close the file when you do not use it any more
		br = new BufferedReader(new FileReader(new File(Path.ResultPreprocessVeg)));
	}


	public Map<String, String> NextDocument() throws IOException {
		// read a line for docNo, put into the map with <"DOCNO", docNo>
		// read another line for the content , put into the map with <"CONTENT", content>
		String line = null;
		if((line = br.readLine()) != null){
			String docNum = "";
			String content = "";
			if(line.startsWith("<DOC"))
				docNum = line;
			while((line = br.readLine())!=null){
				if(line.startsWith("</DOC>"))
					break;
				content = content+line+" ";
			}
			Map<String,String> doc = new HashMap<>();
			doc.put("DOCNUM", docNum);
			doc.put("CONTENT", content);
			return doc;
		}
		br.close();
		return null;
	}

}
