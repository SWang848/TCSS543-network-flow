package graphcode;

import java.io.*;
import java.util.*;

import graphcode.Edge;
import graphcode.Vertex;
import graphcode.SimpleGraph;

public class BipartiteGraph
{
	public static void main(String[] args) throws Exception{

//		per.getScalingMaxflow(G);
	}
	
	public SimpleGraph generator(int n, int m, int minCapacity, int maxCapacity) throws Exception {
		int i, j;
		double maxProbability=1;
		double value, x;
		
		SimpleGraph G = new SimpleGraph();
		
		Vertex s, v, w, t;
		Edge e;
		
//		System.out.println("\n\n---------------------------------------------------");
//		System.out.print("Enter number of nodes on the source side: \t");
//		n = GetInt();
//		System.out.print("Enter number of nodes on the sink side: \t");
//		m = GetInt();
//		System.out.print("Enter max probability: \t\t\t\t");
//		maxProbability = GetReal();
		if(maxProbability > 1)
		{
			System.out.println("Max probability should be less than or equal to 1");
			return null;
		}
//		System.out.print("Enter minimum capacity: \t\t\t");
//		minCapacity = GetInt();
//		System.out.print("Enter maximum capacity: \t\t\t");
//		maxCapacity = GetInt();
		String directory = System.getProperty("user.dir");
		System.out.print("Enter the output file name: \t\t\t");
		String fileName = "test.txt";	
		System.out.println("---------------------------------------------------\n");
		
		s = G.insertVertex(null, 's');
		
		for(i=1; i<n+m+1; i++) {
			 v = G.insertVertex(null, i);
		}
		
		t = G.insertVertex(null, 't');
		
		LinkedList vertexlist;
		
		vertexlist=G.vertexList;
		
		try
		{
			PrintWriter outFile = new  PrintWriter(new FileWriter(new File(directory, fileName)));

			double[][] edge = new double[n][m];
			for(i=0; i<n; i++)
			{
				for(j=0; j<m; j++)
				{
					v=(Vertex)vertexlist.get(i+1);
					w=(Vertex)vertexlist.get(n+j+1);
					System.out.println("v: "+v.getName()+"w: "+w.getName());
					
					value = Math.random();
					if(value <= maxProbability) 
						edge[i][j] = value;
						
					else {
						edge[i][j] = 0;
						e=G.insertEdge(v, w, (int)0, 'e');
					}
				}
			}
			
			System.out.println("-----------------------------------------");
			System.out.println("\tSource\tSink\tCapacity");
			System.out.println("-----------------------------------------");			

			//computing the edges out of source
			for (i = 0; i < n; i++)
			{	
				x=Math.random();
				//Compute a capacity in range of [minCapacity, maxCapacity]
				value = Math.floor(minCapacity + (x * (maxCapacity - minCapacity + 1)));
				
				v=(Vertex)vertexlist.get(i+1);
				e=G.insertEdge(s, v, (int)value, 'e');
				
				System.out.println("\t" + "s" + "\tl" + (i + 1) + "\t" + (int)value);
				outFile.println("\t" + "s" + "\tl" + (i + 1) + "\t" + (int)value);
			}
			for(i=0; i<n; i++)
			{
				for(j=0; j<m; j++)
				{				
					if(edge[i][j] > 0)
					{
						v=(Vertex)vertexlist.get(i+1);
						w=(Vertex)vertexlist.get(n+j+1);
						int min=0;
						edge[i][j] = Math.floor(min + (edge[i][j] * (maxCapacity - min + 1)));
						if(edge[i][j]>0){
							e=G.insertEdge(v, w, (int)edge[i][j], 'e');
						}
						System.out.println("\tl"+ (i+1) + "\tr" + (j+1) + "\t" + (int)edge[i][j]);
						//computing for the vertices between source and sink and writing them to the output file
						outFile.println("\tl"+ (i+1) + "\tr" + (j+1) + "\t" + (int)edge[i][j]);
					}
				}
			}
			//computing the edges into the sink
			for (j=0; j < m; j++)
			{
				x=Math.random();
				value = Math.floor(minCapacity + (x * (maxCapacity - minCapacity + 1)));
				
				v=(Vertex)vertexlist.get(n+j+1);
				e=G.insertEdge(v, t, (int)value, 'e');
				
				System.out.println("\tr" + (j+1) + "\t" + "t" + "\t" + (int)value);
				outFile.println("\tr" + (j + 1) + "\t" + "t" + "\t" + (int)value);
			}

			System.out.println("\n\nOutput is created at: \t" + directory + "\\" + fileName);
			outFile.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		return G;
		
		 
//		int n_G = G.numVertices();
//
//		int s_G = 0;
//		int t_G = n_G-1;
//		
//		NetworkFlowSolverBase solver = new FordFulkersonDfsSolver(n_G, s_G, t_G, G);
//		System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());
//
//		List<Edge_>[] resultGraph = solver.getGraph();
//
//		for (List<Edge_> edges : resultGraph) for (Edge_ e_ : edges) System.out.println(e_.toString(s_G, t_G));

	}

	//helper functions
	public static String GetString() throws IOException 
	{
		BufferedReader stringIn = new BufferedReader (new
			InputStreamReader(System.in));
		return  stringIn.readLine();
	}

	public static int GetInt() throws IOException 
	{
		String aux = GetString();
		return Integer.parseInt(aux);
	}

	public static double GetReal() throws IOException 
	{
		String  aux = GetString();
		Double d  = new Double(aux);
		return  d.doubleValue() ;
	}
}