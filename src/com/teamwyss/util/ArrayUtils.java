package com.teamwyss.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import com.championdata.fansport.data.SortableIntArray;

public class ArrayUtils {

	/**
	 * Split a string int an array of ints. Default separator is comma.
	 * @param sSuper The string to be split.
	 * @return int[] The string split into parts and converted to ints.
	 */
	public int[] splitToInt(String sSuper) {
		return splitToInt(sSuper, ",");
	}

	/**
	 * Split a string a multi-dimensional array of ints.
	 * @param sSuper The string to be split.
	 * @param sSepOuter Outer separator string pattern.
	 * @param sSepInner Inner separator string pattern.
	 * @return int[] The string split into parts and converted to ints.
	 */
	public int[][] splitToInt(String sSuper, String sSepOuter, String sSepInner) {
		String[] asSuper = split(sSuper, sSepOuter);
		int[][] aaiOut = new int[asSuper.length][];
		for (int iOuter = 0; iOuter < asSuper.length; iOuter++) {
			aaiOut[iOuter] = splitToInt(asSuper[iOuter], sSepInner);
		}
		return aaiOut;
	}

	/**
	 * Split a string int an array of ints.
	 * @param sSuper The string to be split.
	 * @param sSep Separator string pattern.
	 * @return int[] The string split into parts and converted to ints.
	 */
	public int[] splitToInt(String sSuper, String sSep) {
		String[] as = split(sSuper, sSep, -1);
		int[] aiToReturn = new int[as.length];
		for (int iCount = 0; iCount < as.length; iCount++) {
			try {
				aiToReturn[iCount] = Integer.parseInt(as[iCount]);
			} catch (NumberFormatException nfe) {
				aiToReturn[iCount] = -1;
			}
		}
		return aiToReturn;
	}

	/**
	 * Split a string int an array of BigDecimals. Default separator is comma.
	 *
	 * @param sSuper
	 *            The string to be split.
	 * @return int[] The string split into parts and converted to BigDecimals.
	 */
	public BigDecimal[] splitToBigDecimal(String sSuper) {
		return splitToBigDecimal(sSuper, ",");
	}

	/**
	 * Split a string int an array of BigDecimals. Default separator is comma.
	 * @param sSuper The string to be split.
	 * @param sSep The string pattern to separate the values.
	 * @return int[] The string split into parts and converted to BigDecimals.
	 */
	public BigDecimal[] splitToBigDecimal(String sSuper, String sSep) {
		String[] as = split(sSuper, sSep, -1);
		BigDecimal[] abdOut = new BigDecimal[as.length];
		for (int iCount = 0; iCount < as.length; iCount++) {
			try {
				abdOut[iCount] = new BigDecimal(as[iCount]);
			} catch (NumberFormatException nfe) {
				abdOut[iCount] = new BigDecimal("-1");
			}
		}
		return abdOut;
	}

	/**
	 * Split a string int an array of Strings. Default separator is comma.
	 * @param sSuper The string to be split.
	 * @return String[] The master string split into parts.
	 */
	public String[] splitToString(String sSuper) {
		return split(sSuper, ",", -1);
	}

	/**
	 * Split a string int into an array of Strings. Separators can be any of the sSeps specified.
	 * @param sSuper The string to be split.
	 * @param asSep The array of string patterns used to separate the parts of the master string.
	 * @return String[] The master string split into parts.
	 */
	public String[] split(String sSuper, String[] asSep) {
		ReplaceSubstring rs = new ReplaceSubstring();
		String sTempSeparator = "$SEpARATOR$";
		sSuper = rs.replaceSubstring(sSuper, asSep, (new String[]{sTempSeparator}));
		return split(sSuper, sTempSeparator);
	}

	/**
	 * Split a string int into an array of Strings. Default separator is comma.
	 * @param sSuper The string to be split.
	 * @param sSep The string pattern used to separate the parts of the master string.
	 * @return String[] The master string split into parts.
	 */
	public String[] splitIgnoreInsideQuotes(String sSuper, String sSep) {
		char cHider = '~';
		if(sSuper.indexOf(cHider) != -1){
			cHider = '^';
			if(sSuper.indexOf(cHider) != -1){
				cHider = '#';
			}
		}
		String sbSuperForPos = (new com.teamwyss.util.StringFormatUtils()).replaceCharactersInsideQuotes(sSuper, cHider);
		String[] asSplit = split(sbSuperForPos, sSep);
		int iPosStart = 0;
		for(int iPart = 0; iPart < asSplit.length; iPart++){
			int iPosEnd = iPosStart + asSplit[iPart].length();
			asSplit[iPart] = sSuper.substring(iPosStart, iPosEnd);
			iPosStart = iPosEnd + sSep.length();
		}
		return asSplit;
	}

	/**
	 * Split a string int into an array of Strings. Default separator is comma.
	 * @param sSuper The string to be split.
	 * @param sSep The string pattern used to separate the parts of the master string.
	 * @return String[] The master string split into parts.
	 */
	public String[] split(String sSuper, String sSep) {
		return split(sSuper, sSep, -1);
	}

	public String[] split(String sIn) {
		return split(sIn, ",", -1);
	}

	/**
	 * Breaks a string into two parts, at an equals sign. Note, the value-string
	 * is not altered at all. You may want to trim and remove quotes.
	 * @param sParamValuePair String containing a parameter-name and a value.
	 * @return String array of exactly 2 elements.
	 */
	public String[] splitAtEquals(String sParamValuePair) {
		return split(sParamValuePair, "=", 2);
	}

	public String[] split(String sSuper, String sSep, int iTrgLeng) {
		return split(sSuper, sSep, iTrgLeng, "");
	}

	public String[] split(String sSuper, String sSep, int iTrgLeng, String sBlankFiller) {
		int iElemCount = 0;
		int iPosStart = 0; // Used to indicate where to start grabbing from
		int iPosEnd = 0; // Grab until this point.
		if ((sSuper == null) || (sSuper.length() == 0)) {
			if (iTrgLeng < 1) {
				return new String[] { "" };
			} else {
				String[] asOutBlank = new String[iTrgLeng];
				while (iElemCount < iTrgLeng) {
					asOutBlank[iElemCount] = ""; // Put in the empty ones.
					iElemCount++;
				}
				return asOutBlank;
			}
		}
		iPosEnd = sSuper.indexOf(sSep);
		if ((sSep == null) || (sSep.length() == 0) || (iPosEnd == -1) || (iTrgLeng == 1)) {
			return new String[] { sSuper };
		}
		if (iTrgLeng < 1) {
			iTrgLeng = 1;
			int iPos = sSuper.indexOf(sSep);
			while (iPos != -1) {
				iTrgLeng++;
				iPos = sSuper.indexOf(sSep, iPos + sSep.length());
			}
		}
		String[] asOut = new String[iTrgLeng];
		while ((iPosEnd != -1) && (iElemCount + 1 < iTrgLeng)) {
			asOut[iElemCount] = sSuper.substring(iPosStart, iPosEnd);
			iPosStart = iPosEnd + sSep.length();
			iPosEnd = sSuper.indexOf(sSep, iPosStart);
			iElemCount++;
		}
		asOut[iElemCount] = sSuper.substring(iPosStart); // Add the tail.
		iElemCount++;
		while (iElemCount < iTrgLeng) {
			asOut[iElemCount] = ""; // Put in the empty ones.
			iElemCount++;
		}
		return asOut;
	}


	/**
	 * Convert a string to an ArrayList object.
	 * @param sSuper String to be split.
	 * @param sSplitMarker Marker to demarkate elements.
	 * @return ArrayList containing a bunch of String objects.
	 */
	public ArrayList<String> splitToArrayList(String sSuper, String sSplitMarker) {
		if((sSuper == null) || (sSuper.length() == 0)){
			return (new ArrayList<String>());
		}
		String[] as = split(sSuper, sSplitMarker);
		List<String> list = Arrays.asList(as);
		ArrayList<String> alOut = new ArrayList<String>(list);
		return alOut;
	}

	/**
	 * Convert an ArrayList to a single String object.
	 * The join marker to be put between each element when appended is a comma.
	 * @see join(ArrayList, String)
	 * @param alSuper Incoming data.
	 * @return String representing the ArrayList.
	 */
	public String join(ArrayList<String> alSuper){
		return join(alSuper, ",");
	}

	/**
	 * Convert an ArrayList to a single String object.
	 * @param alSuper Incoming data.
	 * @param sJoinMarker To be put between each element when appended.
	 * @return String representing the ArrayList.
	 */
	public String join(ArrayList<String> alSuper, String sJoinMarker){
		if(alSuper == null){
			return "";
		}
		StringBuffer sbOut = new StringBuffer();
		for(int iOut = 0; iOut < alSuper.size(); iOut++){
			if(iOut > 0){
				sbOut.append(sJoinMarker);
			}
			sbOut.append((String)alSuper.get(iOut));
		}
		return sbOut.toString();
	}

	/**
	 * Convert an ArrayList to a single String object.
	 * @param alSuper Incoming data.
	 * @param sJoinMarker To be put between each element when appended.
	 * @return String representing the ArrayList.
	 */
	public String join(String[] alSuper, String sJoinMarker){
		if(alSuper == null){
			return "";
		}
		StringBuffer sbOut = new StringBuffer();
		for(int iOut = 0; iOut < alSuper.length; iOut++){
			if(iOut > 0){
				sbOut.append(sJoinMarker);
			}
			sbOut.append(alSuper[iOut]);
		}
		return sbOut.toString();
	}

	/**
	 * Convert a (String) ArrayList to a String[] object.
	 * @param alIn Original data.
	 * @return Very simlilar objec to input, but in different wrapper class.
	 */
	public String[] arrayListToStringArray(ArrayList<String> alIn){
		String[] asOut = new String[0];
		if(alIn != null){
			asOut = new String[alIn.size()];
			for(int iElem = 0; iElem < alIn.size(); iElem++){
				asOut[iElem] = (String)alIn.get(iElem);
			}
		}
		return asOut;
	}

	/**
	 * Compare two ArrayList objects, and return the number of differences between them.
	 * The method, first checks how many of the first group are missing from the second group.
	 * Then, it also adds the overflow - the count difference between the second and first groups.
	 * For example [one,two,three],[one,two,three] will return 0;
	 * For example [one,three,two],[one,two,three] will return 0;
	 * For example [one,two,three],[one,two] will return 1;
	 * For example [one,two],[one,two,three] will return 1;
	 * For example [one,two,three],[] will return 3;
	 * @param alOriginalValues Master group of (String) items.
	 * @param alNewValues Secondary group of (String) items.
	 * @return Difference count.
	 */
	public int getDifferenceCount(ArrayList<String> alOriginalValues, ArrayList<String> alNewValues){
		return getDifferenceCount(arrayListToStringArray(alOriginalValues), arrayListToStringArray(alNewValues));
	}

	/**
	 * Compare two String[] objects, and return the number of differences between them.
	 * The method, first checks how many of the first group are missing from the second group.
	 * Then, it also adds the overflow - the count difference between the second and first groups.
	 * For example ([one,two,three],[one,two,three]) will return 0;
	 * For example ([one,three,two],[one,two,three]) will return 0;
	 * For example ([one,two,three],[one,two]) will return 1;
	 * For example ([one,two],[one,two,three]) will return 1;
	 * For example ([one,two,three],[]) will return 3;
	 * For example (null,[]) will return 0;
	 * @param alOriginalValues Master group of (String) items.
	 * @param alNewValues Secondary group of (String) items.
	 * @return Difference count.
	 */
	public int getDifferenceCount(String[] asValuesNew, String[] asValuesOld){
		int iOut = 0;
		for(int iElem = 0; iElem < asValuesOld.length; iElem++){
			boolean isMissing = (getIndexOfElement(asValuesNew, asValuesOld[iElem]) == -1);
			if(isMissing){
				iOut++;
			}
		}
		//int iOverflow = 0; // If you want to count newly added ones, use this: Math.max(0, (asValuesOld.length - asValuesNew.length));
		//iOut += iOverflow;
		return iOut;
	}

	/**
	 * Get the index of the element within the String[].
	 * For example: (["one","two","three"], "two") will return 1.
	 * For example: (["one","two","three"], "eight") will return -1.
	 * NOTE: Case sensitive.
	 * @param asValues The values to search.
	 * @param sValueToFind The value to try to find.
	 * @return Position of the value to find, in the master list.
	 */
	private int getIndexOfElement(String[] asValues, String sValueToFind) {
		int iX = -1;
		for(int iElem = 0; iElem < asValues.length; iElem++){
			if(asValues[iElem].equals(sValueToFind)){
				return iElem;
			}
		}
		return iX;
	}

	/**
	 * <pre>
	 *   A ___ ___ B
	 *   /   / \   \
	 *  ( X (   )   )
	 *   \___\ /___/</pre>
	 * Supply two lists - A and B.
	 * Return all of the ones not in A, but in B.
	 * For example you have (["mouse","cat","dog"],["mouse","cat"]) will return "dog".
	 * @param alA
	 * @param alB
	 * @return
	 * @see http://en.wikipedia.org/wiki/Complement_%28set_theory%29#Relative_complement
	 */
	public ArrayList<String> getComplimentOfBInA(ArrayList<String> alA, ArrayList<String> alB) {
		ArrayList<String> alOut = new ArrayList<String>();
		if((alA == null) || (alA.size() == 0)){
			return alOut;
		}
		if((alB == null) || (alB.size() == 0)){
			return alA;
		}
		String sB = "," + join(alB, ",") + ",";
		for(int iElem = 0; iElem < alA.size(); iElem++){
			if(sB.indexOf((String)alA.get(iElem)) == -1){
				// The var from A is not in B.
				alOut.add(alA.get(iElem));
			}
		}
		return alOut;
	}

	/**
	 * <pre>
	 *   A ___ ___ B
	 *   /   / \   \
	 *  (   ( X )   )
	 *   \___\ /___/</pre>
	 * Supply two lists - A and B.
	 * Return only the ones in both A and B.
	 * For example you have (["mouse","cat","dog"],["mouse","cat"]) will return ["cat"].
	 * @param alA First of the two ArrayLists to be tested.
	 * @param alB Second ArrayList.
	 * @return ArrayList containing only those elements common to the two lists.
	 * @see http://en.wikipedia.org/wiki/Intersection_%28set_theory%29
	 */
	public ArrayList<String> getIntersection(ArrayList<String> alA, ArrayList<String> alB) {
		ArrayList<String> alOut = new ArrayList<String>();
		if((alA == null) || (alA.size() == 0)){
			return alOut;
		}
		if((alB == null) || (alB.size() == 0)){
			return alOut;
		}
		String sAllIdsInB = "," + join(alB, ",") + ",";
		for(int iA = 0; iA < alA.size(); iA++){
			String sTemp = "," + (String)alA.get(iA) + ",";
			if(sAllIdsInB.indexOf(sTemp) != -1){
				alOut.add(alA.get(iA));
			}
		}
		return alOut;
	}

	/**
	 * <pre>
	 *   A ___ ___ B
	 *   /   / \   \
	 *  ( X (   ) X )
	 *   \___\ /___/</pre>
	 * Supply two lists - A and B.
	 * Return all of the ones NOT shared by A and B.
	 * For example you have (["seal","penguin"],["eagle","penguin"]) will return ["seal","eagle"].
	 * @param alA String ArrayList
	 * @param alB String ArrayList
	 * @return Values exclusive to the two sets. Remove common values and return remaining values as single ArrayList.
	 * @see http://en.wikipedia.org/wiki/Symmetric_difference
	 */
	public ArrayList<String> getSymetricDifference(ArrayList<String> alA, ArrayList<String> alB) {
		ArrayList<String> alOut = new ArrayList<String>();
		if((alA == null) || (alA.size() == 0)){
			if(alB == null){
				return alOut;
			}
			return alB;
		}
		if((alB == null) || (alB.size() == 0)){
			//if(alA == null){
			//	return alOut;
			//}
			return alA;
		}
		String sB = "," + join(alB, ",") + ",";
		String sRemoveFromB = "";
		for(int iA = 0; iA < alA.size(); iA++){
			String sTemp = "," + (String)alA.get(iA) + ",";
			if(sB.indexOf(sTemp) != -1){
				// The var from A is in B.
				sRemoveFromB += sTemp;
			} else {
				alOut.add((String)alA.get(iA));
			}
		}
		for(int iB = 0; iB < alB.size(); iB++){
			String sTemp = (String)alB.get(iB);
			if(sRemoveFromB.indexOf("," + sTemp + ",") == -1){
				alOut.add(sTemp);
			}
		}
		return alOut;
	}

	/**
	 * <pre>
	 *   A ___ ___ B
	 *   /   / \   \
	 *  ( X ( X ) X )
	 *   \___\ /___/</pre>
	 * Get the superset of the two lists.
	 * For example ["zero","one","two"] ["two","three","one"] will return  ["zero","one","two","three"]
	 * @param alA Master list. This will be returned in its original order, possibly with additions.
	 * @param alB List of items to be added, but only if they are not in A.
	 * @return List A plus B if the items are not in A.
	 * @see http://en.wikipedia.org/wiki/Union_%28set_theory%29
	 */
	@SuppressWarnings("all")
	public ArrayList<String> getUnion(ArrayList<String> alA, ArrayList<String> alB) {
		ArrayList<String> alOut = new ArrayList<String>();
		if((alA == null) || (alA.size() == 0)){
			if(alB == null){
				return alOut;
			}
			return alB;
		}
		if((alB == null) || (alB.size() == 0)){
			if(alA == null){
				return alOut;
			}
			return alA;
		}
		alOut.addAll(alA);
		String sToSearch = "," + this.join(alOut, ",") + ",";
		for(int iX = 0; iX < alB.size(); iX++){
			String sTemp = (String)alB.get(iX);
			if(sToSearch.indexOf("," + sTemp + ",") == -1){
				alOut.add(sTemp);
			}
		}
		return alOut;
	}

	/** Convert from String array to int array.
	 * @param as String array to convert.
	 * @return Guaranteed ints (no nulls or Exceptions).
	 */
	public int[] toIntArray(String[] as) {
		int[] aiOut = new int[as.length];
		StringFormatUtils sfu = new StringFormatUtils();
		for(int iX = 0; iX < as.length; iX++){
			aiOut[iX] = sfu.toSafeInt(as[iX], 0);
		}
		return aiOut;
	}

	/**
	 * Comb through an array and remove any zeros.
	 * @param aiIn Array with guaranteed non-zero values.
	 * @return
	 */
	public int[] removeZeros(int[] aiIn) {
		int iFailureCount = 0;
		boolean[] abOkList = new boolean[aiIn.length];
		for(int iX = 0; iX < aiIn.length; iX++){
			if(aiIn[iX] == 0){
				abOkList[iX] = true;
				iFailureCount++;
			} else {
				abOkList[iX] = false;
			}
		}
		if(iFailureCount == 0){
			return aiIn;
		}
		int[] aiOut = new int[aiIn.length - iFailureCount];
		int iTargetIndex = 0;
		for(int iX = 0; iX < aiIn.length; iX++){
			if(abOkList[iX]){
				aiOut[iTargetIndex] = aiIn[iX];
				iTargetIndex++;
			}
		}
		return aiOut;
	}

	/**
	 * Convert an array of ints to a comma separated String.
	 * @param aiIn The array to return.
	 * @return Something like this "12,432,1,23,0,232".
	 */
	public String join(int[] aiIn) {
		return join(aiIn, ",");
	}

	/**
	 * Convert an array of ints to a comma separated String.
	 * @param aiIn The array to return.
	 * @return Something like this "12,432,1,23,0,232".
	 */
	public String join(int[] aiIn, String sSep) {
		StringBuffer sbOut = new StringBuffer();
		for (int iIndex = 0; iIndex < aiIn.length; iIndex++) {
			if(iIndex != 0){
				sbOut.append(sSep);
			}
			sbOut.append(aiIn[iIndex]);
		}
		return sbOut.toString();
	}

	/**
	 * Convert an array of ints to a comma separated String.
	 * @param aiIn The array to return.
	 * @return Something like this "12,432,1,23,0,232".
	 */
	public String join(int[][] aaiIn, String sBigSep, String sLittleSep) {
		StringBuffer sbOut = new StringBuffer();
		for (int iIndex = 0; iIndex < aaiIn.length; iIndex++) {
			if(iIndex != 0){
				sbOut.append(sBigSep);
			}
			sbOut.append(join(aaiIn[iIndex], sLittleSep));
		}
		return sbOut.toString();
	}

	/**
	 * @param aiIn
	 * @param iLength
	 * @return
	 */
	public int[] trimToLength(int[] aiIn, int iLength) {
		int[] aiOut = new int[iLength];
		for (int iIndex = 0; iIndex < aiOut.length; iIndex++) {
			aiOut[iIndex] = aiIn[iIndex];
		}
		return aiOut;
	}

	/**
	 * @param aiMaster
	 * @param iToFind
	 * @return
	 */
	public int indexOfElement(int[] aiMaster, int iToFind) {
		for (int iMasterCounter = 0; iMasterCounter < aiMaster.length; iMasterCounter++) {
			if(aiMaster[iMasterCounter] == iToFind){
				return iMasterCounter;
			}
		}
		return -1;
	}

	/**
	 * Go through a list of the elements in an array, replacing all instances of a given int.
	 * @param aiMaster List to search.
	 * @param iFind Value to find/remove.
	 * @param iReplace Value to put in.
	 */
	public void replaceElement(int[] aiMaster, int iFind, int iReplace) {
		if(aiMaster == null){
			return;
		}
		if(iFind == iReplace){
			return;
		}
		for (int iElem = 0; iElem < aiMaster.length; iElem++) {
			if (aiMaster[iElem] == iFind) {
				aiMaster[iElem] = iReplace;
			}
		}
	}

	/**
	 * Get a list of all duplicates in the list.
	 * @param aiIn Array to search.
	 * @return List of all duplicated ids. Note, only lists each duplicate once.
	 */
	public int[] getDuplicates(int[] aiIn) {
		int[] aiOut = new int[0];
		try {
			boolean[] abDuplicate = new boolean[aiIn.length];
			int iDuplicatesFound = 0;
			for (int iElemUpper = 0; iElemUpper < aiIn.length; iElemUpper++) {
				if((!abDuplicate[iElemUpper]) && (iElemUpper != (aiIn.length - 1))){
					for (int iElemLower = iElemUpper + 1; iElemLower < aiIn.length; iElemLower++) {
						if(aiIn[iElemUpper] == aiIn[iElemLower]){
							abDuplicate[iElemLower] = true;
							iDuplicatesFound++;
						}
					}
				}
			}
			aiOut = new int[iDuplicatesFound];
			int iInsertPointer = 0;
			if(iDuplicatesFound > 0){
				for (int iDupCounter = 0; iDupCounter < aiIn.length; iDupCounter++) {
					if(abDuplicate[iDupCounter]){
						aiOut[iInsertPointer] = aiIn[iDupCounter];
						iInsertPointer++;
					}
				}
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aiOut;
	}

	/**
	 * @param aaiIn
	 * @param iInnerIx
	 * @return
	public int[][] sortByInnerIndex(int[][] aaiIn, int iInnerIx) {
		SortableIntArray[] asia = new SortableIntArray[aaiIn.length];
		for (int iOuter = 0; iOuter < aaiIn.length; iOuter++) {
			SortableIntArray sia = new SortableIntArray(aaiIn[iOuter], iInnerIx, false);
			asia[iOuter] = sia;
		}
		Arrays.sort(asia);
		return convertSortableIntArrayArrayToIntArray(asia);
	}
	 */

	/**
	 * Convert the sortable-multi-dimensional-int-array to normal multi-dimensional-int-array.
	 * @param asia Used for sorting int arrays.
	 * @return
	private int[][] convertSortableIntArrayArrayToIntArray(SortableIntArray[] asia) {
		int[][] aaiOut = new int[asia.length][];
		for (int iAsia = 0; iAsia < asia.length; iAsia++) {
			aaiOut[iAsia] = asia[iAsia].data;
		}
		return aaiOut;
	}
	 */

	public String[] removeEmpty(String[] asIn) {
		int iFailureCount = 0;
		boolean[] abOkList = new boolean[asIn.length];
		for(int iX = 0; iX < asIn.length; iX++){
			if((asIn[iX] == null) || (asIn[iX].length() == 0)){
				abOkList[iX] = false;
				iFailureCount++;
			} else {
				abOkList[iX] = true;
			}
		}
		if(iFailureCount == 0){
			return asIn;
		}
		String[] asOut = new String[asIn.length - iFailureCount];
		int iTargetIndex = 0;
		for(int iX = 0; iX < asIn.length; iX++){
			if(abOkList[iX]){
				asOut[iTargetIndex] = asIn[iX];
				iTargetIndex++;
			}
		}
		return asOut;
	}

	/**
	 * Convert a multidimensional array to a flat array.
	 * For example: [[1,2,3][14,15][26,27]] becomes [1,2,3,14,15,26,27]
	 * @param aas Multidimensional array to convert.
	 * @return Flat String array.
	 */
	public String[] flatten(String[][] aas) {
		// Work out how many objects there are;
		int iFlatCount = 0;
		for (int iOuter = 0; iOuter < aas.length; iOuter++) {
			if(aas[iOuter] != null){
				for (int iInner = 0; iInner < aas[iOuter].length; iInner++) {
					iFlatCount++;
				}
			}
		}
		String[] asOut = new String[iFlatCount];
		int iIxCurr = 0;
		for (int iOuter = 0; iOuter < aas.length; iOuter++) {
			if(aas[iOuter] != null){
				for (int iInner = 0; iInner < aas[iOuter].length; iInner++) {
					if(aas[iOuter][iInner] != null){
						asOut[iIxCurr++] = aas[iOuter][iInner];
					} else {
						asOut[iIxCurr++] = "";
					}
				}
			}
		}
		return asOut;
	}

	/**
	 * Checks for duplicates.
	 * Does not include zeros in the search. Add this bit here in to search for zeros.
	 *	int iBlankValue = 0;
	 *	if(iBlankValue != 0){
	 *		for (int iRegWipe = 0; iRegWipe < aiValueRegister.length; iRegWipe++) {
	 *			aiValueRegister[iRegWipe] = iBlankValue;
	 *		}
	 *	}
	 * @param alToCheck
	 * @param i
	 * @return
	 */
	public boolean isContainsDuplicates(ArrayList<int[]> alToCheck, int iIndexInArray) {
		int[] aiValueRegister = new int[alToCheck.size()];
		int iBlankValue = 0;
		for (int iLTC = 0; iLTC < alToCheck.size(); iLTC++) {
			int iValueTemp = alToCheck.get(iLTC)[iIndexInArray];
			for (int iRegister = 0; iRegister < aiValueRegister.length; iRegister++) {
				if(aiValueRegister[iRegister] == iBlankValue){
					aiValueRegister[iRegister] = iValueTemp;
					break;
				}
				if(aiValueRegister[iRegister] == iValueTemp){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Separate using markers at begin and end.
	 * For example, ("one{inside}two","{","}") will return [one][two].
	 * @param sSuper
	 * @param sSepStart
	 * @param sSepEnd
	 */
	public String[] splitAtTwoPartSeparator(String sSuper, String sSepStart, String sSepEnd) {
		String[] asOut = split(sSuper, sSepStart);
		if(asOut.length > 1){
			for(int i = 1; i < asOut.length; i++){
				int iPos = asOut[i].indexOf(sSepEnd);
				if(iPos != -1){
					asOut[i] = asOut[i].substring(iPos + sSepEnd.length());
				}
			}
		}
		return asOut;
	}
}
