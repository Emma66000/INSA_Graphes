package org.insa.graphs.algorithm.shortestpath;

public class LabelStar extends Label{
	
	protected float cout_dest;
	
	public LabelStar(int node,float cout_dest) {
		super(node);
		this.cout_dest = cout_dest;
		
	}
	
	public float getCoutDest() {
		return this.cout_dest;
	}
	
	public float getTotalCost() {
		
		return (this.getCost() + this.cout_dest);
	}
}
