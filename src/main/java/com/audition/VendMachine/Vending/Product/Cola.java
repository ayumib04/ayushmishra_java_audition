package Vending.Product;

public class Cola implements Product
{
	private double Price;
	private int Quantity;
	public Cola(double p, int q)
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
