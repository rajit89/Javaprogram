package com.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

	public int[] solution(int K, int M, int[] A) {

		int noOfLoop = (A.length - K) + 1;

		int nextCount = K;

		int[] tobeChanged = new int[A.length];

		List<int[]> myArr = new ArrayList<>();

		for (int j = 0; j < noOfLoop; j++) {

			Integer[] subArray = IntStream.range(j, j + nextCount).mapToObj(i -> A[i] + 1).toArray(Integer[]::new);

			int[] intArray = Arrays.stream(subArray).mapToInt(Integer::intValue).toArray();

			tobeChanged = A.clone();

			System.arraycopy(intArray, 0, tobeChanged, j, K);

			myArr.add(tobeChanged);

			// System.out.println("Value of after increasing " +
			// Arrays.toString(tobeChanged));
		}
		int[] return_array = findOutPut(myArr, A.length, K);
		return return_array;
	}

	private int[] findOutPut(List<int[]> myArr, int sizeOfInputArray, int segement) {

		int[] leaderArray = null;
		HashSet<Integer> myHash = new HashSet<>();
		for (int[] myarr : myArr) {

			List<Integer> numbers = Arrays.stream(myarr).boxed().collect(Collectors.toList());

			Integer curr = null;
			int count = 0;

			HashMap<Integer, Integer> myMap = new HashMap<>();
			for (int val : numbers) {
				if (curr == null) {
					curr = val;
					count = 1;
				} else if (curr != val) {
					if (myMap.containsKey(curr)) {
						if (myMap.get(curr) < count) {
							myMap.put(curr, count);
						}
					} else {
						myMap.put(curr, count);
					}
					curr = val;
					count = 1;
				} else {
					++count;
				}
			}
			if (myMap.containsKey(curr)) {
				if (myMap.get(curr) < count) {
					myMap.put(curr, count);
				}
			} else {
				myMap.put(curr, count);
			}

			int leader = myCalculation(myMap, numbers);

			// System.out.println("In for loop " + leader);

			if (leader > 0) {
				myHash.add(leader);
			}

			leaderArray = new int[myHash.size()];
			int countNoOfArray = 0;

			for (Integer integer : myHash) {
				leaderArray[countNoOfArray] = integer;
				countNoOfArray++;
			}
		}
		Arrays.sort(leaderArray);
		return leaderArray;
	}

	private int myCalculation(HashMap<Integer, Integer> myMap, List<Integer> numbers) {

		int halfSize = numbers.size() / 2;

		boolean forward = false;
		for (Map.Entry<Integer, Integer> entry : myMap.entrySet()) {
			if (entry.getValue() > 1) {
				forward = true;
			}

			// System.out.println("Key : " + entry.getKey() + " value : " +
			// entry.getValue());
		}
		if (forward == true) {
			Map.Entry<Integer, Integer> maxEntry = myMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();
			int max = 0;
			int output = 0;
			for (Entry<Integer, Integer> entryMap : myMap.entrySet()) {
				if (entryMap.getValue() == maxEntry.getValue()) {

					int frequencyCount = Collections.frequency(numbers, entryMap.getKey());

					if (frequencyCount > max) {
						max = frequencyCount;
						output = entryMap.getKey();
					}
				}
			}
			int noOfTimes = Collections.frequency(numbers, output);
			if (noOfTimes > halfSize)
				return output;
			else
				return 0;
		} else {
			return 0;
		}
	}
}

public class LeaderSliceInc {

	public static void main(String[] args) {
		Solution s = new Solution();
		int[] myArr = { 1, 2, 2, 1, 2};

		//int[] myArr = { 1, 2, 3, 1, 2, 4, 4 };

		// int[] myArr = { 2, 1, 3, 1, 2, 2, 3 };
		// Integer[] myArr = {1};
		int[] output = s.solution(4, 2, myArr);

		// int[] output = s.solution(3, 5, myArr);

		System.out.println("Final Output " + Arrays.toString(output));
		// int[] output = s.solution(3, 5, myArr);

		// System.out.println(Arrays.toString(output));
	}

}
