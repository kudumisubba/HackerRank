package com.hackerrank;

import java.io.*;
import java.util.*;

public class NearestClone3 {

    // Complete the findShortest function below.

    /*
     * For the unweighted graph, <name>:
     *
     * 1. The number of nodes is <name>Nodes.
     * 2. The number of edges is <name>Edges.
     * 3. An edge exists between <name>From[i] to <name>To[i].
     
     * This code has help from the discussion board, especially the queue handler
     *
     */
	static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
		 // 1 - Create a hashmap of from two pairs
    	// Where key is from , value is a list of to's
    	HashMap<Integer, List<Integer>> fromToMap =
    			new HashMap<Integer, List<Integer>>();
    	
    	// 2 - Create another hashmap that holds distance between nodes of the same color
    	HashMap<Integer, Integer> colorMap =
    			new HashMap<Integer, Integer>();
    	
    	int min = graphNodes - 1;
    	int i = 1;
    	for(; i <= graphFrom.length; i++)
    	{
    		if(fromToMap.containsKey(graphFrom[i - 1]))
    		{
    			List<Integer> l = fromToMap.get(graphFrom[i - 1]);
    			l.add(graphTo[i - 1]);
    			fromToMap.put(graphFrom[i - 1], l);
    		}
    		else
    		{
    			List<Integer> l = new ArrayList<>();
    			l.add(graphTo[i - 1]);
    			fromToMap.put(graphFrom[i - 1], l);    			    		
    		}
    		
    		if(fromToMap.containsKey(graphTo[i - 1]))
    		{
    			List<Integer> l = fromToMap.get(graphTo[i - 1]);
    			l.add(graphFrom[i - 1]);
    			fromToMap.put(graphTo[i - 1], l);
    		}
    		else
    		{
    			List<Integer> l = new ArrayList<>();
    			l.add(graphFrom[i - 1]);
    			fromToMap.put(graphTo[i - 1], l);    			
    		}    		    		
    	}    	    	

	    Queue<Integer> queue = new LinkedList();
	    for(int j = 0; j < ids.length; j++){
	        if(ids[j] == val){
	            queue.add(j + 1);
	            colorMap.put(j + 1, 0);
	        }
	    }

	    if(queue.size() < 2){
	        return -1;
	    }
	    HashSet<Integer> visited = new HashSet();
	    while(queue.size() > 0){
	        int current = queue.poll();
	        visited.add(current);

	        for(Integer a : fromToMap.get(current)) {
	            if(colorMap.containsKey(a) && !visited.contains(a)){
	                return colorMap.get(a) + colorMap.get(current) + 1;
	            }
	            else {
	                queue.add(a);
	                colorMap.put(a, colorMap.get(current) + 1);
	            }
	        }
	    }
	    return -1;
	}
	

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
    	BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] graphNodesEdges = scanner.nextLine().split(" ");
        int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
        int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

        int[] graphFrom = new int[graphEdges];
        int[] graphTo = new int[graphEdges];

        for (int i = 0; i < graphEdges; i++) {
            String[] graphFromTo = scanner.nextLine().split(" ");
            graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
            graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
        }

        long[] ids = new long[graphNodes];

        String[] idsItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < graphNodes; i++) {
            long idsItem = Long.parseLong(idsItems[i]);
            ids[i] = idsItem;
        }

        int val = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int ans = findShortest(graphNodes, graphFrom, graphTo, ids, val);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();
                
        scanner.close();
    }
}


