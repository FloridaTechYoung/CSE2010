/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW3
*/
import java.util.ArrayList;

public class Node {
	
	public String data;
	public ArrayList<Node> children; // the children arrayList will store new arrays
	
	public Node(String data) {
		this.data = data;
		this.children = new ArrayList<>();
	}
	
	public void addChild(Node child) {
		this.children.add(child);
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public int size() {
		return children.size();
	}
	
	public String toString() {
		return "( " + data + " : " + children + " )"; 
	}
	
	public String State() {
		return data.substring(0, 2);
	}
	
}

