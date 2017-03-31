package it.eng.test;

import static it.eng.queryplan.QueryManager.getActivablePlan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.eng.clingo.ClingoExecutor;
import it.eng.clingo.parser.ClingoParser;
import it.eng.clingo.parser.InvestigationType;
import it.eng.clingo.parser.LinkParser;
import it.eng.datastructure.Hypothesis;
import it.eng.queryplan.QueryBootStrap;
import it.eng.queryplan.QueryPlan;
import it.eng.util.PathLoader;
import it.eng.util.ReaderUtil;


/**
 * Richiama lo script per lanciare clingo
 * @author Andolfo
 *
 */
public class ClingoTest {

	private static String path2Cligno4=PathLoader.isWindows()?
			PathLoader.loadPath("clingo4.exe"):PathLoader.loadPath("clingo4Linux");
			private static String path2decAxioms=PathLoader.loadPath("dec.lp");
			private static String path2ClaudiaLawrence=PathLoader.loadPath("ClaudiaLawrence7.lp");
			private static String path2LinkAndEntities=PathLoader.loadPath("LinkAndEntities.lp");
			private static String path2Observations=PathLoader.loadPath("Observations.lp");
			private static String commands="-n 0 -c maxstep=8 --outf=2";
			/*
			 * gestione domande
			 */
			private static String path2ManagerQuestions=PathLoader.loadPath("gestioneDomande.lp");
			private static String path2ResultsQuestions=PathLoader.loadPath("risultatiDomande.lp");
			private static String path2RulesQuestions=PathLoader.loadPath("regoleDomande.lp");
			private static String commandsToQuestions="-n 0  --outf=2";

			public static String victimUUid;


			public static void main(String[]args){



				//ClingoController clingoController=new ClingoController(path2Cligno4);

				Set<String>doneQuestions=new HashSet<>();
				Set<String>doneNotifications=new HashSet<>();
				Set<String>questionsToDo=new HashSet<>();
				Set<String>notificationsToDo=new HashSet<>();

				/*
				 * Set di informazioni da raccogliere per lanciare una investigazione
				 * Dovrebbe contenere, eventi e oggetti nella forma leggibile dal ragionatore
				 */
				Hypothesis informationSet=new Hypothesis();

				boolean noMoreQuestions=false;
				int counter=0;



				while(!noMoreQuestions){

					if(counter==0){
						/*
						 * Pulisco il file delle risposte
						 */
						ReaderUtil.getInstance().cleanObservations(path2ResultsQuestions);
					}

					/*
					 * chiama il reasoner per fare le query
					 */
					ClingoExecutor clingoExecutor=new ClingoExecutor(path2Cligno4, commandsToQuestions, 
							new String[]{path2RulesQuestions,path2ManagerQuestions,path2ResultsQuestions});

					clingoExecutor.runClingo();


					/*
					 * parserizza il risultato del reasoner per ottenere le prossime domande da fare
					 * 
					 */
					if(commandsToQuestions.contains("--outf=2")){

						String clingoJson=clingoExecutor.getOutputDataString();

						System.out.println(clingoJson);


						ClingoParser clingoParser=new ClingoParser(InvestigationType.query);

						try {
							clingoParser.parse(clingoJson);

							List<Hypothesis> hypotheses=clingoParser.getHypotheses();

							for(Hypothesis hypothesis: hypotheses){


								if(hypothesis.getMakeQuestions().size()>0){
									Set<String> questions=hypothesis.getMakeQuestions();
									questionsToDo=new HashSet<>(questions);
									/*
									 * Prelevo solo le domande da fare
									 */
									questionsToDo.removeAll(doneQuestions);
									/*
									 * Se ci sono altre domande da fare faccio l'unione dei due set
									 */
									if(questionsToDo.size()>0){
										doneQuestions.addAll(questionsToDo);

									}else{
										/*
										 * non ci sono altre domande da fare
										 */
										noMoreQuestions=true;
									}

								}
								if(hypothesis.getNotifications().size()>0){
									Set<String> notificationsFromHypothesis=hypothesis.getNotifications();
									notificationsToDo=new HashSet<>(notificationsFromHypothesis);

									notificationsToDo.removeAll(doneNotifications);

									if(notificationsToDo.size()>0){
										doneNotifications.addAll(notificationsToDo);

									}

								}

							}
							/*
							 * Se ci sono domande da fare chiama il piano apposito
							 * e inserisci il suo risultato nel file dei risultati
							 */
							if(questionsToDo.size()>0){

								for(String question: questionsToDo){

									/*
									 * Crea il piano apposito
									 */
									String key=question.substring(11,question.length()-1);

									QueryPlan queryPattern=getActivablePlan(key);
									/*
									 * Aggiorna l'information Set
									 */
									queryPattern.action(informationSet);

									if(queryPattern instanceof QueryBootStrap){
										victimUUid=((QueryBootStrap) queryPattern).getVictimUuid();
									}



									String result;
									String questionDone;
									String questionResult;



									if(queryPattern.isSuccess()){
										result="true";	

									}else{
										result="false";	
									}
									questionDone="eStataFattaLaDomanda("+key+").";
									questionResult="risultatoDomanda("+key+","+result+").";
									/*
									 * Stampo le informazioni nel file di clingo
									 */
									ReaderUtil.getInstance().printOnObservation(path2ResultsQuestions,
											new ArrayList<>(Arrays.asList(new String[]{questionDone,questionResult})), true);

								}
							}


							if(notificationsToDo.size()>0){
								/*
								 * TO DO: dovrebbe sollevare una notifica/suggerimento
								 */

							}


						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


					}

					/*
					 * aumento il contatore
					 */
					counter++;
				}//end while


				/*
				 * A) una investigazione inizia quando non ci sono più domande e non ci sono più notifiche
				 * B) una investigazione non inizia quando non ci sono più domande e contemporaneamente 
				 * c'è una notifica
				 */
				boolean startInvestigation=false;
				//boolean startInvestigation=(questionsToDo.size()==0 && notificationsToDo.size()==0)?true:false;	

				if(questionsToDo.size()==0){
					if( notificationsToDo.size()==0){
						startInvestigation=true;
					}else{
						/*
						 * To do: gestire questa condizione
						 */

					}
				}


				if(startInvestigation){

					List<String>crimes=new ArrayList<>(Arrays.asList(new String[]{"runAway","kidnapped"}));;
					informationSet.setCrimes(new HashSet<>(crimes));

					String observations=informationSet.stringToObservation();

					//System.out.println(observations);

					ReaderUtil.getInstance().printOnObservation(path2Observations,observations, false);


					/*
					 * Launch the Clingo Solver
					 */
					ClingoExecutor clingoExecutor=new ClingoExecutor(path2Cligno4, commands, 
							new String[]{path2decAxioms,path2ClaudiaLawrence,path2LinkAndEntities, path2Observations});

					clingoExecutor.runClingo();

					/*
					 * testo l'output di clingo
					 */
					//testClingoOutput(clingoExecutor);

					/*
					 * Parse the clingo json
					 */
					if(commands.contains("--outf=2")){

						String clingoJson=clingoExecutor.getOutputDataString();

						ClingoParser clingoParser=new ClingoParser(InvestigationType.investigation);

						try {
							clingoParser.parse(clingoJson);
							/*
							 * Test clingo Hypotheses
							 */
							//testClingoHypotheses(clingoParser.getHypotheses());
							/*
							 * Parserizzo ogni ipotesi e ricavo il grafo corrispondente
							 */
							for(Hypothesis hypothesis: clingoParser.getHypotheses()){

								LinkParser linkParser=new LinkParser(hypothesis);

								linkParser.getGraphFromHypothesis();


							}

						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}


			}





			private static void testClingoHypotheses(List<Hypothesis> hypotheses) {

				int i=0;
				for(Hypothesis hypothesis: hypotheses){
					System.out.println("ipotesi "+i);
					i++;
					Set<String>links=hypothesis.getLinks();
					Set<String>eventsFromHypothesis=hypothesis.getEvents();
					Set<String>personsFromHypothesis=hypothesis.getPersons();

					/*
					 * Controllo il parser dei link
					 */
					LinkParser linkParser=new LinkParser(hypothesis);

					linkParser.getERAModelFromHypothesis();


					for(String link: links){
						System.out.println(link+"");
					}
					for(String event: eventsFromHypothesis){
						System.out.println(event+"");
					}
					for(String person: personsFromHypothesis){
						System.out.println(person+"");
					}

				}

			}

			
			
			
			/**
			 * 
			 * @param clingoExecutor
			 */
			private static void testClingoOutput(ClingoExecutor clingoExecutor) {
				System.out.println(clingoExecutor.getOutputDataString());

			}


			/**
			 * 
			 * @param path2Observations2
			 */
			private static void testContentInObservation(String path2Observations2) {
				InputStream is;
				try {
					is = new FileInputStream(path2Observations2);

					BufferedReader buf = new BufferedReader(new InputStreamReader(is));

					String line = buf.readLine();
					StringBuilder sb = new StringBuilder();

					while(line != null){
						sb.append(line).append("\n");
						line = buf.readLine();
					}

					String fileAsString = sb.toString();
					System.out.println("Contents : " + fileAsString);
					buf.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}




		

}
