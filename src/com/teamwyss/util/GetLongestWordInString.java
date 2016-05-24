package com.teamwyss.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetLongestWordInString {

	/**
	 * Scan through a superstring, and get the tokens.
	 * A token is an unbroken chain of alphanumeric-or-underscore chars, not starting with a number.
	 * @param sSuper Base master string.
	 * @param iMaxNumberOfWords Maximum words to return.
	 * @return ArrayList<String> of the longest words in the master string, ordered longest to shortest.
	 */
	public ArrayList<String> getLongestWordsInString(String sSuper, int iMaxNumberOfWords) {
		ArrayList<String> alOut = new ArrayList<String>();
		boolean isCollecting = false;
		StringBuffer buff = new StringBuffer();
		boolean isTheStartOfThisTokenANumber = false;
		for (int i = 0; i < sSuper.length(); i++){
			char c = sSuper.charAt(i);
			if (((c > 64) && (c < 91)) || ((c > 96) && (c < 123)) || (c == 95)){
				// It is alphanumeric or underscore, keep collecting.
				if (!isTheStartOfThisTokenANumber){
					isCollecting = true;
					buff.append(c);
				}
			} else if ((c > 47) && (c < 58)){
				// It is a digit.
				if (isCollecting){
					// It is a digit, but not the first char. Is continuing to buffer.
					buff.append(c);
				} else {
					// It is a digit AND the first char. Do not collect.
					isTheStartOfThisTokenANumber = true;
				}
			} else {
				// it is NOT alphanumeric
				if(isCollecting){
					// Finish collecting.
					mergeIn(alOut, buff.toString(), iMaxNumberOfWords);
					buff = new StringBuffer();
				}
				isCollecting = false;
				isTheStartOfThisTokenANumber = false;
			}
		}
		if(isCollecting){
			// Finish collecting.
			mergeIn(alOut, buff.toString(), iMaxNumberOfWords);
			buff = new StringBuffer();
		}
		return alOut;
	}

	/**
	 * Put the new String into the given ArrayList<String>.
	 * Only add it to the list if it is longer, than the shortest existing value.
	 * @param alOut The existing array to be added to.
	 * @param buff String to add.
	 * @param iMaxNumberOfResults Maximum num of elements in the list.
	 * @return List sorted by the length of the string.
	 */
	private boolean mergeIn(ArrayList<String> alOut, String buff, int iMaxNumberOfResults) {
		int iMaxWordLength = 16;
		if(buff.length() > iMaxWordLength){
			return false;
		}
		Comparator<String> comp = new StringLengthComparator();
		if (alOut.size() < iMaxNumberOfResults){
			// There are still positions free. Add the String.
			alOut.add(buff);
			Collections.sort(alOut, comp);
			return true;
		}
		int iShortest = 100; // Imaginary long word.
		int ixShortest = 0; // Pos of shortest word.
		for (int i = 0; i < alOut.size(); i++){
			int iLenThis = alOut.get(i).length();
			if (iLenThis < iShortest){
				iShortest = iLenThis;
				ixShortest = i;
			}
		}
		if(buff.length() > iShortest) {
			alOut.set(ixShortest, buff);
			Collections.sort(alOut, comp);
			return true;
		}
		return false;
	}

	public String joinArrayList(ArrayList<String> alIn, String sSep) {
		StringBuffer sbOut = new StringBuffer();
		//String sLa = "-- impossible --";
		int iNrElems = alIn.size();
		int iSameCount = 1;
		//ArrayList<String> alOut = new ArrayList<String>();
		StringFormatUtils sfu = new StringFormatUtils();
		for (int i = 0; i < iNrElems; i++){
			String sCurr = alIn.get(i);
			if(i != (iNrElems - 1)){
				// it is not the last one.
				if (alIn.get(i + 1).equals(sCurr)){
					// The next one is the same as this.
					iSameCount++;
				} else {
					if(sbOut.length() != 0){
						sbOut.append(sSep);
					}
					sbOut.append(sfu.formatNumberToLength(iSameCount * alIn.get(i).length(), 3) + " (" +  iSameCount + ") " +  alIn.get(i));
					iSameCount = 1;
				}
			}
		}
		return sbOut.toString();
	}

}

class StringLengthComparator implements Comparator<String> {
    // Comparator interface requires defining compare method.
    public int compare(String filea, String fileb) {
        int comp = fileb.length() - filea.length();
        if (comp != 0) {
            //... If different lengths, we're done.
            return comp;
        } else {
            //... If equal lengths, sort alphabetically.
            return filea.compareToIgnoreCase(fileb);
        }
    }
}