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

	public Label creerLabel(Node n,Node dest) {
		return  new Label(n.getId());
	}
	
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
        	Label newLabel =creerLabel(n,data.getDestination());
        	newLabel.setCost(Float.MAX_VALUE);
        	newLabel.marque = false;
        	newLabel.pere = null;
        	labels.add(newLabel);

        }
        
        labels.get(data.getOrigin().getId()).setCost(0);
        Tas.insert(labels.get(data.getOrigin().getId()));
        
        notifyOriginProcessed(data.getOrigin());
        
        Label x = null;
        Label y = null;
        
        //Iterations
        boolean alafin = false;
        
        while (!(Tas.isEmpty())&& !alafin) { //tant qu'il existe des sommets non marqués
        	
        	System.out.println(Tas.isValid());
        	
        	int count = 0;
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
        	
        	//on verifie que les couts sont croissants
        	System.out.println("cout = " + x.getTotalCost());
        	x.marque = true;
        	
        	if(x ==labels.get(data.getDestination().getId())) {
        		System.out.println("On est à la fin, node n° " + x.sommet_courant);
        		alafin = true;
        	}else {
        		
	        	
	        	for(Arc a : graph.get(x.sommet_courant).getSuccessors()) { //on parcourt tous les y sucesseurs de x
	        		count++;
	        		
	        		if(!(data.isAllowed(a))) {
	        			continue;
	        		}
	        		y = labels.get(a.getDestination().getId());
	        		if (!(y.marque))  { //si y n'est pas marqué
	        			float oldCost=y.getCost();
	        			y.cout = Math.min(y.getCost(), x.getCost() + a.getLength());
	        			if(!(y.cout==oldCost)){ //le cost(y) a changé
	        				
	        				if(oldCost!=Float.MAX_VALUE) {
	        					System.out.println("remove y: " + y.sommet_courant);
	        					Tas.remove(y);
	        					
	        				}
	        				
	        				Tas.insert(y);
	        				y.pere = a;
	        			}
	        			
	        		}
	        		
	        	}
	        	
	        	//on verifie le nombre de sucesseurs
	        	System.out.println("nb de successeurs = " + count + " reel = " + graph.get(x.sommet_courant).getNumberOfSuccessors() );
	        	
        	}
        	
        }
        
        List<Node> Node_solu = new ArrayList<Node>();
        List<Arc> Arcs_Solu = new ArrayList<Arc>();
        
        //on verifie la longueur du plus court chemin de Dijkstra
        float chemin=0;
        //System.out.println(Tas);
        
        if (!alafin) {
        	//on a pas trouvé de chemin
        	solution = new ShortestPathSolution(data,Status.INFEASIBLE,Path.createShortestPathFromNodes(graph, Node_solu));
        }
        else {
        	notifyDestinationReached(data.getDestination());
	        Arc currentArc = x.pere;
	        Node_solu.add(0,nodes.get(x.sommet_courant));
	        while(currentArc!=null) {
		        //System.out.println("l'arc actuel : " + currentArc);
		        //System.out.println("son origine : " + currentArc.getOrigin().getId());
	        	//System.out.println(currentArc.getOrigin().getId());
	        	Arcs_Solu.add(0,currentArc);
	        	Node_solu.add(0,currentArc.getOrigin());
	        	chemin +=currentArc.getLength();
	        	currentArc = labels.get(currentArc.getOrigin().getId()).pere;
	        }

	        
	        System.out.println("solution récupérée : ");
	        System.out.println(Arcs_Solu);
	        System.out.println(Node_solu);
	        Path newPath = Path.createShortestPathFromNodes(graph, Node_solu);//new Path(graph, listArcsSolution);
	        
	        //on verifie que le path est valid et que il fait bien la même taille que celui obtenu avec la methode path
	        System.out.println("Path valid ? " + newPath.isValid());
	        System.out.println("Path Length class path ? " + newPath.getLength() + " Dijkstra length ? " + chemin);
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, newPath);
        }
        return solution;
    }

}
