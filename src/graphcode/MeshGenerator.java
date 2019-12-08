package graphcode;

import graphcode.FordFulkerson.Edge_;
import graphcode.FordFulkerson.FordFulkersonDfsSolver;
import graphcode.FordFulkerson.NetworkFlowSolverBase;
import graphcode.Edge;
import graphcode.Vertex;
import graphcode.SimpleGraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeshGenerator {

	public SimpleGraph generate(int row, int col, int lower, int upper) {
		Vertex s, t;
		Edge e;
		int capacity;
		Iterator<Edge> edges_iter;
		String directory = System.getProperty("user.dir");
		String fileName = "MeshGraph.txt";
		ArrayList<Vertex> vertex = new ArrayList<Vertex>();
		ArrayList<Edge> edge = new ArrayList<Edge>();
		SimpleGraph G = new SimpleGraph();

		s = G.insertVertex(null, 's');

		// add vertex
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				vertex.add(G.insertVertex(null, "v" + i + "_" + j));
			}
		}

		t = G.insertVertex(null, 't');

		// add edges from source and sink to internal vertexes
		for (int i = 0; i < row; i++) {
			capacity = lower + (int)(Math.random() * (upper-lower+1));
			edge.add(G.insertEdge(s, vertex.get(i * col), capacity, "s_" + vertex.get(i * col).getName()));
			capacity = lower + (int)(Math.random() * (upper-lower+1));
			edge.add(G.insertEdge(vertex.get((i + 1) * col - 1), t, capacity, vertex.get((i + 1) * col - 1).getName() + "_t"));
		}

		// add parallel edges
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col - 1; j++) {
				capacity = 1 + (int)(Math.random() * (upper+1));
				edge.add(G.insertEdge(vertex.get(j + i * col), vertex.get(j + 1 + i * col), capacity,
						(String) vertex.get(j + i * col).getName() + "-"
								+ (String) vertex.get(j + 1 + i * col).getName()));
			}
		}

		// add vertical edges
		for (int j = 0; j < col; j++) {
			for (int i = 0; i < row - 1; i++) {
				capacity = 1 + (int)(Math.random() * (upper+1));
				edge.add(G.insertEdge(vertex.get(j + i * col), vertex.get(j + (i + 1) * col), capacity,
						(String) vertex.get(j + i * col).getName() + "-"
								+ (String) vertex.get(j + (i + 1) * col).getName()));
			}
		}
		
		
		// write the graph into txt
		try {
			File writename = new File(directory, fileName);
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write("-------------------------------------\n");
			out.write("from\tto\tcapacity\n");
			out.write("-------------------------------------\n");
			for(edges_iter=G.edges(); edges_iter.hasNext();) {
				e = edges_iter.next();
				System.out.println(e.getFirstEndpoint().getName()+" "+e.getSecondEndpoint().getName()+" "+e.getData());
				out.write(e.getFirstEndpoint().getName()+"\t"+e.getSecondEndpoint().getName()+"\t"+e.getData()+"\n");
			}
			out.flush();
			out.close();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return G;
	}
	


	public static void main(String[] args) {
		MeshGenerator mesh = new MeshGenerator();
		int lower = 4;
		int upper = 10;
		SimpleGraph G = mesh.generate(3, 4, lower, upper);
	}

}
