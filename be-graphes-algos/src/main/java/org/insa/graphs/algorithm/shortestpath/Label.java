package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label>{
	
	protected int sommet_courant;
	protected boolean marque;
	protected float cout;
	protected Arc pere;
	
	public Label(int node) {
		this.sommet_courant = node;
		this.marque = false;
		this.cout = 0;
		this.pere = null;
	}
	
	public float getCost() {
		return this.cout;
	}
	
	public int compareTo(Label l) {
		return (int)(this.cout - l.cout);
	}
	
}
