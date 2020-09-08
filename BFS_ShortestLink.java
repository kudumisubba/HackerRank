import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class BFS_ShortestLink {
    
    static int[] doBFS(HashMap<Integer, List<Integer>> fTm, int startPoint, int maxVal)
    {
        int [] result = new int[maxVal];
        Arrays.fill(result, -1); 
        HashSet<Integer> visited = new HashSet<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        
        queue.add(startPoint);
        result[0] = 0; // Ignore this position
        result[startPoint] = 0;
        visited.add(startPoint);
        
        while(queue.size() > 0){
            int current = queue.poll();   
            if(fTm.get(current) == null)
                continue;         
            for(Integer a : fTm.get(current))
            {
                if(!visited.contains(a))
                {
                    queue.offer(a); // offer returns false, whereas add will throw ex
                    visited.add(a);
                    result[a] = result[current] + 6;
                }
            }
        }                
        return result;        
    }
    
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int q = scanner.nextInt();
        int graphNodes = 0;  //m
        int graphEdges = 0;  //n
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        
        for (int i1 = 0; i1 < q; i1++) {
            String[] graphNodesEdges = scanner.nextLine().split(" ");
            graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
            graphEdges = Integer.parseInt(graphNodesEdges[1].trim());
            
            HashMap<Integer, List<Integer>> fromToMap = new HashMap<>();

            for (int i = 0; i < graphEdges; i++) {
                String[] graphFromTo = scanner.nextLine().split(" ");
                        
                int ab = Integer.parseInt(graphFromTo[0].trim());
                int ba = Integer.parseInt(graphFromTo[1].trim());
                
                if(fromToMap.containsKey(ab))
                {
                    List<Integer> l = fromToMap.get(ab);
                    l.add(ba);
                    fromToMap.put(ab, l);
                }
                else
                {
                    List<Integer> l = new ArrayList<>();
                    l.add(ba);
                    fromToMap.put(ab, l);                            
                }
                
                if(fromToMap.containsKey(ba))
                {
                    List<Integer> l = fromToMap.get(ba);
                    l.add(ab);
                    fromToMap.put(ba, l);
                }
                else
                {
                    List<Integer> l = new ArrayList<>();
                    l.add(ab);
                    fromToMap.put(ba, l);                
                }                        
                                
            }
            
            int startPoint = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int [] ans = doBFS(fromToMap, startPoint, graphNodes+1);
            
            for(int a : ans)
            {
                if(a == 0)
                    continue;
                bufferedWriter.write(String.valueOf(a) + " ");
            }
            bufferedWriter.newLine();
        }
        bufferedWriter.close();

        scanner.close();
    }

}
