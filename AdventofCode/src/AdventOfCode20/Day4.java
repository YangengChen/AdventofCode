package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Day4 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day4.txt";
		readFromFile(fileName);

	}

	public enum Field {
		byr, iyr, eyr, hgt, hcl, ecl, pid, cid
	}
	
	public enum EyeColor {
		amb, blu, brn, gry, grn, hzl, oth
	}
	
	public static void readFromFile(String fileName) {
		File file = new File(fileName);
		int numValids = 0;
		int numFields = 0;
		boolean cidMissing = true;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.equals("")) {
					if(numFields == 8) 
						numValids++;
					else if(numFields == 7 && cidMissing)
						numValids++;
					numFields = 0;
					cidMissing = true;
					continue; 
				}
				String[] entries = line.split(" ");	
				for(String entry : entries) {
					String key = entry.split(":")[0];
					String value = entry.split(":")[1];
					Field field = Field.valueOf(key);
					if(validateField(field, value))
						numFields++;
					if(field == Field.cid)
						cidMissing = false;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		if(numFields == 8) numValids++;
		else if(numFields == 7 && cidMissing) numValids++;
		System.out.println(numValids);
	}
	
	public static boolean validateField(Field field, String value) {
		int val;
		switch (field) {
		case byr:
			val = Integer.parseInt(value);
			if(val >= 1920 && val <= 2002)
				return true;
			break;
		case iyr:
			val = Integer.parseInt(value);
			if(val >= 2010 && val <= 2020)
				return true;
			break;
		case eyr:
			val = Integer.parseInt(value);
			if(val >= 2020 && val <= 2030)
				return true;
			break;
		case hgt:
			if(value.endsWith("cm")) {
				val =  Integer.parseInt(value.substring(0, value.length()-2));
				if(val >= 150 && val <= 193)
					return true;
			} else if(value.endsWith("in")) {
				val =  Integer.parseInt(value.substring(0, value.length()-2));
				if(val >= 59 && val <= 76)
					return true;
			}
			break;
		case hcl:
			if(value.charAt(0) != '#')
				break;
			if(value.length() != 7)
				break;
			for(int i = 1; i < value.length(); i++) {
				char c = value.charAt(i);
				if(c < '0' || (c > '9' && c < 'a') || c > 'f') {
					return false;
				} 
			}
			return true;
		case ecl:
			for(EyeColor ec : EyeColor.values()) {
				if(value.equals(ec.toString()))
					return true;
			}
			break; 
		case pid:
			if(value.length() != 9) 
				break;
			for(char c : value.toCharArray()) {
				if(!Character.isDigit(c)) {
					return false;
				}
			}
			return true;
		case cid:
			return true;
		}
		return false;
	}
	

}
