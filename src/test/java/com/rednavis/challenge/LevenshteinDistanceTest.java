package com.rednavis.challenge;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;

public class LevenshteinDistanceTest {

  @Test
  public void shouldProceedWithCornerCases() {
    assertThat(LevenshteinDistance.levenshtein("", "Maus")).isEqualTo(4);
    assertThat(LevenshteinDistance.levenshtein("", "")).isEqualTo(0);
    assertThat(LevenshteinDistance.levenshtein("Maus", "Maus")).isEqualTo(0);
  }

  @Test
  public void levenshtein() {
    assertThat(LevenshteinDistance.levenshtein("Haus", "Mausi")).isEqualTo(2);
    assertThat(LevenshteinDistance.levenshtein("Haus", "Häuser")).isEqualTo(3);
    assertThat(LevenshteinDistance.levenshtein("Kartoffelsalat", "Runkelrüben")).isEqualTo(12);
  }

  @Test
  public void shouldReturnDistanceEqualToOne() {
    assertThat(LevenshteinDistance.levenshtein("Haus", "Maus")).isEqualTo(1);
  }

  @Test
  public void kittenSittingTest() {
    assertThat(LevenshteinDistance.levenshtein("sitting", "kitten")).isEqualTo(3);
  }
}