package com.rednavis.challenge;

// Minimum Edit Distance - Explained ! - Stanford University
// https://youtu.be/Xxx0b7djCrs
// https://web.stanford.edu/class/cs124/lec/med.pdf
//
// https://en.wikipedia.org/wiki/Levenshtein_distance
public class LevenshteinDistance {

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

    // corner-cases: for empty string distance would be the length of another string
    if (token1.isEmpty()) {
      return token2.length();
    } else if (token2.isEmpty()) {
      return token1.length();
    }

    return levenshteinDistance(token1.toCharArray(), token1.length(), token2.toCharArray(), token2.length());
  }

  private static int levenshteinDistance(char[] s, final int m, char[] t, final int n) {

    // for all i and j, d[i,j] will hold the Levenshtein distance between
    // the first i characters of s and the first j characters of t
    int d[][] = new int[m + 1][n + 1];

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

    // displayMatrix(d, m + 1, n + 1);

    return d[m][n];
  }

  private static int minimum(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }

  private static void displayMatrix(int[][] source, int m, int n) {
    System.out.println("display matrix of " + (m - 1) + "x" + (n - 1));
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(source[i][j] + " ");
      }
      System.out.print("\n");
    }
  }
}
