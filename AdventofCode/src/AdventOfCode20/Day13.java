package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day13 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day13.txt";
		List<String> list = readFileAndPopulateList(fileName);
		Long timestamp = Long.parseLong(list.get(0));
		Map<Integer, Integer> busIDs = getBusIDs(list.get(1));
		puzzle1(timestamp, busIDs);
		String[] busArray = list.get(1).split(",");
		puzzle2(busArray);
	}
	
	public static Map<Integer, Integer> getBusIDs(String buses) {
		Map<Integer, Integer> busIDs = new HashMap<>(); 
		String[] split = buses.split(",");
		for(int i = 0; i < split.length; i++) {
			if(split[i].equals("x"))
				continue;
			busIDs.put(Integer.parseInt(split[i]), i);
		}
		return busIDs;
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

	public static void puzzle1(Long timestamp, Map<Integer, Integer> busIDs) {
		int minWaitTime = Integer.MAX_VALUE;
		int earliestBusID = -1;
		for(int busID : busIDs.keySet()) {
			int waitTime = getWaitTime(timestamp, busID);
			if(waitTime < minWaitTime) {
				minWaitTime = waitTime;
				earliestBusID = busID;
			}
		}
		System.out.println(minWaitTime * earliestBusID);
	}
	
	public static int getWaitTime(Long timestamp, int busID) {
		long time = busID;
		while(time < timestamp) {
			time += busID;
		}
		return (int) (time - timestamp);
	}
	
	public static void puzzle2(String[] busArray) {
		long incr = 1;
		long at = 0;
		for(int offset = 0; offset <  busArray.length; offset++) {
			if(busArray[offset].equals("x")) 
				continue;
			int busID = Integer.parseInt(busArray[offset]); 
			while((at + offset) % busID != 0)
				at += incr;
			incr *= busID;
		}
		System.out.println(at);
	}
	
}
