import java.util.*;
public class PokerHands 
{
	private String[] hand;
	private int rank = 0;

	public PokerHands(String[] arr)
	{
		hand = new String[5];
		hand[0] = arr[0];
		hand[1] = arr[1];
		hand[2] = arr[2];
		hand[3] = arr[3];
		hand[4] = arr[4];
		Ranker();
	}

	public int getRank()
	{
		return rank;
	}

	public static int getVal(String hand)
	{
		String temp = hand.split("")[0];
		if(temp.toString().equals("J"))
			return 11;
		else if(temp.toString().equals("Q"))
			return 12;
		else if(temp.toString().equals("K"))
			return 13;
		else if(temp.toString().equals("A"))
			return 14;
		else
			return Integer.parseInt(temp);
	}

	public String getSuit(String hand)
	{
		return hand.split("")[1].toString();
	}

	private void sort(String[] hands)
	{
		String temp;
		int minidx;
		for(int i = 0; i < hands.length; i++){
			minidx = i;
			for(int j = 0; j < hands.length; j++){
				if(getVal(hands[minidx]) > getVal(hands[j]))
					minidx = j;
			}
			//swap i,j
			temp = hands[minidx];
			hands[minidx] = hands[i];
			hands[i] = temp;
		}
	}

	private int ispair(String[] hands)
	{
		int pairidx = -1;
		for(int i = 0; i < 4; i++)
			if(getVal(hands[i]) == getVal(hands[i+1])) {
				pairidx = i;
				rank = 1;
				i = 4;
			}
		return pairidx;
	}

	private void ispair2nd(String[] hands, int pairidx)
	{
		if(rank == 1) {
			for(int i = pairidx + 2; i < 4; i++)
				if(getVal(hands[i]) == getVal(hands[i+1]))
					rank = 2;
		}
	}

	private void is3ofakindorfullhouse(String[] hands)
	{
		for(int i = 0; i < 3; i++)
			if(getVal(hands[i]) == getVal(hands[i+1]) && getVal(hands[i+1])==getVal(hands[i+2])){
				rank = 3;
				if(i==0 && getVal(hands[3]) == getVal(hands[4]) || i==2 && getVal(hands[0]) == getVal(hands[1]))
					rank = 6;
			}
	}

	private void is4ofakind(String[] hands)
	{
		for(int i = 0 ; i < 2; i++)
			if(getVal(hands[i]) == getVal(hands[i+1]) && getVal(hands[i+1]) == getVal(hands[i+2]) && getVal(hands[i+2]) == getVal(hands[i+3]))
				rank = 7;
	}

	private void isstraight(String[] hands)
	{
		if(rank == 0) 
			if((getVal(hands[4]) - getVal(hands[0]) == 4) ||
					(getVal(hands[3]) - getVal(hands[0]) == 3 && getVal(hands[4]) == 14 && getVal(hands[0]) == 2)) {
				rank = 4;
			}
	}

	private void isflush(String[] hands)
	{
		int flush = 1;
		if(rank == 0 || rank == 4){//either none or straight
			for(int i = 0; i < 4; i++)
				if(!getSuit(hands[i]).equals(getSuit(hands[i+1])))
					flush = 0; //no flush since suits mismatch
			if(flush == 1 && rank == 4)
				rank = 8;//straight flush
			else if(flush == 1)
				rank = 5;
		}
	}

	private void Ranker()
	{
		String[] sorted = new String[5];
		for(int i = 0; i < 5; i++)
			sorted[i] = hand[i];

		this.sort(sorted);//sort the hand
		int pairidx = -1; //check for pairings

		//check pair
		pairidx = this.ispair(sorted); //store most recent pair index

		//check second pair
		this.ispair2nd(sorted, pairidx);

		//check for 3 of a kind or full house
		this.is3ofakindorfullhouse(sorted);

		//check for 4 of a kind
		this.is4ofakind(sorted);

		//check for Straight
		this.isstraight(sorted);

		//check for flush/straight flush
		this.isflush(sorted);
	}

	public static String[] sorter(String[] hands)
	{
		//sort
		String temp;
		int minidx;
		for(int i = 0; i < hands.length; i++){
			minidx = i;
			for(int j = 0; j < hands.length; j++){
				if(getVal(hands[minidx]) > getVal(hands[j]))
					minidx = j;
			}
			//swap i,j
			temp = hands[minidx];
			hands[minidx] = hands[i];
			hands[i] = temp;
		}		
		return hands;
	}

	public static void highestval(String[] hands1, String[] hands2) //In case of a rank tie
	{
		hands1 = sorter(hands1);
		hands2 = sorter(hands2);
		int idx = -1;
		for(int i = 4; i >= 0; i--){
			if(getVal(hands1[i]) != getVal(hands2[i])){
				idx = i;
				break;
			}
		}
		if(idx == -1)
			System.out.println("Tie.");
		else if(getVal(hands1[idx]) > getVal(hands2[idx]))
			System.out.println("Black wins. - with High Card "+hands1[idx].toString());
		else
			System.out.println("White wins. - with High Card "+hands2[idx].toString());
	}

	public static String ranktoString(int rank){
		String result="";
		switch(rank){
		case 0: result = "noRank";break;
		case 1: result = "Pair"; break;
		case 2: result = "TwoPairs"; break;
		case 3: result = "3 of a kind"; break;
		case 4: result = "Straight"; break;
		case 5: result = "Flush"; break;
		case 6: result = "Full House"; break;
		case 7: result = "4 of a Kind"; break;
		case 8: result = "Straight Flush"; break;
		default: break;
		}
		return result;
	}

	public static void main(String[] args)
	{
		PokerHands h1, h2;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the hands to evaluate. Press c to continue. any other key to quit");
		String line = in.nextLine();
		while(line.equals("c"))
		{
			System.out.println("Enter Black Hand");
			String black = in.nextLine();
			String[] tempblack = black.split(" ");
			h1 = new PokerHands(black.split(" "));
			h1.Ranker();
			//System.out.println("h1 rank: "+h1.rank);
			
			System.out.println("Enter White Hand");
			String white = in.nextLine();
			String[] tempwhite = white.split(" ");
			h2 = new PokerHands(white.split(" "));
			h2.Ranker();
			//System.out.println("h2 rank: "+h2.rank);
			
			if(h1.getRank() > h2.getRank())
				System.out.println("Black wins. - with "+ranktoString(h1.getRank()));
			
			else if(h1.getRank() < h2.getRank())
				System.out.println("White wins. - with "+ranktoString(h2.getRank()));
			
			else if(h1.getRank() == h2.getRank())
				highestval(tempblack,tempwhite);
			
			System.out.println("Press c to continue. any other key to quit");
			line = in.nextLine();
		}
		in.close();
	}
}
