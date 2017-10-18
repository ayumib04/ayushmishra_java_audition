package Vending.Product;

public class Candy implements Product
{
	private double Price;
	private int Quantity;
	public Candy(double p, int q)
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
