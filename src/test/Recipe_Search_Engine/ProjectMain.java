import java.io.FileWriter;
import java.util.Map;

import Classes.*;
import PreProcessData.*;
import PreProcessData.WordNormalizer;
import PreProcessData.WordTokenizer;


public class ProjectMain {
	public static void main(String...arg) throws Exception{
		ProjectMain obj = new ProjectMain();
		obj.PreProcess();
	}
	public void PreProcess() throws Exception{
		recipeCollection recipes;

		//Initialize the object
		recipes = new recipeText();

		StopWordRemover stopwordRemover = new StopWordRemover();
		WordNormalizer normalizer = new WordNormalizer();

		FileWriter wr = new FileWriter(Path.ResultPreprocessVeg);

		//For writing the docnum and preprocessed corpus to file one at a time
		Map<String, String> doc = null;

		int count = 0;

		while((doc = recipes.nextrecipe()) != null){

			//Load Doc number
			String docnum = doc.keySet().iterator().next();

			//Load Doc content
			String content = doc.get(docnum);

			// write docnum into the result file
			wr.append("<DOC "+docnum + ">\n");

			// initiate the WordTokenizer class
			WordTokenizer tokenizer = new WordTokenizer(content);

			// initiate a word object, which can hold a word
			String word = null;

			int ctr = 0;
			// process the document word by word iteratively
			while ((word = tokenizer.nextWord()) != null) {
				// each word is transformed into lowercase
				word = normalizer.lowercase(word);


				// filter out stopword, and only non-stopword will be written into result file
				// stemmed format of each word is written into result file
				if (!stopwordRemover.isStopword(word)){
					if(normalizer.stem(word).contains(".")){
						ctr = ctr+1;
						wr.append(normalizer.stem(word.replaceAll("\\.", "")));
						wr.append("\n");
					}
					else
						wr.append(normalizer.stem(word) + " ");
				}
			}

			wr.append("</DOC>\n");// finish processing one document

			count++;
			if (count % 100 == 0)
				System.out.println("finish " + count + " docs");
		}
		System.out.println("total document count:  " + count);
		wr.close();
	}
}
