package PreProcessData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;




public class StopWordRemover {
	//you can add essential private methods or variables
	private File stopWordsFile = null;
	private BufferedReader br = null;
	private List<String> stopWordsList;
	public StopWordRemover( ) throws IOException{
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
		stopWordsFile = new File("C:\\Users\\Ayush\\Desktop\\Information Storage and Retrieval\\Project\\stopwords\\updated_sw.txt");
		br = new BufferedReader(new InputStreamReader(new FileInputStream(stopWordsFile)));
		stopWordsList = new ArrayList<>();
		String line = null;
		while((line = br.readLine()) != null){
			stopWordsList.add(line);
		}
	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword(String word ) {
		// return true if the input word is a stopword, or false if not
		return stopWordsList.contains(String.valueOf(word));
	}
}
