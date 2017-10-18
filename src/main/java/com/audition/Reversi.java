import java.util.Scanner;


public class Reversi {

	//Function to populate grid with dead cells
	public static void initializeOriginal(String[][] grid, String[][] grid2nd)
	{
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j< grid[0].length; j++){
				grid2nd[i][j] = grid[i][j];
			}
		}
	}
	
	public static void printgrid(String[][] grid)
	{
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j< grid[0].length; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public static String[][] opponentpos(int grow, int gcol, String[][] grid, String[][] grid2nd, String Who)
	{
		//Now loop through the neighbors to check for live ones
		for(int x = Math.max(0, grow-1); x <= Math.min(grow+1, grid.length-1); x++)
		{
			for(int y = Math.max(0, gcol-1); y <= Math.min(gcol+1, grid[0].length-1); y++)
			{
				if((x != grow || y != gcol)&&((x+y)!=(grow+gcol) || (x+y) != (grow+gcol+2) || (x+y) != (grow+gcol-2)))
				{//all neighbors except self or diagonally placed
					if((grid[x][y].toString().equals("W") && Who.equals("B") && grid[grow][gcol].toString().equals("B")) || (grid[x][y].toString().equals("B") && Who.equals("W") && grid[grow][gcol].toString().equals("W") ))
					{
						if(x<grow && x-1>=0)
							grid2nd[x-1][y] = "0";
						else if(x>grow && x+1<grid.length)
							grid2nd[x+1][y] = "0";
						else if(y<gcol && y-1>=0)
							grid2nd[x][y-1] = "0";
						else if(y>gcol && y+1<grid[0].length)
							grid2nd[x][y+1] = "0";
					}
				}
			}
		}
		return grid2nd;
	}
	
	//function to generate the 2nd generation of the grid
	public static String[][] gen2nd(String[][] grid, String WhosChance)
	{
		String[][] grid2ndgen = new String[grid.length][grid[0].length];
		initializeOriginal(grid,grid2ndgen);//populate with dead cells
		for(int i = 0; i < grid.length; i++)
		{//Traverse through the grid
			for(int j = 0; j < grid[0].length; j++)
			{//column limit grid[0]
				grid2ndgen = opponentpos(i,j,grid, grid2ndgen,WhosChance);
			}
		}
		return grid2ndgen;
	}
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Enter grid Dimensions (row col)");
		String line = in.nextLine();
		int r = Integer.parseInt(line.split(" ")[0].toString());
		int c = Integer.parseInt(line.split(" ")[1].toString());
		String[][] grid = new String[r][c];
		
		System.out.println("Enter the 1st gen grid row - wise (without spaces)");
		for(int i = 0; i < r; i++)
		{
			line = in.nextLine();
			String[] split = line.split("");
			for(int j = 0; j < c; j++)
				grid[i][j] = split[j].toString();
		}//absorbed the User entered field into grid
		
		System.out.println("Enter who's turn to play:");
		String who = in.next();
		String[][] result = gen2nd(grid, who);
		printgrid(result);
		in.close();
	}
}
