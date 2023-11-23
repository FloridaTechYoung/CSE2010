/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW6
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class HW6 {

	public static void main(String[] args) throws FileNotFoundException{
		
		 Scanner scan = new Scanner(new File(args[0]));
		// get input // 7 
		
		/* for debugging
		 * Scanner scan = new Scanner(
"10 10\n"
+ "0123456789\n"
+ "0##########\n"
+ "1#h......P#\n"
+ "2#o#.#.#..#\n"
+ "3#.#.S.#..#\n"
+ "4#.......G#\n"
+ "5#.#.#.#.##\n"
+ "6#.#.#.#.##\n"
+ "7#........#\n"
+ "8#.#.#.#.##\n"
+ "9##########\n"
+ "");
		 */
		
		
		Scanner scan2 = new Scanner(System.in);
		// for moving
		
		int row = scan.nextInt();
		int column = scan.nextInt();
		
		// make ghost arrays
		int[] ghostG = {-1,-1};
		int[] ghostH = {-1,-1};
		int[] ghostO = {-1,-1};
		int[] ghostS = {-1,-1};
		
		// skip 2 lines don't know why but this works
		scan.nextLine(); 
		scan.nextLine();
		
		Node[][] map = new Node[row][column];
		int score = 0; 		
		int dotNum = 0; 	// situation for the game to end
		int pacRow = -1;	// for memorization of the pacMans location
		int pacColumn = -1;	// for memorization of the pacMans location
		
		// scan all of the map
		for (int j = 0; j < row; j++) {
			String mapRow = scan.nextLine();
			for (int i = 0; i < column; i++) {
				char element = mapRow.charAt(i+1);

				map[j][i] = new Node(element, false, false, false, false, false);
				/*
				 * The element could be 
				 * #   		: wall
				 * .   		: space with dot
				 * P   		: place with pacMan
				 * " " 		: place without dot
				 * G,H,O,S 	: ghost with dot(int the initial) 
				 * 
				 */

				
				if (element == '.') {
					map[j][i].isWall = false;
					map[j][i].hasDot = true;
					map[j][i].isGhost = false;
					map[j][i].isPacMan = false;
					map[j][i].isGoodToMove =true;
					dotNum++;
				} else if (element == ' '){
					map[j][i].isWall = false;
					map[j][i].hasDot = false;
					map[j][i].isGhost = false;
					map[j][i].isPacMan = false;
					map[j][i].isGoodToMove =true;
				} else if (element == '#') {
					map[j][i].isWall = true;
					map[j][i].hasDot = false;
					map[j][i].isGhost = false;
					map[j][i].isPacMan = false;
					map[j][i].isGoodToMove =false;
				} else if (element == 'P') {
					map[j][i].isWall = false;
					map[j][i].hasDot = false;
					map[j][i].isGhost = false;
					map[j][i].isPacMan = true;
					map[j][i].isGoodToMove =true;
					pacRow = j;
					pacColumn = i;
				} else {
					map[j][i].isWall = true;
					map[j][i].isGhost = true;
					map[j][i].isPacMan = false;
					map[j][i].isGoodToMove = true;
					
					if (map[j][i].element== 'G'||map[j][i].element== 'g') {
						ghostG[0] = j;
						ghostG[1] = i;
					} else if (map[j][i].element== 'H'||map[j][i].element== 'h') {
						ghostH[0] = j;
						ghostH[1] = i;
					} else if (map[j][i].element== 'O'||map[j][i].element== 'o') {
						ghostO[0] = j;
						ghostO[1] = i;
					} else if (map[j][i].element== 'S'||map[j][i].element== 's') {
						ghostS[0] = j;
						ghostS[1] = i;
					}
					
					// for the dot clearification
					if (Character.isUpperCase(element)) {
						map[j][i].hasDot = true;
					} else {
						map[j][i].hasDot = false;
					}
					
					
				}
				
				
			}
		}
		
		// print map
		System.out.print(" ");
		for (int i = 0; i < map[0].length; i++) {
			System.out.print(i);
		}
			System.out.println();
		for (int j = 0; j < map.length; j++) {
					
			System.out.print(j);
			for (int i = 0; i < map[j].length; i++) {
						
				System.out.print(map[j][i].element);
			}
			System.out.println();
		}
		scan.close();
				
		boolean isCorrectInput = true; // to check for correct input
		while (isCorrectInput) { // j (pacRow) i (pacColumn)
			System.out.print("Please enter your move [u(p), d(own), l(eft), r(ight)]: ");
			// up j+1 i down j-1 i left j i-1 right j i+1
			
			
			String command = scan2.next();
			
			
			switch (command) {
			case "u":
				if (map[pacRow-1][pacColumn].isWall) {
					System.out.println("invalid input");
					break;
				} else { // map[pacRow+1][pacColumn]
					
					if (!map[pacRow-1][pacColumn].isGhost) {
						
						if (map[pacRow-1][pacColumn].hasDot) {
							score++;
							map[pacRow-1][pacColumn].hasDot = false;
						}
						
						
						map[pacRow-1][pacColumn].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						map[pacRow-1][pacColumn].isPacMan = true;
						map[pacRow][pacColumn].isPacMan = false;
						pacRow = pacRow-1;
						isCorrectInput = false;
						break;
					} else { // if decide to suicide
						map[pacRow-1][pacColumn].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						
						//print map
						System.out.print(" ");
						for (int i = 0; i < map[0].length; i++) {
							System.out.print(i);
						}
						System.out.println();
						for (int j = 0; j < map.length; j++) {
							
							System.out.print(j);
							for (int i = 0; i < map[j].length; i++) {
								
								System.out.print(map[j][i].element);
							}
							System.out.println();
						}
						
						System.out.println("A ghost is not hungry any more!");
						System.exit(0);
					}
				}
			case "d":
				if (map[pacRow+1][pacColumn].isWall) {
					System.out.println("invalid input");
					break;
				} else { // map[pacRow+1][pacColumn]
					
					if (!map[pacRow+1][pacColumn].isGhost) {
						
						if (map[pacRow+1][pacColumn].hasDot) {
							score++;
							map[pacRow+1][pacColumn].hasDot = false;
						}
						
						
						map[pacRow+1][pacColumn].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						map[pacRow+1][pacColumn].isPacMan = true;
						map[pacRow][pacColumn].isPacMan = false;
						pacRow = pacRow+1;
						isCorrectInput = false;
						break;
					} else { // if decide to suicide
						map[pacRow-1][pacColumn].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						
						//print map
						System.out.print(" ");
						for (int i = 0; i < map[0].length; i++) {
							System.out.print(i);
						}
						System.out.println();
						for (int j = 0; j < map.length; j++) {
							
							System.out.print(j);
							for (int i = 0; i < map[j].length; i++) {
								
								System.out.print(map[j][i].element);
							}
							System.out.println();
						}
						
						System.out.println("A ghost is not hungry any more!");
						System.exit(0);
					}
				}
			case "l":
				if (pacColumn - 1 < 0||map[pacRow][pacColumn-1].isWall) {
					System.out.println("invalid input");
					break;
				} else { // map[pacRow][pacColumn-1]
					
					if (!map[pacRow][pacColumn-1].isGhost) {
						
						if (map[pacRow][pacColumn-1].hasDot) {
							score++;
							map[pacRow][pacColumn-1].hasDot = false;
						}
						
						map[pacRow][pacColumn-1].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						map[pacRow][pacColumn-1].isPacMan = true;
						map[pacRow][pacColumn].isPacMan = false;
						pacColumn = pacColumn-1;
						isCorrectInput = false;
						break;
					} else { // if decide to suicide
						map[pacRow][pacColumn-1].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						
						//print map
						System.out.print(" ");
						for (int i = 0; i < map[0].length; i++) {
							System.out.print(i);
						}
						System.out.println();
						for (int j = 0; j < map.length; j++) {
							
							System.out.print(j);
							for (int i = 0; i < map[j].length; i++) {
								
								System.out.print(map[j][i].element);
							}
							System.out.println();
						}
						
						System.out.println("A ghost is not hungry any more!");
						System.exit(0);
					}
				}
			case "r":
				if (pacColumn + 1 > column||map[pacRow][pacColumn+1].isWall) {
					System.out.println("invalid input");
					break;
				} else { // map[pacRow][pacColumn-1]
					
					if (!map[pacRow][pacColumn+1].isGhost) {
						
						if (map[pacRow][pacColumn+1].hasDot) {
							score++;
							map[pacRow][pacColumn+1].hasDot = false;
						}
						
						map[pacRow][pacColumn+1].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						map[pacRow][pacColumn+1].isPacMan = true;
						map[pacRow][pacColumn].isPacMan = false;
						pacRow = pacRow+1;
						isCorrectInput = false;
						break;
					} else { // if decide to suicide
						map[pacRow][pacColumn+1].element = 'P';
						map[pacRow][pacColumn].element = ' ';
						
						//print map
						System.out.print(" ");
						for (int i = 0; i < map[0].length; i++) {
							System.out.print(i);
						}
						System.out.println();
						for (int j = 0; j < map.length; j++) {
							
							System.out.print(j);
							for (int i = 0; i < map[j].length; i++) {
								
								System.out.print(map[j][i].element);
							}
							System.out.println();
						}
						
						System.out.println("A ghost is not hungry any more!");
						System.exit(0);
					}
				}
				default:
					System.out.println("invalid input");
			}
			
			System.out.println();
		}
		scan2.close();
		
		// print map
		System.out.print(" ");
		for (int i = 0; i < map[0].length; i++) {
			System.out.print(i);
		}
		System.out.println();
		for (int j = 0; j < map.length; j++) {
			
			System.out.print(j);
			for (int i = 0; i < map[j].length; i++) {
				
				System.out.print(map[j][i].element);
			}
			System.out.println();
		}
		
		System.out.println("Points: " + score);
		
		
		
		
		// Check if the ghost exists first, then find the path with the findPath method
		if (!(ghostG[1]==-1)) {
			Queue<Integer[]> queue = new LinkedList<>();
			System.out.print("Ghost G" + ": ");
			boolean[][] hasSearched = new boolean[row][column];
			findPath(map,hasSearched,ghostG[0],ghostG[1],pacRow,pacColumn, queue);
			System.out.println();
			hasSearched = null;
		}
		
		if (!(ghostH[1]==-1)) {
			Queue<Integer[]> queue = new LinkedList<>();
			System.out.print("Ghost H" + ": ");
			boolean[][] hasSearched = new boolean[row][column];
			findPath(map,hasSearched,ghostH[0],ghostH[1],pacRow,pacColumn, queue);
			System.out.println();
			hasSearched = null;
		}
		
		if (!(ghostO[1]==-1)) {
			Queue<Integer[]> queue = new LinkedList<>();
			System.out.print("Ghost O" + ": ");
			boolean[][] hasSearched = new boolean[row][column];
			findPath(map,hasSearched,ghostO[0],ghostO[1],pacRow,pacColumn, queue);
			System.out.println();
			hasSearched = null;
		}
		
		if (!(ghostS[1]==-1)) {
			Queue<Integer[]> queue = new LinkedList<>();
			System.out.print("Ghost S" + ": ");
			boolean[][] hasSearched = new boolean[row][column];
			findPath(map,hasSearched,ghostS[0],ghostS[1],pacRow,pacColumn, queue);
			System.out.println();
			hasSearched = null;
		}
		
		if (dotNum == 0) {
			System.out.println("Pac-man is full");
			System.exit(0);
		}
		
	}
	
	private static void findPath(Node[][] map,boolean[][] hasSearched, int ghostRow, int ghostColumn, int pacRow, int pacColumn, Queue<Integer[]> queue) {
		queue.offer(new Integer[] {ghostRow,ghostColumn});
		hasSearched[ghostRow][ghostColumn] = true;
		if (ghostRow < 0 || ghostRow >= map.length || ghostColumn < 0 || ghostColumn >= map[0].length) {
            return;
        }
		// when the ghost has reached the pacman, print the que
		if (map[ghostRow][ghostColumn].isPacMan) { // ghostRow == pacRow && ghostColumn == pacColumn
			int length = queue.size();
			System.out.print(length + " ");
			while (!queue.isEmpty()) {
				
	            Integer[] array = queue.poll();
	            System.out.print("(");	
	            System.out.print(array[0] + "," + array[1]);
	            System.out.print(")");
	        }
			
		}
		
		// up + check if it is a valid move
		if (ghostRow-1 > 0 &&!hasSearched[ghostRow-1][ghostColumn] && map[ghostRow-1][ghostColumn].isGoodToMove) {
			findPath(map,hasSearched,ghostRow-1,ghostColumn,pacRow,pacColumn,queue);
		}
		
		// right + check if it is a valid move
		if (ghostColumn+1 < map[0].length &&!hasSearched[ghostRow][ghostColumn+1] &&map[ghostRow][ghostColumn+1].isGoodToMove) {
			findPath(map,hasSearched,ghostRow,ghostColumn+1,pacRow,pacColumn,queue);
		}
		
		//left + check if it is a valid move
		if (ghostColumn-1 > 0 && !hasSearched[ghostRow][ghostColumn-1] &&map[ghostRow][ghostColumn-1].isGoodToMove) {
			findPath(map,hasSearched,ghostRow,ghostColumn-1,pacRow,pacColumn,queue);
		}
		// down + check if it is a valid move
		if (ghostRow+1 < map.length && !hasSearched[ghostRow+1][ghostColumn] && map[ghostRow-1][ghostColumn].isGoodToMove) {
			findPath(map,hasSearched,ghostRow+1,ghostColumn,pacRow,pacColumn,queue);
		}
		
	}

	public static class Node{
		char element;
		boolean isPacMan;
		boolean isWall;
		boolean hasDot;
		boolean isGhost;
		boolean isGoodToMove;
		
		public Node(char element, boolean isWall, boolean hasDot, boolean isGhost, boolean isPacMan, boolean isGoodToMove) {
	        this.element = element;
	        this.isWall = isWall;
	        this.hasDot = hasDot;
	        this.isGhost = isGhost;
	        this.isPacMan = isPacMan;
	        this.isGoodToMove = isGoodToMove;
	    }
		
		
		
	}

}
