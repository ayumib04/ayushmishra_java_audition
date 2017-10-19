package Indexing;

import java.io.*;
import java.util.*;


import Classes.Path;

public class MyIndexWriter {
	// We suggest you to write very efficient code here, otherwise, your memory cannot hold our corpus...
	int docId = 1; // for incrementally numbering the documents in the collection

	//Create dictionary, Postings List and Doc Number map files
	public File dictFile = null;
	public File postFile = null;
	public File docnumFile = null;

	public List<String> docIdToNum = new ArrayList<>();
	//Create an Inverse document list (posting list for each term
	Map<String,LinkedList<String[]>> term_postings = new TreeMap<>();



	public MyIndexWriter() throws IOException {
		// This constructor should initiate the FileWriter to output your index files
		// remember to close files if you finish writing the index
		dictFile = new File(Path.IndexDir+"dictionary.txt");
		postFile = new File(Path.IndexDir+"postings.txt");
		docnumFile = new File(Path.IndexDir+"docnum.txt");

		dictFile.getParentFile().mkdirs();
		dictFile.createNewFile();
		postFile.createNewFile();
	}

	public void IndexADocument(String docno, String content) throws IOException {
		docIdToNum.add(docno);
		
		ArrayList<String> doctokens = new ArrayList<String>();
		//doctokens.add("");
		//Iterator<String> diterator = doctokens.iterator();
		boolean presentflag = false;
		
		String[] lines = content.split("\\n+");
		//System.out.println(lines.length);
		//Create intermediary dictionary for the document
		for(int i = 0; i< lines.length; i++){
			String[] tokens = lines[i].toString().split("\\s+");
			//System.out.println("Linetokens: "+tokens.length);
			for(int j = 0; j<tokens.length; j++){
				Iterator<String> diterator = doctokens.iterator();
				while(diterator.hasNext()){
					if(tokens[j].toString().equals(diterator.next().toString())){
						presentflag = true;
					}
				}
				if(!presentflag && !tokens[j].toString().equals(" ") && !tokens[j].toString().equals("")){
					if(tokens[j].toString().equals("extra"))
						System.out.println(content);
					doctokens.add(tokens[j].toString());
				}
				else
					presentflag = false;
			}
		}
		//System.out.println("doctokens size: "+doctokens.size());
		Iterator<String> diterator2 = doctokens.iterator();

		//Now, read the terms by the sentence.
		while(diterator2.hasNext()){
			String term = diterator2.next().toString();
			String linenum = docno+" "; // for storing each term's appearance in which of the sentences in given docId
			for(int i = 0; i<lines.length ; i++){
				if(lines[i].toString().contains(term)){
					linenum = linenum+String.valueOf(i+1)+" ";
				}
			}
			//linenum = linenum + " *";
			String[] linenumarr = linenum.split(" ");
			if(!term_postings.containsKey(term)){
				LinkedList<String[]> docpositions = new LinkedList<String[]>();
				docpositions.add(linenumarr);
				term_postings.put(term, docpositions);
			}
			else
				term_postings.get(term).add(linenumarr);
		}
		
		++docId;		
	}

	public void Close() throws IOException {
		// close the index writer, and you should output all the buffered content (if any).
		// if you write your index into several files, you need to fuse them here.
		BufferedWriter DictWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dictFile),"UTF-8"));
		BufferedWriter PostWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(postFile),"UTF-8"));
		long ptr = 1;
		for(Map.Entry<String, LinkedList<String[]>> postings : term_postings.entrySet()){
			PostWriter.write(String.valueOf(ptr));
			PostWriter.write("-");
			String Term = postings.getKey();
			LinkedList<String[]> postingsList = postings.getValue();
			//int docFreq = postingsFreq.size();
			//String[] docIDFreq = null;
			//int collectionFreq = 0;
			String[] docId_term_positions = null;
			while((docId_term_positions = postingsList.poll())!= null){
				String docID = docId_term_positions[0];
				//String tf = docIDFreq[1];
				//collectionFreq += tf;
				PostWriter.write(String.valueOf(docID));
				PostWriter.write("=");
				for(int i = 1; i < docId_term_positions.length; i++){
					PostWriter.write(docId_term_positions[i].toString());
					if(i<docId_term_positions.length-1)
						PostWriter.write(",");
				}
				PostWriter.write(":");
			}
			PostWriter.newLine();

			DictWriter.write(String.valueOf(ptr));
			DictWriter.write("-");
			DictWriter.write(Term);;
			//DictWriter.write(":");
			//DictWriter.write(String.valueOf(docFreq));
			//DictWriter.write(":");
			//DictWriter.write(String.valueOf(collectionFreq));
			DictWriter.newLine();
			ptr++;
		}

		BufferedWriter docnumWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docnumFile)));

		for(String dnum : docIdToNum){
			docnumWriter.write(dnum);
			docnumWriter.newLine();
		}
		DictWriter.close();
		PostWriter.close();
		docnumWriter.close();
	}

	public static void main(String[] args) throws IOException{
		//read the pre-processed file and send it to the indexer method
		MyIndexWriter obj = new MyIndexWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Path.ResultPreprocessVeg)));
		String line = ""; String docno = "", content = "";
		while((line = br.readLine()) != null){
			if(line.startsWith("<DOC"))
				docno = line.replaceAll("[^\\d.]", "");
			if(line.startsWith("</DOC")){
				content = content.replaceAll(">", "");
				content = content.replaceAll("<DOC", "");
				content = content.replaceAll("</DOC>", "");
				content = content.replaceAll("</DOC", "");
				obj.IndexADocument(docno,content);
				docno = "";
				content = "";
			}
			else{
				content = content+line+"\n";
			}
				
		}
		obj.Close();
		br.close();
	}

}
