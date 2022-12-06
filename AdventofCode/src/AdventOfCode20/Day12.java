package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day12.txt";
		List<String> list = readFileAndPopulateList(fileName); 
		Direction east = new Direction('E');
		Direction north = new Direction('N');
		Direction west = new Direction('W');
		Direction south = new Direction('S');
		east.setPrevAndNext(north, south);
		north.setPrevAndNext(west, east);
		west.setPrevAndNext(south, north );
		south.setPrevAndNext(east, west);
		puzzle1(list, east);
		puzzle2(list);
	}
	
	public static class Direction {
		char dir;
		Direction prev;
		Direction next;
		
		public Direction(char dir) {
			this.dir = dir;
		}
		
		public char getDir() {
			return dir;
		}
		
		public void setPrevAndNext(Direction prev, Direction next) {
			this.prev = prev;
			this.next = next;
		}
		
		public Direction getPrev() {
			return prev;
		}
		
		public Direction getNext() {
			return next;
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
	
	public static void puzzle1(List<String> list, Direction dir) {
		int x = 0, y = 0;
		for(String s : list) {
			char c = s.charAt(0);
			int val = Integer.parseInt(s.substring(1)); 
			switch(c) {
			case 'N':
				y += val;
				break;
			case 'S':
				y -= val;
				break;
			case 'W':
				x -= val;
				break;
			case 'E':
				x += val;
				break;
			case 'F':
				switch(dir.getDir()) {
				case 'N':
					y += val;
					break;
				case 'S':
					y -= val;
					break;
				case 'W':
					x -= val;
					break;
				case 'E':
					x += val;
					break;
				}
				break;
			case 'L':
				dir = getNewDirection(dir, 'L', val);
				break;
			case 'R':
				dir = getNewDirection(dir, 'R', val);
				break; 
			}
		}
		System.out.println(Math.abs(x) + Math.abs(y));
	}
	
	public static Direction getNewDirection(Direction dir, char c, int degree) {
		int turns = (degree % 360) / 90;
		for(int i = 0; i < turns; i++) {
			if(c == 'R')
				dir = dir.next;
			else 
				dir = dir.prev;
		}
		return dir;
	}

	public static void puzzle2(List<String> list) {
		int shipX = 0, shipY = 0;
		int wayPointX = 10, wayPointY = 1;
		
		for(String s : list) {
			char c = s.charAt(0);
			int val = Integer.parseInt(s.substring(1)); 
			switch(c) {
			case 'N':
				wayPointY += val;
				break;
			case 'S':
				wayPointY -= val;
				break;
			case 'W':
				wayPointX -= val;
				break;
			case 'E':
				wayPointX += val;
				break;
			case 'F':
				shipX += wayPointX * val;
				shipY += wayPointY * val;
				break;
			case 'L':
				for(int i = 0; i < val / 90; i++) {
					int temp = wayPointX;
					wayPointX = -(wayPointY);
					wayPointY = temp;
				}
				break;
			case 'R':
				for(int i = 0; i < val / 90; i++) {
					int temp = wayPointY;
					wayPointY = -(wayPointX);
					wayPointX = temp;
				}
				break; 
			}
		}
		System.out.println(Math.abs(shipX) + Math.abs(shipY));
	}
	
	
}
