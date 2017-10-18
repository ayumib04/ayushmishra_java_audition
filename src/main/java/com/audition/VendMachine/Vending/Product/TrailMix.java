package Vending.Product;

public class TrailMix implements Product 
{
	private double Price;
	private int Quantity;
	public TrailMix(double p, int q)
	{
		Price = p;
		Quantity = q;
	}
	public double getPrice()
	{
		return this.Price;
	}
	public double getQuantity()
	{
		return this.Quantity;
	}
}
