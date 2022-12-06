package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day16 {

	static Map<String, String> rules = new HashMap<>();
	static String myTicket;
	static List<String> nearbyTickets = new ArrayList<>();
	
	public static void main(String[] args) {
		String fileName = "./input/2020/day16.txt";
		readFile(fileName);
		puzzle1(); 
		puzzle2();
	}
	
	
	public static void readFile(String fileName) {
		File file = new File(fileName);
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.equals("")) {
					continue;
				} else if(line.startsWith("your ticket")) {
					myTicket = sc.nextLine();
				} else if(line.startsWith("nearby tickets")) {
					while(sc.hasNextLine()) {
						String nextLine = sc.nextLine(); 
						if(nextLine.equals("")) break;
						nearbyTickets.add(nextLine); 
					}
				} else {
					String[] split = line.split(":");
					String[] secondSplit = split[1].split(" ");
					rules.put(split[0], secondSplit[1] + " " + secondSplit[3]); 
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
	}

//	class: 1-3 or 5-7
//	row: 6-11 or 33-44
//	seat: 13-40 or 45-50
//
//	your ticket:
//	7,1,14
//
//	nearby tickets:
//	7,3,47
//	40,4,50
//	55,2,20
//	38,6,12
	
	public static void puzzle1() {
		List<String> ruleList = getRuleList();
		int invalidSum = 0;
		for(String nearbyTicket : nearbyTickets) {
			String[] values = nearbyTicket.split(",");
			int invalidNum = getInvalid(values,ruleList);
			if(invalidNum != -1)
				invalidSum += invalidNum;
		}
		System.out.println(invalidSum);
	}
	
	public static List<String> getRuleList() {
		List<String> ruleList = new ArrayList<>();
		for(Map.Entry<String, String> entry : rules.entrySet()) {
			String value = entry.getValue();
			String[] split = value.split(" ");
			ruleList.add(split[0]);
			ruleList.add(split[1]);
		}
		return ruleList;
	}
	
	public static int getInvalid(String[] values, List<String> ruleList) {
		for(String s : values) {
			int value = Integer.parseInt(s);
			if(!isInRange(value, ruleList)) {
				return value;
			}
		}
		return -1;
	}
	
	public static boolean isInRange(int num, List<String> ruleList) {
		for(String rule : ruleList) {
			String[] values = rule.split("-");
			int low = Integer.parseInt(values[0]);
			int high = Integer.parseInt(values[1]);
			if(low <= num && num <= high)
				return true;
		}
		return false;
	}
	
	public static void puzzle2() {
		List<String> ruleList = getRuleList();
		discardInvalidTickets(ruleList);
		nearbyTickets.add(myTicket);

		Map<List<String>, Integer> candidatesMap = getCandidates();
		List<List<String>> candidatesList = new ArrayList<>(candidatesMap.keySet());
		Collections.sort(candidatesList, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> l1, List<String> l2) {
				return l1.size() - l2.size();
			}
		});
		
		String[] positions = new String[rules.size()];
		Set<String> fieldUsed = new HashSet<>();
		for(int i = 0; i < positions.length; i++) {
			List<String> candidates = candidatesList.get(i); 
			for(String candidate : candidates) {
				if(!fieldUsed.contains(candidate)) {
					positions[candidatesMap.get(candidates)] = candidate;
					fieldUsed.add(candidate);
				}
			}
		}
		
		long total = 1;
		String[] myTicketValues = myTicket.split(",");
		for(int n = 0; n < positions.length; n++) {
			String field = positions[n];
			if(field.startsWith("departure")) {
				total *= Long.parseLong(myTicketValues[n]);
			}
		}
		System.out.println(total);
	}
	
	public static void discardInvalidTickets(List<String> ruleList) {
		Iterator<String> it = nearbyTickets.iterator();
		while(it.hasNext()) {
			String nearbyTicket = it.next();
			String[] values = nearbyTicket.split(",");
			if(getInvalid(values, ruleList) != -1) {
				it.remove();
			}
		}
	}
	
	public static Map<List<String>, Integer> getCandidates() { 
		Map<List<String>, Integer> candidatesMap = new HashMap<>();
		for(int i = 0; i < rules.size(); i++) {
			List<String> candidates = new ArrayList<>();
			for(Map.Entry<String, String> entry : rules.entrySet()) {
				String field = entry.getKey();
				String parameters = entry.getValue();
				if(matchesAllValuesAtPosition(parameters, i)) {
					candidates.add(field);
				}
			}
			candidatesMap.put(candidates, i);
		}
		return candidatesMap;
	}
	
	public static boolean matchesAllValuesAtPosition(String parameters, int i) {
		for(String ticket : nearbyTickets) {
			String[] values = ticket.split(",");
			int value = Integer.parseInt(values[i]); 
			String split[] = parameters.split(" "); 
			String bounds1[] = split[0].split("-");
			String bounds2[] = split[1].split("-");
			int low1 = Integer.parseInt(bounds1[0]);
			int high1 = Integer.parseInt(bounds1[1]);
			int low2 = Integer.parseInt(bounds2[0]);
			int high2 = Integer.parseInt(bounds2[1]);
			if(value < low1 || (value > high1 && value < low2) || value > high2)
				return false; 
		}
		return true;
	}
}
