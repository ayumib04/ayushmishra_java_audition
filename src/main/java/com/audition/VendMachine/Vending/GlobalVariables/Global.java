package Vending.GlobalVariables;
import Vending.Coins.*;
import Vending.Product.*;

public class Global 
{
	public static final Penny P = new Penny(2.5,0.75);
	public static final Dime D = new Dime(2.268,0.705);
	public static final Nickel N = new Nickel(5.0, 0.835);
	public static final Quarter Q = new Quarter(5.67, 0.955);
	
	public static final Cola CO = new Cola(1.0, 10);
	public static final Chips CH = new Chips(0.50, 10);
	public static final Candy CA = new Candy(0.65, 10);
	public static final TrailMix TM = new TrailMix(0.50, 0);
}
