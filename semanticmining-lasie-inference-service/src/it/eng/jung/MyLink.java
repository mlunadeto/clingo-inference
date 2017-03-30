package it.eng.jung;

import java.util.concurrent.atomic.AtomicInteger;

class MyLink {
	private static AtomicInteger count=new AtomicInteger(0);
    //double capacity; // should be private 
    //double weight;   // should be private for good practice
    int id;
    private String nameLink;
    public MyLink(/*double weight, double capacity*/ String nameLink) {
        this.id = count.addAndGet(1); 
        //this.weight = weight;
        //this.capacity = capacity;
        this.nameLink=nameLink;
    } 
    public String toString() { // Always good for debugging
        return "E"+id+" "+this.nameLink;
    }
}