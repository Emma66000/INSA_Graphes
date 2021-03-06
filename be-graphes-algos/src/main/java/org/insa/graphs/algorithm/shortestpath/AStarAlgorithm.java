package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
      
    }
    


	public Label creerLabel(Node n, Node dest,ShortestPathData data) {
		
		if(data.getMode()==Mode.LENGTH) {
			return  new LabelStar(n.getId(), (float)Point.distance(n.getPoint(),dest.getPoint()));
		}else {
			
			return  new LabelStar(n.getId(), (float)(Point.distance(n.getPoint(),dest.getPoint())/((float)data.getGraph().getGraphInformation().getMaximumSpeed()/3.6)));
		}
		
		
	}
}
