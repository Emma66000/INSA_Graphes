package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraAlgorithmTest {
	
	private static Graph graph;
	private static List<Node> node_list = new ArrayList<Node>();
	private static GraphReader reader;
	
	@BeforeClass
	public static void initAll() throws IOException {

        String mapCarreDense = "C:\\Users\\fifid\\Documents\\3MIC\\Graphe\\be-graphes\\Maps\\carre-dense.mapgr";

        // Create a graph reader.
        reader = new BinaryGraphReader( new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarreDense))));

        //TODO : Read the graph.
        graph = reader.read();
            
    }
	
	@Test
	public void testDoRun() throws IOException{
		
		//on génère des paires Origine/destination
		List<List<Node>> paire_node = new ArrayList<>();
		List <Node> nodes = new ArrayList<Node>();
		int origine;
		int destination;
		
		for(int i = 0; i<30; i++) {
			origine = (int)Math.random()*node_list.size();
			destination = (int)Math.random()*node_list.size();
			nodes.add(nodes.get(origine));
			nodes.add(nodes.get(destination));
			paire_node.add(nodes);
			//a chaque on efface le contenu de la liste pour n'avoir que le couple que l'on veut à chaque boucle
			nodes.clear();
			
		}
		nodes.add(nodes.get(1256));
		nodes.add(nodes.get(1020));
		paire_node.add(nodes);
		nodes.clear();
		
		//TEST DE BASE
		
		//invalid
		System.out.println("Test invalide, un noeud pas existant");
		DijkstraAlgorithm D_no_road = new DijkstraAlgorithm(new ShortestPathData(graph,paire_node.get(0).get(0),nodes.get(99999999), null));
		assertEquals(D_no_road.run().isFeasible(),false);
		
		//infeasible
		System.out.println("Test infaisable, pas de path");
		D_no_road = new DijkstraAlgorithm(new ShortestPathData(graph,nodes.get(1256),nodes.get(1020), null));
		assertEquals(D_no_road.run().isFeasible(),false);
		
		//1seul point
		System.out.println("Test un seul point");
		D_no_road = new DijkstraAlgorithm(new ShortestPathData(graph,paire_node.get(0).get(0),paire_node.get(0).get(0), null));
		assertEquals(D_no_road.run().isFeasible(),true);
		

		
		/*
		System.out.println("Test pas de chemin, en distance");
		DijkstraAlgorithm D_no_road = new DijkstraAlgorithm(new ShortestPathData(graph,paire_node.get(0).get(0),paire_node.get(0).get(1), null));
		BellmanFordAlgorithm B_no_road = new BellmanFordAlgorithm(new ShortestPathData(graph,paire_node.get(0).get(0),paire_node.get(0).get(1), null));
		assertEquals(D_no_road.run(),B_no_road.run());
		*/
		
		reader.close();
		
	}
	

	public void TestDist() {
		//on génère des paires Origine/destination
		List<List<Node>> paire_node = new ArrayList<>();
		List <Node> nodes = new ArrayList<Node>();
		int origine;
		int destination;
		
		int nb_test_ok = 0;

		
		for(int i = 0; i<50; i++) {
			
			origine = (int)Math.random()*node_list.size();
			destination = (int)Math.random()*node_list.size();
			
			nodes.add(nodes.get(origine));
			nodes.add(nodes.get(destination));
			paire_node.add(nodes);
			//a chaque on efface le contenu de la liste pour n'avoir que le couple que l'on veut à chaque boucle
			nodes.clear();
			
			DijkstraAlgorithm D_no_road = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), null));
			ShortestPathSolution solu = D_no_road.run();
			
			if(solu.isFeasible()) {
				nb_test_ok++;
				
				BellmanFordAlgorithm B_no_road = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), null));
				ShortestPathSolution soluB = B_no_road.run();
				assertEquals(solu, soluB);
				
				assertEquals(solu.getPath().getMinimumTravelTime(),soluB.getPath().getMinimumTravelTime(),0.0001);
				assertEquals(solu.getPath().getLength(),soluB.getPath().getLength(),0.0001);
				
		        //on verifie que le path est valid et que il fait bien la même taille que celui obtenu avec la methode path
		        System.out.println("Path valid ? " + solu.getPath().isValid());
		        
		        
				
			}
			
			if(nb_test_ok>=50) {
				break;
			}
			
		}
		

	}
	
	
	
    
  


}
