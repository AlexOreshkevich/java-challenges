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

  public static int levenshtein(String token1, String token2, int maxDist) {
    return levenshtein(token1, token2, maxDist, Algorithm.ADAPTIVE);
  }

  public static int levenshtein(String token1, String token2, Algorithm algorithm) {
    return levenshtein(token1, token2, -1, algorithm);
  }

  /**
   * <p>The Levenshtein distance algorithm w/o allocating the full matrix, but an optimized version, which allocates only one column at a time. which
   * makes an early exit if the distance exceeds a maximum distance #maxDist.</p>
   * <p>Allows to specify implementation details for performance measurement.</p>
   *
   * @param first       first string
   * @param second      second string
   * @param maxDistance maximum allowed distance (early exit trigger condition)
   * @param algorithm   specify implementation details
   * @return Levenshtein (edit) distance
   */
  public static int levenshtein(String first, String second, int maxDistance, Algorithm algorithm) {

    // it is zero if and only if the strings are equal
    if (first.equals(second)) {
      return 0;
    }

    char[] firstChars;
    char[] secondChars;

    // re-reference first and second if first string is longer than second one
    if (first.length() > second.length()) {
      firstChars = second.toCharArray();
      secondChars = first.toCharArray();
    } else {
      firstChars = first.toCharArray();
      secondChars = second.toCharArray();
    }

    int m = firstChars.length;
    int n = secondChars.length;

    // corner-cases: for empty string distance would be the length of another string
    if (m == 0) {
      return n;
    } else if (n == 0) {
      return m;
    }

    switch (algorithm) {
      case RECURSIVE:
        return recursive(firstChars, secondChars, m, n);
      case ADAPTIVE:
        return adaptive(firstChars, secondChars, m, n, maxDistance);
      case FULL_MATRIX:
        return fullMatrix(firstChars, secondChars, m, n);
      default:
        throw new IllegalStateException("Unexpected value: " + algorithm);
    }
  }

  private static int adaptive(char[] s, char[] t, int m, int n, int maxDist) {

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

  private static int fullMatrix(char[] s, char[] t, final int m, final int n) {

    // for all i and j, d[i,j] will hold the Levenshtein distance between
    // the first i characters of s and the first j characters of t
    int[][] d = new int[m + 1][n + 1];

    // set each element in d to zero
    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        d[i][j] = 0;
      }
    }

    // source prefixes can be transformed into empty string by dropping all characters
    for (int i = 1; i <= m; i++) {
      d[i][0] = i;
    }

    // target prefixes can be reached from empty source prefix by inserting every character
    for (int j = 1; j <= n; j++) {
      d[0][j] = j;
    }

    for (int j = 1; j <= n; j++) {
      for (int i = 1; i <= m; i++) {

        int substitutionCost = s[i - 1] != t[j - 1] ? 1 : 0;

        d[i][j] = minimum(
            d[i - 1][j] + 1,                    // deletion
            d[i][j - 1] + 1,                      // insertion
            d[i - 1][j - 1] + substitutionCost  // substitution
        );
      }
    }

    return d[m][n];
  }

  // based on pseudocode from https://en.wikipedia.org/wiki/Levenshtein_distance#Recursive
  private static int recursive(char[] s, char[] t, int m, int n) {

    int cost = 0;

    /* base case: empty strings */
    if (m == 0) {
      return n;
    }
    if (n == 0) {
      return m;
    }

    // test if last characters of the strings match
    if (s[m - 1] == t[n - 1]) {
      cost = 0;
    } else {
      cost = 1;
    }

    // return minimum of delete char from s, delete char from t, and delete char from both
    return minimum(
        recursive(s, t, m - 1, n) + 1,
        recursive(s, t, m, n - 1) + 1,
        recursive(s, t, m - 1, n - 1) + cost
    );
  }

  private static int minimum(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }

  public enum Algorithm {
    RECURSIVE, FULL_MATRIX, ADAPTIVE
  }
}
