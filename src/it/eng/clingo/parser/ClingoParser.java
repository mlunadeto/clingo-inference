package it.eng.clingo.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import it.eng.datastructure.Hypothesis;


/**
 * Parserizza il json in uscita da clingo
 * @author Andolfo
 *
 */
public class ClingoParser {


	/*	private List<Hypothesis> hypotheses;

	 */

	/**
	 * @param hypotheses
	 * @param in
	 */
	public ClingoParser(InvestigationType in) {
		super();
		this.hypotheses = new ArrayList<>();
		this.in = in;
	}


	private List<Hypothesis> hypotheses;

	private InvestigationType in;


	public List<Hypothesis> getHypotheses() {
		return hypotheses;
	}


	public void setHypotheses(List<Hypothesis> hypotheses) {
		this.hypotheses = hypotheses;
	}


	/**
	 * 
	 * @param json
	 * @throws ClassNotFoundException
	 */
	public  void parse(String json) throws ClassNotFoundException{

		ObjectMapper mapper=new ObjectMapper();

		JsonNode node=null;

		/*for(String link: this.myLinks){
			System.out.println(link);

		}*/


		try {
			node = mapper.readTree(json);

			if(node!=null){
				JsonNode nodes = node.get("Call");
				Iterator<JsonNode> nodesElements = nodes.iterator();
				while(nodesElements.hasNext()){
					JsonNode dataElement = nodesElements.next();
					JsonNode witnesses=dataElement.get("Witnesses");
					Iterator<JsonNode> witnessesElements = witnesses.iterator();
					//int hypothesisNumber = 0;
					while(witnessesElements.hasNext()){
						//	hypothesisNumber++;
						JsonNode valueElement = witnessesElements.next();
						/*
						 * l'attributo Value rappresenta l'ipotesi investigativa
						 */
						JsonNode value=valueElement.get("Value");
						Iterator<JsonNode> valuesElements = value.iterator();
						//	List<Happens> happenss=new LinkedList<>();
						//	List<HoldsAt> holdsAts = new LinkedList<HoldsAt>();
						/*
						 * Fornisco i set per inglobare le informazioni
						 */

						Set<String> happens = new HashSet<>();
						Set<String> persons = new HashSet<>();
						Set<String> links = new HashSet<>();
						Set<String> timeEvents = new HashSet<>();
						Set<String> groups = new HashSet<>();
						Set<String> resources = new HashSet<>();
						Set<String> evidences = new HashSet<>();
						Set<String> locations = new HashSet<>();

						//Set<String> happens = new HashSet<>();

						Set<String> notifications = new HashSet<>();
						Set<String> makeQuestions = new HashSet<>();


						//int i=0;
						while(valuesElements.hasNext()){
							JsonNode element = valuesElements.next();

							/*	if(element.getTextValue().startsWith("holdsAt")){
								String holdsAtString=element.getTextValue();
								String fluentString=holdsAtString.substring(8, holdsAtString.lastIndexOf(","));
								//String fluentString="isDangerous(deJesus,dang)";	
								String timeFluentString=holdsAtString.substring(holdsAtString.lastIndexOf(",")+1,holdsAtString.lastIndexOf(")"));
								//System.out.println(timeFluentString);
								Fluent fluent=getFluent(fluentString);
								HoldsAt holdsAt=new HoldsAt(fluent, timeFluentString);
								holdsAt.setHoldsAtString(holdsAtString);
								holdsAts.add(holdsAt);


							}else*/ if(element.getTextValue().startsWith("happens")){
								/*		String happensString=element.getTextValue();
								String eventString=happensString.substring(8, happensString.lastIndexOf(","));
								String timeEventString=happensString.substring(happensString.lastIndexOf(",")+1,happensString.lastIndexOf(")"));
								Event event=getEvent(eventString);
								Happens happens=new Happens(event, timeEventString);
								happens.setHappensString(happensString);
								happenss.add(happens);*/

								String eventString=element.getTextValue().substring(8,element.getTextValue().length()-3);
								happens.add(eventString);


							}
							else if(element.getTextValue().startsWith("person")){


								String personString=element.getTextValue();
								persons.add(personString);

							}
							else if(element.getTextValue().startsWith("group")){
								String group=element.getTextValue();
								groups.add(group);

							}
							else if(element.getTextValue().startsWith("timeEvent")){
								String timeEvent=element.getTextValue();
								timeEvents.add(timeEvent);

							}
							else if(element.getTextValue().startsWith("resource")){
								String resource=element.getTextValue();
								resources.add(resource);

							}
							else if(element.getTextValue().startsWith("evidence")){
								String evidence=element.getTextValue();
								evidences.add(evidence);

							}
							else if(element.getTextValue().startsWith("location")){

								String location=element.getTextValue();
								locations.add(location);

							}
							else if(element.getTextValue().startsWith("link")){//trova i link
								/*
								 * Preleva i link e mettili in una lista.
								 * 
								 */

								String linkString=element.getTextValue();
								links.add(linkString);
							}
							else if(element.getTextValue().startsWith("faiDomanda")){

								String makeQuestion=element.getTextValue();
								makeQuestions.add(makeQuestion);

							}
							else if(element.getTextValue().startsWith("notifica")){

								String notification=element.getTextValue();
								notifications.add(notification);

							}


							//i++;

						}

						/*
						 * Alla fine di ogni value creo l'ipotesi e la aggiungo alla lista
						 */
						/*
						 * Dipende dal tipo di ipotesi
						 */
						if(in.equals(InvestigationType.investigation)){
							this.hypotheses.add(new Hypothesis(happens, persons, links, timeEvents, groups, resources, evidences, locations));

						}else
						{
							this.hypotheses.add(new Hypothesis(notifications, makeQuestions));
						}


					}


				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}


	public InvestigationType getIn() {
		return in;
	}


	public void setIn(InvestigationType in) {
		this.in = in;
	}



	/**
	 * 
	 * @param fluentString
	 * @return
	 */
	/*private Fluent getFluent(String fluentString){

		Fluent c=null;

		String fluentName=fluentString.substring(0, fluentString.indexOf("("));
		//System.out.println(fluentName);

		try {
			String upperCaseFluentName=fluentName.substring(0, 1).toUpperCase() + fluentName.substring(1);
			String className="it.eng.lasie.model.fluent."+upperCaseFluentName;

			Class<?> cls = Class.forName(className);
			c= (Fluent) cls.newInstance();

			c.setFluentName(fluentName);		

			String regex="[\\(,]\\w+";
			Pattern p = Pattern.compile(regex);
			Matcher m=p.matcher(fluentString);
			int count = 0;
			switch(fluentName){
			case "alive":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					Victim victim=new Victim(fieldX);
					((Alive) c).setVictim(victim);

				}break;
			case "at":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					if(count==1){
						Agent agent=new Agent(fieldX);
						((At) c).setAgent(agent);
					}else if(count==2){
						Location location=new Location(fieldX);
						((At) c).setLocation(location);
					}
				}break;
			case "dead":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					Victim victim=new Victim(fieldX);
					((Dead) c).setVictim(victim);
				}break;
			case "holding":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((Holding) c).setExecutor(executor);
					}else if(count==2){
						Victim victim=new Victim(fieldX);
						((Holding) c).setVictim(victim);
					}
				}break;
			case "hourMissed":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					String time=new String(fieldX);
					((HourMissed) c).setTime(time);
				}break;
			case "isDangerous":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((IsDangerous) c).setExecutor(executor);
					}else if(count==2){
						DangLevel dangLevel=DangLevel.valueOf(fieldX);
						((IsDangerous) c).setDangLevel(dangLevel);
					}
				}break;	
			case "killerInstinct":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					Executor executor=new Executor(fieldX);
					((KillerInstinct) c).setExecutor(executor);

				}break;	
			case "know":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((Know) c).setExecutor(executor);
					}else if(count==2){
						Victim victim=new Victim(fieldX);
						((Know) c).setVictim(victim);
					}
				}break;
			case "missed":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					Victim victim=new Victim(fieldX);
					((Missed) c).setVictim(victim);
				}break;
			case "diffTime":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					String time=new String(fieldX);
					((DiffTime) c).setTime(time);
				}break;
			case "unknownState":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=fluentString.substring(start+1, end);
					Victim victim=new Victim(fieldX);
					((UnknownState) c).setVictim(victim);
				}break;
			default: 
				break;
			}

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		return c;

	}


	private Event getEvent(String eventString){

		Event c=null;

		String eventName=eventString.substring(0, eventString.indexOf("("));
		//System.out.println(eventName);

		try {
			String upperCaseEventName=eventName.substring(0, 1).toUpperCase() + eventName.substring(1);
			String className="it.eng.lasie.model.event."+upperCaseEventName;

			Class<?> cls = Class.forName(className);
			c= (Event) cls.newInstance();

			c.setEventName(eventName);		

			String regex="[\\(,]\\w+";
			Pattern p = Pattern.compile(regex);
			Matcher m=p.matcher(eventString);
			int count = 0;
			switch(eventName){
			case "abduct":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((Abduct) c).setExecutor(executor);
					}else if(count==2){
						Victim victim=new Victim(fieldX);
						((Abduct) c).setVictim(victim);
					}

				}break;
			case "drive":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((Drive) c).setExecutor(executor);
					}else if(count==2){
						Location location1=new Location(fieldX);
						((Drive) c).setLocation1(location1);
					}
					else if(count==3){
						Location location2=new Location(fieldX);
						((Drive) c).setLocation2(location2);
					}
				}break;
			case "foundPerson":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					Victim victim=new Victim(fieldX);
					((FoundPerson) c).setVictim(victim);
				}break;
			case "kill":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((Kill) c).setExecutor(executor);
					}else if(count==2){
						Victim victim=new Victim(fieldX);
						((Kill) c).setVictim(victim);
					}
				}break;
			case "missedPerson":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					Victim victim=new Victim(fieldX);
					((MissedPerson) c).setVictim(victim);
				}break;
			case "runAway":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);

					Victim victim=new Victim(fieldX);
					((RunAway) c).setVictim(victim);	
				}break;	
			case "see":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					Agent agent=new Agent(fieldX);
					((See) c).setAgent(agent);

				}break;	
			case "statementAt":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Agent agent=new Agent(fieldX);
						((StatementAt) c).setAgent(agent);
					}else if(count==2){
						Location location=new Location(fieldX);
						((StatementAt) c).setLocation(location);
					}
				}break;
			case "statementIsDangerous":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((StatementIsDangerous) c).setExecutor(executor);
					}else if(count==2){
						DangLevel dangLevel=DangLevel.valueOf(fieldX);
						((StatementIsDangerous) c).setDangLevel(dangLevel);
					}
				}break;
			case "statementKnow":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Executor executor=new Executor(fieldX);
						((StatementKnow) c).setExecutor(executor);
					}else if(count==2){
						Victim victim=new Victim(fieldX);
						((StatementKnow) c).setVictim(victim);
					}
				}break;
			case "statementMissedPerson":
				while(m.find()){
					count++;
					int start=m.start();
					int end=m.end();
					String fieldX=eventString.substring(start+1, end);
					if(count==1){
						Victim victim=new Victim(fieldX);
						((StatementMissedPerson) c).setVictim(victim);
					}else if(count==2){
						String sinceTime=new String(fieldX);
						((StatementMissedPerson) c).setSinceTime(sinceTime);
					}
					else if(count==3){
						Location location=new Location(fieldX);
						((StatementMissedPerson) c).setLocation(location);
					}
				}break;

			default: 
				break;
			}

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		return c;

	}*/




}
