/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW1 // There was no test case fails in the code
*/
package HW1;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * main for making all of reading the file and most of the outputs are happening
 * created a String array to check the right person, than assigning it to a link
 * 
 */
public class HW1 {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(args[0]));
		
		// Set three people in one array
		String[] list = new String[3];
		list[0] = "Alice";
		list[1] = "Bob";
		list[2] = "Carol";
		
		// Set Integer for each person for popup
		int[] messageList = new int[3];
		messageList[0] = 0; // Alice
		messageList[1] = 0; // Bob
		messageList[2] = 0; // Carol
		
		LinkedList0 Alice = new LinkedList0();
		LinkedList0 Bob = new LinkedList0();
		LinkedList0 Carol = new LinkedList0();
		
		// scan the Line when Line exists
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] words = line.split(" ");
			String command = words[0];
			
			switch (command) {
			
			case "ReceiveMessage":
				int time = Integer.parseInt(words[1]);
				String person = words[2];
				int personInt = -1;
				
				// get the right person
				for (int i = 0; i < 3; i++) {
					
					if (list[i].equals(person)) {
						personInt = i; 
					}
				}
				
				String message = ""; // to add all of the words in the array and make to one
				
				for (int i = 3; i < words.length; i++) { // i starts from 3 due to the previous ones are not messages 
					message = message + " " + words[i];
				}
				
				if (personInt == 0) {
					Alice.add("Alice",time,message); 
					System.out.println("NotifyUser " + list[0]);
					messageList[0]++;
				} else if (personInt == 1) {
					Bob.add("Bob",time,message);
					messageList[1]++;
					System.out.println("NotifyUser " + list[1]);
				} else if (personInt == 2) {
					Carol.add("Carol",time,message);
					messageList[2]++;
					System.out.println("NotifyUser " + list[2]);
				} else {
					System.out.println("Incorrect nameInput, check name again");
				}
				break;
				
			case "OpenApp": // when the app is open, there could be extra functions available
				
				for (int i = 0; i <= 2; i++) {
					System.out.println(list[i] +" " + messageList[i]); // show all the people with message cnt
				}
			
				break;
				
			case "DisplayConversation":

				personInt = -1;
				person = words[1];
				// get the right person
				for (int i = 0; i < 3; i++) {
					if (list[i].equals(person)) {
						personInt = i; 
						messageList[i] = 0;
					}
				}
							
				if (personInt == 0) {
					Alice.show();
				} else if (personInt == 1) {
					Bob.show();
				} else if ((personInt == 2)) {
					Carol.show();
				} else {
					System.out.println("Incorrect nameInput, check name again");
				}
							
				break;
					
				case "SendMessage":
					time = Integer.parseInt(words[1]);
					person = words[2];
					personInt = -1;
					
					message = ""; // to add all of the words in the array and make to one
					
					for (int i = 3; i < words.length; i++) { // i starts from 3 due to the previous ones are not messages 
						message = message + " " + words[i];
					}
					// get the right person
					for (int i = 0; i < 3; i++) {
								
						if (list[i].equals(person)) {
							personInt = i; 
							messageList[i] = 0;
						}
					}
							
					if (personInt == 0) {
						Alice.add("me",time,message); 
						Alice.show();
					} else if (personInt == 1) {
						Bob.add("me",time,message); 
						Bob.show();
					} else if ((personInt == 2)) {
						Carol.add("me",time,message); 
						Carol.show();
					} else {
						System.out.println("Incorrect nameInput, check name again");
					}
							
					break;
							
				case "DeleteMessage":
					time = Integer.parseInt(words[1]);
					person = words[2];
					personInt = -1;
					// get the right person
					for (int i = 0; i < 3; i++) {
						if (list[i].equals(person)) {
							personInt = i; 
							messageList[i] = 0;
						}
					}
							
					if (personInt == 0) {
						Alice.delete(time);
						Alice.show();
					} else if (personInt == 1) {
						Bob.delete(time);
						Bob.show();
					} else if ((personInt == 2)) {
						Carol.delete(time);
						Carol.show();
					} else {
						System.out.println("Incorrect nameInput, check name again");
					}
					break;
							
				case "CloseApp": // need extra control code for the closeApp
					break;
			}
		}	
	}
}

class LinkedList0 { // named LinkedList0 because LinkedList already exists
	
	private Node head;
	private Node tail;
	private int size = 0; // for count of number of messages
	private class Node{
		// Store two data in one node time & Message
		private String sendPerson; // This is a String to check whether I sent, or received 
		private int dataTime;
		private String dataMessage;
		private Node next;
		
		// Make constructor to initialize two data
		public Node(String sendPerson, int inputTime, String inputMessage) {
			this.sendPerson = sendPerson;
			this.dataTime = inputTime;
			this.dataMessage = inputMessage;
			
			this.next = null; // when making a new node, which is the last node, so the next is null
		}
		
	}
	
	public void addFirst (String sendPerson, int inputTime, String inputMessage) {
		Node newNode = new Node(sendPerson, inputTime, inputMessage);
		newNode.next = head; // Point the new headnode.next to old headNode
		head = newNode; // set the head pointers to the newNode
		size++; // increment size of list
		
		// if the list size is zero
		if (head.next == null) {
			tail = head; // point out the head node is same as tail node
		}
	}
	
	/* Method to add the recent message
	 */
	public void addLast (String sendPerson, int inputTime, String inputMessage) {
		Node newNode = new Node(sendPerson, inputTime, inputMessage);
		if (size == 0) {
			addFirst(sendPerson, inputTime, inputMessage); // when there is no node, the method can just be used with addFirst Method
		} else { // if data exists
			tail.next = newNode;
			tail = newNode;
			size++; 
		}
	}
	
			// Method to get the index
			public Node node(int index) {
				Node x = head;
				
				for(int i = 0; i < index; i++) {
					x = x.next;
				}
				
				return x;
			}
			
			/*
			 * Method to add Between the linkedList after comparing
			 */
			public void add(String sendPerson, int inputTime, String inputMessage) {
				
				if (size == 0) {
					addLast(sendPerson, inputTime, inputMessage);

					return;
				}
				//
				
				Node node = head;
				Node prevNode = null;
				while (node != null) { ///// addd
					
					
					if (inputTime < node.dataTime) {
						
						if (node == head) {
							Node newNode = new Node(sendPerson, inputTime, inputMessage);
							newNode.next = node;
							head = newNode;
							
						} else {
							// when timeStamp is between
							Node newNode = new Node(sendPerson, inputTime, inputMessage);
							prevNode.next = newNode;
							newNode.next = node;
							
							
						}
						size++;
						break;
					}
					
					if (node.next == null) { // when the timeStamp is the biggest
						addLast(sendPerson, inputTime, inputMessage);
						break;
					} 
					prevNode = node;
					node = node.next;
				}
			}
			
			/*
			 * Method that prints out the messages
			 */
			public void show() {
				if (size < 0) {
					System.out.println("No message currently sent or received");
				} else {
					for (int i = 0; i < size; i++) {
						System.out.printf("%06d %s %s%n",node(i).dataTime,node(i).sendPerson,node(i).dataMessage.substring(1));
					}
				}
			}
			
			// Unsure of delete Method
			public void delete(int timeStamp) {
				
				boolean isExist = true; // find if there is the existing timeStamp
				
				for (int i = 0; i < size; i++) {
					
					if (node(i).dataTime == timeStamp) {
						
						if (i == 0) {
							if (size == 1) {
								head = null;
								size = 0;
							} else if (size > 1) {
								head = node(1); // check if this is correct
								size--;
							}
							
							isExist = false;
							
						} else if (i == size-1) { // at the end of the line
							node(size-2).next = null; // check if this is correct
							tail = node(size-2);
							isExist = false;
							size--;
							// node(size-1) = null; // why is this wrong????
						} else if (0 < i && i <=size-1) {
							node(i-1).next = node(i+1); // is this method it????
							isExist = false;
							size--;
						}
					}
				}
				
				if (isExist) {
					System.out.println("No matching time exist");
				}
			}
	
	
}
