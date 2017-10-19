import java.io.*;
import java.util.*;
public class pos_neg_stopwords {

	public static void main(String...arg){
		ArrayList<String> pnwords = new ArrayList<String>();
		ArrayList<String> sw_original = new ArrayList<String>();
		ArrayList<String> new_sw = new ArrayList<String>();
		File posneg = new File("C:\\Users\\Ayush\\Desktop\\Information Storage and Retrieval\\Project\\stopwords\\combined_pos_neg.txt");
		File stopword = new File("C:\\Users\\Ayush\\Desktop\\Information Storage and Retrieval\\Project\\stopwords\\stopword.txt");
		File updated_sw = new File("C:\\Users\\Ayush\\Desktop\\Information Storage and Retrieval\\Project\\stopwords\\updated_sw.txt");
		try{
			BufferedReader brpn = new BufferedReader(new InputStreamReader(new FileInputStream(posneg)));
			BufferedReader brsw = new BufferedReader(new InputStreamReader(new FileInputStream(stopword)));
			String line = "";		
			int matchflag = 0;
			while((line = brpn.readLine()) != null){
				pnwords.add(line.trim().toLowerCase());
			}
			while((line = brsw.readLine()) != null){
				sw_original.add(line.trim().toLowerCase());
			}
			
			for(String sw : sw_original){
				for(String pnw : pnwords){
					if(pnw.equals(sw))
						matchflag = 1;
				}
				if(matchflag == 0)
					new_sw.add(sw);
				else
					matchflag = 0;
			}
			
			
			BufferedWriter new_swWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(updated_sw),"UTF-8"));
			for(String nsw : new_sw){
				new_swWriter.write(nsw.toString());
				new_swWriter.newLine();
			}
			
			brpn.close();
			brsw.close();
			new_swWriter.close();
						
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
