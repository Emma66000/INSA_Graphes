package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraAlgorithmTest {
	
	private static Graph graph, graph2;
	private static List<Node> node_list, node_list2= new ArrayList<Node>();
	
	private static GraphReader reader,reader2;
	
	@BeforeClass
	public static void initAll() throws IOException {

        String map = "C:\\Users\\fifid\\Documents\\3MIC\\Graphe\\be-graphes\\Maps\\insa.mapgr";
        String map2 = "C:\\Users\\fifid\\Documents\\3MIC\\Graphe\\be-graphes\\Maps\\carre-dense.mapgr";
        // Create a graph reader.
        reader = new BinaryGraphReader( new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        reader2 = new BinaryGraphReader( new DataInputStream(new BufferedInputStream(new FileInputStream(map2))));
        //TODO : Read the graph.
        graph = reader.read();
        graph2 = reader2.read();
        node_list = graph.getNodes();
        node_list2 = graph2.getNodes();
        
        
        
            
    }
	
	@Test
	public void testDoRun() throws IOException{
		
		//on génère des paires Origine/destination
		List<List<Node>> paire_node = new ArrayList<>();
		
		int origine;
		int destination;
		
		for(int i = 0; i<30; i++) {
			List <Node> nodes = new ArrayList<Node>();
			origine = (int)(Math.random()*node_list.size());
			destination = (int)(Math.random()*node_list.size());
			nodes.add(node_list.get(origine));
			nodes.add(node_list.get(destination));
			paire_node.add(nodes);
		

			
		}
		
		List <Node> nodes = new ArrayList<Node>();
		nodes.add(node_list.get(1256));
		nodes.add(node_list.get(1020));
		paire_node.add(nodes);

		
		System.out.println(paire_node.get(0).get(1));
		System.out.println(paire_node.get(0).get(0));

		
		//TEST DE BASE
		
		DijkstraAlgorithm D_road;
		List<ArcInspector> arcInsp = ArcInspectorFactory.getAllFilters();
		
		
		
		//infeasible
		D_road = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(1256),node_list.get(1020), arcInsp.get(0)));
		assertEquals(D_road.run().isFeasible(),false);
		
		//1seul point
		D_road = new DijkstraAlgorithm(new ShortestPathData(graph,paire_node.get(0).get(0),paire_node.get(0).get(0), arcInsp.get(0)));
		assertEquals(D_road.run().isFeasible(),true);
		
		
		
		reader.close();
		
	}
	
	@Test
	public void TestDistTemps_insa() throws IOException {
		//on génère des paires Origine/destination
		List<List<Node>> paire_node = new ArrayList<>();
		
		int origine;
		int destination;
		List<ArcInspector> arcInsp = ArcInspectorFactory.getAllFilters();
		int nb_test0= 0;
		int nb_test1=0;
		int nb_test2 = 0;
		int nb_test3=0; 
		int nb_test4 = 0;

		/*
		//Shortest path, all roads allowed
		DijkstraAlgorithm D_road = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(561),node_list.get(561), arcInsp.get(0)));
		ShortestPathSolution solu = D_road.run();
		
		BellmanFordAlgorithm B_road = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(561),node_list.get(561), arcInsp.get(0)));
		ShortestPathSolution soluB = B_road.run();

		System.out.println(solu.isFeasible() +" " + soluB.isFeasible());
		assertEquals(solu.isFeasible(),soluB.isFeasible());
		*/
		
		
		while(nb_test0<50 && nb_test1<50 && nb_test2<50 && nb_test3<50 && nb_test4<50) {

			origine = (int)(Math.random()*node_list.size());
			destination = (int)(Math.random()*node_list.size());


			
			
		
			//Shortest path, all roads allowed
			DijkstraAlgorithm D_road_dist = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(0)));
			ShortestPathSolution solu_dist = D_road_dist.run();
			
			//Shortest path, only roads open for cars
			DijkstraAlgorithm D_road_dist_car = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(1)));
			ShortestPathSolution solu_dist_car = D_road_dist_car.run();
			
			//Fastest path, all roads allowed
			DijkstraAlgorithm D_road_temps = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(2)));
			ShortestPathSolution solu_temps = D_road_temps.run();
			
			//Fastest path, only roads open for cars
			DijkstraAlgorithm D_road_temps_car = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(3)));
			ShortestPathSolution solu_temps_car = D_road_temps_car.run();
			
			//Fastest path for pedestrian
			DijkstraAlgorithm D_road_temps_ped = new DijkstraAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(4)));
			ShortestPathSolution solu_temps_ped = D_road_temps_ped.run();
			
			//on vérifie que la solution en temps a effectivement une durée de trajet plus courte que la solution en distance et inversément pour la distance
			assertEquals(solu_temps.getPath().getMinimumTravelTime()<=solu_dist.getPath().getMinimumTravelTime(),true);
			assertEquals(solu_temps.getPath().getLength()>=solu_dist.getPath().getLength(),true);
 
			System.out.println(origine +" " + destination);
			
			if(solu_dist.isFeasible()) {
				nb_test0++;
				BellmanFordAlgorithm B_road_dist = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(0)));
				ShortestPathSolution soluB_dist = B_road_dist.run();
				if(soluB_dist.isFeasible()) {
					//on vérifie que la solution en temps a effectivement une durée de trajet plus courte que la solution en distance et inversément pour la distance
					System.out.println(solu_dist.getPath().getMinimumTravelTime() + " " + soluB_dist.getPath().getMinimumTravelTime());
					//MODE 0
					assertEquals(solu_dist.getPath().getMinimumTravelTime(),soluB_dist.getPath().getMinimumTravelTime(),0.001);
					assertEquals(solu_dist.getPath().getLength(),soluB_dist.getPath().getLength(),0.001);
				}

		        assertEquals(solu_dist.getPath().isValid(),true);
			}
			
			if(solu_dist_car.isFeasible()) {
				nb_test1++;
				BellmanFordAlgorithm B_road_dist_car = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(1)));
				ShortestPathSolution soluB_dist_car = B_road_dist_car.run();
				if(soluB_dist_car.isFeasible()) {
					//MODE 1
					assertEquals(solu_dist_car.getPath().getMinimumTravelTime(),soluB_dist_car.getPath().getMinimumTravelTime(),0.001);
					assertEquals(solu_dist_car.getPath().getLength(),soluB_dist_car.getPath().getLength(),0.001);
				}

		        assertEquals(solu_dist_car.getPath().isValid(),true);
			}
			if(solu_temps.isFeasible()) {
				nb_test2++;
				BellmanFordAlgorithm B_road_temps = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(2)));
				ShortestPathSolution soluB_temps = B_road_temps.run();
				
				if(soluB_temps.isFeasible()) {
					//MODE 2
					assertEquals(solu_temps.getPath().getMinimumTravelTime(),soluB_temps.getPath().getMinimumTravelTime(),0.0001);
					assertEquals(solu_temps.getPath().getLength(),soluB_temps.getPath().getLength(),0.0001);
				}

		        assertEquals(solu_temps.getPath().isValid(),true);
			}
			
			if(solu_temps_car.isFeasible()) {
				nb_test3++;
				BellmanFordAlgorithm B_road_temps_car = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(3)));
				ShortestPathSolution soluB_temps_car = B_road_temps_car.run();
				if(soluB_temps_car.isFeasible()) {
					//MODE 3
					assertEquals(solu_temps_car.getPath().getMinimumTravelTime(),soluB_temps_car.getPath().getMinimumTravelTime(),0.0001);
					assertEquals(solu_temps_car.getPath().getLength(),soluB_temps_car.getPath().getLength(),0.0001);
				}

		        assertEquals(solu_temps_car.getPath().isValid(),true);
			}
			
			if(solu_temps_ped.isFeasible()) {
				nb_test4++;
				BellmanFordAlgorithm B_road_temps_ped = new BellmanFordAlgorithm(new ShortestPathData(graph,node_list.get(origine),node_list.get(destination), arcInsp.get(4)));
				ShortestPathSolution soluB_temps_ped = B_road_temps_ped.run();
				
				if(soluB_temps_ped.isFeasible()) {
					//MODE 4
					assertEquals(solu_temps_ped.getPath().getMinimumTravelTime(),soluB_temps_ped.getPath().getMinimumTravelTime(),0.0001);
					assertEquals(solu_temps_ped.getPath().getLength(),soluB_temps_ped.getPath().getLength(),0.0001);
				}

		        assertEquals(solu_temps_ped.getPath().isValid(),true);
			}

			
			
		}

		reader.close();

	}
	
	
	
	@Test
	public void TestDistTemps_carredense() throws IOException {
		//on génère des paires Origine/destination
		List<List<Node>> paire_node = new ArrayList<>();
		List<ArcInspector> arcInsp = ArcInspectorFactory.getAllFilters();
		int origine;
		int destination;
		
		/*
		DijkstraAlgorithm D_road = new DijkstraAlgorithm(new ShortestPathData(graph2,node_list2.get(160275),node_list2.get(334671), arcInsp.get(0)));
		ShortestPathSolution test_d = D_road.run();
		
		DijkstraAlgorithm D_road_t = new DijkstraAlgorithm(new ShortestPathData(graph2,node_list2.get(160275),node_list2.get(334671), arcInsp.get(2)));
		ShortestPathSolution test_t = D_road_t.run();
		
		if(test_t.isFeasible() && test_d.isFeasible()) {
			
			//on vérifie que la solution en temps a effectivement une durée de trajet plus courte que la solution en distance et inversément pour la distance
			System.out.println(test_t.getPath().getMinimumTravelTime() + " " + test_d.getPath().getMinimumTravelTime());
			//assertTrue((test_t.getPath().getMinimumTravelTime())<=(test_d.getPath().getMinimumTravelTime()));
			assertTrue(test_t.getPath().getLength()>=test_d.getPath().getLength());
		}
		*/
		origine = (int)(Math.random()*node_list2.size());
		destination = (int)(Math.random()*node_list2.size());


		

	
		
		DijkstraAlgorithm D_road_dist = new DijkstraAlgorithm(new ShortestPathData(graph2,node_list2.get(origine),node_list2.get(destination), arcInsp.get(0)));
		ShortestPathSolution solu_dist = D_road_dist.run();
		
		DijkstraAlgorithm D_road_temps = new DijkstraAlgorithm(new ShortestPathData(graph2,node_list2.get(origine),node_list2.get(destination), arcInsp.get(2)));
		ShortestPathSolution solu_temps = D_road_temps.run();
		
		
		if(solu_dist.isFeasible()) {
	        //on verifie que le path est valid et que il fait bien la même taille que celui obtenu avec la methode path
	        assertEquals(solu_dist.getPath().isValid(),true);
	       	
		}
		
		if(solu_temps.isFeasible()) {
			assertEquals(solu_temps.getPath().isValid(),true);
		}
		
		if(solu_temps.isFeasible() && solu_dist.isFeasible()) {
			System.out.println(origine +" " + destination);
			//on vérifie que la solution en temps a effectivement une durée de trajet plus courte que la solution en distance et inversément pour la distance
			System.out.println(solu_temps.getPath().getMinimumTravelTime() + " " + solu_dist.getPath().getMinimumTravelTime());
			//assertTrue((solu_temps.getPath().getMinimumTravelTime())<=(solu_dist.getPath().getMinimumTravelTime()));

			assertTrue(solu_temps.getPath().getLength()>=solu_dist.getPath().getLength());
			
			for(Arc a : solu_dist.getPath().getArcs()) {
				DijkstraAlgorithm D_road2 = new DijkstraAlgorithm(new ShortestPathData(graph2,a.getDestination(),node_list2.get(destination), arcInsp.get(0)));
				ShortestPathSolution solu2 = D_road2.run();
				
				if(solu2.isFeasible()) {
					assertEquals(solu2.getPath().getMinimumTravelTime()<solu_dist.getPath().getMinimumTravelTime(),true);
					solu_dist = solu2;
				}

				
			}
		}
		

		reader2.close();

	}

}
