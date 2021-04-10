package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        List<Node>nodes = graph.getNodes();
        List<Label> labels = new ArrayList<Label>();
        
        
        //Initialisation on met tous les sommets à faux
        for (Node n : nodes) {
        	Label newLabel = new Label(n.getId());
        	newLabel.cout = Float.MAX_VALUE;
        	newLabel.marque = false;
        	newLabel.pere = null;
        	labels.add(newLabel);

        }
        
        labels.get(data.getOrigin().getId()).cout = 0;
        labels.get(data.getOrigin().getId()).marque = true;
        
        BinaryHeap<Label> Tas = new BinaryHeap<Label>();
        Tas.insert(labels.get(data.getOrigin().getId()));
        
        int count = 1;
        Label x = null;
        Label y = null;
        
        //Iterations
        boolean alafin = false;
        
        while (count<nodes.size() && !alafin) { //tant qu'il existe des sommets non marqués
        	x = Tas.deleteMin();
        	x.marque = true;
        	if(x ==labels.get(data.getDestination().getId())) {
        		System.out.println("On est à la fin, node n° " + x.sommet_courant);
        		alafin = true;
        	}else {
        		
	        	count++;
	        	for(Arc a : graph.get(x.sommet_courant).getSuccessors()) { //on parcourt tous les y sucesseurs de x
	        		y = labels.get(a.getDestination().getId());
	        		if (!(y.marque))  { //si y n'est pas marqué
	        			float oldCost=y.getCost();
	        			//y.cout = Math.min(y.cout, x.cout + a.getLength());
	        			if(y.cout>x.cout + a.getLength()){ //le cost(y) a changé
	        				System.out.println("UPDATE");
	        				y.cout = x.cout + a.getLength(); 
	        				if(oldCost!=Float.MAX_VALUE) {
	        					Tas.remove(y);
	        				}
	        				
	        				Tas.insert(y);
	        				y.pere = a;
	        			}
	        			
	        		}
	        	}
        	}
        	
        }
        
        
        System.out.println(Tas);
        
        List<Node> Node_solu = new ArrayList<Node>();
        Node_solu.add(nodes.get(x.sommet_courant));
        Arc currentArc = x.pere;
        while(currentArc!=null) {
	        System.out.println("l'arc actuel : " + currentArc);
	        System.out.println("son origine : " + currentArc.getOrigin().getId());
        	System.out.println(currentArc.getOrigin().getId());
        	Node_solu.add(currentArc.getOrigin());
        	currentArc = labels.get(currentArc.getOrigin().getId()).pere;
        }
        
        solution = new ShortestPathSolution(data,Status.OPTIMAL, Path.createShortestPathFromNodes(graph,Node_solu));
        return solution;
    }

}
