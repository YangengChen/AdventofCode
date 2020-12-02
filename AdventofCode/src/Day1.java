import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {

	public static void main(String[] args) {
		String fileName = "./day1.txt";
		List<Integer> list = readFromFileAndPopulateList(fileName);
		puzzle1(list);
		puzzle2(list);
	}

	public static List<Integer> readFromFileAndPopulateList(String fileName) {
		File file = new File(fileName);
		List<Integer> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextInt()) {
				list.add(sc.nextInt());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return list;
	}
	
	public static void puzzle1(List<Integer> list) {
		Set<Integer> set = new HashSet<>();
		for(int num : list) {
			if(set.contains(2020 - num)) {
				System.out.println(num * (2020-num));
				break;
			} else
				set.add(num);
		}
	}
	
	public static void puzzle2(List<Integer> list) {
		for(int i = 0; i < list.size(); i++) {
			int subSum = 2020 - list.get(i);
			Set<Integer> set = new HashSet<>();
			for(int j = i + 1; j < list.size(); j++) {
				if(set.contains(subSum - list.get(j))) {
					System.out.println(list.get(i) * list.get(j) * (subSum - list.get(j)));
					return; 
				} else
					set.add(list.get(j));
			}
		}
	}
	
}
