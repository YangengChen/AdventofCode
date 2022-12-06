package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;

public class Day6 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day6.txt";
		readFile(fileName);
	}

	public static void readFile(String fileName) {
		File file = new File(fileName);
		int puzzle1Sum = 0;
		int puzzle2Sum = 0;
		int ppl = 0;
		Map<Character, Integer> map = new HashMap<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.equals("")) {
					puzzle1Sum += map.size();
					puzzle2Sum += getNumOfCommon(map, ppl);
					map.clear();
					ppl = 0;
					continue;
				}
				countUnique(line, map);
				ppl++; 
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		puzzle1Sum += map.size();
		puzzle2Sum += getNumOfCommon(map, ppl);
		System.out.println(puzzle1Sum);
		System.out.println(puzzle2Sum);
	}
	
	public static void countUnique(String s, Map<Character, Integer> map) {
		for(char c : s.toCharArray())
			map.put(c, map.getOrDefault(c, 0)+1);
	}

	public static int getNumOfCommon(Map<Character, Integer> map, int ppl) {
		int count = 0;
		for(Entry<Character, Integer> entry : map.entrySet()) {
			if(entry.getValue() == ppl)
				count++;
		}
		return count; 
	}
	
}
