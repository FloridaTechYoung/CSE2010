
/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu
Course: CSE2010
Section: 3
Description of this file: Try to use stacks // need more time to think about the full code
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HW2 {
	public static void main(String args[]) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(args[0]));
		
	}
}

/*
 * most of the code in the main part will be the smae
 * push each char into the stack.
 * when the stack's length is equal to the input, start from a * k(length)
 * try to crypto the stack and check if it equals.
 * make a loop or a recursion to continue the stack ex) a * (k-1) b , a * (k-1) c, ...
 * 
*/
