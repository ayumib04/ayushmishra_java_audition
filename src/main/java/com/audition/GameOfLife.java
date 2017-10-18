import java.util.Scanner;


public class GameOfLife 
{

	//Function to populate grid with dead cells
	public static void initializeDead(String[][] grid)
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j< grid[0].length; j++)
			{
				grid[i][j] = ".";
			}
		}
	}
	
	public static void printgrid(String[][] grid)
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j< grid[0].length; j++)
			{
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public static int getlivecount(int gridrow, int gridcol, String[][] grid)
	{
		int livecount = 0;
		//Now loop through the neighbors to check for live ones
		for(int x = Math.max(0, gridrow-1); x <= Math.min(gridrow+1, grid.length-1); x++)
		{
			for(int y = Math.max(0, gridcol-1); y <= Math.min(gridcol+1, grid[0].length-1); y++)
			{
				if(x != gridrow || y != gridcol) //all neighbors except self
				{
					if(grid[x][y].toString().equals("*"))
					{
						livecount = livecount + 1;
					}
				}
			}
		}
		return livecount;
	}
	
	//function to generate the 2nd generation of the grid
	public static String[][] gen2nd(String[][] grid)
	{
		String[][] grid2ndgen = new String[grid.length][grid[0].length];
		initializeDead(grid2ndgen);//populate with dead cells
		int livecount = 0;
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				livecount = getlivecount(i,j,grid);
				
				//Implement conditions
				if(grid[i][j].toString().equals("*")) // for live cells
				{ 
					grid2ndgen[i][j] = livecount < 2 ? "." : "*"; //underpopulation
					grid2ndgen[i][j] = livecount > 3 ? "." : "*"; //overcrowding
					grid2ndgen[i][j] = (livecount >= 2 && livecount <= 3) ? "*" : "."; //exactly 3 live neighbors
				}
				else if(grid[i][j].toString().equals("."))// for dead cells
				{ 
					grid2ndgen[i][j] = livecount == 3 ? "*" : ".";
				}
				
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
		System.out.println("Enter the 1st gen grid row - wise");
		for(int i = 0; i < r; i++)
		{
			line = in.nextLine();
			String[] split = line.split("");
			for(int j = 0; j < c; j++)
				grid[i][j] = split[j].toString();
		}
		//printgrid(grid);
		String[][] result = gen2nd(grid);
		printgrid(result);
		in.close();
	}
}
