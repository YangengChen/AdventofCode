package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day8.txt";
		List<String> list = readFileAndPopulateList(fileName);
		puzzle1(list);
		puzzle2(list);
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
	
//	nop +0
//	acc +1
//	jmp +4
	public static void puzzle1(List<String> list) {
		int acc = 0, i = 0;
		Set<Integer> indicesRan = new HashSet<>();
		while(i < list.size()) {
			if(indicesRan.contains(i)) break;
			indicesRan.add(i);
			String[] instructs = list.get(i).split(" ");
			String cmd = instructs[0];
			char sign = instructs[1].charAt(0);
			int num = Integer.parseInt(instructs[1].substring(1, instructs[1].length()));
			if(cmd.equals("acc")) {
				if(sign == '+')
					acc += num;
				else
					acc -= num;
			} else if(cmd.equals("jmp")) {
				if(sign == '+')
					i += num;
				else
					i -= num;
				continue; 
			}
			i++;
		}
		System.out.println(acc);
	}
	
//	nop +0
//	acc +1
//	jmp +4
//	acc +3
//	jmp -3
//	acc -99
//	acc +1
//	jmp -4
//	acc +6
	
	public static void puzzle2(List<String> list) {
		int acc = 0, i = 0, idxChanged = -1;
		Set<Integer> indicesRan = new HashSet<>();
		Set<Integer> indicesChanged = new HashSet<>();
		while(i < list.size()) {
			if(indicesRan.contains(i)) {
				indicesRan.clear();
				i = 0; 
				acc = 0; 
				idxChanged = -1;
				continue;
			}
			indicesRan.add(i);
			String[] instructs = list.get(i).split(" ");
			String cmd = instructs[0];
			char sign = instructs[1].charAt(0);
			int num = Integer.parseInt(instructs[1].substring(1, instructs[1].length()));
			if(cmd.equals("acc")) {
				if(sign == '+')
					acc += num;
				else
					acc -= num;
			} else if(cmd.equals("jmp")) {
				if(!indicesChanged.contains(i) && idxChanged == -1) {
					indicesChanged.add(i);
					idxChanged = i++;
					continue;
				}
				if(sign == '+')
					i += num;
				else
					i -= num;
				continue; 
			} else {
				if(!indicesChanged.contains(i) && idxChanged == -1) {
					indicesChanged.add(i);
					idxChanged = i++;
					if(sign == '+')
						i += num;
					else
						i -= num;
					continue;
				}
			}
			i++;
		}
		System.out.println(acc);
	}

}
