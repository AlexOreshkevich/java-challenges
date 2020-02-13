package com.rednavis.challenge;

// Minimum Edit Distance - Explained ! - Stanford University
// https://youtu.be/Xxx0b7djCrs
// https://web.stanford.edu/class/cs124/lec/med.pdf
//
// https://en.wikipedia.org/wiki/Levenshtein_distance
//
// https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm
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

    int m = token1.length();
    int n = token2.length();

    // corner-cases: for empty string distance would be the length of another string
    if (m == 0) {
      return n;
    } else if (n == 0) {
      return m;
    }

    char[] s = token1.toCharArray();
    char[] t = token2.toCharArray();

    // https://bitbucket.org/clearer/iosifovitch/src/master/src/levenshtein.cpp

    // create two work vectors of integer distances
    int[] v0 = new int[n + 1];
    int[] v1 = new int[n + 1];

    // initialize v0 (the previous row of distances)
    // this row is A[0][i]: edit distance for an empty s
    // the distance is just the number of characters to delete from t
    for (int i = 0; i < n + 1; i++) {
      v0[i] = i;
    }

    // calculate v1 (current row distances) from the previous row v0
    for (int i = 1; i < m; i++) {

      // first element of v1 is A[i+1][0]
      // edit distance is delete (i+1) chars from s to match empty t
      v1[0] = i + 1;

      // use formula to fill in the rest of the row
      for (int j = 1; j < n; j++) {

        // calculating costs for A[i+1][j+1]
        int deletionCost = v0[j + 1] + 1;
        int insertionCost = v0[j] + 1;
        int substitutionCost = v0[j];
        if (s[i] != t[j]) {
          substitutionCost++;
        }

        v1[j] = minimum(deletionCost, insertionCost, substitutionCost);
      }

      // copy v1 (current row) to v0 (previous row) for next iteration
      // since data in v1 is always invalidated, a swap without copy could be more efficient
      System.arraycopy(v1, 0, v0, 0, n + 1);
    }

    return v0[n];
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
