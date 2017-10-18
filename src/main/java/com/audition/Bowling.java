import java.util.*;
public class Bowling {

	public static int returnInt(String x)
	{
		if(x.contains("-"))
			return Integer.parseInt((x.replaceAll("-","")));
		else{
			String[] twos = x.split("");
			if(twos.length > 1)
				return (Integer.parseInt(twos[0].toString())+Integer.parseInt(twos[1].toString()));
			else
				return (Integer.parseInt(twos[0].toString()));
		}
	}
	public static int Bowl(String[] linearr){
		int sum = 0;
		for(int i = 0; i<10 ; i++)
		{
			if(linearr[i].toString().contains("X"))
			{
				if(linearr[i+1].toString().contains("X"))
				{
					if(linearr[i+2].toString().contains("X"))
					{
						sum = sum + 30;
					}
					else if(linearr[i+2].toString().contains("/"))
					{
						sum = sum + 20 + Integer.parseInt(linearr[i+2].toString().split("")[0]);
					}
				}
				else if(linearr[i+1].toString().contains("/"))
				{
					sum = sum + 20;
				}
				else
					sum = sum + 10 + returnInt(linearr[i+1].toString());
			}
			else if(linearr[i].toString().contains("/"))
			{
				if(linearr[i+1].toString().contains("X"))
				{
					sum = sum + 20;
				}
				else if(linearr[i+1].toString().contains("/"))
				{
					sum = sum + 10 + Integer.parseInt(linearr[i+1].toString().split("")[0]);
				}
				else
					sum = sum + 10 + returnInt(linearr[i+1].toString());
			}
			else if(!(linearr[i].toString().contains("X") || linearr[i].toString().contains("/")))
				sum = sum + returnInt(linearr[i].toString());
		}
		return sum;
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the bowling line:");
		String line = in.nextLine();
		String[] linearr = line.split(" ");
		
		int Result = Bowl(linearr);
		
		System.out.println("Result: "+Result);
		in.close();
	}
}
