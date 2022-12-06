package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;

public class Day7 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day7.txt";
		Map<String, List<String>> map = readFileAndPopulateMap(fileName);
		puzzle1(map);
		puzzle2(map);
	}
	
	public static Map<String, List<String>> readFileAndPopulateMap(String fileName) {
		File file = new File(fileName);
		Map<String, List<String>> map = new HashMap<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				setMappings(map, line);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return map;
	}
	
	public static void setMappings(Map<String, List<String>> map, String line) {
		String[] words = line.split(" ");
		String key = words[0] + " " + words[1];
		map.put(key, new LinkedList<String>());
		String[] values = line.split("contain")[1].split(",");
		for(String value : values) {
			String[] parts = value.split(" ");
			if(parts[1].equals("no")) continue;
			String val = parts[1] + " " + parts[2] + " " + parts[3];
			map.get(key).add(val);
		}
	}
	
	public static void puzzle1(Map<String, List<String>> map) {
		Set<String> bags = new HashSet<>();
		for(Entry<String, List<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			if(key.equals("shiny gold")) continue;
			Queue<String> queue = new LinkedList<>();
			queue.add(key);
			while(!queue.isEmpty()) {
				for(int i = 0; i < queue.size(); i++) {
					String bag = queue.poll();
					if(bags.contains(bag) || bag.equals("shiny gold")) {
						bags.add(key);
						queue.clear();
						break;
					}
					if(map.get(bag) != null) {
						for(String value : map.get(bag)) 
							queue.add(value.substring(value.indexOf(" ")+1));
					}
				}
			}
		}
		System.out.println(bags.size());
	}
	
	public static void puzzle2(Map<String, List<String>> map) {
		int totalBags = DFS(map, new HashMap<>(), "1 shiny gold");
		System.out.println(totalBags);
	}
	
	public static int DFS(Map<String, List<String>> map, Map<String, Integer> memo, String key) {
		String bag = key.substring(key.indexOf(" ")+1);
		if(memo.containsKey(bag)) return memo.get(bag);
		List<String> otherBags = map.get(bag);
		if(otherBags == null) 
			return 0;
		int sum = 0;
		for(String otherBag : otherBags) {
			int val = Integer.parseInt(otherBag.substring(0, otherBag.indexOf(" ")));
			sum += val + (val * DFS(map, memo, otherBag));
		}
		memo.put(bag,  sum);
		return sum;
	}

}
