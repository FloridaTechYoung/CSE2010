/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW3
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;


public class HW3 {

	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub

		Scanner scan1 = new Scanner(new File(args[0])); // get the data file first
		Scanner scan2 = new Scanner(new File(args[1])); // receive 
		
		/*
		 * scan the first Line of the data file and check how many categories of TS there are
		 * set the categories in the roots child
		 */
		Tree tree = new Tree();
		String firstLine = scan1.nextLine();
		/*
		 * initial input : hurricanes(0) cat1(1) cat2(2) cat3(3)
		 */
		String[] words = firstLine.split(" "); 
		for (int i = 1; i < words.length; i++) { // start with int i = 1 because the first is "hurricane"
			Node catChild = new Node(words[i]);
			tree.root.addChild(catChild);
		}
		int catNum = words.length - 1; // get the number of categories
		 
		/*
		 * second scan each line
		 * cat3 FL_cat3 LA_cat3
		 * cat1 FL_cat1 TX_cat1
		 * cat2 FL_cat2 TX_cat2
		 */
		for (int i = 0; i < catNum; i++) { // put each state below into category Ex) cat1 -> FL_cat1
			String catLine = scan1.nextLine();
			String[] stateCat = catLine.split(" "); 
			
			
			for (Node child : tree.root.children) { // search through the childs of the root
				String category = child.data;
					if (category.equals(stateCat[0])) {
						for (int j = 1; j < stateCat.length; j++) {
							Node stateCatNode = new Node(stateCat[j]);
							child.addChild(stateCatNode);
						}
					}
			}
			
		}
		
		/*
		 * scan the FL_cat3 args[0]
		 * Ex) FL_cat3 Marco Jerry
		 * Ex) LA_cat3 Barry Laura
		 * first scan cat3 which is name[0].substring(3);
		 * after, scan the names of the hurricane, and set them in alphabetical order
		 * 
		 */
		while (scan1.hasNextLine()) {
			String nameLine = scan1.nextLine();
			String[] name = nameLine.split(" ");
			
			String[] nameArray = new String[name.length - 1];
			for (int i = 0; i < name.length-1; i++) {
				nameArray[i] = name[i+1];
			}
			Arrays.sort(nameArray); // the names of the hurricane are sorted
			
			String category = name[0].substring(3);
			for (Node child : tree.root.children) {
				if (child.data.equals(category)) {
					for (Node state_catNum : child.children) {
						if (state_catNum.data.equals(name[0])) {
							for (int i = 0; i < nameArray.length; i++) {
								Node NameNode = new Node(nameArray[i]) ;
								state_catNum.addChild(NameNode);
							}
						}
					}
				}
				
			}

		}
		
		String output = tree.root.toString();
		// System.out.println(output);
		
		/*
		 * scan1 is done,
		 * Now it is time for scan2 which is queries
		 * 
		 */
		while(scan2.hasNextLine()) {
			String query = scan2.nextLine();
			String[] lineQuery = query.split(" ");
			String command = lineQuery[0];
			
			ArrayList<String> nameOfHurricane = new ArrayList<String>();
			int size = 0;
			
			switch (command) {
			
			case "GetNamesByCategory":  // + cat2 all outputs should be on alphabetical order
				System.out.print("GetNamesByCategory " + lineQuery[1]); // print out --> GetNamesByCategory cat2

				
				for (Node child : tree.root.children) { // tree.root.children is category (cat1,cat2...)
					if (child.data.equals(lineQuery[1])) {
						for (Node state_catNum : child.children) {
							for (Node name: state_catNum.children) {
								nameOfHurricane.add(name.data);
								size++;
								
							}
						}
						break;
					}
					
				}
				// after storing all of the names of the hurricane, sort them and print in alphabetical order
				
				Collections.sort(nameOfHurricane);
				int arrayListSize = nameOfHurricane.size();
				
				/*
				 * for (int i = 0; i < arrayListSize; i++) {
					String compare = nameOfHurricane.get(i);
					for (int j = i+1; j < arrayListSize; j++) {
						if (compare.equals(nameOfHurricane.get(j))) {
							nameOfHurricane.remove(j);
							arrayListSize--;
							j--;
							System.out.println(12);
						}
					}
				}
				 * 
				 */
				for (int i = arrayListSize - 1; i >= 0; i--) {
				    String compare = nameOfHurricane.get(i);
				    for (int j = i - 1; j >= 0; j--) {
				        if (compare.equals(nameOfHurricane.get(j))) {
				            nameOfHurricane.remove(j);
				            arrayListSize--;
				        }
				    }
				}

				
				// print the array
				for (int i = 0; i < arrayListSize; i++) {
					System.out.print(" " + nameOfHurricane.get(i));
				}
				
				// reset the parameters after at the end of one switch
				nameOfHurricane = null;
				size = 0;
				System.out.println();
				break;
					
				/*
				 * input : GetNamesByState FL
				 * output : GetNamesByState FL Gordon Jerry Marco Rebekah Valerie William
				 * 
				 */
			case "GetNamesByState":
				// get all of the names of hurricanes with no alphabetical order
				System.out.print(lineQuery[0] + " " + lineQuery[1]);
				String stateSymbol = lineQuery[1];
				for (Node child : tree.root.children) {
					for (Node state_catNum : child.children) {
						if (state_catNum.data.substring(0,2).equals(stateSymbol)) {
							for (Node name: state_catNum.children) {
								nameOfHurricane.add(name.data);
								size++;
							}
						}
					}
				}
				
				// delete duplicate names
				Collections.sort(nameOfHurricane);
				arrayListSize = nameOfHurricane.size();
				for (int i = arrayListSize - 1; i >= 0; i--) {
				    String compare = nameOfHurricane.get(i);
				    for (int j = i - 1; j >= 0; j--) {
				        if (compare.equals(nameOfHurricane.get(j))) {
				            nameOfHurricane.remove(j);
				            arrayListSize--;
				        }
				    }
				}
				// print the array
				for (int i = 0; i < arrayListSize; i++) {
					System.out.print(" " + nameOfHurricane.get(i));
				}
				
				// reset the parameters after at the end of one switch
				nameOfHurricane = null;
				size = 0;
				System.out.println();
				break;
				
				/*
				 *  input: GetNamesByCategoryAndState cat1 TX
				 *  output: GetNamesByCategoryAndState cat1 TX Jerry Valerie
				 * 
				 */
			case "GetNamesByCategoryAndState":
				System.out.print(lineQuery[0] + " " + lineQuery[1] + " " + lineQuery[2]);
				for (Node child : tree.root.children) {
					if (child.data.equals(lineQuery[1])) {
						for (Node state_catNum : child.children) {
							if (state_catNum.data.substring(0,2).equals(lineQuery[2])) {
								for (Node name: state_catNum.children) {
									System.out.print(" " + name.data);
								}
							}
						}
					}
				}
				System.out.println();
				
				break;
				
				/*
				 * input : GetCategory Harvey
				 * output : GetCategory Harvey cat4 **** Must output in reverse order!!
				 */
			case "GetCategory":
				System.out.print(lineQuery[0] + " " + lineQuery[1]); // GetCategory Harvey
				ArrayList<String> catNumStringList = new ArrayList<String>();
				for (Node child : tree.root.children) {
					String catNumString = child.data;
					
					boolean isPrint = true;
					for (Node state_catNum : child.children) {
						for (Node name: state_catNum.children) {
							if (name.data.equals(lineQuery[1])) {
								if (isPrint) {							
									catNumStringList.add(catNumString);
									isPrint = false;
								}
								
							}
						}
					}
					isPrint = true;
				}
				for (int i = catNumStringList.size()-1; i >= 0; i--) {
					System.out.print(" " + catNumStringList.get(i));
				}
				System.out.println();
				catNumStringList = null;
				break;
				/*
				 * input : GetNamesWithMultipleCategories
				 * output : GetNamesWithMultipleCategories Bonnie Colin Don Dorian Emily Henri Julia Karl Katia
				 * nameOfHurricane : arrayList : to store whole names of one category
				 * duplicateNameFound : arrayList : add when there is a duplicage name found
				 * 1. Store the names in 1 whole categories with an arrayList(nameOfHurricane) ** check if there is any duplicate
				 * 2. for each arrayList, compare the whole names add add to new arrayList(duplicateNameFound) ** do this catNum-1
				 * 3. after the loop has ended, again check the arrayList(duplicateNameFound) if there are any duplicate names
				 * 4. after, sort in alphabetical order
				 * 
				 */
			case "GetNamesWithMultipleCategories": // 
				ArrayList<String> duplicateNameFound = new ArrayList<String>();
				nameOfHurricane = new ArrayList<>();
				
				System.out.print(lineQuery[0]);
				// 1. Store the names in 1 whole categories with an arrayList(nameOfHurricane) ** check & delete if there is any duplicate
				for (int i = tree.root.size()-1; i>=1; i--) {
					Node categoryNode =  tree.root.children.get(i); // category only
					for (int j = 0; j < categoryNode.size(); j++) {
						Node nameNode = categoryNode.children.get(j); // category and state
						for (int k = 0; k < nameNode.children.size(); k++) {
								nameOfHurricane.add(nameNode.children.get(k).data);
							
						}
					}
					
					// 1-1 check and delete duplicate
					Collections.sort(nameOfHurricane);
					Iterator<String> nameofHurricaneIterator = nameOfHurricane.iterator();
					String compare1 = nameofHurricaneIterator.next();
					while (nameofHurricaneIterator.hasNext()) {
						String compare2 = nameofHurricaneIterator.next();
						if (compare1.equals(compare2)) {
							nameofHurricaneIterator.remove();
						}
						compare1 = compare2;
					}
					
					
					/*
					 * for (int i2 = arrayListSize - 1; i2 >= 0; i2--) {
						System.out.println(i2);
					    String compare = nameOfHurricane.get(i2);
					    for (int j = i2 - 1; j >= 0; j--) {
					        if (compare.equals(nameOfHurricane.get(j))) {
					            nameOfHurricane.remove(j);
					            arrayListSize--;
					        }
					    }
					}
					 * 
					 */

					// 2. for each arrayList, compare the whole names add add to new arrayList(duplicateNameFound) ** do this catNum-1
					for (int i3 = i-1; i3>=0; i3--) {
						Node compareCatNode = tree.root.children.get(i3); // cat
						for (int j = 0; j < compareCatNode.size(); j++) {
							Node compare_state_cat_Node = compareCatNode.children.get(j); // cat&state
							for (int k = 0; k < compare_state_cat_Node.size(); k++) {
								for (String name : nameOfHurricane) {
									if (name.equals(compare_state_cat_Node.children.get(k).data)) {
										duplicateNameFound.add(name);
									}
								}
							}
						}
					}
					
					nameOfHurricane.clear();
				}
				
				// 2-1 check and delete duplicate
				
				Collections.sort(duplicateNameFound);
				Iterator<String> duplicateIterator = duplicateNameFound.iterator();
				String compare10 = duplicateIterator.next();
				while (duplicateIterator.hasNext()) {
					String compare20 = duplicateIterator.next();
					if (compare10.equals(compare20)) {
						duplicateIterator.remove();
					}
					compare10 = compare20;
				}
			
				
				if (duplicateNameFound.size() > 0) {
					
					for (String name : duplicateNameFound) {
						System.out.print(" " + name);
					}
				}
				
				System.out.println();
				
				break;
			case "GetNamesWithMultipleStates": //
				System.out.print(lineQuery[0]);
				ArrayList<String> allNames = new ArrayList<String>();
				nameOfHurricane = new ArrayList<>();
				
				for(int i = tree.root.size()-1; i > 0; i--) {
					Node categoryNode = tree.root.children.get(i); // category only
					for (int j = 0; j < categoryNode.size(); j++) {
						Node state_catNode = categoryNode.children.get(j); // state_catNode
						for (int k = 0; k < state_catNode.size(); k++) {
							allNames.add(state_catNode.children.get(k).data);
						}
					}
				}
				
				Collections.sort(allNames);
				
				/*
				 * remove duplicate
				 */
				Iterator<String> allNamesIterator = allNames.iterator();
				String compare100 = allNamesIterator.next();
				while (allNamesIterator.hasNext()) {
					String compare20 = allNamesIterator.next();
					if (compare100.equals(compare20)) {
						allNamesIterator.remove();
					}
					compare100 = compare20;
				}
				
				GetState getState = new GetState();
				
				for (String name : allNames) {
					getState.ifMultipleState(name, tree);

				}
				
				
				System.out.println();
				
				
				break;
				
				/*
				 * similar to getCategory
				 * GetState Don LA TX
				 */
			case "GetState": 
				
				System.out.print(lineQuery[0] + " " + lineQuery[1]); // GetCategory Harvey
				ArrayList<String> catNumStringList1 = new ArrayList<String>();
				for (Node child : tree.root.children) { // category
					
					for (Node state_catNum : child.children) { // ST_cat0
						String ST_name = state_catNum.data.substring(0,2);
						for (Node name: state_catNum.children) { // name of each hurricane
							if (name.data.equals(lineQuery[1])) {
									catNumStringList1.add(ST_name);
								
							}
						}
					}
				}
				
				Collections.sort(catNumStringList1);
				String temp1;
				for (int i = catNumStringList1.size()-1; i >= 0; i--) {
					temp1 = catNumStringList1.get(i);
					
					if (i == 0) {
						System.out.print(" " + catNumStringList1.get(0));
						break;
					}
					
					if (temp1.equals(catNumStringList1.get(i-1))) {
						continue;
					}
					System.out.print(" " + catNumStringList1.get(i));
					
					
					
				}
				System.out.println();
				catNumStringList1 = null;
				break;
			}
		}
		
	}

}

class GetState {
	
	public void ifMultipleState(String hurricaneName, Tree tree) {
		ArrayList<String> catNumStringList1 = new ArrayList<String>();
		for (Node child : tree.root.children) { // category
			for (Node state_catNum : child.children) { // ST_cat0
				String ST_name = state_catNum.data.substring(0,2); // 
				for (Node nameNode: state_catNum.children) { // name of each hurricane
					if (nameNode.data.equals(hurricaneName)) {
							catNumStringList1.add(ST_name);
					}
				}
			}
		}
		Collections.sort(catNumStringList1);
		int arrayListSize = catNumStringList1.size();
		for (int i = arrayListSize - 1; i >= 0; i--) {
		    String compare = catNumStringList1.get(i);
		    for (int j = i - 1; j >= 0; j--) {
		        if (compare.equals(catNumStringList1.get(j))) {
		        	catNumStringList1.remove(j);
		            arrayListSize--;
		        }
		    }
		}
		
		if (arrayListSize > 1) {
			System.out.print(" " + hurricaneName);
		}
	}
}
