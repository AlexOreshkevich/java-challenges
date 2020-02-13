package com.rednavis.challenge;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class LevenshteinTest {

  @Test
  public void shouldProceedWithCornerCases() {
    assertThat(Levenshtein.levenshtein("", "Maus")).isEqualTo(4);
    assertThat(Levenshtein.levenshtein("", "")).isEqualTo(0);
    assertThat(Levenshtein.levenshtein("Maus", "Maus")).isEqualTo(0);
  }

  @Test
  public void levenshtein() {
    assertThat(Levenshtein.levenshtein("Haus", "Maus")).isEqualTo(1);
    assertThat(Levenshtein.levenshtein("Haus", "Mausi")).isEqualTo(2);
    assertThat(Levenshtein.levenshtein("Haus", "Häuser")).isEqualTo(3);
    assertThat(Levenshtein.levenshtein("Kartoffelsalat", "Runkelrüben")).isEqualTo(12);
  }
}