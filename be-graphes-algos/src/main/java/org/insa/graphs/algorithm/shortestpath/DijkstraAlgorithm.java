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
        BinaryHeap<Label> Tas = new BinaryHeap<Label>();
        
        //Initialisation on met tous les sommets à faux
        for (Node n : nodes) {
        	Label newLabel = new Label(n.getId());
        	newLabel.cout = Float.MAX_VALUE;
        	newLabel.marque = false;
        	newLabel.pere = null;
        	labels.add(newLabel);

        }
        
        labels.get(data.getOrigin().getId()).cout = 0;
        Tas.insert(labels.get(data.getOrigin().getId()));
        notifyOriginProcessed(data.getOrigin());
        int count = 1;
        Label x = null;
        Label y = null;
        
        //Iterations
        boolean alafin = false;
        
        while (!(Tas.isEmpty())&& !alafin) { //tant qu'il existe des sommets non marqués
        	/*
        	
        	for(Label l : labels) {
        		boolean c =(Tas.contains(l)==-1);
        		boolean b =(l.cout==Float.MAX_VALUE || l.marque==true);
        		
        		if(b!=c) {
        			
        			System.out.println("c pas bon" + " "+ b + " " + c + " " + l.sommet_courant + " " + " marque = " + l.marque +  " cout = " + l.cout +"😠" );
        			throw new IllegalArgumentException();

        		}
        	} */
        	
        	x = Tas.deleteMin();
        	notifyNodeReached(nodes.get(x.sommet_courant));
        	x.marque = true;
        	
        	if(x ==labels.get(data.getDestination().getId())) {
        		System.out.println("On est à la fin, node n° " + x.sommet_courant);
        		alafin = true;
        	}else {
        		
	        	count++;
	        	for(Arc a : graph.get(x.sommet_courant).getSuccessors()) { //on parcourt tous les y sucesseurs de x
	        		if(!(data.isAllowed(a))) {
	        			continue;
	        		}
	        		y = labels.get(a.getDestination().getId());
	        		if (!(y.marque))  { //si y n'est pas marqué
	        			float oldCost=y.getCost();
	        			y.cout = Math.min(y.cout, x.cout + a.getLength());
	        			if(!(y.cout==oldCost)){ //le cost(y) a changé
	        				
	        				if(oldCost!=Float.MAX_VALUE) {
	        					System.out.println("remiove y " + y.sommet_courant);
	        					Tas.remove(y);
	        					
	        				}
	        				
	        				Tas.insert(y);
	        				y.pere = a;
	        			}
	        			
	        		}
	        	}
        	}
        	
        }
        
        List<Node> Node_solu = new ArrayList<Node>();
        List<Arc> Arcs_Solu = new ArrayList<Arc>();
        
        System.out.println(Tas);
        
        if (!alafin) {
        	//on a pas trouvé de chemin
        	solution = new ShortestPathSolution(data,Status.INFEASIBLE,Path.createShortestPathFromNodes(graph, Node_solu));
        }
        else {
        	notifyDestinationReached(data.getDestination());
	        Arc currentArc = x.pere;
	        Node_solu.add(0,nodes.get(x.sommet_courant));
	        while(currentArc!=null) {
		        System.out.println("l'arc actuel : " + currentArc);
		        System.out.println("son origine : " + currentArc.getOrigin().getId());
	        	System.out.println(currentArc.getOrigin().getId());
	        	Arcs_Solu.add(0,currentArc);
	        	Node_solu.add(0,currentArc.getOrigin());
	        	currentArc = labels.get(currentArc.getOrigin().getId()).pere;
	        }

	        
	        System.out.println("solution récupérée : ");
	        System.out.println(Arcs_Solu);
	        System.out.println(Node_solu);
	        Path newPath = Path.createShortestPathFromNodes(graph, Node_solu);//new Path(graph, listArcsSolution);
	        
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, newPath);
        }
        return solution;
    }

}
