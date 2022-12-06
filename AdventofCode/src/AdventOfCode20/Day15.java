package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day15.txt";
		List<String> list = readFileAndPopulateList(fileName);
		calculateNth(list, 2020);
		calculateNth(list, 30000000);
	}
	
	
	public static List<String> readFileAndPopulateList(String fileName) {
		File file = new File(fileName);
		List<String> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				list.add(line);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return list;
	}
	
	public static void  calculateNth(List<String> list, int nth) {
		List<Integer> gameList = new ArrayList<>();
		String[] split = list.get(0).split(",");
		Map<Integer, Integer> recentMap = new HashMap<>();
		for(int i = 0; i < split.length; i++) {
			int num = Integer.parseInt(split[i]);
			gameList.add(num);
			if(i != split.length-1)
				recentMap.put(num, i);
		}
		for(int i = gameList.size(); i < nth; i++) {
			int prev = gameList.get(i-1);
			if(recentMap.containsKey(prev)) {
				int idx = recentMap.get(prev);
				gameList.add(i-1-idx);
			} else {
				gameList.add(0);
			}
			recentMap.put(prev, i-1);
		}
		System.out.println(gameList.get(nth-1 ));
	}

}
