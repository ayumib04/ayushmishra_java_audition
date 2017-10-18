
public class Yahtzee {

    public static int chance(int d1, int d2, int d3, int d4, int d5)
    {
        int total = 0;
        total += d1;
        total += d2;
        total += d3;
        total += d4;
        total += d5;
        return total;
    }

    public static int yahtzee(int... dice)
    {
        int[] counts = new int[6];
        for (int die : dice)
            counts[die-1]++;
        for (int i = 0; i != 6; i++)
            if (counts[i] == 5)
                return 50;
        return 0;
    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        int sum = 0;
        if (d1 == 1) sum++;
        if (d2 == 1) sum++;
        if (d3 == 1) sum++;
        if (d4 == 1) sum++;
        if (d5 == 1) 
            sum++;

        return sum;
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        int sum = 0;
        if (d1 == 2) sum += 2;
        if (d2 == 2) sum += 2;
        if (d3 == 2) sum += 2;
        if (d4 == 2) sum += 2;
        if (d5 == 2) sum += 2;
        return sum;
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        int s;    
        s = 0;
        if (d1 == 3) s += 3;
        if (d2 == 3) s += 3;
        if (d3 == 3) s += 3;
        if (d4 == 3) s += 3;
        if (d5 == 3) s += 3;
        return s;
    }

    protected int[] dice;
    public Yahtzee(int d1, int d2, int d3, int d4, int _5)
    {
        dice = new int[5];
        dice[0] = d1;
        dice[1] = d2;
        dice[2] = d3;
        dice[3] = d4;
        dice[4] = _5;
    }

    public int fours()
    {
        int sum;    
        sum = 0;
        for (int at = 0; at != 5; at++) {
            if (dice[at] == 4) {
                sum += 4;
            }
        }
        return sum;
    }

    public int fives()
    {
        int s = 0;
        int i;
        for (i = 0; i < dice.length; i++) 
            if (dice[i] == 5)
                s = s + 5;
        return s;
    }

    public int sixes()
    {
        int sum = 0;
        for (int at = 0; at < dice.length; at++) 
            if (dice[at] == 6)
                sum = sum + 6;
        return sum;
    }

    public static int score_pair(int d1, int d2, int d3, int d4, int d5)
    {
        int[] counts = new int[6];
        counts[d1-1]++;
        counts[d2-1]++;
        counts[d3-1]++;
        counts[d4-1]++;
        counts[d5-1]++;
        int at;
        for (at = 0; at != 6; at++)
            if (counts[6-at-1] >= 2)
                return (6-at)*2;
        return 0;
    }

    public static int two_pair(int d1, int d2, int d3, int d4, int d5)
    {
        int[] counts = new int[6];
        counts[d1-1]++;
        counts[d2-1]++;
        counts[d3-1]++;
        counts[d4-1]++;
        counts[d5-1]++;
        int n = 0;
        int score = 0;
        for (int i = 0; i < 6; i += 1)
            if (counts[6-i-1] >= 2) {
                n++;
                score += (6-i);
            }        
        if (n == 2)
            return score * 2;
        else
            return 0;
    }

    public static int four_of_a_kind(int _1, int _2, int d3, int d4, int d5)
    {
        int[] counts;
        counts = new int[6];
        counts[_1-1]++;
        counts[_2-1]++;
        counts[d3-1]++;
        counts[d4-1]++;
        counts[d5-1]++;
        for (int i = 0; i < 6; i++)
            if (counts[i] >= 4)
                return (i+1) * 4;
        return 0;
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
        int[] t;
        t = new int[6];
        t[d1-1]++;
        t[d2-1]++;
        t[d3-1]++;
        t[d4-1]++;
        t[d5-1]++;
        for (int i = 0; i < 6; i++)
            if (t[i] >= 3)
                return (i+1) * 3;
        return 0;
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5)
    {
        int[] counts;
        counts = new int[6];
        counts[d1-1] += 1;
        counts[d2-1] += 1;
        counts[d3-1] += 1;
        counts[d4-1] += 1;
        counts[d5-1] += 1;
        if (counts[0] == 1 &&
        	counts[1] == 1 &&
        	counts[2] == 1 &&
        	counts[3] == 1 &&
        	counts[4] == 1)
            return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5)
    {
        int[] counts;
        counts = new int[6];
        counts[d1-1] += 1;
        counts[d2-1] += 1;
        counts[d3-1] += 1;
        counts[d4-1] += 1;
        counts[d5-1] += 1;
        if (counts[1] == 1 &&
        	counts[2] == 1 &&
        	counts[3] == 1 &&
        	counts[4] == 1 && 
        	counts[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5)
    {
        int[] counts;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;




        counts = new int[6];
        counts[d1-1] += 1;
        counts[d2-1] += 1;
        counts[d3-1] += 1;
        counts[d4-1] += 1;
        counts[d5-1] += 1;

        for (i = 0; i != 6; i += 1)
            if (counts[i] == 2) {
                _2 = true;
                _2_at = i+1;
            }

        for (i = 0; i != 6; i += 1)
            if (counts[i] == 3) {
                _3 = true;
                _3_at = i+1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
    
    public static void main(String[] args){
    	//----Test cases
    	int[] Score = new int[20];
    	String[] category = new String[20];
    	//chance
    	Score[0] = chance(2,2,4,2,1);
    	category[0] = "Chance";
    	
    	//yahtzee
    	Score[1] = yahtzee(4,4,4,4,4);
    	category[1] = "Yahtzee";
    	Score[2] = yahtzee(1,1,2,1,1);
    	category[2] = "Yahtzee";
    	
    	//test 1s
    	Score[3] = ones(1,1,2,1,3);
    	category[3] = "Ones";
    	Score[4] = ones(2,3,2,5,6);
    	category[4] = "Ones";
    	
    	//test 2s
    	Score[5] = twos(4,2,3,2,2);
    	category[5] = "Twos";
    	Score[6] = twos(6,5,5,5,1);
    	category[6] = "Twos";
    	
    	//test one pair
    	Score[6] = score_pair(4,2,3,2,2);
    	category[6] = "One Pair";
    	Score[7] = score_pair(6,2,3,4,1);
    	category[7] = "One Pair";
    	
    	//test two pairs
    	Score[8] = two_pair(2,3,4,3,2);
    	category[8] = "Two Pair";
    	Score[9] = two_pair(3,3,4,5,6);
    	category[9] = "Two Pair";
    	
    	//test 3 of a kind
    	Score[10] = three_of_a_kind(3,3,3,4,5);
    	category[10] = "3 of a kind";
    	Score[11] = three_of_a_kind(3,3,4,5,6);
    	category[11] = "3 of a kind";
    	
    	//test 4 of a kind
    	Score[12] = four_of_a_kind(3,3,3,3,3);
    	category[12] = "4 of a kind";
    	Score[13] = four_of_a_kind(3,3,4,5,6);
    	category[13] = "4 of a kind";
    	
    	//small straight
    	Score[14] = smallStraight(1,2,3,4,5);
    	category[14] = "small straight";
    	Score[15] = smallStraight(2,3,4,5,6);
    	category[15] = "small straight";
    	
    	//large straight
    	Score[16] = largeStraight(1,2,3,4,5);
    	category[16] = "large straight";
    	Score[17] = largeStraight(2,3,4,5,6);
    	category[17] = "large straight";
    	
    	//full house
    	Score[18] = fullHouse(1,1,2,2,2);
    	category[18] = "full house";
    	Score[19] = fullHouse(2,3,4,5,6);
    	category[19] = "full house";
    	
    	for(int i = 0; i < Score.length; i++)
    	{
    		System.out.println("Category: "+category[i]+" Score: "+Score[i]);
    	}
    	
    	
    }
}



