package com.albanyly.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Countgroups {
	protected static boolean _debug = true;
	
	static Map<Integer, Integer> countgroups(int[][] m) {
		Map<Integer, Integer> result = new HashMap<>();
		if (m == null || m.length <= 0 || m.length != m[0].length) {
			return result;
		}
		
		// key - group size, value group numbers as a list
		HashMap<Integer, List<Integer>> groupSizeTable = new HashMap<>();
		// n x n matrix, mask matrix, same as input matrix but '1's changed to group numbers
		int[][] groupMatrix = new int[m.length][m.length];

		int currentGroup = 0;
		int currentGroupSize = 0;
		List<int[]> queue = new ArrayList<>(); // nodes to process, value is node position
		List<int[]> preQueue = new ArrayList<>();  // nodes to put into queue

		// mark groups in groupMatrix using BFS or Breath First Search
		// basic idea is process current node, then all its adjacent nodes, then adj of those node, ...
		// and use a queue for adj. nodes to be processed to avoid using recursive method/stack in
		// PFS or Path First Search
		for (int i = 0; i < m.length; i++) {  // browse each cell
			for (int j = 0; j < m.length; j++) {
				// skip '0' cell in m and marked cell in groupMatrix
				if (m[i][j] == 0 || groupMatrix[i][j] > 0) {
					continue;
				}

				// new group
				currentGroup++;
				currentGroupSize = 0;
				int[] coords = {i, j};
				queue.add(coords);

				// process every node in queue
				while(!queue.isEmpty()) {
					// process current node or add it to current group in groupMatrix, remove it from queue
					int x = queue.get(0)[0];
					int y = queue.get(0)[1];
					groupMatrix[x][y] = currentGroup;
					currentGroupSize++;
					queue.remove(0);

					// check and put left note into preQueue
					// in matrix so that y > 0, or y-1 >= 0
					// value in m is 1
					// value in groupMatrix not marked yet
					if (y > 0 && m[x][y-1] == 1 && groupMatrix[x][y-1] == 0) {
						int[] leftcoords = {x, y-1};
						preQueue.add(leftcoords);
					}
					// check and put upper note into preQueue
					if (x > 0 && m[x-1][y] == 1 && groupMatrix[x-1][y] == 0) {
						int[] uppercoords = {x-1, y};
						preQueue.add(uppercoords);
					}
					// check and put right note into preQueue
					if (y < m.length-1 && m[x][y+1] == 1 && groupMatrix[x][y+1] == 0) {
						int[] rightcoords = {x, y+1};
						preQueue.add(rightcoords);
					}
					// check and put lower note into preQueue
					if (x < m.length-1 && m[x+1][y] == 1 && groupMatrix[x+1][y] == 0) {
						int[] lowercoords = {x+1, y};
						preQueue.add(lowercoords);
					}
					// check nodes in preQueue in queue or not, add it to queue if not
					// do not want to repeat this code so that using preQueue
					while (!preQueue.isEmpty()) {
						int _x = preQueue.get(0)[0];
						int _y = preQueue.get(0)[1];
						boolean found = false;
						for (int p = queue.size()-1; p >= 0; p--) {
							if (_x == queue.get(p)[0] && _y == queue.get(p)[1]) {
								found = true;
								break;
							}
						}
						// add to queue if not in queue
						if (!found) {
							queue.add(preQueue.get(0));
						}
						preQueue.remove(0);
					}
				}
				// add current group to groupSizeTable using group size as key
				if (groupSizeTable.containsKey(currentGroupSize)) {
					groupSizeTable.get(currentGroupSize).add(currentGroup);
				}
				else {
					List<Integer> entry = new ArrayList<>();
					entry.add(currentGroup);
					groupSizeTable.put(currentGroupSize, entry);
				}

				if (_debug) {
					System.out.println("Group Matrix:");
					for (int x = 0; x < m.length; x++) {
						for (int y = 0; y < m.length; y++) {
							System.out.print(" " + groupMatrix[x][y]);
						}
						System.out.print("\r\n");
					}
				}
			}
		}
		// for each group size as key, group count is size of value list
		for (Integer key: groupSizeTable.keySet()) {
			result.put(key, groupSizeTable.get(key).size());
		}
		return result;
	}

	private static void printResult(Map<Integer, Integer> result) {
		System.out.println("Size\t#Groups");
		for (Integer key: new TreeSet<Integer>(result.keySet())) {
			System.out.print("  " + key + "\t  " + result.get(key) + "\r\n");
		}
	}
	
	public static void main(String[] args) {
		int[][] m1 = {
				{1, 0, 1, 1, 0},
				{0, 1, 0, 0, 1},
				{1, 0, 1, 1, 0},
				{1, 0, 1, 1, 0},
				{0, 1, 0, 0, 1}
		};
		System.out.println("Matrix 1:");
		Map<Integer, Integer> result1 = countgroups(m1);
		printResult(result1);
		
		int[][] m2 = {
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
				{1, 1, 0, 0, 1, 0, 0, 1, 1, 1},
				{1, 0, 1, 0, 0, 1, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
		System.out.println("\r\nMatrix 2:");
		Map<Integer, Integer> result2 = countgroups(m2);
		printResult(result2);	
    }
}
