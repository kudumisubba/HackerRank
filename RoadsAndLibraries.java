package com.hackerrank;

import java.io.*;
import java.util.*;

public class RoadAndLibrary {
	static int numConnected = 0;

    // Complete the roadsAndLibraries function below.
    static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {

    	long retValue = 0;
    	if(numConnected > 0)
    		numConnected = 0;
    	
    	int sizeOfAdjList = ( n > cities.length ? n + 1 : cities.length + 1);

    	@SuppressWarnings("unchecked")
		ArrayList<Integer>[] connectedCities =
    			(ArrayList<Integer>[]) new ArrayList[sizeOfAdjList];
    	
    	boolean[] visited = new boolean[sizeOfAdjList];
    	visited[0] = true;
    	
    	connectedCities[0] = new ArrayList<Integer>();
    	connectedCities[0].add(0);
    	
    	// Never start from 0
    	
    	for(int i = 0; i < cities.length; i++)
    	{
    		// 2 dimensional only
    		if(connectedCities[cities[i][0]] == null)
    			connectedCities[cities[i][0]] = new ArrayList<Integer>();
    		
    		connectedCities[cities[i][0]].add(cities[i][1]);
    		if(connectedCities[cities[i][1]] == null)
    			connectedCities[cities[i][1]] = new ArrayList<Integer>();
			connectedCities[cities[i][1]].add(cities[i][0]);
			
			visited[i] = false;
    	}
    	
    	/*
    	 * Each road between cities, if the cost of that road is greater than the
    	 * cost of a library, it is better to build a library in every city rather
    	 * than a road between cities
    	 */
        
    	if(c_road > c_lib)
    	{
    		retValue = (long)c_lib * (long)n;    		
    	}
    	else
    	{
    		for(int c = 1; c < connectedCities.length; c++) {
                 if(visited[c] == false) {
                     dfs(c, connectedCities, visited);                           
                 }
                 else
                 {
                	 numConnected++;
                 }
             }
    	
    		 // Number of libraries required = Number of cities - Number of connectedComponents
    		 long numberLibraries = n - numConnected;
    		 retValue = numberLibraries * (long)c_lib;
    		 
    		 if( n <= cities.length)
    			 retValue += (n - 1) * (long)c_road;
    		 if (n > cities.length)
    			 retValue += (numConnected) * (long)c_road;    			 
    	}
    	    	
    	
    	return retValue;

    }
    
    private static void dfs(int city, ArrayList<Integer>[] connectedCities, boolean[] visited){
        visited[city] = true; 
        if(connectedCities[city] == null)
        	return;
        for (int c = 0; c < connectedCities[city].size(); c++){        	
            if(!visited[connectedCities[city].get(c)]){
                dfs(connectedCities[city].get(c) , connectedCities, visited); // understand this recursion
            }
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
    	  BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nmC_libC_road = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nmC_libC_road[0]);

            int m = Integer.parseInt(nmC_libC_road[1]);

            int c_lib = Integer.parseInt(nmC_libC_road[2]);

            int c_road = Integer.parseInt(nmC_libC_road[3]);

            int[][] cities = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] citiesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int citiesItem = Integer.parseInt(citiesRowItems[j]);
                    cities[i][j] = citiesItem;
                }
            }

            long result = roadsAndLibraries(n, c_lib, c_road, cities);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
