package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day18 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day18.txt";
		List<String> list = readFileAndPopulateList(fileName);
		puzzle1(list);
		puzzle2(list);
	}
	
	public static List<String> readFileAndPopulateList(String fileName) {
		File file = new File(fileName);
		List<String> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				list.add(line);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file does not exist.");
		}
		return list;
	}
	
	public static void puzzle1(List<String> list) {
		long total = 0;
		for(String exp : list) {
			total += getResult(exp, false);
		}
		System.out.println(total);
	}
	
	public static void puzzle2(List<String> list) {
		long total = 0;
		for(String exp : list) {
			total += getResult(exp, true);
		}
		System.out.println(total);
 	}

	public static long getResult(String expression, boolean precedence) {
		Stack<Long> valueStack = new Stack<>();
		Stack<Character> operatorStack = new Stack<>();
		for(char c : expression.replaceAll("\\s", "").toCharArray()) {
			if(Character.isDigit(c)) {
				valueStack.push((long) (c - '0'));
			} else if(c == '(') {
				operatorStack.push(c);
			} else if(c == ')') {
				char topOp = operatorStack.pop();
				while(topOp != '(') {
					long num1 = valueStack.pop();
					long num2 = valueStack.pop();
					long total = executeExpression(num1, num2, topOp);
					valueStack.push(total);
					topOp = operatorStack.pop();
				}
			} else {
				while(!operatorStack.isEmpty() && (operatorStack.peek() == '+' || operatorStack.peek() == '*')) {
					if(precedence && c == '+' && operatorStack.peek() == '*') break;
					char topOp = operatorStack.pop();
					long num1 = valueStack.pop();
					long num2 = valueStack.pop();
					long total = executeExpression(num1, num2, topOp);
					valueStack.push(total);
				}
				operatorStack.push(c);
			}
		}
		
		while(!operatorStack.isEmpty()) {
			char topOp = operatorStack.pop();
			long num1 = valueStack.pop();
			long num2 = valueStack.pop();
			long total = executeExpression(num1, num2, topOp);
			valueStack.push(total);
		}
		
		return valueStack.pop();
	}
	
	public static long executeExpression(long num1, long num2, char operator) {
		switch(operator) {
		case '+':
			return num1 + num2;
		case '-': 
			return num2 - num1;
		case '*':
			return num1 * num2;
		case '/':
			return num2 / num1;
		}
		
		return -1;
	}
}
