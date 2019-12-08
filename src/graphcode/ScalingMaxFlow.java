package graphcode;
import graphcode.Edge;
import graphcode.Vertex;
import graphcode.SimpleGraph;


import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graphcode.Edge;
import graphcode.SimpleGraph;
import graphcode.Vertex;

public class ScalingMaxFlow {

  private static class Edge_ {
    public int from, to;
    public Edge_ residual;
    public long flow;
    public final long capacity;

    public Edge_(int from, int to, long capacity) {
      this.from = from;
      this.to = to;
      this.capacity = capacity;
    }

    public boolean isResidual() {
      return capacity == 0;
    }

    public long remainingCapacity() {
      return capacity - flow;
    }

    public void augment(long bottleNeck) {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
    }

    public String toString(int s, int t) {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
      return String.format(
          "Edge_ %s -> %s | flow = %3d | capacity = %3d | is residual: %s",
          u, v, flow, capacity, isResidual());
    }
  }

  private abstract static class NetworkFlowSolverBase {

    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    static final long INF = Long.MAX_VALUE / 2;

    // Inputs: n = number of nodes, s = source, t = sink
    final int n, s, t;
    SimpleGraph G= new SimpleGraph();

    // 'visited' and 'visitedToken' are variables used in graph sub-routines to
    // track whether a node has been visited or not. In particular, node 'i' was
    // recently visited if visited[i] == visitedToken is true. This is handy
    // because to mark all nodes as unvisited simply increment the visitedToken.
    private int visitedToken = 1;
    private int[] visited;

    // Indicates whether the network flow algorithm has ran. The solver only
    // needs to run once because it always yields the same result.
    protected boolean solved;

    // The maximum flow. Calculated by calling the {@link #solve} method.
    protected long maxFlow;

    // The adjacency list representing the flow graph.
    protected List<Edge_>[] graph;

    public NetworkFlowSolverBase(int n, int s, int t, SimpleGraph G) {
      this.n = n;
      this.s = s;
      this.t = t;
      this.G=G;
      initializeEmptyFlowGraph(G);
      visited = new int[n];
    }

    // Constructs an empty graph with n nodes including s and t.
    private void initializeEmptyFlowGraph(SimpleGraph G) {
      graph = new List[n];
      for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge_>();
      LinkedlistToArrayList(G);
    }
    
    public void LinkedlistToArrayList (SimpleGraph G) {
		//List<Edge_>[] graph=new List[n];
	    //for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge>();
	    
	    Object[] VertexArray= new Object[n];
	    
	    Vertex v, u;
	    Edge e;
	    Iterator i;
	    int x=0, y=0;
	    
	    for (i= G.vertices(); i.hasNext(); x++) {
	    	v = (Vertex) i.next();
	    	VertexArray[x]=v.getName();
		}
			
	    for (i=G.edges(); i.hasNext();) {

	    	e=(Edge) i.next();
	    	v=e.getFirstEndpoint();
	    	u=e.getSecondEndpoint();
	    	
	    	for(x=0; VertexArray[x]!=v.getName(); x++);
	    	
	    	for(y=0; VertexArray[y]!=u.getName(); y++);  
	    	
	    	String stringToConvert = String.valueOf(e.getData());
	    	Long convertedLong = Long.parseLong(stringToConvert);
	    	addEdge(x,y,convertedLong);
	    	
		}	
	   
		//return graph;
	}

    public void addEdge(int from, int to, long capacity) {
      if (capacity <= 0) throw new IllegalArgumentException("Forward edge capacity <= 0");
      Edge_ e1 = new Edge_(from, to, capacity);
      Edge_ e2 = new Edge_(to, from, 0);
      e1.residual = e2;
      e2.residual = e1;
      graph[from].add(e1);
      graph[to].add(e2);
    }

    public List<Edge_>[] getGraph() {
      execute();
      return graph;
    }

    // Returns the maximum flow from the source to the sink.
    public long getMaxFlow() {
      execute();
      return maxFlow;
    }

    // Marks node 'i' as visited.
    public void visit(int i) {
      visited[i] = visitedToken;
    }

    // Returns true/false depending on whether node 'i' has been visited or not.
    public boolean visited(int i) {
      return visited[i] == visitedToken;
    }

    // Resets all nodes as unvisited. This is especially useful to do
    // between iterations finding augmenting paths, O(1)
    public void markAllNodesAsUnvisited() {
      visitedToken++;
    }

    // Wrapper method that ensures we only call solve() once
    private void execute() {
      if (solved) return;
      solved = true;
      solve();
   
    }

    // Method to implement which solves the network flow problem.
    public abstract void solve();
  }

  private static class CapacityScalingSolver extends NetworkFlowSolverBase {

    private long delta;

    public CapacityScalingSolver(int n, int s, int t, SimpleGraph G) {
      super(n, s, t, G);
    }

    @Override
    public void addEdge(int from, int to, long capacity) {
      super.addEdge(from, to, capacity);
      delta = max(delta, capacity);
    }

    // Performs the Ford-Fulkerson method applying a depth first search as
    // a means of finding an augmenting path.
    @Override
    public void solve() {
      // Start delta at the largest power of 2 <= the largest capacity.
      // Equivalent of: delta = (long) pow(2, (int)floor(log(delta)/log(2)))
      delta = Long.highestOneBit(delta);
      // Repeatedly find augmenting paths from source to sink using only edges
      // with a remaining capacity >= delta. Half delta every time we become unable
      // to find an augmenting path from source to sink until the graph is saturated.
      for (long f = 0; delta > 0; delta /= 2) {
        do {
          markAllNodesAsUnvisited();
          f = dfs(s, INF);
          maxFlow += f;
        } while (f != 0);
      }
    }

    private long dfs(int node, long flow) {
      // At sink node, return augmented path flow.
      if (node == t) return flow;
      visit(node);

      for (Edge_ edge : graph[node]) {
        long cap = edge.remainingCapacity();
        if (cap >= delta && !visited(edge.to)) {
          long bottleNeck = dfs(edge.to, min(flow, cap));
          // Augment flow with bottle neck value
          if (bottleNeck > 0) {
            edge.augment(bottleNeck);
            return bottleNeck;
          }
        }
      }
      return 0;
    }
  }

  /* EXAMPLE */

  public static void main(String[] args) {
    // n is the number of nodes including the source and the sink.

      SimpleGraph G = new SimpleGraph();
      Vertex v, w, a, b, c;
      Edge g, x, y,z,f;
      v = G.insertVertex(null, "v");
      a = G.insertVertex(null, "a");
      b = G.insertVertex(null, "b");
      x = G.insertEdge(v, a, 8, "X");
      c = G.insertVertex(null, "c");
      y = G.insertEdge(v, b, 10, "Y");
      w = G.insertVertex(null, "w");
      g = G.insertEdge(a, c, 3, "g");
      f = G.insertEdge(c, w, 20, "f");
      z = G.insertEdge(b, c, 9, "z");


      int n = G.numVertices();

      int s = 0;
      int t = n-1;

      NetworkFlowSolverBase solver = new CapacityScalingSolver(n, s, t, G);

      System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());
      
      List<Edge_>[] resultGraph = solver.getGraph();

      // Displays all edges part of the resulting residual graph.
      for (List<Edge_> edges : resultGraph) for (Edge_ e : edges) System.out.println(e.toString(s, t));
  	}
}