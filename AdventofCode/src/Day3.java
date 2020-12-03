import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {

	public static void main(String[] args) {
		String fileName = "./input/day3.txt";
		List<String> list = readFromFileAndPopulateList(fileName);
		double slope1 = calculateTrees(list, 3, 1);
		double slope2 = calculateTrees(list, 1, 1);
		double slope3 = calculateTrees(list, 5, 1);
		double slope4 = calculateTrees(list, 7, 1);
		double slope5 = calculateTrees(list, 1, 2);
		System.out.println(slope1 * slope2 * slope3 * slope4 * slope5);
	}

	public static List<String> readFromFileAndPopulateList(String fileName) {
		File file = new File(fileName);
		List<String> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				list.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return list;
	}
	
	public static double calculateTrees(List<String> list, int right, int down) {
		int numOfTrees = 0;
		int x = 0, y = 0;
		while(x < list.size()-1) {
			y = (y + right) % list.get(0).length();
			x += down;
			if(x >= list.size()) break;
			if(list.get(x).charAt(y) == '#')
				numOfTrees++;
		}
		System.out.println(numOfTrees);
		return numOfTrees;
	}
}
