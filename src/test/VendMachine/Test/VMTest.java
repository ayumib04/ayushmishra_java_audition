package Vending.Test;
import Vending.Machine.*;
import Vending.GlobalVariables.*;

public class VMTest 
{
	private VendingMachine vm;
	
	public void init()
	{
		vm = new VendingMachine();
	}
	
	//Empty Display INSERT COINS
	public void emptyDisplay()
	{
		System.out.println(vm.display());
	}
	
	//Accept Coins
	public void acceptN()
	{
		vm.accept(Global.N);
		System.out.println(vm.display());
	}
	public void acceptD()
	{
		vm.accept(Global.D);
		System.out.println(vm.display());
	}
	public void acceptQ()
	{
		vm.accept(Global.Q);
		System.out.println(vm.display());
	}
	public void multi()
	{
		vm.accept(Global.D);
		vm.accept(Global.N);
		vm.accept(Global.Q);
		System.out.println(vm.display());
	}
	
	//Accept Valid and reject invalid coins
	public void acceptReject()
	{
		vm.accept(Global.Q);
		vm.accept(Global.P);
		vm.accept(Global.P);
		vm.accept(Global.D);
		System.out.println(vm.display());
	}
	
	//Exact change vending
	public void exactChange()
	{
		vm.accept(Global.N);
		vm.accept(Global.D);
		vm.accept(Global.Q);
		vm.accept(Global.Q);
		vm.selectProduct(Global.CA);
		System.out.println(vm.display()); //Thank You
		System.out.println(vm.display()); //Insert Coins
	}
	
	//Return Change
	public void returnChange()
	{
		vm.accept(Global.Q);
        vm.accept(Global.D);
        vm.accept(Global.D);
        vm.accept(Global.D);
        vm.selectProduct(Global.CH);
        System.out.println(vm.display()); //Thank You
		System.out.println(vm.display()); //Insert Coins
		System.out.println("Your change: "+vm.retrieveChange()); //0.05
    }
	
	//Return Coins
	public void returnCoins()
	{
		vm.accept(Global.Q);
        vm.accept(Global.D);
        vm.accept(Global.D);
        vm.accept(Global.D);
        System.out.println(vm.display()); //0.55
        vm.returnCoins();
        System.out.println(vm.display()); //Insert Coins
	}
	
	//Sold Out
	public void soldOut()
	{
		vm.selectProduct(Global.TM);
		System.out.println(vm.display()); //SOLD OUT
        System.out.println(vm.display()); //Insert Coins
	}
	
	public static void main(String[] args)
	{
		VMTest obj = new VMTest();
		obj.init();
		obj.acceptN();
		obj.acceptD();
		obj.acceptQ();
		obj.multi();
		obj.acceptReject();
		obj.exactChange();
		obj.returnChange();
		obj.returnCoins();
		obj.soldOut();
	}
}
