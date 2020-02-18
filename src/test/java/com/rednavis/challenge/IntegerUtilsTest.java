package com.rednavis.challenge;

import static com.rednavis.challenge.IntegerUtils.zeroSequenceLength;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class IntegerUtilsTest {

  @Test
  public void shouldReturnBinaryGaps() {
    assertThat(zeroSequenceLength(1041)).isEqualTo(5);
    assertThat(zeroSequenceLength(32)).isEqualTo(0);
    assertThat(zeroSequenceLength(0b100010101110)).isEqualTo(3);
    assertThat(zeroSequenceLength(0b100000000)).isEqualTo(0);
    assertThat(zeroSequenceLength(0b00010000)).isEqualTo(0);
  }
}