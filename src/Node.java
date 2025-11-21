import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.lang.Integer;

public class Node implements Comparable<Node> {
    private String name;
    private Integer distance = Integer.MAX_VALUE;
    private List<Node> shortestPath = new LinkedList<>();
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(String name){
        this.name = name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPath(List<Node> shortestPath){
        this.shortestPath = shortestPath;
    }
    public void setDistance(Integer distance){
        this.distance = distance;
    }
    public void setAdjacentNodes(Map<Node, Integer> adjNode){
        this.adjacentNodes = adjNode;
    }
    public String getName(){
        return name;
    }
    public List<Node> getPath(){
        return shortestPath;
    }
    public Integer getDistance(){
        return distance;
    }
    public Map<Node, Integer> getAdjacentNodes(){
        return adjacentNodes;
    }

    public void addAdjacentNode(Node node, int weight){
        adjacentNodes.put(node,weight);

    }
    public int compareTo(Node node){
        return Integer.compare(this.distance, node.getDistance());
    }


}
