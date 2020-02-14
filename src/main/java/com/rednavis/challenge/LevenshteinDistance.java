package com.rednavis.challenge;

/**
 * Utility class for calculating Levenshtein distance.
 *
 * @linkplain https://en.wikipedia.org/wiki/Levenshtein_distance
 * @linkplain https://youtu.be/Xxx0b7djCrs
 */
public class LevenshteinDistance {

  /**
   * The Levenshtein distance algorithm w/o allocating the full matrix, but an optimized version, which allocates only one column at a time.
   *
   * @param token1 first string
   * @param token2 second string
   * @return Levenshtein (edit) distance
   * @linkplain https://bitbucket.org/clearer/iosifovitch/src/master/src/levenshtein.cpp
   * @linkplain https://en.wikipedia.org/wiki/Levenshtein_distance#Adaptive_variant
   */
  public static int levenshtein(String token1, String token2) {
    return levenshtein(token1, token2, -1);
  }

  /**
   * The Levenshtein distance algorithm w/o allocating the full matrix, but an optimized version, which allocates only one column at a time. which
   * makes an early exit if the distance exceeds a maximum distance #maxDist.
   *
   * @param token1  first string
   * @param token2  second string
   * @param maxDist maximum allowed distance (early exit trigger condition)
   * @return Levenshtein (edit) distance
   */
  public static int levenshtein(String token1, String token2, int maxDist) {

    // it is zero if and only if the strings are equal
    if (token1.equals(token2)) {
      return 0;
    }

    char[] s;
    char[] t;

    // re-reference s and t to token1 and token2 respectively if first string is longer than second one
    if (token1.length() > token2.length()) {
      s = token2.toCharArray();
      t = token1.toCharArray();
    } else {
      s = token1.toCharArray();
      t = token2.toCharArray();
    }

    int m = s.length;
    int n = t.length;

    // corner-cases: for empty string distance would be the length of another string
    if (m == 0) {
      return n;
    } else if (n == 0) {
      return m;
    }

    // create buffer array of integer distances
    int length = n + 1;
    int[] buffer = new int[length];

    // initialize v0 (the previous row of distances)
    // this row is A[0][i]: edit distance for an empty s
    // the distance is just the number of characters to delete from t
    for (int i = 0; i < length; i++) {
      buffer[i] = i;
    }

    // calculate v1 (current row distances) from the previous row v0
    for (int i = 1; i < m + 1; i++) {

      // edit distance is delete (i+1) chars from s to match empty t
      int temp = buffer[0]++;

      for (int j = 1; j < length; j++) {

        // calculating costs
        int insert = buffer[j - 1];
        int remove = buffer[j];
        temp = Math.min(
            Math.min(remove, insert) + 1,
            temp + (s[i - 1] == t[j - 1] ? 0 : 1) // substitutionCost
        );

        // swap buffer[j] and temp
        int swapTemp = temp;
        temp = buffer[j];
        buffer[j] = swapTemp;
      }
    }

    // early? exit in a case of exceeding allowed maximum distance
    if (maxDist != -1 && buffer[length - 1] > maxDist) {
      return maxDist + 1;
    } else {
      return buffer[length - 1];
    }
  }
}
