package Indexing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//import java.util.StringTokenizer;

import Classes.*;
//import IndexingLucene.MyIndexReader;
import PreProcessData.*;
//import Search.QueryRetrievalModel;


public class MyIndexReader {
	public File dictFile = null;
	public File postFile = null;
	public File docnumFile = null;
	File sentimentFile = null;
	public List<String> docidToDocnum = new ArrayList<>();
	StopWordRemover swRem = new StopWordRemover();
	
	public MyIndexReader() throws IOException {
		//read the index files you generated in task 1
		//remember to close them when you finish using them
		//use appropriate structure to store your index
		String pathDir = Path.IndexDir;

		String line = null;
		dictFile = new File(pathDir+"dictionary.txt");
		postFile = new File(pathDir+"postings.txt");

		File Docnum_Docid = new File(pathDir+"docnum.txt");
		BufferedReader brDocnum_Docid = new BufferedReader(new FileReader(Docnum_Docid));
		while((line = brDocnum_Docid.readLine()) != null){
			docidToDocnum.add("DOC"+line);
		}
		brDocnum_Docid.close();
	}
	
	public List<Document> retrieveQuery(Query aQuery, int TopN) throws Exception{
		List<Document> res = new LinkedList<Document>();//Store the final result here
		
		//MyIndexReader ixrobj = new MyIndexReader();
		BufferedReader brdict = new BufferedReader(new FileReader(dictFile));
		String line = "";
		
		//Build tokenId_token Map
		HashMap<String, String> tokId_token = new HashMap<String, String>();
		while((line = brdict.readLine()) != null){
			String[] split = line.trim().split("-");
			tokId_token.put(split[1].toString(), split[0].toString());//built the tokenId to token map, this helps retrieve corresponding posting list
		}
		brdict.close();
		
		//Get query content
		List<String> querytokens = new LinkedList<String>();
		WordNormalizer normalizer = new WordNormalizer();
		String content = aQuery.GetQueryContent();
		WordTokenizer tokenizer = new WordTokenizer(content);
		String word = "";
		while((word = tokenizer.nextWord()) != null) {
			word = normalizer.lowercase(word);;//Normalize Tokens
			if(!swRem.isStopword(word)) {//Remove Stop Words
				word = normalizer.stem(word.replaceAll("\\.", "")); //stem the query word before creating the list 
				querytokens.add(word);
			}
		}
		
		//Traverse querytokens to get postings list, store as Map<tokenid, Map<docid,positions>>
		Iterator<String> it = querytokens.listIterator(0);
		HashMap<String, HashMap<String,List<String>>> token_postings = new HashMap<>();
		HashMap<String,List<String>> postings = new HashMap<>();
		while(it.hasNext()){
			String token = it.next();
			String tokenId = tokId_token.get(token);
			if(tokenId != null){ //token is present in the dictionary
				postings = retrievePostings(tokenId);
				token_postings.put(tokenId, postings);
				System.out.println("Tokenids: "+tokenId);
			}
		}
		
		//System.out.println("Token_postings size:"+token_postings.size());
		//Get all pos_neg tokens from "combined_pos_neg.txt"
		sentimentFile = new File(Path.SentimentDir+"pos_neg.txt");
		BufferedReader brsenti = new BufferedReader(new FileReader(sentimentFile));
		List<String> poslist = new LinkedList<String>();
		List<String> neglist = new LinkedList<String>();
		boolean posflag=false, negflag=false;
		while((line = brsenti.readLine()) != null){
			if(line.startsWith("Positive"))
				posflag = true;
			else if(line.startsWith("Negative")){
				negflag = true;
				posflag = false;
			}
			if(posflag){
				//normalize, tokenize and put it in sentiList
				WordTokenizer tokenizer2 = new WordTokenizer(line.trim());
				while((word = tokenizer2.nextWord()) != null) {
					word = normalizer.lowercase(word);;//Normalize Tokens
					if(!swRem.isStopword(word)) {//Remove Stop Words
						word = normalizer.stem(word.replaceAll("\\.", "")); //stem the query word before creating the list 
						poslist.add(word);
					}
				}
			}
			if(negflag){
				//normalize, tokenize and put it in sentiList
				WordTokenizer tokenizer2 = new WordTokenizer(line.trim());
				while((word = tokenizer2.nextWord()) != null) {
					word = normalizer.lowercase(word);;//Normalize Tokens
					if(!swRem.isStopword(word)) {//Remove Stop Words
						word = normalizer.stem(word.replaceAll("\\.", "")); //stem the query word before creating the list 
						neglist.add(word);
					}
				}
			}
		}
		brsenti.close();
		//check for corresponding dictionary entries
		//	//Traverse poslist to get postings list, store as Map<tokenid, Map<docid,positions>>
		Iterator<String> it2 = poslist.listIterator(0);
		//HashMap<String, HashMap<String,List<String>>> postokens_postings = new HashMap<>();
		//HashMap<String, HashMap<String,List<String>>> token_postings = new HashMap<>();
		//HashMap<String,List<String>> postings = new HashMap<>();
		HashMap<String, HashMap<String,List<String>>> postings_poslist = new HashMap<>();
		HashMap<String,List<String>> temp = new HashMap<>();
		while(it2.hasNext()){
			String token = it2.next();
			String tokenId = tokId_token.get(token);
			if(tokenId != null){ //token is present in the dictionary
				temp = retrievePostings(tokenId);
				postings_poslist.put(tokenId, temp);
				//postokens_postings.put(tokenId, postings_poslist);
			}
		}
		//	//Traverse neglist to get postings list, store as Map<tokenid, Map<docid,positions>>
		Iterator<String> it3 = neglist.listIterator(0);
		//HashMap<String, HashMap<String,List<String>>> negtokens_postings = new HashMap<>();
		HashMap<String, HashMap<String,List<String>>> postings_neglist = new HashMap<>();
		while(it3.hasNext()){
			String token = it3.next();
			String tokenId = tokId_token.get(token);
			if(tokenId != null){ //token is present in the dictionary
				temp = retrievePostings(tokenId);
				postings_neglist.put(tokenId, temp);
				//negtokens_postings.put(tokenId, postings_neglist);
			}
		}
		
		//-------------------------------------------------------------------------------------------------------------------------------------------------//
		//----------------------------------------------------------------------RANKING--------------------------------------------------------------------//
		//-------------------------------------------------------------------------------------------------------------------------------------------------//
		//Retrieve the first token's, first doc's first position
		//token_postings
		//postings_poslist
		//postings_neglist
		
		//Create a docid, score hashmap to record score of each doc corresponding to every query term hit
		HashMap<String, Double> docid_score = new HashMap<>();
		
		//Create Iterators for the three HashMaps
		Iterator<Map.Entry<String,HashMap<String,List<String>>>> it_tokenpostings = token_postings.entrySet().iterator();
		
		boolean positivehit = false, negativehit = false;
		
		//SCORING the DOCS---------------------------------------------------------------------------------------------------------------------------------//
		while (it_tokenpostings.hasNext()) {
			Map.Entry<String,HashMap<String,List<String>>> pair = it_tokenpostings.next();
			Iterator<Map.Entry<String, List<String>>> nestit = pair.getValue().entrySet().iterator();
			while(nestit.hasNext()){
				//get the docid and check whether it matches with docids of any of the pos or neg words
				Iterator<Map.Entry<String,HashMap<String,List<String>>>> it_pospostings = postings_poslist.entrySet().iterator();
				Iterator<Map.Entry<String,HashMap<String,List<String>>>> it_negpostings = postings_neglist.entrySet().iterator();
				
				Map.Entry<String,List<String>> npair = nestit.next();//token_(docid, positions)
//				System.out.println("npair.key: "+npair.getKey());
				//Iterator<String> it_tokenpos = npair.getValue().listIterator(1);//token_(positions)
				//use pos iterator to get the doc id and match it with the current docid of the token
				//		//get first position from npair and match it with positions in pospair
				String first_pos = npair.getValue().get(0); //first occurrence sentence number for the token in the document
//				System.out.println("first pos: "+first_pos);
				while(it_pospostings.hasNext()){
					Map.Entry<String,HashMap<String,List<String>>> pospair = it_pospostings.next();//poslist_(docid, positions)
					Iterator<Map.Entry<String,List<String>>> it_positivepospairiter = pospair.getValue().entrySet().iterator();
					while(it_positivepospairiter.hasNext()){
						Map.Entry<String,List<String>> it_positivepospair = it_positivepospairiter.next();
						List<String> it_positivepos = it_positivepospair.getValue();//poslist_(positions)
						//System.out.println("pospair.key: "+pospair.getKey());
						//System.out.println("pospair.getvalue().get(0): "+it_positivepos);
						if(it_positivepospair.getKey().equals(npair.getKey())){ //if the docid in the positive list matches with token docid
							
							//check if positions match
							//	//check against positive positions doc
							for(String temppos : it_positivepos){
								if(temppos.equals(first_pos)){
//									System.out.println("positivehit>>>>>>>>>>>>>>>>>>>>>>>>");
//									System.out.println("docid_token: "+npair.getKey());
//									System.out.println("tokenid_sentiment: "+pospair.getKey());
//									System.out.println("docid_sentiment: "+it_positivepospair.getKey());
									positivehit = true;
									break;
								}
							}
						}
						if(positivehit)
							break;
					}
					if(positivehit)
						break;
				}
				while(it_negpostings.hasNext()){
					Map.Entry<String,HashMap<String,List<String>>> negpair = it_negpostings.next();//neglist_(docid, positions)
					Iterator<Map.Entry<String,List<String>>> it_negativepospairiter = negpair.getValue().entrySet().iterator();
					while(it_negativepospairiter.hasNext()){
						Map.Entry<String,List<String>> it_negativepospair = it_negativepospairiter.next();
						List<String> it_negativepos = it_negativepospair.getValue();//neglist_(positions)
						if(it_negativepospair.getKey().equals(npair.getKey())){ //if the docid in the negative list matches with token docid
							//check if positions match
							//	//check against negative positions doc
							for(String tempneg : it_negativepos){
								if(tempneg.equals(first_pos)){
//									System.out.println("negativehit>>>>>>>>>>>>>>>>>>>>>>>>");
//									System.out.println("docid_token: "+npair.getKey());
//									System.out.println("tokenid_sentiment: "+negpair.getKey());
//									System.out.println("docid_sentiment: "+it_negativepospair.getKey());
									negativehit = true;
									break;
								}
							}
						}
						if(negativehit)
							break;
					}
					if(negativehit)
						break;
				}
				//score the docid here
				if(positivehit && !negativehit){
					if(null == docid_score.get(npair.getKey())){ //DocId not seen yet
						//Double score = 0.99/(Math.log10(Double.valueOf(first_pos)+1.0)/Math.log10(2.0)); //"0.99/log2(first_pos+1)"
						Double score = 0.99/(Math.log10(Double.valueOf(first_pos))/Math.log10(2.0)); //"0.99/log2(first_pos+1)"
						docid_score.put(npair.getKey(), score);
					}
					else{
						Double score = docid_score.get(npair.getKey());
						//score = score + (0.99/(Math.log10(Double.valueOf(first_pos)+1.0)/Math.log10(2.0)));
						score = score + (0.99/(Math.log10(Double.valueOf(first_pos))/Math.log10(2.0)));
						docid_score.put(npair.getKey(), score);
					}
					positivehit = false;
				}
				else if(negativehit && !positivehit){
					if(null == docid_score.get(npair.getKey())){ //DocId not seen yet
						//Double score = 0.15/(Math.log10(Double.valueOf(first_pos)+1.0)/Math.log10(2.0)); //"0.99/log2(first_pos+1)"
						Double score = 0.15/(Math.log10(Double.valueOf(first_pos))/Math.log10(2.0)); //"0.99/log2(first_pos+1)"
						docid_score.put(npair.getKey(), score);
					}
					else{
						Double score = docid_score.get(npair.getKey());
						//score = score + (0.15/(Math.log10(Double.valueOf(first_pos)+1.0)/Math.log10(2.0)));
						score = score + (0.15/(Math.log10(Double.valueOf(first_pos))/Math.log10(2.0)));
						docid_score.put(npair.getKey(), score);
					}
					negativehit = false;
				}
				else if((positivehit && negativehit) || (!positivehit && !negativehit)){
					if(null == docid_score.get(npair.getKey())){ //DocId not seen yet
						//Double score = 0.50/(Math.log10(Double.valueOf(first_pos)+1.0)/Math.log10(2.0)); //"0.99/log2(first_pos+1)"
						Double score = 0.50/(Math.log10(Double.valueOf(first_pos))/Math.log10(2.0)); //"0.99/log2(first_pos+1)"
						docid_score.put(npair.getKey(), score);
					}
					else{
						Double score = docid_score.get(npair.getKey());
						//score = score + (0.50/(Math.log10(Double.valueOf(first_pos)+1.0)/Math.log10(2.0)));
						score = score + (0.50/(Math.log10(Double.valueOf(first_pos))/Math.log10(2.0)));
						docid_score.put(npair.getKey(), score);
					}
					positivehit = false;
					negativehit = false;
				}
			}
		}
		//SCORING COMPLETE---------------------------------------------------------------------------------------------------------------------------------//
		
		//Iterate over map(docid,scores) to create Documents and store in res
		Iterator<Map.Entry<String, Double>> it_docscore = docid_score.entrySet().iterator();
		while(it_docscore.hasNext()){
			Map.Entry<String, Double> pair = it_docscore.next();
			Document doc = new Document(pair.getKey(),"DOC"+pair.getKey(),pair.getValue());
			res.add(doc);
		}
		
		//Sort res
		Collections.sort(res, new Comparator<Document>() {
			public int compare(Document doc1, Document doc2) {
				if(doc2.score() > doc1.score()){
					return 1;
				}
				if(doc2.score() < doc1.score()){
					return -1;
				}
				else{
					return 0;
				}
			}
		});
		
		if(res.size()>=TopN)
			return res.subList(0, TopN);
		else
			return res.subList(0, res.size());
	}
	
	public HashMap<String,List<String>> retrievePostings(String qtokenId) throws Exception{
		HashMap<String,List<String>> result = new HashMap<>();
		BufferedReader brpost = new BufferedReader(new FileReader(postFile));
		String line = "";
		while((line = brpost.readLine()) != null){
			String[] split = line.split("-");
			if(split[0].toString().equals(qtokenId)){ //got the tokenId match in postings
				//build hashmap(String_docID,List_positions)
				String[] split2 = split[1].toString().split(":"); //split2 now contains all docs with the positions
				for(int i = 0; i < split2.length; i++){
					String[] docsplit = split2[i].toString().split("=");
					String docId = docsplit[0].toString();
					List<String> positions = new LinkedList<String>();
					String[] pos_split = docsplit[1].toString().split(",");
					for(int j = 0; j < pos_split.length; j++){
						positions.add(pos_split[j].toString());
					}
					result.put(docId, positions);
				}
				brpost.close();
				return result;
			}
		}
		brpost.close();
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		MyIndexReader readerobj = new MyIndexReader();
		Query aQuery = new Query();
		aQuery.SetTopicId("901");
		aQuery.SetQueryContent("cheese pasta");
		List<Document> results = new LinkedList<Document>();
		results = readerobj.retrieveQuery(aQuery, 20);
		
		Iterator<Document> it = results.listIterator(0);
		while(it.hasNext()){
			Document doc = it.next();
			System.out.println("DocID: "+doc.docid()+"; Docnum: "+doc.docno()+"; score: "+doc.score());
		}
	}

}
