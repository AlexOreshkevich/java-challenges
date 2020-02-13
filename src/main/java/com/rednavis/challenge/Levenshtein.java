package com.rednavis.challenge;

// Minimum Edit Distance - Explained ! - Stanford University
// https://youtu.be/Xxx0b7djCrs
// https://web.stanford.edu/class/cs124/lec/med.pdf
//
// https://en.wikipedia.org/wiki/Levenshtein_distance
public class Levenshtein {

  /**
   * The Levenshtein distance algorithm w/o allocating the full matrix, but an optimized version, which allocates only one column at a time.
   *
   * @param token1 first string
   * @param token2 second string
   * @return Levenshtein (edit) distance
   */
  public static int levenshtein(String token1, String token2) {

    // it is zero if and only if the strings are equal
    if (token1.equals(token2)) {
      return 0;
    }

    // recursive approach
    return levenshteinDistance(token1.toCharArray(), token1.length(), token2.toCharArray(), token2.length());
  }

  // implemented based on https://en.wikipedia.org/wiki/Levenshtein_distance#Recursive
  private static int levenshteinDistance(char[] first, int firstLength, char[] second, int secondLength) {

    int cost = 0;

    // base case: empty strings
    if (firstLength == 0) {
      return secondLength;
    }
    if (secondLength == 0) {
      return firstLength;
    }

    // test if last characters of the strings match
    if (first[firstLength - 1] != second[secondLength - 1]) {
      cost = 1;
    }

    // return minimum of delete char from s, delete char from t, and delete char from both
    return minimum(
        levenshteinDistance(first, firstLength - 1, second, secondLength) + 1,
        levenshteinDistance(first, firstLength, second, secondLength - 1) + 1,
        levenshteinDistance(first, firstLength - 1, second, secondLength - 1) + cost
    );
  }

  private static int minimum(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }
}
