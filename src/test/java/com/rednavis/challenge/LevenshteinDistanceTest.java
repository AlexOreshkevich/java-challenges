package com.rednavis.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import com.rednavis.challenge.LevenshteinDistance.Algorithm;
import java.util.UUID;
import org.junit.Test;

/**
 * Performance measurements provided by gradle build system automatically, it could be taken from test execution time.
 */
public class LevenshteinDistanceTest {

  static final String randomFirst = UUID.randomUUID().toString().substring(0, 7);
  static final String randomSecond = UUID.randomUUID().toString().substring(0, 7);

  @Test
  public void shouldPassCornerCases() {
    assertThat(LevenshteinDistance.levenshtein("", "Maus")).isEqualTo(4);
    assertThat(LevenshteinDistance.levenshtein("", "")).isEqualTo(0);
    assertThat(LevenshteinDistance.levenshtein("Maus", "Maus")).isEqualTo(0);
    assertThat(LevenshteinDistance.levenshtein("a", "b")).isEqualTo(1);
  }

  private static void masterTestCase(Algorithm algorithm) {

    assertThat(LevenshteinDistance.levenshtein("Haus", "Maus", algorithm)).isEqualTo(1);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Mausi", algorithm)).isEqualTo(2);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Häuser", algorithm)).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("Kartoffelsalat", "Runkelrüben", algorithm)).isEqualTo(12);

    assertThat(LevenshteinDistance.levenshtein(randomFirst, randomSecond, algorithm))
        .isLessThanOrEqualTo(Math.max(randomFirst.length(), randomSecond.length()));
  }

  private static void masterTestCaseWithMaxDistance(Algorithm algorithm) {
    assertThat(LevenshteinDistance.levenshtein("Haus1", "Maus2", 0, algorithm)).isEqualTo(1);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Maus", 2, algorithm)).isEqualTo(1);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Mausi", 2, algorithm)).isEqualTo(2);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Häuser", 2, algorithm)).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("Kartoffelsalat", "Runkelrüben", 2, algorithm)).isEqualTo(3);
  }

  @Test(timeout = 10000L)
  public void recursive() {
    masterTestCase(Algorithm.RECURSIVE);
  }

  @Test(timeout = 10000L)
  public void fullMatrix() {
    masterTestCase(Algorithm.FULL_MATRIX);
  }

  @Test(timeout = 10000L)
  public void adaptive() {
    masterTestCase(Algorithm.ADAPTIVE);
  }

  @Test(timeout = 10000L)
  public void adaptiveWithMaxDistance() {
    masterTestCaseWithMaxDistance(Algorithm.ADAPTIVE);
  }
}