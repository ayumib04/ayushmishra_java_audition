package PreProcessData;
import java.util.StringTokenizer;

/**
 * This is for INFSCI 2140 in 2017
 * 
 * TextTokenizer can split a sequence of text into individual word tokens.
 */
public class WordTokenizer {
	//you can add essential private methods or variables
	private StringTokenizer tokenizer = null;
	// YOU MUST IMPLEMENT THIS METHOD
	public WordTokenizer( String texts ) {
		// this constructor will tokenize the input texts
		// please remove all punctuations
		tokenizer = new StringTokenizer(texts.replaceAll("[-+^:,*!`#()%;|/&\\[@\\]{}\"]", " "), " ");
	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public String nextWord() {
		// read and return the next word of the document
		// or return null if it is the end of the document
		while(tokenizer.hasMoreTokens()){
			String nextString = tokenizer.nextToken();
			return nextString;
		}
		return null;
	}
	
	public static void main(String[] args){//test tokenizer
		WordTokenizer tk = new WordTokenizer("ayush . Mishra");
		WordNormalizer normalizer = new WordNormalizer();
		String word = "";
		while ((word = tk.nextWord()) != null) {
			System.out.println(word+" "+normalizer.stem(word));
		}
		
	}
	
}
