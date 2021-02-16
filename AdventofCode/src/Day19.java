import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day19 {

	public static void main(String[] args) {
		String fileName = "./input/day19.txt";
		List<String> list = readFileAndPopulateList(fileName);
		puzzle1(list);
	}
	
	public static List<String> readFileAndPopulateList(String fileName) {
		File file = new File(fileName);
		List<String> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				list.add(line);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return list;
	}
	
	public static void puzzle1(List<String> input) {
		Map<Integer, String> rules = new HashMap<>();
		List<String> msgs = new ArrayList<>();
		boolean flag = false;
		for(String s : input) {
			if(s.equals(""))
				flag = true;
			if(flag) {
				msgs.add(s);
			} else {
				String[] split = s.split(":");
				rules.put(Integer.parseInt(split[0]), split[1].trim());
			}
		}
		List<String> results = getMessagesRecursively(rules, new HashMap<Integer, List<String>>(), 0);
		int num = 0;
		for(String msg : msgs) {
			if(results.contains(msg))
				num++;
		}
		System.out.println(num);
		
	}
	
	public static List<String> getMessagesRecursively(Map<Integer, String> rules, Map<Integer, List<String>> memo, int ruleNum) {
		String rule = rules.get(ruleNum);
		if(memo.containsKey(ruleNum)) return memo.get(ruleNum);
		List<String> messages = new ArrayList<>();
		if(rule.contains("\"")) {
			char c = rule.charAt(1);
			messages.add(c+"");
		    return messages;
		}
		String[] options = rule.split(" \\| "); 
		for(String op : options) {
			String[] subRules = op.split(" ");
			List<List<String>> subLists = new ArrayList<>();
			for(String sub : subRules) {
				subLists.add(getMessagesRecursively(rules, memo, Integer.parseInt(sub)));
			}
			generateMessages(subLists, messages, "", 0);
		}
		memo.put(ruleNum, messages);
		return messages;
	}
	
	public static void generateMessages(List<List<String>> subLists, List<String> msgs, String msg, int n) {
		if(n == subLists.size())
			msgs.add(msg);
		else {
			List<String> subList = subLists.get(n);
			for(String s : subList) {
				generateMessages(subLists, msgs, msg+s, n+1);
			}
		}
	}

}
