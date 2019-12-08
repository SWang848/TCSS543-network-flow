package graphcode;

import java.io.*;
import java.util.*;

import graphcode.Edge;
import graphcode.SimpleGraph;
import graphcode.Vertex;

public class BuildGraphClass
{
	public static void BuildGraph(String fileName, String directory, int vertices, int Svertives, int dense/*must be 100*/, int maxCapacity, int minCapacity, SimpleGraph G){
		Random random = new Random();
		Vertex s, v, w, t;
		Edge e;
		
		for(int i=0; i<vertices; i++) {
			 v = G.insertVertex(null, i);
		}
		
		LinkedList vertexlist;
		
		vertexlist=G.vertexList;
		
		try{
			String dirName = directory;//           
			if (dirName.equals ("")) {dirName = ".";}

			File outputfile = new File(dirName, fileName);
			int[][] Graph = new int[vertices][vertices];
			int n, m;
			
			for ( n = 0; n < vertices; n++) {
				
				for ( m = n+1; m < vertices; m++)
				{
										
					int randomInt = (random.nextInt((maxCapacity-minCapacity+1))+minCapacity) ;
					
					int k =(int) (1000.0*Math.random()/10.0);
					int b = (k < dense) ? 1 : 0;
					if(b == 0)
					{
						Graph[n][m] = Graph[m][n] = b;
						//e=G.insertEdge(v, w, (int)b, 'e');
					}
					else
					{
						Graph[n][m] = Graph[m][n] = randomInt;
					
					}
				}
			}
			
            	PrintWriter output = new  PrintWriter(new FileWriter(outputfile));
            	System.out.println("graph.length: "+ Graph.length);
            	for(int x = 0; x < Graph.length; x++)
            	{
            		v=(Vertex)vertexlist.get(x);
            		if(x == 0)
            		{
            			for(int y = 0; y < Svertives/*Graph[x].length*/; y++)
            			{
            				w=(Vertex)vertexlist.get(y);
            				String value = String.valueOf(Graph[x][y]);
            				System.out.println("x: "+x+", y: "+y+", value: "+value);
            				if(y != 0)
           				{
            					if(value.equals("0") == false)
            					{
            						System.out.println("v: "+v.getName()+"w: "+w.getName());
            						output.print("s " + String.valueOf(y) + " " +  value +"\n");
            						e=G.insertEdge(v, w, (int)Graph[x][y], 'e');
            						System.out.println("s " + String.valueOf(y) + " " +  value +"\n");
            					}
            				}
            			}
            		}
            		else
            		{
            			if(x == Graph.length-1)
            			{
            				for(int y = 0; y < Graph[x].length; y++)
            				{
            					w=(Vertex)vertexlist.get(y);
            					String value = String.valueOf(Graph[x][y]);
            					System.out.println("x: "+x+", y: "+y+", value: "+value);
            					if(y != 0)
            					{
            						if(value.equals("0") == false)
            						{
            							System.out.println("v: "+v.getName()+"w: "+w.getName());
            							output.print(String.valueOf(y) + " t " +  value +"\n");
            							e=G.insertEdge(v, w, (int)Graph[x][y], 'e');
            							System.out.println(String.valueOf(y) + " t " +  value );
            						}
            					}
            				}
            			}
            			else
            			{
            				for(int y = 0; y < Graph[x].length; y++)
            				{
            					w=(Vertex)vertexlist.get(y);
            					String value = String.valueOf(Graph[x][y]);
            					System.out.println("x: "+x+", y: "+y+", value: "+value);
            					if(y != 0)
            					{
            						if(value.equals("0") == false)
            						{
            							System.out.println("v: "+v.getName()+"w: "+w.getName());
            							output.print(x + " " + String.valueOf(y) + " " +  value +"\n");
            							e=G.insertEdge(v, w, (int)Graph[x][y], 'e');
            							System.out.println(x + " " + String.valueOf(y) + " " +  value +"\n");
            						}
            					}
            				}
            			}
            		}
            		
            	}

            	output.close();
		} 
		catch (IOException ex)
		{
			System.err.println("Error opening file" +ex);
			return;
		}
		System.out.print("\nDone");
	}
	public static void main(String[] args) throws Exception{
//		int vertices, Svertices, dense, maxCapacity, minCapacity;
//		
//		System.out.println("\n\n---------------------------------------------------");
//		System.out.print("Enter number of vertices: \t");
//		vertices = GetInt();
//		System.out.print("Enter dense: \t");
//		dense = GetInt();
//		
//		System.out.print("Enter minimum capacity: \t\t\t");
//		minCapacity = GetInt();
//		System.out.print("Enter maximum capacity: \t\t\t");
//		maxCapacity = GetInt();
//		String directory = System.getProperty("user.dir");
//		System.out.print("Enter the output file name: \t\t\t");
//		String fileName = "test.txt";	
//		System.out.println("---------------------------------------------------\n");
//		
//		SimpleGraph G = new SimpleGraph();
//		BuildGraph(fileName, directory, vertices, Svertices, dense, maxCapacity, minCapacity,G);
//		
//		System.out.println("\n\nOutput is created at: \t" + directory + "\\" + fileName);
//		
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

}