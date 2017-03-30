package it.eng.clingo.parser;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import it.eng.datastructure.Hypothesis;

public class LinkParser {

	/**
	 * @param hypothesis
	 */
	public LinkParser(Hypothesis hypothesis) {
		super();
		this.setHypothesis(hypothesis);
	}

	public Hypothesis getHypothesis() {
		return hypothesis;
	}

	public void setHypothesis(Hypothesis hypothesis) {
		this.hypothesis = hypothesis;
	}

	private Hypothesis hypothesis;


	/**
	 * dovrebbe usare la struttura dati dell'ipotesi per fornire un modello entità relazioni
	 * per ogni link dell'ipotesi:
	 * 	prelevo il nome del link
	 * 	prelevo il primo elemento:
	 * 		per ogni set (persone, eventi...)
	 * 		vedo a quale entità appartiene
	 *  prelevo il sec elemento:
	 * 		per ogni set (persone, eventi...)
	 * 		vedo a quale entità appartiene	
	 * 	collego il primo elemento con il secondo		
	 */
	public void getERAModelFromHypothesis(){
		Graph<String, String> g = new SparseMultigraph<String, String>();

		/*	Set<String> events=this.hypothesis.getEvents();
		for(String event: events){
			//String eventString=event.substring(8,event.length()-3);
			g.addVertex(event);
		}

		Set<String> persons=this.hypothesis.getPersons();
		for(String person: persons){
			g.addVertex(person);
		}

		Set<String> timeEvents=this.hypothesis.getTimeEvents();
		for(String timeEvent: timeEvents){
			//String eventString=event.substring(8,event.length()-3);
			g.addVertex(timeEvent);
		}

		Set<String> groups=this.hypothesis.getGroups();
		for(String group: groups){
			g.addVertex(group);
		}


		Set<String> resources=this.hypothesis.getResources();
		for(String resource: resources){
			g.addVertex(resource);
		}

		Set<String> evidences=this.hypothesis.getEvidences();
		for(String evidence: evidences){
			//String eventString=event.substring(8,event.length()-3);
			g.addVertex(evidence);
		}

		Set<String> locations=this.hypothesis.getLocations();
		for(String location: locations){
			g.addVertex(location);
		}
		 */


		Set<String> links=this.hypothesis.getLinks();
		int i=0;
		for(String link: links){

			try {
				String[]linksArgouments=parseLink(link);

				String linkName=linksArgouments[0];

				String firstArgoument=linksArgouments[1];

				String secondArgoument=linksArgouments[2];

				Set<String> vertices=new HashSet<String>(g.getVertices());

				if(!vertices.contains(firstArgoument))
				{
					g.addVertex(firstArgoument);
				}
				if(!vertices.contains(secondArgoument))
				{
					g.addVertex(secondArgoument);
				}

				g.addEdge(linkName+i, firstArgoument, secondArgoument);
				i++;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		/*
		 * Stampa il grafo
		 */
		System.out.println("The graph g = " + g.toString());

		// SimpleGraphView sgv = new SimpleGraphDraw(); //We create our graph in here
		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(800,800)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer,String> vv = 
				new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(1000,950)); //Sets the viewing area size
		//vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv); 
		frame.pack();
		frame.setVisible(true);       




	}
	
	/**
	 * Data l'ipotesi, restituisce il corrspettivo grafo
	 */
	public void getGraphFromHypothesis(){
		Graph<String, String> g = new SparseMultigraph<String, String>();
		
		Set<String> links=this.hypothesis.getLinks();
		int i=0;
		
		for(String link: links){

			try {
				String[]linksArgouments=parseLink(link);

				String linkName=linksArgouments[0];


				String firstArgoument=linksArgouments[1];



				String secondArgoument=linksArgouments[2];

				Set<String> vertices=new HashSet<String>(g.getVertices());

				if(!vertices.contains(firstArgoument))
				{
					g.addVertex(firstArgoument);
				}
				if(!vertices.contains(secondArgoument))
				{
					g.addVertex(secondArgoument);
				}

				g.addEdge(linkName+i, firstArgoument, secondArgoument);
				i++;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		/*
		 * Stampa il grafo
		 */
		System.out.println("The graph g = " + g.toString());

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(800,800)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer,String> vv = 
				new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(1000,950)); //Sets the viewing area size
		//vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv); 
		frame.pack();
		frame.setVisible(true); 	
		
	}
	
	

	/**
	 * Prende in ingresso una stringa di tipo link e ritorna i suoi tre argomenti
	 * @param link
	 * @return
	 * @throws Exception 
	 */
	private String[] parseLink(String link) throws Exception{

		if(!link.startsWith("link")){
			throw new Exception("Stringa passata errata");
		}

		String linkName=link.substring(5, link.indexOf(","));
		String firstArgoument=new String();
		String secondArgoument=new String();
		//System.out.println(linkName);
		String stringRemain=link.substring(link.indexOf(",")+1, link.length()-1);
		//System.out.println(stringRemain);
		/*
		 * Nel caso il primo argomento è un evento:
		 */
		if(stringRemain.indexOf("(")!=-1 && stringRemain.indexOf("(")<stringRemain.indexOf(",")){
			firstArgoument=stringRemain.substring(0, stringRemain.indexOf(")")+1);
			secondArgoument=stringRemain.substring(stringRemain.indexOf(")")+2);
			//System.out.println("bo");


		}else{
			firstArgoument=stringRemain.substring(0, stringRemain.indexOf(","));
			secondArgoument=stringRemain.substring(stringRemain.indexOf(",")+1);
			//System.out.println("bo");
		}

		/*
		 * System.out.println("nome Link: " +linkName);
		System.out.println("primo arg: "+firstArgoument);
		System.out.println("secondo arg: "+secondArgoument);
		 */
		return new String[]{linkName,firstArgoument,secondArgoument};



	}


}
