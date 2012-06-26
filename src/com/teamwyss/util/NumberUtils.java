package com.teamwyss.util;

import java.util.ArrayList;
import java.util.Arrays;

public class NumberUtils {

	/**
	 * Return an int array with duplicates removed, in order.
	 * @param aiIn Original array.
	 * @return New, sorted array with duplicates removed.
	 */
	public int[] getUniqueSorted(int[] aiIn){
		//int[] aiOut = new int[aiIn.length];
		Arrays.sort(aiIn);
		int iPrevious = -99;
		int iTargetLength = 0;
		for (int iInCounter = 0; iInCounter < aiIn.length; iInCounter++) {
			if(aiIn[iInCounter] != iPrevious){
				iTargetLength++;
				iPrevious = aiIn[iInCounter];
			}
		}
		if(iTargetLength != aiIn.length){
			int[] aiTemp = new int[iTargetLength];
			int iPos = 0;
			iPrevious = -99;
			for (int iCounter = 0; iCounter < aiIn.length; iCounter++) {
				if(aiIn[iCounter] != iPrevious){
					aiTemp[iPos] = aiIn[iCounter];
					iPos++;
					iPrevious = aiIn[iCounter];
				}
			}
			aiIn = aiTemp;
		}
		return aiIn;
	}

	/**
	 * Convert an int array into a readable range string.
	 * For example: int[]{1} would return "1".
	 * For example: int[]{1,2,3,4,5,6,7,8,9,10} would return "1-10".
	 * For example: int[]{1,5,6,7,8,9,10} would return "1,5-10".
	 * Note, this allows for non sorted ints.
	 * For example: int[]{8,7,6,5,4,3,2,1,0} would return "8,7,6,5,4,3,2,1,0";
	 * @param ai Array to process.
	 * @return An int array into a readable range string.
	 */
	public String numberArrayToRange(int[] ai){
		int iLast = -99;
		StringBuffer sbOut = new StringBuffer();
		boolean isInContig = false;
		for(int iCount = 0; iCount < ai.length; iCount++){
			int iCurr = ai[iCount];
			boolean isLastOne = (iCount == (ai.length - 1));
			if(iCount == 0){
				sbOut.append(iCurr);
			} else {
				if(iCurr == iLast){
					// Repeat of the previous number, do nothing unless it is the last number.
					if(isLastOne){
						sbOut.append(((isInContig) ? "-" : ", ")).append(iCurr);
					}
				} else if(iCurr == iLast + 1){
					// In a contig, keep adding.
					isInContig = true;
					if(isLastOne){
						sbOut.append("-").append(iCurr);
					}
				} else {
					if(isInContig){
						// We are finishing a contig. Finish the last one off, and start again.
						sbOut.append("-").append(iLast).append(", ");
					} else {
						if(sbOut.length() != 0){
							sbOut.append(", ");
						}
					}
					sbOut.append(iCurr);
					isInContig = false;
				}
			}
			iLast = iCurr;
		}
		return sbOut.toString();
	}

	public String toString(int[] aiIn) {
		StringBuffer sbOut = new StringBuffer();
		for(int iCount = 0; iCount < aiIn.length; iCount++){
			sbOut.append(((iCount == 0) ? "" : ",")).append(aiIn[iCount]);
		}
		return sbOut.toString();
	}

	public int[] integerArrayListToIntArray(ArrayList<Integer> alIn) {
		int[] aiOut = new int[alIn.size()];
		for (int i = 0; i < alIn.size(); i++) {
			aiOut[i] = alIn.get(i).intValue();
		}
		return aiOut;
	}
}
