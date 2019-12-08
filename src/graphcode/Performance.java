package graphcode;

import java.util.ArrayList;
import java.util.List;

import graphcode.GraphGenerator;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import graphcode.FordFulkerson.Edge_;
import graphcode.FordFulkerson.FordFulkersonDfsSolver;
import graphcode.FordFulkerson.NetworkFlowSolverBase;
import graphcode.MeshGenerator;
import graphcode.BipartiteGraph;
import graphcode.BuildGraphClass;

public class Performance {
	
	public void getMaxflow(SimpleGraph G) {
		int n = G.numVertices();

		int s = 0;
		int t = n - 1;

		NetworkFlowSolverBase solver = new FordFulkersonDfsSolver(n, s, t, G);
		System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

		List<Edge_>[] resultGraph = solver.getGraph();

		for (List<Edge_> edges : resultGraph) {
			for (Edge_ e_1 : edges) {
				System.out.println(e_1.toString(s, t));}}
	}
	
	private double[][] cal_average(double[][] data){
		for(int i=0; i<=data.size(); i++) {
			
		}
		return data;
	}
	
	public void getScalingMaxflow(SimpleGraph G) {
		int n = G.numVertices();

		int s = 0;
		int t = n - 1;

		NetworkFlowSolverBase solver = new FordFulkersonDfsSolver(n, s, t, G);
		System.out.printf("Maximum Flow is: %d\n", solver.getMaxFlow());

		List<Edge_>[] resultGraph = solver.getGraph();

		for (List<Edge_> edges : resultGraph) {
			for (Edge_ e_1 : edges) {
				System.out.println(e_1.toString(s, t));}}
	}
	
	public void MeshPerformance() {
		MeshGenerator mesh = new MeshGenerator();
		Performance per = new Performance();
		int lower = 2;
		int upper = 10;
		long startTime, endTime, time;
		int flag = 0;
		String[] colKeys = new String[100];
		double[][] data = new double[2][100];
		for(int i=10; i<=1000; i+=10) {
			SimpleGraph G = mesh.generate(10+i, 10, lower, upper);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = 10+i+"*10";
			flag++;
		}
		
		flag=0;
		per.DrawGraph(colKeys, data, "mesh_size", "time(ms)", "D:\\TCSS543\\project\\Mesh_size_fixed_col.jpg", "Mesh");
		for(int i=10; i<=1000; i+=10) {
			SimpleGraph G = mesh.generate(10, 10+i, lower, upper);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = "10*"+10+i;
			flag++;
		}
		per.DrawGraph(colKeys, data, "mesh_size", "time(ms)", "D:\\TCSS543\\project\\Mesh_size_fixed_row.jpg", "Mesh");
		flag = 0;
		for(int i=10; i<=1000; i+=10) {
			SimpleGraph G = mesh.generate(100, 100, i, i);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = 100*i+"";
			flag++;
		}
		per.DrawGraph(colKeys, data, "capacity", "time(ms)", "D:\\TCSS543\\project\\Mesh_fixed_edges.jpg", "Mesh");
	}
	
	public void BipartitePerformance() throws Exception {
		BipartiteGraph bipartite = new BipartiteGraph();
		Performance per = new Performance();
		int n=40, m=40, minCapacity=6, maxCapacity=6;
		long startTime, endTime, time;
		int flag = 0;
		String[] colKeys = new String[100];
		double[][] data = new double[2][100];
		for(int i=10; i<=1000; i+=10) {
			SimpleGraph G = bipartite.generator(n, i, minCapacity, maxCapacity);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = n+i+i*n+"";
			flag++;
		}
		per.DrawGraph(colKeys, data, "The number of edges", "time(ms)", "D:\\TCSS543\\project\\Bipartite_fixed_c.jpg", "Bipartite");
		flag = 0;
		for(int i=10; i<=1000; i+=10) {
			SimpleGraph G = bipartite.generator(n, m, i, i);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime-startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = i*n+"";
			flag++;
		}
		per.DrawGraph(colKeys, data, "capacity", "time(ms)", "D:\\TCSS543\\project\\Bipartite_fixed_edges.jpg", "Bipartite");
	
	}
	
	public void ProbabilityPerformance() throws Exception{
		BuildGraphClass probabilityGraph = new BuildGraphClass();
		Performance per = new Performance();
		int vertices = 40, Svertices=10, dense = 100, minCapacity=10, maxCapacity=10;
		long startTime, endTime, time;
		int flag = 0;
		String[] colKeys = new String[50];
		double[][] data = new double[2][50];
		for(int i=10; i<=500; i+=10) {
			SimpleGraph G = new SimpleGraph();
			probabilityGraph.BuildGraph("test", "", i, Svertices, dense, maxCapacity, minCapacity, G);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime - startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime - startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = i*(i-Svertices)+"";
			flag++;
		}
		per.DrawGraph(colKeys, data, "edges", "time(ms)", "D:\\TCSS543\\project\\Probability_fixed_capacity.jpg", "Probability");
		flag = 0;
		for(int i=10; i<=500; i+=10) {
			SimpleGraph G = new SimpleGraph();
			probabilityGraph.BuildGraph("test", "", vertices, Svertices, dense, i, i, G);
			startTime=System.currentTimeMillis();
			per.getMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime - startTime;
			data[0][flag] = (double)time;
			startTime=System.currentTimeMillis();
			per.getScalingMaxflow(G);
			endTime=System.currentTimeMillis();
			time = endTime - startTime;
			data[1][flag] = (double)time;
			colKeys[flag] = i*minCapacity+"";
			flag++;
		}
		per.DrawGraph(colKeys, data, "capacity", "time(ms)", "D:\\TCSS543\\project\\Probability_fixed_edge.jpg", "Probability");
	}
	
	
	
	public void DrawGraph(String[] colKeys, double[][] data, String xAxisLabel, String yAxisLabel, String path, String title) {
		CategoryDataset dataset = GraphGenerator.createDataset(colKeys, data);
		JFreeChart freeChart = GraphGenerator.createChart(dataset, xAxisLabel, yAxisLabel, title);
		GraphGenerator.saveAsFile(freeChart, path, 2000, 1000);
	}
	
	
	public static void main(String[] args) throws Exception {
		Performance test = new Performance();
//		test.BipartitePerformance();
//		test.MeshPerformance();
		test.ProbabilityPerformance();
	}
}
