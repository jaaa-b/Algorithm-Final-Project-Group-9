import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
public class Dijkstra {
    public static void calculateShortestPath(Node source, Node dest){
        source.setDistance(0);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(source);

        while (!pq.isEmpty()){
            Node curr = pq.poll(); //node with smallest distance
            if (curr == dest){
                return;
            }

            for (Map.Entry<Node,Integer> entry : curr.getAdjacentNodes().entrySet()){
                Node neighbour = entry.getKey();
                int edgeweight = entry.getValue();

                int newDistance = curr.getDistance() + edgeweight;

                if (newDistance < neighbour.getDistance()){
                    neighbour.setDistance(newDistance);
                    List<Node>newPath = new LinkedList<>(curr.getPath());
                    newPath.add(curr);
                    neighbour.setPath(newPath);
                    pq.add(neighbour);
                }
            }
        }

    }
}
