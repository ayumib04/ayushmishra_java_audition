package Vending.Machine;
import Vending.Coins.*;
import Vending.Product.*;
import java.util.*;

public class VendingMachine 
{
	private List<Double> acceptedCoins = new ArrayList<Double>();
	private Product product = null;
	private Double change = 0.0;
	private Boolean exactChange = false;

	public VendingMachine(Boolean exactChange) {
		this.exactChange = exactChange;
	}

	public VendingMachine() {}

	public void accept(Coin coin) {
		if(isQuarter(coin.getWeight(), coin.getSize()))
			acceptedCoins.add(0.25);
		if(isDime(coin.getWeight(), coin.getSize()))
			acceptedCoins.add(0.10);
		if(isNickel(coin.getWeight(), coin.getSize()))
			acceptedCoins.add(0.05);
	}

	public void selectProduct(Product product) {
		this.product = product;
	}

	public String retrieveChange() {
		String returnChange = String.format("%.2f", change);
		change = 0.0;
		return returnChange;
	}

	public void returnCoins() {
		product = null;
		acceptedCoins = new ArrayList<Double>();
	}

	public String display() {
		Double totalCents = sum(acceptedCoins);
		String msg = buildDisplay(totalCents);

		return msg;
	}

	private String buildDisplay(Double total) {
		if(product != null) {
			return displayWhenProductSelected(total);
		} else {
			return displayWhenProductNotSelected(total);
		}
	}

	private String displayWhenProductSelected(Double total) {
		if(product.getQuantity() == 0.0) {
			product = null;
			return "SOLD OUT";
		}
		if(total >= product.getPrice()) {
			change = sum(acceptedCoins) - product.getPrice();
			acceptedCoins = new ArrayList<Double>();
			product = null;
			return "THANK YOU";
		} else {
			return String.format("PRICE %.2f", product.getPrice());
		}
	}

	private String displayWhenProductNotSelected(Double total) {
		if(total > 0.0)
			return String.format("%.2f", total);
		else
			return (exactChange) ? "EXACT CHANGE ONLY" : "INSERT COIN";
	}

	private boolean isQuarter(double weight, double size) {
		return (((5.6 < weight) && (weight < 5.7)) && ((0.95 < size) && (size < 0.96)));
	}

	private boolean isDime(double weight, double size) {
		return (((2.2 < weight) && (weight < 2.3)) && ((0.7 < size) && (size < 0.71)));
	}

	private boolean isNickel(double weight, double size) {
		return (((4.95 < weight) && (weight < 5.05)) && ((0.83 < size) && (size < 0.84)));
	}

	private Double sum(List<Double> coinList) {
		Double total = 0.0;
		for(Double coin : coinList)
			total += coin;
		return total;
	}
}

