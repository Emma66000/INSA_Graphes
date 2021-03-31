package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

public class Label {
	
	protected int sommet_courant;
	protected boolean marque;
	protected float cout;
	protected Arc pere;
	
	public Label(int node) {
		this.sommet_courant = node;
	}
	
	public float getCost() {
		return this.cout;
	}
	
}
