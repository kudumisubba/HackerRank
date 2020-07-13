package com.hackerrank;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import static java.util.stream.Collectors.joining;

/*
 * https://www.hackerrank.com/challenges/frequency-queries/problem
 * Solution has been written with help from the discussion board users
 * - Kudumi
 */

public class FrequencyQueries2 {

	static List<Integer> freqQuery(List<int[]> queries) {
		 List<Integer> arr = new ArrayList<>(); 
		    Map<Integer, Integer> hm = new HashMap<Integer,Integer>();
		    Map<Integer, Integer> fm = new HashMap<Integer,Integer>();
		    int a;
		    int b; 
		    for(int i=0;i<queries.size();i++)
		    {
		        a=queries.get(i)[0];
		        b=queries.get(i)[1];

		        if(a==1)
		        {
		        	int tmp = (hm.getOrDefault(b, 0));
		            hm.put(b, hm.getOrDefault(b, 0)+1);
		            if(fm.get(tmp) !=  null)
		            	fm.put(tmp, fm.getOrDefault(tmp, 0)-1);
		            fm.put(hm.get(b), fm.getOrDefault(hm.get(b), 0)+1);
		        }
		        else if(a==2)
		        {
		            if(hm.getOrDefault(b, 0)>0)
		            {
		            	int tmp = hm.get(b);
		                hm.put(b,hm.getOrDefault(b, 0)-1);
		                
		                // Follow the same for fm
		                fm.put(tmp, fm.getOrDefault(tmp, 0)-1);
		                // Update tmp - 1
		                if(fm.get(tmp - 1) != null)
		                	fm.put(tmp-1, fm.getOrDefault(tmp-1, 0)+1);
		            }  

		        }
		        else
		        {		          
		        	if(fm.get(b) != null && fm.get(b) > 0)
		            {		         
		                arr.add(1);
		            }
		            else
		            {
		                arr.add(0);
		            }
		        }
		    }
		    //Debug
		    hm.forEach( (k,v) -> {
		    	System.out.print(k + ":" + v + " ");
		    });
		    System.out.println();
		    fm.forEach( (k,v) -> {
		    	System.out.print(k + ":" + v + " ");
		    });
		    System.out.println();
		    return arr;
}


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int q = Integer.parseInt(bufferedReader.readLine().trim());
        
        List<int[]> queries = new ArrayList<>(q);
        Pattern p  = Pattern.compile("^(\\d+)\\s+(\\d+)\\s*$");

        for (int i = 0; i < q; i++) {
            int[] query = new int[2];
            Matcher m = p.matcher(bufferedReader.readLine());
            if (m.matches()) {
              query[0] = Integer.parseInt(m.group(1));
              query[1] = Integer.parseInt(m.group(2));
              queries.add(query);
            }
          }

        List<Integer> ans = freqQuery(queries);

        bufferedWriter.write(
            ans.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}    
