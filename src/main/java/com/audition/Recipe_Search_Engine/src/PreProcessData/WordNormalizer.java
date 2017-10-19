package PreProcessData;
import Classes.Stemmer;

/**
 * This is for INFSCI 2140 in 2017
 * 
 */
public class WordNormalizer {
	//you can add essential private methods or variables
	
	// YOU MUST IMPLEMENT THIS METHOD
	public String lowercase(String chars ) {
		//transform the uppercase characters in the word to lowercase
		return chars.toLowerCase();
	}
	
	public String stem(String chars)
	{
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		//String str="";
		char[] chararr = chars.toCharArray();
		Stemmer s = new Stemmer();
		s.add(chararr,chararr.length);
		s.stem();
		return s.toString();
	}
	
}
