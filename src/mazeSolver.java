package genTrackTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class mazeSolver {
	
	/*
	 * class the defines a position in Maze matrix
	 */
	public static class MazePosition{
		public int pos_x;
		public int pos_y;
		
		public MazePosition(int pos_x, int pos_y) {
			this.pos_x = pos_x;
			this.pos_y = pos_y;
		}
		
		@Override
		public String toString() {
		      return "pos_x: " + pos_x + "', pos_y=" + pos_y;
		}
		
		@Override
	    public boolean equals(Object o) {
	        if (o == this) return true;  
	        if (o instanceof MazePosition) {
	           MazePosition that = (MazePosition) o;
	           return ((this.pos_x == that.pos_x)  && (this.pos_y == that.pos_y));
	        }
	        return false;
	    }
	}
	
	public static class Maze {
		
		public int width; //maze's width
		public int height; // maze's height
		public MazePosition start; // maze's start positon
		public MazePosition end; // maze's end positon
		public int mazeMatrix[][]; // maze's initial matrix
		
		public Maze(){}
		
		
		/*
		 * method which print matrix
		 */
		public void printMatrix() {
		   for(int i = 0; i < height; i++)
		   {
		      for(int j = 0; j < width; j++)
		      {
		         System.out.printf("%1d ", mazeMatrix[j][i]);
		      }
		      System.out.println();
		   }
		}
		
		/*
		 * method which print solution matrix
		 */
		public void printSolutionMatrix(ArrayList<MazePosition> solutionPath) {
			for (int i = 0; i < height; i++)
			   {
			      for(int j = 0; j < width; j++)
			      {
			    	 if(new MazePosition(j,i).equals(this.start))
			    		 System.out.printf("%1c ", 'S');
			    	 else if(new MazePosition(j,i).equals(this.end))
			    		 System.out.printf("%1c ", 'E');
			    	 else if(solutionPath.contains(new MazePosition(j, i)))
				    	 System.out.printf("%1c ", 'X');
			    	 else if(mazeMatrix[j][i]==1)
			    		 System.out.printf("%1c ",'#');
			    	 else 
			    		 System.out.printf("%1c ", ' ');
			      }
			      System.out.println();
			   }
		}
	}
	
	/*
	 * method which loads maze from file
	 */
	public static Maze loadMaze(String filename) {
		Maze maze = new Maze();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));
		    
		    String line = br.readLine();
		    
		    // read first line to acquire maze's width and height
		    String[] lineElements = line.split(" ");
		    maze.width = Integer.parseInt(lineElements[0]);
		    maze.height = Integer.parseInt(lineElements[1]);
		    
		    // read second line to acquire maze's start position
		    line = br.readLine();
		    lineElements = line.split(" ");
		    maze.start = new MazePosition(Integer.parseInt(lineElements[0]),
		    		Integer.parseInt(lineElements[1]));
		    
		    // read second line to acquire maze's end position
		    line = br.readLine();
		    lineElements = line.split(" ");
		    maze.end = new MazePosition(
		    	Integer.parseInt(lineElements[0]),
	    		Integer.parseInt(lineElements[1])
	    		);
		    
		    
		    // read maze's matrix
		    line = br.readLine();
		    maze.mazeMatrix = new int[maze.width][maze.height];
		    int indexRow=0;
		    while (line != null) {
		    	lineElements = line.split(" ");
		    	for (int indexColumn=0; indexColumn<lineElements.length;indexColumn++) {
		    		maze.mazeMatrix[indexColumn][indexRow] = Integer.parseInt(lineElements[indexColumn]);
		    	}
		        line = br.readLine();
		        indexRow++;
		    }
		    br.close();
		} catch(Exception e) {
			return null;
		}
		return maze;
	}
	
	/*
	 * method which solves maze
	 */
	public static ArrayList<MazePosition> solveMaze(Maze maze,ArrayList<MazePosition> currentPath){
		//get currentPositon
		MazePosition currentPosition = currentPath.get(currentPath.size()-1);
		
		//check if solution was found
		if (currentPosition.pos_x==maze.end.pos_x && currentPosition.pos_y== maze.end.pos_y) {
			System.out.println("FOUND SOLUTION");
			System.out.println(currentPath);
			return currentPath;
		}
		
		//next possible steps
		MazePosition nextRight = new MazePosition(currentPosition.pos_x+1, currentPosition.pos_y);
		MazePosition nextDown = new MazePosition(currentPosition.pos_x, currentPosition.pos_y+1);
		MazePosition nextLeft = new MazePosition(currentPosition.pos_x-1, currentPosition.pos_y);
		MazePosition nextUp = new MazePosition(currentPosition.pos_x, currentPosition.pos_y-1);
		
		// if can go right, goes right and update currentPath
		if (
			maze.mazeMatrix[currentPosition.pos_x+1][currentPosition.pos_y]==0 &&
			!currentPath.contains(nextRight)
			)
		{
			
			ArrayList<MazePosition> currentPathAux = new ArrayList<MazePosition>();
			currentPathAux.addAll(currentPath);
			currentPathAux.add(nextRight);
			
			ArrayList<MazePosition> res = solveMaze(maze,currentPathAux);
			if(res!=null) {
				return res;
			} 
		}
		// if can go down, goes down and update currentPath
		if(maze.mazeMatrix[currentPosition.pos_x][currentPosition.pos_y+1]==0 &&
				!currentPath.contains(nextDown)){
			ArrayList<MazePosition> currentPathAux = new ArrayList<MazePosition>();
			currentPathAux.addAll(currentPath);
			currentPathAux.add(nextDown);
			
			ArrayList<MazePosition> res = solveMaze(maze,currentPathAux);
			if (res!=null) {
				return res;
			}
		}
		// if can go left, goes left and update currentPath
		if(maze.mazeMatrix[currentPosition.pos_x-1][currentPosition.pos_y]==0 &&
				!currentPath.contains(nextLeft)){
			ArrayList<MazePosition> currentPathAux = new ArrayList<MazePosition>();
			currentPathAux.addAll(currentPath);
			currentPathAux.add(nextLeft);
			ArrayList<MazePosition> res = solveMaze(maze,currentPathAux);
			if (res!=null) {
				return res;
			}
		}
		// if can go up, goes up and update currentPath
		if(maze.mazeMatrix[currentPosition.pos_x][currentPosition.pos_y-1]==0 &&
				!currentPath.contains(nextUp)){
			ArrayList<MazePosition> currentPathAux = new ArrayList<MazePosition>();
			currentPathAux.addAll(currentPath);
			currentPathAux.add(nextUp);
			ArrayList<MazePosition> res = solveMaze(maze,currentPathAux);
			if (res!=null) {
				return res;
			}
		}
		
		//no solution found
		return null;			
	}
	
	
	
	public static void main(String[] args){
		//load maze from file
		if (args.length!=1) {
			System.out.println("Please enter one file name");
			return;
		}
			
		Maze maze = loadMaze(args[0]);
		if(maze == null){
			System.out.println("Error: Maze not loaded");
			return;
		}
		
		// solve maze
		ArrayList<MazePosition> currentPath = new ArrayList<MazePosition>();
		currentPath.add(new MazePosition(maze.start.pos_x,maze.start.pos_y));
		
		// solution 
		ArrayList<MazePosition> solutionPath = solveMaze(maze,currentPath);

		if (solutionPath==null) {
			System.out.println("Error: solution not found");
		} else {
			//System.out.println("Solution found!");
			//print solution
			maze.printSolutionMatrix(solutionPath);
		}
	}
}
