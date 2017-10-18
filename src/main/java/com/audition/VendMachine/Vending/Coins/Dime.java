package Vending.Coins;

public class Dime implements Coin
{
	private double Weight;
	private double Size;
	public Dime(double w, double s)
	{
		Weight = w;
		Size = s;
	}
	public double getWeight()
	{
		return this.Weight;
	}
	public double getSize()
	{
		return this.Size;
	}
}
