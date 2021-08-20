package com.hackerrank;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class CWResult {

	static class LineObj {
		int len;
		int startIdx;
		int endIdx;
		char[] letter;

		public LineObj(int i, int j, int k, char c[]) {
			this.len = i;
			this.startIdx = j;
			this.endIdx = k;
			this.letter = c;
		}
	}

	private static String compareStringsCols(List<String> l, char[][] arr) {
		char c[] = new char[10];
		String uniqueStr = "";
		int res[] = new int[l.size()];

		for (int i = 0; i < arr.length; i++) {
			// We need the first letter of the first row
			for (int j = 0; j < 10; j++) {
				c[j] = arr[j][i];
			}

			LineObj lObj = countDashAndLetters(c);

			if (lObj.len == 0)
				continue;
			if (lObj.letter.length == 0)
				continue;
			// Extreme case
			if(lObj.startIdx == lObj.endIdx)
				continue;
			if(lObj.len != (lObj.endIdx - lObj.startIdx + 1))
				continue;

			for (int j = 0; j < l.size(); j++) {
				String s = l.get(j);
				boolean foundAllLetters = true;

				for (char cc : lObj.letter) {
					if (!s.contains(cc + "")) {
						foundAllLetters = false;
						break;
					}
				}
				if (foundAllLetters)
					res[j] = 1;
			}
			int sum = Arrays.stream(res).sum();

			if (sum == 1)
				break;
			else
			{
				// Mess, two strings have the same letter
				return "";
			}
		}
		
		if(res.length == 1)
			return l.get(0);

		int idx = Arrays.stream(res).filter(i -> res[i] == 1).findFirst().orElse(-1);
		if (idx != -1)
			return l.get(idx);
		else
			return uniqueStr;
	}

	private static String compareStrings(List<String> l, char[][] arr) {
		int res[] = new int[l.size()];
		for (int i = 0; i < 10; i++) {
			char c[] = arr[i];

			LineObj lObj = countDashAndLetters(c);

			if (lObj.len == 0)
				continue;
			if (lObj.letter.length == 0)
				continue;
			// Extreme case
			if(lObj.startIdx == lObj.endIdx)
				continue;
			if(lObj.len != (lObj.endIdx - lObj.startIdx + 1))
				continue;

			for (int j = 0; j < l.size(); j++) {
				String s = l.get(j);
				boolean foundAllLetters = true;

				for (char cc : lObj.letter) {
					if (!s.contains(cc + "")) {
						foundAllLetters = false;
						break;
					}
				}
				if (foundAllLetters)
					res[j] = 1;
			}
			int sum = Arrays.stream(res).sum();

			if (sum == 1)
				break;
			else
			{
				// Mess, two strings have the same letter
				return "";
			}
		}
		
		if(res.length == 1)
			return l.get(0);

		int idx = Arrays.stream(res).filter(i -> res[i] == 1).findFirst().orElse(-1);
		if (idx != -1)
			return l.get(idx);
		else
			return "";

	}

	private static char[][] make2DArray(List<String> c) {
		char arr[][] = new char[10][10];
		int i = 0;
		for (String s : c) {
			char[] ch = s.toCharArray();
			for (int j = 0; j < 10; j++) {
				arr[i][j] = ch[j];
			}
			i++;
		}
		return arr;
	}

	private static LineObj countDashAndLetters(char[] ch) {
		int ct = 0;
		int max = 0;
		LineObj p;
		boolean foundDash = false;
		String letters = "";
		int start = -1;
		int end = -1;

		// Rule out whole words in the puzzle

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];

			if (c >= 'A' && c <= 'Z') {
				if(start != -1 && end != -1)
					break;
				ct++;
				letters += c;

				if (start == -1)
					start = i;
			}

			if (c == '-') {
				ct++;
				foundDash = true;
				if (start == -1)
					start = i;
			}

			if (c == '+') {
				if (ct > max)
					max = ct;
				ct = 0;
				if (start != -1 && end == -1)
					end = i - 1;
			}
			
			if(start != -1 && end != -1)
			{
				if (end - start <= 1) {
					end = -1;
					start = -1;
					
					if(!letters.isEmpty())
						letters = "";
			}
			 
		}
		}

		if (end == -1 && start != -1)
			end = ch.length - 1;

		// Final check
		if (ct > max)
			max = ct;

		if (!foundDash) {
			p = new LineObj(0, 0, 0, "".toCharArray());
		} else {
			p = new LineObj(max, start, end, letters.toCharArray());
		}

		return p;
	}

	private static boolean processRows(char arr[][], String word) {
		boolean isUpdated = false;
		boolean allLettersFound = true;

		for (int i = 0; i < 10; i++) {
			char c[] = arr[i];
			
			if(!allLettersFound)
				allLettersFound = true;

			LineObj lObj = countDashAndLetters(c);

			if (lObj.len == word.length()) {
				// Check if there are any letters
				for (char ll : lObj.letter) {
					if (!word.contains(ll + "")) {						
						allLettersFound = false;
					}
				}

				if (!allLettersFound)
					continue;
				
				char w[] = word.toCharArray();
				int x = lObj.startIdx;
				int xx = 0;
				while (x <= lObj.endIdx) {
					c[x] = w[xx++];
					x++;
				}
				isUpdated = true;
				break;
			} else
				continue;
		}
		return isUpdated;
	}

	private static boolean processCols(char arr[][], String word) {
		String columnStr = "";
		boolean isUpdated = false;
		boolean allLettersFound = true;

		for (int i = 0; i < arr.length; i++) {
			// We need the first letter of the first row
			for (int j = 0; j < 10; j++) {
				columnStr += arr[j][i];
			}
			// Handle this string now
			char c[] = columnStr.toCharArray();

			LineObj lObj = countDashAndLetters(c);

			if (lObj.len == word.length()) {
				// Check if there are any letters
				for (char ll : lObj.letter) {
					if (!word.contains(ll + "")) {
						columnStr = "";
						allLettersFound = false;
					}
				}

				if (!allLettersFound)
					break;
				char w[] = word.toCharArray();
				int x = lObj.startIdx;
				int xx = 0;
				while (x <= lObj.endIdx) {
					c[x] = w[xx];
					x++;
					xx++;
				}
				isUpdated = true;
			} else {
				columnStr = "";
				continue;
			}

			if (isUpdated) {
				for (int j = 0; j < 10; j++) {
					arr[j][i] = c[j];
				}
				break;
			}
			columnStr = "";
		}
		return isUpdated;
	}

	private static Map<Integer, List<String>> splitToMap(String words) {
		HashMap<Integer, List<String>> hm = new HashMap<Integer, List<String>>();
		List<String> l = Stream.of(words.split(";")).collect(Collectors.toList());

		for (String s : l) {
			int len = s.length();
			if (hm.get(len) == null) {
				List<String> tmpL = new ArrayList<String>();
				tmpL.add(s);
				hm.put(len, tmpL);
			} else {
				hm.get(len).add(s);
			}
		}

		return hm;
	}

	public static List<String> crosswordPuzzle(List<String> crossword, String words) {

		Map<Integer, List<String>> hm = splitToMap(words);

		// DEBUG //hm.forEach((k,v) -> System.out.println(k + " : " + v));

		char arr[][] = make2DArray(crossword);
		
		// Declare a List
		List<String> listOfSizeOne = new ArrayList<String>();
		
		Iterator<Integer> hmIt = hm.keySet().iterator();
		while(hmIt.hasNext())
		{
			int i = hmIt.next();
			if(hm.get(i).size() == 1)
			{
				listOfSizeOne.add(hm.get(i).get(0));
				hmIt.remove();
			}			
		}
		
		int listIdx = 0;
		// First Finish the List then the map
		while(!listOfSizeOne.isEmpty())
		{
			boolean listUpdated = processRows(arr,listOfSizeOne.get(listIdx));
			if(!listUpdated)
				listUpdated = processCols(arr, listOfSizeOne.get(listIdx));
			
			if(listUpdated)
				listOfSizeOne.remove(listIdx);				
		}

		while (!hm.isEmpty()) {
			boolean updated = false;
			Iterator <Integer> it = hm.keySet().iterator();	
			
			while(it.hasNext()) {
				int i = it.next();
				
				// Break if empty
				if(hm.get(i).size() == 0)
				{
					it.remove();
					break;
				}

					String uniqStr = compareStrings(hm.get(i), arr);
					if (uniqStr.length() > 0) {
						updated = processRows(arr, uniqStr);
						if (updated)
							hm.get(i).remove(uniqStr);
					} else {
						// Check Columns
						uniqStr = compareStringsCols(hm.get(i), arr);
						if (uniqStr.length() > 0) {
							updated = processCols(arr, uniqStr);
							if (updated)
								hm.get(i).remove(uniqStr);
						}
					}
				if(!updated)
				{
					// We are in a circle
					// Go one after the other
					for(int idx = 0; idx < hm.get(i).size(); idx++)
					{
						updated = processRows(arr, hm.get(i).get(idx));
						if(!updated)
							updated = processCols(arr, hm.get(i).get(idx));		
						else
						{
							hm.get(i).remove(idx); // Very risky
							idx--;
						}
					}					
					if(updated)
						it.remove();
				}
	
			}
		}
		
		ArrayList<String> finalArray = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			char c[] = arr[i];
			finalArray.add(new String(c));
		}

		return finalArray;
	}

}

public class CrossWordPuzzle {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

		List<String> crossword = IntStream.range(0, 10).mapToObj(i -> {
			try {
				return bufferedReader.readLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}).collect(toList());

		String words = bufferedReader.readLine();

		List<String> result = CWResult.crosswordPuzzle(crossword, words);

		bufferedWriter.write(result.stream().collect(joining("\n")) + "\n");

		bufferedReader.close();
		bufferedWriter.close();
	}
}
