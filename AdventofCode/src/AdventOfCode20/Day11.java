package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day11.txt";
		List<String> list = readFileAndPopulateList(fileName); 
		puzzle1(list);
		puzzle2(list);
	}
	
	public enum Pos {
		  TOP(-1, 0), RIGHT(0, 1), BOTTOM(1, 0), LEFT(0, -1), TOPLEFT(-1,-1), TOPRIGHT(-1, 1), BOTTOMLEFT(1, -1), BOTTOMRIGHT(1,1);

		  public final int dx, dy;

		  private Pos(int dx, int dy) {
		    this.dx = dx;
		    this.dy = dy;
		  }
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
	
	public static List<char[]> convertStringListToCharArrayList(List<String> list) {
		List<char[]> charArrayList = new ArrayList<>();
		for(String s : list) {
			charArrayList.add(s.toCharArray());
		}
		return charArrayList;
	}
	
	public static void printState(List<char[]> list) {
		for(char[] cArray : list) {
			for(char c : cArray) {
				System.out.print(c);
			}
			System.out.println();
		}
	}
	
	public static void puzzle1(List<String> list) {
		boolean isChanged = true;
		List<char[]> oldState = convertStringListToCharArrayList(list);
		List<char[]> newState = convertStringListToCharArrayList(list); 
		while(isChanged) {
			isChanged = false;
			for(int row = 0; row < list.size(); row++) {
				for(int col = 0; col < list.get(row).length(); col++) {
					char seat = oldState.get(row)[col];
					if(seat == 'L') {
						if(getNumOfAdjacentOccupied(oldState, row, col) == 0) {
							newState.get(row)[col] = '#';
							isChanged = true; 
						}
					} else if(seat == '#') {
						if(getNumOfAdjacentOccupied(oldState, row, col) >= 4) {
							newState.get(row)[col] = 'L';
							isChanged = true;
						}
					}
				}
			}
			for(int i = 0; i < newState.size(); i++) {
				String line = new String(newState.get(i));
				oldState.set(i, line.toCharArray());
			}
		}
		int numOfOccupied = 0;
		for(int row = 0; row < newState.size(); row++) {
			for(int col = 0; col < newState.get(row).length; col++) {
				if(newState.get(row)[col] == '#')
					numOfOccupied++;
			}
		}
		System.out.println(numOfOccupied);
	}
	
	public static int getNumOfAdjacentOccupied(List<char[]> list, int row, int col) {
		int numOfOccupied = 0;
		for(Pos pos : Pos.values()) {
			int x = pos.dx + row;
			int y = pos.dy + col;
			if(x < 0 || x == list.size() || y < 0 || y == list.get(row).length) continue;
			if(list.get(x)[y] == '#')
				numOfOccupied++;
		}
		return numOfOccupied;
	}
	
	public static void puzzle2(List<String> list) {
		boolean isChanged = true;
		List<char[]> oldState = convertStringListToCharArrayList(list);
		List<char[]> newState = convertStringListToCharArrayList(list); 
		while(isChanged) {
			isChanged = false;
			for(int row = 0; row < list.size(); row++) {
				for(int col = 0; col < list.get(row).length(); col++) {
					char seat = oldState.get(row)[col];
					if(seat == 'L') {
						if(getNumOfAdjacentOccupied2(oldState, row, col) == 0) {
							newState.get(row)[col] = '#';
							isChanged = true; 
						}
					} else if(seat == '#') {
						if(getNumOfAdjacentOccupied2(oldState, row, col) >= 5) {
							newState.get(row)[col] = 'L';
							isChanged = true;
						}
					}
				}
			}
			for(int i = 0; i < newState.size(); i++) {
				String line = new String(newState.get(i));
				oldState.set(i, line.toCharArray());
			}
		}
		int numOfOccupied = 0;
		for(int row = 0; row < newState.size(); row++) {
			for(int col = 0; col < newState.get(row).length; col++) {
				if(newState.get(row)[col] == '#')
					numOfOccupied++;
			}
		}
		System.out.println(numOfOccupied);
	}
	
	public static int getNumOfAdjacentOccupied2(List<char[]> list, int row, int col) {
		int numOfOccupied = 0;
		for(Pos pos : Pos.values()) {
			int x = pos.dx + row;
			int y = pos.dy + col;
			while(true) {
				if(x < 0 || x == list.size() || y < 0 || y == list.get(row).length) break;
				if(list.get(x)[y] == '#') {
					numOfOccupied++;
					break;
				}
				if(list.get(x)[y] == 'L')
					break;
				x += pos.dx;
				y += pos.dy;
					
			}

		}
		return numOfOccupied;
	}
	
}
