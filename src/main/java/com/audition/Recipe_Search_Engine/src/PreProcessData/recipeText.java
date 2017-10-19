package PreProcessData;
import java.io.*;
import java.util.*;

public class recipeText implements recipeCollection{
	File recipeFile = null;
	BufferedReader br = null;	
	int docctr = 0;
	int cdatflag = 0;
	public recipeText() throws FileNotFoundException{
		recipeFile = new File("C:\\Users\\Ayush\\Desktop\\Information Storage and Retrieval\\Project\\data\\VegetarianRecipes.exl");
		//recipeFile = new File("C:\\Users\\Ayush\\Desktop\\Information Storage and Retrieval\\Project\\data\\ArmedForcesRecipes.exl");
		br = new BufferedReader(new InputStreamReader(new FileInputStream(recipeFile)));
	}
	
	public Map<String, String> nextrecipe() throws IOException{
		String line = "";
		docctr++;
		StringBuilder builder = new StringBuilder();
		while((line = br.readLine()) != null){
			if(!line.contains("CDATA") && !line.contains("xml")){
				builder.append(line+" ");
			}
			else if(line.contains("CDATA") && line.contains("]]>")){
				String[] split = line.split("]]>");
				builder.append(split[0].toString()+" ");
				Map<String,String> doc = new HashMap<>();
				doc.put(String.valueOf(docctr), builder.toString());
				return doc;
			}
		}
		br.close();
		return null;
	}
 }
