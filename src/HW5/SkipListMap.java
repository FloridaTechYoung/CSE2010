/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW5
*/

import DoublyLinkedList.Node;

public class SkipListMap {
	int lvl = -1;
	Node<String> leftUpNode; // start of the node
	Node<String> leftDownNode;
	Node<String> rightUpNode;
	DoublyLinkedList<String> linkedList;



	FakeRandHeight height = new FakeRandHeight();
	
	public SkipListMap() { // constructor
		lvl++;
		linkedList = new DoublyLinkedList<String>(lvl);
		leftUpNode = linkedList.getHeader();
		leftDownNode = linkedList.getHeader();
	}
	
	/**
	 * add an event
	 * @param time
	 * @param event
	 */
	public void AddEvent(int time,String event) {
		int coinFlip = height.get(); 
		
			// add whne the coinflip is larger than the lvl
		for (int i = lvl + 1; i <= coinFlip; i++) {
			addLayer();
			lvl++;
		}
		
		// add the nodes between 
		Node<String> downLvlNode = null;
		for (int i = 0; i <= lvl; i++) {
			if (i <= coinFlip) {
				
				Node<String> newNode = new Node<>(event, time, i, null,null);
				// link the noew with same timestamps
				if (i>0 && i <= lvl) {
					addWithUp(newNode, downLvlNode);
				}
				
				// get the node just Before
				Node<String> prevNode = findAndInsertNode(newNode.getTime(), i);
				
				// add between the nodes
				addBetween(newNode, prevNode, prevNode.getNext());
				downLvlNode = newNode;
			} else {
				break;
			}
		}
		
		
			// when there should be a new layer added
		for (int i = lvl + 1; i <= coinFlip; i++) { 

		}
	}

	public void addLayer() {
		
		Node<String> newHead = new Node<>(null,-1, lvl, null, null);
		Node<String> newTail = new Node<>(null,130000, lvl, newHead, null);
		newHead.setNext(newTail);
		newHead.setDown(leftUpNode);
		leftUpNode = newHead;
		
		newTail.setDown(rightUpNode);
		rightUpNode = newTail;
		
	}
	
	/**
	 * 
	 * for Adding Events
	 * @param time
	 * @param height
	 * @return
	 */
	public Node<String> findAndInsertNode(int time, int height) {
		Node<String> x = leftUpNode;
		
		while(true) {
			if (x.getNext().getTime() < time) {
				x = x.getNext();
			} else if ( x.getHeight() > height) { // makes to go down to the bottom layer
				x = x.getDown();
			} else if (x.getHeight() == height){
				return x;
			}	else  if (x.getDown() != null){
				return x; // height should be 0;
			}
		}
	
	}
	
	
	public void addBetween(Node<String> insertNode, Node<String> prevNode, Node<String> nextNode) {
		insertNode.setNext(nextNode);
		insertNode.setPrev(prevNode);
		prevNode.setNext(insertNode);
		nextNode.setPrev(insertNode);
	 }
	  
	public void addWithUp (Node<String> UpNode, Node<String> DownNode) {
		UpNode.setDown(DownNode);
		DownNode.setUp(UpNode);
	}
	  
	/**
	 * 
	 * find and cancel the exent
	 * @param time
	 */
	  public void findAndCancelEvent(int time) {
		  Node<String> x = leftUpNode;
		  boolean isExist = false;
		  while (true) {
			  if(x == null) {
			  break;
		  	} else if (x.getNext().getElement() != null && x.getNext().getTime() == time) {
				  System.out.print(x.getElement());
				  // take away the row
				  removeNodeSameRow(x);
				  x = x.getDown();
				  isExist = true;
				  
			  } else if (x.getNext().getTime() < time) {
				  x = x.getNext();
			  } else if (time < x.getNext().getTime()) {
				  if (x.getDown() != null) {
					  x = x.getDown();
				  } else {
					  if (!isExist) {
						  System.out.print("None");
						  break;
					  }
				  }
			  }
		  }
	  }
	  /**
	   * find and print the element
	   * if not exist, print none
	   * @param time
	   */
	  public void findEvent(int time) {
		  Node<String> x = leftUpNode;
		  boolean isExist = false;
		  while (true) {
			  if (x.getNext().getTime() == time) {
				  System.out.print(x.getElement());
				  break;
			  } else if (x.getNext().getTime() < time) {
				  x = x.getNext();
			  } else if (time < x.getNext().getTime()) {
				  if (x.getDown() != null) {
					  x = x.getDown();
				  } else {
					  if (!isExist) {
						  System.out.print("None");
						  break;
					  }
				  }
			  }
		  }
	  }
	  
	  /**
	   * 
	   * use for the rest of the getEvents that have a time between
	   * find the start of the node and stop until the endtime equals or exceeds
	   * 
	   * @param startTime
	   * @param endTime
	   */
	  public void getEventsBetweenTimes(int startTime, int endTime) {
		  Node<String> x = leftUpNode;
		  
		  while(true) {
			 if (x.getNext().getTime() < startTime) {
				 x = x.getNext();
			 } else if (startTime < x.getNext().getTime()) {
				x = x.getDown();
			 } else if (startTime > x.getNext().getTime() && x.getHeight() == 0) {
				 while (endTime > x.getTime()) {
					 System.out.print(x.getTime() + " " + x.getElement());
					 x = x.getNext();
				 }
				 break;
			 } else {
				 x = x.getDown();
			 }
		  }
	  }
	  
	  public void removeNodeSameRow(Node<String> target) {
		  
		  Node<String> prevNode = target.getPrev();
		  Node<String> nextNode = target.getNext();
		  
		  nextNode.setPrev(prevNode);
		  prevNode.setNext(nextNode);
	  }
	  
	  public void printSkipList() {
		  Node<String> current = leftUpNode;
		  int num = 0;
		  while (num < lvl) {
			  System.out.print("(S" + current.getHeight() + ")" + " ");
			  if (current.getNext().getTime() == 130000) {
				  System.out.print("empty");
			  } else {
				  while (current.getNext().getTime() != 130000) {
					  current = current.getNext();
					  System.out.print(" " + current.getTime() + " " + current.getElement());
					  
					  
				  }
			  }
			  Node<String> x = leftUpNode.getDown();
			  for (int i = 0; i <= num; i++) {
			  	x = x.getDown();
			  }
	          current = x;
	          System.out.println();
	          num++;
	        }

	       
	  }

}




