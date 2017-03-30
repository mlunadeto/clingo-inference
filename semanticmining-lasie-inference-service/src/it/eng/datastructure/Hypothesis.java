package it.eng.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author Andolfo
 *
 */
public class Hypothesis {

	
	/**
	 * Crea la stringa da passare al reasoner:
	 * 
	 */
	public String stringToObservation() {
		
		String eventString="";
		for(String event: events)
			eventString=eventString+event+"\n\n";
		
		return eventString+"\n\n"+
		createString("person(", new ArrayList<String>(persons))+"\n\n" + 
		createString("timeEvent(",  new ArrayList<String>(timeEvents))+"\n\n" + 
		createString("group(",  new ArrayList<String>(groups))+"\n\n" + 
		createString("victim(", new ArrayList<String>(Arrays.asList(new String[]{victim})))+"\n\n" + 
		createString("location(",  new ArrayList<String>(locations))+"\n\n" + 
		createString("evidence(",  new ArrayList<String>(evidences))+"\n\n" + 
		createString("resource(",  new ArrayList<String>(resources))+"\n\n" + 
		createString("crime(",  new ArrayList<String>(crimes))+"\n\n";
	
	}


	/**
	 * @param events
	 * @param persons
	 * @param links
	 */
	public Hypothesis(Set<String> events, Set<String> persons, Set<String> links) {
		super();
		this.events = events;
		this.persons = persons;
		this.links = links;
	}


	public Hypothesis(Set<String> links){
		super();
		this.links = links;
	}

	public Hypothesis(Set<String> events, Set<String> persons, Set<String> links, Set<String> timeEvents, Set<String> groups, Set<String> resources, Set<String> evidences, Set<String> locations){
		super();
		this.events=events;
		this.persons=persons;
		this.links=links;
		this.timeEvents=timeEvents;
		this.groups=groups;
		this.resources=resources;
		this.evidences=evidences;
		this.locations=locations;
		
	}

	private Set<String> events;
	private Set<String> persons;
	private Set<String>links;

	private Set<String> timeEvents;
	private Set<String> groups;
	private Set<String> resources;
	private Set<String> evidences;
	private Set<String> locations;
	
	private Set<String> crimes;
	
	


	private Set<String> notifications;
	private Set<String> makeQuestions;
	
	private String victim;


	public Hypothesis(Set<String> notifications, Set<String>makeQuestions){
		super();
		this.notifications=notifications;
		this.makeQuestions=makeQuestions;
	}

	public Hypothesis() {
		events=new HashSet<>();
		locations=new HashSet<>();
		timeEvents=new HashSet<>();
		persons=new HashSet<>();
		groups=new HashSet<>();
		resources=new HashSet<>();
		evidences=new HashSet<>();
		crimes=new HashSet<>();
	}


	public Set<String> getEvents() {
		return events;
	}
	public void setEvents(Set<String> events) {
		this.events = events;
	}
	public Set<String> getPersons() {
		return persons;
	}
	public void setPersons(Set<String> persons) {
		this.persons = persons;
	}
	public Set<String> getLinks() {
		return links;
	}
	public void setLinks(Set<String> links) {
		this.links = links;
	}


	public Set<String> getTimeEvents() {
		return timeEvents;
	}


	public void setTimeEvents(Set<String> timeEvents) {
		this.timeEvents = timeEvents;
	}


	public Set<String> getGroups() {
		return groups;
	}


	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}


	public Set<String> getResources() {
		return resources;
	}


	public void setResources(Set<String> resources) {
		this.resources = resources;
	}


	public Set<String> getEvidences() {
		return evidences;
	}


	public void setEvidences(Set<String> evidences) {
		this.evidences = evidences;
	}


	public Set<String> getLocations() {
		return locations;
	}


	public void setLocations(Set<String> locations) {
		this.locations = locations;
	}


	public Set<String> getNotifications() {
		return notifications;
	}


	public void setNotifications(Set<String> notifications) {
		this.notifications = notifications;
	}


	public Set<String> getMakeQuestions() {
		return makeQuestions;
	}


	public void setMakeQuestions(Set<String> makeQuestions) {
		this.makeQuestions = makeQuestions;
	}


	public String getVictim() {
		return victim;
	}


	public void setVictim(String victim) {
		this.victim = victim;
	}

	public Set<String> getCrimes() {
		return crimes;
	}


	public void setCrimes(Set<String> crimes) {
		this.crimes = crimes;
	}


	private  String createString(String stringToComplete, List<String> list) {

	if(list.size()>0){
		for(int i=0; i<= list.size()-1;i++){
			String instance="\""+list.get(i)+"\"";
			if(i==list.size()-1){
				stringToComplete=stringToComplete+instance+").";
			}else{
				stringToComplete=stringToComplete+instance+"; ";
			}
		}
		return stringToComplete;
	}else
	{
		return "";
	}


		
	}
	
	public void addLocation(String location){
		this.getLocations().add(location);
	}
	
	
	public void addTimeEvent(String timeEvent){
		this.getTimeEvents().add(timeEvent);
	}
	
	
	public void addEvent(String event){
		this.getEvents().add(event);
	}
	
	public void addPerson(String person){
		this.getPersons().add(person);
	}
	
	public void addGroupt(String group){
		this.getGroups().add(group);
	}
	
	
	public void addResource(String respurce){
		this.getResources().add(respurce);
	}
	
	public void addEvidence(String evidence){
		this.getEvidences().add(evidence);
	}



}
