package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day10.txt";
		List<Integer> list = readFromFileAndPopulateList(fileName);
		Collections.sort(list);
		puzzle1(list);
		puzzle2(list);
		long num = puzzle2Recursive(list, new HashMap<Integer, Long>(), list.get(list.size()-1));
		System.out.println(num);
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
		int oneDiff = 0;
		int threeDiff = 1;
		int prev = 0;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) - prev == 1)
				oneDiff++;
			else
				threeDiff++;
			prev = list.get(i);
		}
		System.out.println(oneDiff * threeDiff);
	}
	
	public static void puzzle2(List<Integer> list) {
		long[] dp = new long[list.get(list.size()-1)+1];
		dp[0] = 1;
		for(int n : list) {
			long n3 = n-3 < 0 ? 0 : dp[n-3];
			long n2 = n-2 < 0 ? 0 : dp[n-2];
			long n1 = n-1 < 0 ? 0 : dp[n-1];
			dp[n] = n3 + n2 + n1;
		}
		System.out.println(dp[dp.length-1]);
	}
	
	public static long puzzle2Recursive(List<Integer> list, Map<Integer, Long> memo, int n) {
		if(memo.containsKey(n)) return memo.get(n);
		if(n == 0)
			return 1;
		if(n < 0 || !list.contains(n))
			return 0;
		long res = puzzle2Recursive(list, memo, n-3) + puzzle2Recursive(list, memo, n-2) + puzzle2Recursive(list, memo, n-1);
		memo.put(n, res);
		return res;
	}
	

}
