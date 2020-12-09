import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day9 {

	public static void main(String[] args) {
		String fileName = "./input/day9.txt";
		List<Long> list = readFromFileAndPopulateList(fileName);
		long num = puzzle1(list);
		puzzle2(list, num);
	}

	public static List<Long> readFromFileAndPopulateList(String fileName) {
		File file = new File(fileName);
		List<Long> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLong()) {
				list.add(sc.nextLong());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return list;
	}

	public static long puzzle1(List<Long> list) {
		Set<Long> set = new HashSet<>();
		for(int i = 0; i < 25; i++) {
			set.add(list.get(i));
		}
		for(int i = 25; i < list.size(); i++) {
			long num = list.get(i);
			if(isSumOfPrev(num, set)) {
				set.remove(list.get(i-25));
				set.add(num);
			
			} else {
				System.out.println(num);
				return num;
			}
		}
		return -1; 
	}
	
	public static boolean isSumOfPrev(long num, Set<Long> set) {
		for(long n : set) {
			long diff = num - n;
			if(set.contains(diff) && n != diff)
				return true;
		}
		return false;
	}
	
	
	public static long puzzle2(List<Long> list, long num) {
		int begin = 0, end = 0;
		long sum = 0;
		while(end < list.size()) {
			sum += list.get(end);
			if(sum > num)
				while(begin < end && sum > num)
					sum -= list.get(begin++);
			if(sum == num)
				break;
			end++;
		}
		long min = findSmallest(list, begin, end);
		long max = findLargest(list, begin, end);
		System.out.println(min+max);
		return min+max;
	}
	
	public static long findSmallest(List<Long> list, int begin, int end) {
		long min = Long.MAX_VALUE;
		for(int i = begin; i <= end; i++) {
			if(min > list.get(i))
				min = list.get(i);
		}
		return min; 
	}
	
	public static long findLargest(List<Long> list, int begin, int end) {
		long max = Long.MIN_VALUE;
		for(int i = begin; i <= end; i++) {
			if(max < list.get(i))
				max = list.get(i);
		}
		return max;
	}
}
