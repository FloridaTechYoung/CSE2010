
/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu
Course: CSE2010
Section: 3
Description of this file: 
The code was right but didn't run :: the GSA helped and confirmed that the code should be run in the upper file
solved the problem but commenting just in case
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/*
 * 
 * main code to run the code
 */
public class HW2Original {
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(args[0]));
		
		int maxLength = scan.nextInt();
		
		PuzzleSolve puzzlesolve = new PuzzleSolve();
		
		String[] alphabetArray = {"a", "b" , "c", "d", "e", "f", "g" , "h" , "i", "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" , "u" , "v" , "w" , "x" , "y" , "z"};
		
		String blank = "";
		
		ArrayList<String> input = new ArrayList<String>();
		while (scan.hasNextLine()) {
			String codeLine = scan.nextLine();
			input.add(codeLine);

		}
		
		String[] inputInArray = input.toArray(new String[input.size()]); // make an array to store the encrypted word

		
		// start the recursion
		puzzlesolve.puzzleSolve(maxLength, blank, alphabetArray, input, inputInArray);
		
		// after recursion has ended, print out the correct encrypted word
		for (String word : inputInArray) {
			System.out.println(word);
		}
		
		
	}
}
/*
 * The method PuzzleSolve is a recursive method
 *  int n means the N th alphabet in the string. when N hits 0, the recursion breaks
 *  String word is where the string is built
 *  String Unsorted is where the alphabet is stored where the recursion takes to make the string
 *  ArrayList is the input of crypted word
 *  String input Array is made because to save the encrypted word due to the arrayList is only working as a pointer
 */
class PuzzleSolve {
	
	void puzzleSolve(int n, String word, String[] Unsorted, ArrayList<String> input, String[] inputInArray) {
		
		for (String element : Unsorted) {
			if (n == 0) {
				break;
			}
			word = word + element; // add a char to the string
			// make comparison algorithm base case
			String cryptedWord = HW2crypto.encrypt(word);
			for (String inputElement : input)
			if (cryptedWord.equals(inputElement)) {
				inputElement = word;
				
				for (int i = 0; i < inputInArray.length; i++) {
					if (cryptedWord.equals(inputInArray[i])) {
						inputInArray[i] = word;
					}
				}
			}
			
			puzzleSolve(n-1,word,Unsorted,input, inputInArray);
			
			word = word.substring(0, word.length()-1);
		}
		
		
		
	}
}
