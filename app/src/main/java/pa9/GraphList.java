package pa9;
import java.util.*;


public class GraphList implements Graph{
    private int vertices; // Number of vertices
    private List<List<Edge>> adjacencyList; // Adjacency list for the graph

    public GraphList(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addWeightedEdge(int source, int destination, int weight) {
        adjacencyList.get(source).add(new Edge(source, destination, weight));
    }

    // public boolean hasNegativeCycle(){
    //     int[] failed = new int[vertices];
    //     Arrays.fill(failed,Integer.MAX_VALUE);
       
    //     for(int i=0;i<vertices; i++){
    //         int[] result = shortestPath(i);
    //         if (result.equals(failed)) {
    //                 return true;
    //         }
    //     }
    //     return false;
        
    // }
    public boolean hasNegativeCycle(){
        if (vertices < 2) {
            return false;
        }
        int[] distances = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        
        distances[0] = 0;
        
        for(int i = 0; i< vertices-1; i++) {
            for(int j = 0; j< vertices; j++) {
                for (Edge edge : adjacencyList.get(j)) {
                    int newDist = distances[edge.source] + edge.weight;
                    if (newDist < distances[edge.destination]) {
                        distances[edge.destination] = newDist;
                    }
                }
            }
         }
        

        for(int j = 0; j< vertices; j++) {
           for (Edge edge : adjacencyList.get(j)) {
               int newDist = distances[edge.source] + edge.weight;
               if (newDist < distances[edge.destination]) {
                    return true;
                }
            }
        }
        return false;
    }
    public int[] shortestPath(int startVertex) {
        int[] distances = new int[vertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;
        for(int i = 0; i< vertices-1; i++) {
            for(int j = 0; j< vertices; j++) {
                for (Edge edge : adjacencyList.get(j)) {
                    int newDist = distances[edge.source] + edge.weight;
                    if (newDist < distances[edge.destination]) {
                        distances[edge.destination] = newDist;
                    }
                }
            }
         }
        

        for(int j = 0; j< vertices; j++) {
           for (Edge edge : adjacencyList.get(j)) {
               int newDist = distances[edge.source] + edge.weight;
               if (newDist < distances[edge.destination]) {
                    int[] failed = new int[vertices];
                    Arrays.fill(failed,Integer.MAX_VALUE);
                    return failed;
                }
            }
        }
        return distances;
    }


    public HashSet<Edge> minimumSpanningTreePrim() {
        HashSet<Edge> T = new HashSet<>();

        if(vertices == 0){
            return T;
        }
        List<Integer> results = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        int curr = 0;
        Arrays.fill(visited, false);
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        pq.add(new Edge(-1, 0, 0));
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            curr = edge.destination;
            if (visited[curr]) {
                continue;
            }
            visited[curr] = true;
            if (edge.source != -1) {
                T.add(edge);
                results.add(edge.weight);
            }
            List<Edge> outgoingEdges = adjacencyList.get(curr);
            for (Edge outgoingEdge : outgoingEdges) {
                if (!visited[outgoingEdge.destination]) {
                    pq.add(outgoingEdge);
                }
            }
        }
        return T;
    }
    public HashSet<Edge> minimumSpanningTree(){
        HashSet<Edge> mst = new HashSet<>();

        if(vertices == 0){
            return mst;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(List<Edge> edge: adjacencyList){
            pq.addAll(edge);
        }

        Set<Integer> visited = new HashSet<>();

        while(!pq.isEmpty() && visited.size() < vertices){
            Edge edge = pq.poll();

            if(!visited.contains(edge.source) || !visited.contains(edge.destination)){
                mst.add(edge);
                visited.add(edge.source);
                visited.add(edge.destination);
            }
        }
        return mst;
    }

}