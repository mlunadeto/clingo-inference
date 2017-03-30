package it.eng.datastructure;


import java.util.List;

import it.eng.hybrid.model.era.data.Entity;

public class Result {
	
	
	/**
	 * @param entityResults
	 * @param graph
	 */
	public Result(List<Entity> entityResults, List<String> graph) {
		super();
		this.entityResults = entityResults;
		this.graph = graph;
	}
	private List<it.eng.hybrid.model.era.data.Entity> entityResults;
	private List<String> graph;
	public List<it.eng.hybrid.model.era.data.Entity> getEntityList() {
		return entityResults;
	}
	public void setEntityList(List<it.eng.hybrid.model.era.data.Entity> entityResults) {
		this.entityResults = entityResults;
	}
	public List<String> getGraph() {
		return graph;
	}
	public void setGraph(List<String> graph) {
		this.graph = graph;
	}
	
}
