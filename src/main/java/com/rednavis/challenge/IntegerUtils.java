package com.rednavis.challenge;

public class IntegerUtils {

  // finds longest sequence of zeros in binary representation of an integer
  // TODO use bit manipulations to improve efficiency
  public static int zeroSequenceLength(int n) {

    String binary = Integer.toBinaryString(n);
    char[] chars = binary.toCharArray();

    if (chars[0] != '1') {
      return 0;
    }

    int maxGap = 0;
    int curGap = 0;

    for (int i = 1; i < chars.length; i++) {
      if (chars[i] == '0') {
        curGap++;
      } else {
        maxGap = Math.max(maxGap, curGap);
        curGap = 0;
      }
    }

    return maxGap;
  }
}
