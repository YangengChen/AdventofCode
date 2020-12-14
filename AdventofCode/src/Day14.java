import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day14 {

	public static void main(String[] args) {
		String fileName = "./input/day14.txt";
		readFile(fileName); 
	}
	
	public static void readFile(String fileName) {
		File file = new File(fileName);
		Map<Long, Long> mem1 = new HashMap<>();
		Map<Long, Long> mem2 = new HashMap<>();
		try {
			Scanner sc = new Scanner(file);
			String mask = "";
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] split = line.split(" "); 
				if(split[0].equals("mask")) {
					mask = split[2];
				} else {
					long key = Long.parseLong(split[0].substring(4, split[0].length()-1));
					long value = Long.parseLong(split[2]); 
					long result = getDecimalResult(mask, value); 
					mem1.put(key, result); 
					
					List<Long> modifiedAddresses = getModifiedAddresses(mask, key);
					for(long n : modifiedAddresses) {
						mem2.put(n, value);
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		long sum1 = 0;
		long sum2 = 0;
		for(long n : mem1.values()) sum1 += n;
		for(long n : mem2.values()) sum2 += n;
		System.out.println(sum1);
		System.out.println(sum2);
	}
	
	public static long getDecimalResult(String mask, long value) {
		int[] binaryArr = getBinaryValue(value);
		long result = 0;
		for(int i = 0 ; i < 36; i++) {
			int bitValue = mask.charAt(35-i) == 'X' ? binaryArr[i] : mask.charAt(35-i) - '0';
			result += (Math.pow(2, i) * bitValue);
		}
		return result;
	}
	
	public static int[] getBinaryValue(long value) {
		int[] binaryArr = new int[36]; 
		int i = 0;
		long val = value;
		while(val != 0) {
			binaryArr[i++] = (int) val % 2;
			val /= 2;
		}
		return binaryArr;
	}

	public static List<Long> getModifiedAddresses(String mask, long address) {
		int[] binaryArr = getBinaryValue(address);
		char[] resultArr = new char[36];
		for(int i = 0; i < 36; i++) {
			resultArr[i] = (char) (mask.charAt(35-i) == 'X' ? 'X' : (mask.charAt(35-i) == '1' ? '1' : binaryArr[i] + '0'));
		}
		List<Long> memoryAddresses = new ArrayList<>();
		findAllMemoryAddresses(memoryAddresses, resultArr, 0, 0);
		return memoryAddresses;
	}
	
	public static void findAllMemoryAddresses(List<Long> addresses, char[] resultArr, long decimal, int i) {
		if(i == 36) {
			addresses.add(decimal);
			return; 
		}
		if(resultArr[i] == 'X') {
			findAllMemoryAddresses(addresses, resultArr,  decimal + ((long) Math.pow(2, i) * 0), i+1);
			findAllMemoryAddresses(addresses, resultArr,  decimal + ((long) Math.pow(2, i) * 1), i+1);
		} else {
			findAllMemoryAddresses(addresses, resultArr, decimal + ((long) Math.pow(2, i) * (resultArr[i] - '0')), i+1);
		}
	}

}
