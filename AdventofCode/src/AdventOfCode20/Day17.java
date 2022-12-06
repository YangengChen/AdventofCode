package AdventOfCode20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day17 {

	public static void main(String[] args) {
		String fileName = "./input/2020/day17.txt";
		List<String> list = readFileAndPopulateList(fileName);
		puzzle1(list, 6, 3);
		puzzle2(list, 6, 4);
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

	public static void printMatrix(char[][][] matrix) {
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = 0; z < matrix[0][0].length; z++) {
					System.out.print(matrix[x][y][z]);
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public static void puzzle1(List<String> list, int cycle, int dimension) {
		char[][][] matrix = new char[list.size() + ((cycle - 1) * 2)][list.get(0).length()
				+ ((cycle - 1) * 2)][cycle * 2 + 1];
		int startX = (matrix.length / 2) - (list.size() / 2);
		int endX = startX + list.size() - 1;
		int startY = (matrix[0].length / 2) - (list.get(0).length() / 2);
		int endY = startY + list.get(0).length() - 1;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = 0; z < matrix[0][0].length; z++) {
					if ((x >= startX && x <= endX) && (y >= startY && y <= endY) && z == matrix[0][0].length / 2) {
						matrix[x][y][z] = list.get(x - startX).charAt(y - startY);
					} else
						matrix[x][y][z] = '.';
				}
			}
		}

		char[][][] oldMatrix = new char[list.size() + ((cycle - 1) * 2)][list.get(0).length()
				+ ((cycle - 1) * 2)][cycle * 2 + 1];
		copyToOld(matrix, oldMatrix, cycle, cycle);
		List<String> neighbors = getNeighbors(new int[] { -1, 0, 1 }, dimension);

		int itr = 1;
		while (itr <= cycle) {
			for (int x = startX - (itr - 1); x <= endX + (itr - 1); x++) {
				for (int y = startY - (itr - 1); y <= endY + (itr - 1); y++) {
					for (int z = cycle - itr; z <= cycle + itr; z++) {
						char curr = oldMatrix[x][y][z];
						int activeNeighbors = getActiveNeighbors(oldMatrix, neighbors, x, y, z);
						if (curr == '.') {
							if (activeNeighbors == 3) {
								matrix[x][y][z] = '#';
							}
						} else {
							if (activeNeighbors != 2 && activeNeighbors != 3) {
								matrix[x][y][z] = '.';
							}
						}
					}
				}
			}
			copyToOld(matrix, oldMatrix, cycle, itr);
			itr++;
		}

		int actives = 0;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = 0; z < matrix[0][0].length; z++) {
					if (matrix[x][y][z] == '#')
						actives++;
				}
			}
		}
		System.out.println(actives);
	}

	public static void copyToOld(char[][][] matrix, char[][][] oldMatrix, int cycle, int itr) {
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = cycle - itr; z <= cycle + itr; z++) {
					oldMatrix[x][y][z] = matrix[x][y][z];
				}
			}
		}
	}

	public static int getActiveNeighbors(char[][][] matrix, List<String> neighbors, int x, int y, int z) {
		int activeNeighbors = 0;
		for (String neighbor : neighbors) {
			String[] XYZ = neighbor.split(" ");
			if (XYZ[0].equals("0") && XYZ[1].equals("0") && XYZ[2].equals("0"))
				continue;
			int newX = Integer.parseInt(XYZ[0]) + x;
			int newY = Integer.parseInt(XYZ[1]) + y;
			int newZ = Integer.parseInt(XYZ[2]) + z;
			if (newX < 0 || newX == matrix.length || newY < 0 || newY == matrix[0].length || newZ < 0
					|| newZ == matrix[0][0].length)
				continue;
			if (matrix[newX][newY][newZ] == '#')
				activeNeighbors++;
		}
		return activeNeighbors;
	}

	public static void generateNeighbors(int n, int arr[], List<String> neighbors, int dimension) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dimension; i++) {
			sb.append(arr[n % arr.length] + " ");
			n /= arr.length;
		}
		neighbors.add(sb.toString());
	}

	public static List<String> getNeighbors(int arr[], int dimension) {
		List<String> neighbors = new ArrayList<>();
		for (int i = 0; i < (int) Math.pow(arr.length, dimension); i++) {
			generateNeighbors(i, arr, neighbors, dimension);
		}
		return neighbors;
	}

	public static void puzzle2(List<String> list, int cycle, int dimension) {
		char[][][][] matrix = new char[list.size() + ((cycle - 1) * 2)][list.get(0).length()
				+ ((cycle - 1) * 2)][cycle * 2 + 1][cycle * 2 + 1];
		int startX = (matrix.length / 2) - (list.size() / 2);
		int endX = startX + list.size() - 1;
		int startY = (matrix[0].length / 2) - (list.get(0).length() / 2);
		int endY = startY + list.get(0).length() - 1;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = 0; z < matrix[0][0].length; z++) {
					for (int w = 0; w < matrix[0][0][0].length; w++) {
						if ((x >= startX && x <= endX) && (y >= startY && y <= endY) && z == matrix[0][0].length / 2
								&& w == matrix[0][0][0].length / 2) {
							matrix[x][y][z][w] = list.get(x - startX).charAt(y - startY);
						} else
							matrix[x][y][z][w] = '.';
					}
				}
			}
		}

		char[][][][] oldMatrix = new char[list.size() + ((cycle - 1) * 2)][list.get(0).length()
				+ ((cycle - 1) * 2)][cycle * 2 + 1][cycle * 2 + 1];
		copyToOld2(matrix, oldMatrix, cycle, cycle);
		List<String> neighbors = getNeighbors(new int[] { -1, 0, 1 }, dimension);

		int itr = 1;
		while (itr <= cycle) {
			for (int x = startX - (itr - 1); x <= endX + (itr - 1); x++) {
				for (int y = startY - (itr - 1); y <= endY + (itr - 1); y++) {
					for (int z = cycle - itr; z <= cycle + itr; z++) {
						for (int w = cycle - itr; w <= cycle + itr; w++) {
							char curr = oldMatrix[x][y][z][w];
							int activeNeighbors = getActiveNeighbors2(oldMatrix, neighbors, x, y, z, w);
							if (curr == '.') {
								if (activeNeighbors == 3) {
									matrix[x][y][z][w] = '#';
								}
							} else {
								if (activeNeighbors != 2 && activeNeighbors != 3) {
									matrix[x][y][z][w] = '.';
								}
							}
						}
					}
				}
			}
			copyToOld2(matrix, oldMatrix, cycle, itr);
			itr++;
		}

		int actives = 0;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = 0; z < matrix[0][0].length; z++) {
					for (int w = 0; w < matrix[0][0][0].length; w++) {
						if (matrix[x][y][z][w] == '#')
							actives++;
					}
				}
			}
		}
		System.out.println(actives);
	}

	public static void copyToOld2(char[][][][] matrix, char[][][][] oldMatrix, int cycle, int itr) {
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				for (int z = cycle - itr; z <= cycle + itr; z++) {
					for (int w = cycle - itr; w <= cycle + itr; w++) {
						oldMatrix[x][y][z][w] = matrix[x][y][z][w];
					}
				}
			}
		}
	}

	public static int getActiveNeighbors2(char[][][][] matrix, List<String> neighbors, int x, int y, int z, int w) {
		int activeNeighbors = 0;
		for (String neighbor : neighbors) {
			String[] XYZ = neighbor.split(" ");
			if (XYZ[0].equals("0") && XYZ[1].equals("0") && XYZ[2].equals("0") && XYZ[3].equals("0"))
				continue;
			int newX = Integer.parseInt(XYZ[0]) + x;
			int newY = Integer.parseInt(XYZ[1]) + y;
			int newZ = Integer.parseInt(XYZ[2]) + z;
			int newW = Integer.parseInt(XYZ[3]) + w;
			if (newX < 0 || newX == matrix.length || newY < 0 || newY == matrix[0].length || newZ < 0
					|| newZ == matrix[0][0].length || newW < 0 || newW == matrix[0][0][0].length)
				continue;
			if (matrix[newX][newY][newZ][newW] == '#')
				activeNeighbors++;
		}
		return activeNeighbors;
	}

}
