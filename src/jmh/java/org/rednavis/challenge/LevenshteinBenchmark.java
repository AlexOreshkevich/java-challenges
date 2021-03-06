package org.rednavis.challenge;

import com.rednavis.challenge.LevenshteinDistance;
import com.rednavis.challenge.LevenshteinDistance.Algorithm;
import java.util.ArrayList;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class LevenshteinBenchmark {

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void recursive(TestSuite testSuite) {
    for (TestCase testCase : testSuite.testCases) {
      LevenshteinDistance.levenshtein(testCase.first, testCase.second, Algorithm.RECURSIVE);
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void fullMatrix(TestSuite testSuite) {
    for (TestCase testCase : testSuite.testCases) {
      LevenshteinDistance.levenshtein(testCase.first, testCase.second, Algorithm.FULL_MATRIX);
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void adaptive(TestSuite testSuite) {
    for (TestCase testCase : testSuite.testCases) {
      LevenshteinDistance.levenshtein(testCase.first, testCase.second, Algorithm.ADAPTIVE);
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void adaptiveWithMaxDistance(TestSuite testSuite) {
    for (TestCase testCase : testSuite.testCases) {
      LevenshteinDistance.levenshtein(testCase.first, testCase.second, testCase.maxDistance, Algorithm.ADAPTIVE);
    }
  }

  static class TestCase {

    String first;
    String second;
    int distance;
    int maxDistance;

    public TestCase(String first, String second, int distance, int maxDistance) {
      this.first = first;
      this.second = second;
      this.distance = distance;
      this.maxDistance = maxDistance;
    }
  }

  @State(Scope.Benchmark)
  public static class TestSuite {

    List<TestCase> testCases = new ArrayList<>();

    {
      testCases.add(new TestCase("Haus", "Maus", 1, 0));
      testCases.add(new TestCase("Haus", "Mausi", 2, 2));
      testCases.add(new TestCase("Haus", "Häuser", 3, 2));
      testCases.add(new TestCase("Kartoffelsalat", "Runkelrüben", 12, 2));
    }
  }
}
