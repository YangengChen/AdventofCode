package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day5.txt";
		List<Integer> seats = readFile(fileName);
		findSeat(seats);
	}

	public static List<Integer> readFile(String fileName) {
		File file = new File(fileName);
		List<Integer> seats = new ArrayList<>(); 
		int max = -1;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String rowEncode = line.substring(0, 7);
				String colEncode = line.substring(7); 
				int row = decodeRow(rowEncode);
				int col = decodeCol(colEncode);
				int seatID = (row * 8) + col;
				max = Math.max(max, seatID);
				seats.add(seatID);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		System.out.println(max);
		return seats;
	}
	
	// 0 - 127 
	public static int decodeRow(String code) {
		return binarySearchRow(code, 0, 0, 127);
		
	}
	
	public static int binarySearchRow(String code, int idx, int low, int high) {
		if(low == high) return low;
		int mid = (low + high) / 2;
		if(code.charAt(idx) == 'F') {
			return binarySearchRow(code, idx+1, low, mid);
		} else
			return binarySearchRow(code, idx+1, mid+1, high);
		
	}
	
	// 0 - 7
	public static int decodeCol(String code) {
		return binarySearchCol(code, 0, 0, 7);
	}
	
	public static int binarySearchCol(String code, int idx, int low, int high) {
		if(low == high) return low;
		int mid = (low + high) / 2;
		if(code.charAt(idx) == 'L') {
			return binarySearchCol(code, idx+1, low, mid);
		} else
			return binarySearchCol(code, idx+1, mid+1, high); 
	}
	
	public static void findSeat(List<Integer> seats) {
		Collections.sort(seats);
		for(int n : seats) System.out.println(n);
		int seat = binarySearchSeat(seats, 0, seats.size()-1);
		System.out.println(seat);
	}
	
	public static int binarySearchSeat(List<Integer> seats, int low, int high) {
		if(low == high) {
			if(seats.get(low) == low+1) return seats.get(low)+1;
			else return seats.get(low)-1;
		}
		int mid = (low + high) / 2;
		if(seats.get(mid) == seats.get(low) + (mid - low)) {
			return binarySearchSeat(seats, mid+1, high);
		} else
			return binarySearchSeat(seats, low, mid-1);
	}

}
