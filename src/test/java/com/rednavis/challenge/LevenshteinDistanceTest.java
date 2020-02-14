package com.rednavis.challenge;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;

// https://mkyong.com/java/java-jmh-benchmark-tutorial/
// http://tutorials.jenkov.com/java-performance/jmh.html
// JMH Benchmark Modes
public class LevenshteinDistanceTest {

  @Test
  public void shouldPassCornerCases() {
    assertThat(LevenshteinDistance.levenshtein("", "Maus")).isEqualTo(4);
    assertThat(LevenshteinDistance.levenshtein("", "")).isEqualTo(0);
    assertThat(LevenshteinDistance.levenshtein("Maus", "Maus")).isEqualTo(0);
    assertThat(LevenshteinDistance.levenshtein("a", "b")).isEqualTo(1);

    // test case from Wikipedia
    assertThat(LevenshteinDistance.levenshtein("kitten", "sitting")).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("sitting", "kitten")).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("kitten", "kitten")).isEqualTo(0);
  }

  @Test
  public void shouldPassMasterTestCase() {
    assertThat(LevenshteinDistance.levenshtein("Haus", "Maus")).isEqualTo(1);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Mausi")).isEqualTo(2);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Häuser")).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("Kartoffelsalat", "Runkelrüben")).isEqualTo(12);
  }

  @Test
  public void shouldMakeEarlyExitWhenExceedMaximumAllowedDistance() {
    assertThat(LevenshteinDistance.levenshtein("Haus1", "Maus2", 0)).isEqualTo(1);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Maus", 2)).isEqualTo(1);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Mausi", 2)).isEqualTo(2);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Häuser", 2)).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("Kartoffelsalat", "Runkelrüben", 2)).isEqualTo(3);
  }
}