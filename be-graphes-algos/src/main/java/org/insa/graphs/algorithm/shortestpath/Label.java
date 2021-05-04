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
	
	public void setCost(float i) {
		this.cout = i;
	}
	
	public float getTotalCost() {
		return this.cout;
	}
	
	public int compareTo(Label l) {
		float diff = this.getTotalCost() - l.getTotalCost();
		if (diff == 0) {
			return 0;
		} else if (diff < 0) {
			return -1;
		} else {
			return 1;
		}
	
	}
	
}
