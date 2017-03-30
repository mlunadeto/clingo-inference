package it.eng.jung;

import java.util.concurrent.atomic.AtomicInteger;

class MyNode {
	int id; // good coding practice would have this as private
	private String nameNode;
	
	private static AtomicInteger count=new AtomicInteger(0);
	
	
	public MyNode(String nameNode) {
		
		this.id =count.getAndAdd(1);//incremento il contatore
		this.setNameNode(nameNode);
	}
	public String toString() { // Always a good idea for debuging
		return "V"+id+" "+this.nameNode;
		// JUNG2 makes good use of these.
	}
	public String getNameNode() {
		return nameNode;
	}
	public void setNameNode(String nameNode) {
		this.nameNode = nameNode;
	}        
}