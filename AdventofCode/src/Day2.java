import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {

	public static void main(String[] args) {
		String fileName = "./input/day2.txt";
		readFile(fileName);
	}

	public static void readFile(String fileName) {
		File file = new File(fileName);
		int puzzle1Ans = 0;
		int puzzle2Ans = 0;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String[] info = sc.nextLine().split(" ");
				String[] range = info[0].split("-");
				int first = Integer.parseInt(range[0]);
				int second = Integer.parseInt(range[1]);
				char let = info[1].charAt(0);
				String pw = info[2];
				if(validatePassword1(first, second, let, pw))
					puzzle1Ans++;
				if(validatePassword2(first, second, let, pw))
					puzzle2Ans++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		System.out.println(puzzle1Ans);
		System.out.println(puzzle2Ans);
	}
	
	public static boolean validatePassword1(int min, int max, char let, String pw) {
		int count = 0;
		for(char c : pw.toCharArray()) {
			if(c == let)
				count++;
		}
		return count >= min ? count <= max : false;
		
	}
	
	public static boolean validatePassword2(int pos1, int pos2, char let, String pw) {
		return pw.charAt(pos1-1) == let ? pw.charAt(pos2-1) != let : pw.charAt(pos2-1) == let;
	}
	
}


