package com.spun.util.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TemplateNumberUtilsTest
{
  @ParameterizedTest
  @MethodSource("useCases")
  void testDoArighmetic2(String[] params, int result)
  {
    assertEquals(result, TemplateNumberUtils.doArithmetic(params), 0.005);
  }
  private static Stream<Arguments> useCases()
  {
    return Stream.of(Arguments.of(new String[]{"3", "+", "5"}, 8), Arguments.of(new String[]{"3", "-", "5"}, -2),
        Arguments.of(new String[]{"3", "*", "5"}, 15), Arguments.of(new String[]{"15", "/", "3"}, 5),
        Arguments.of(new String[]{"9", "+", "3"}, 12),
        Arguments.of(new String[]{"1", "+", "2", "*", "3", "-", "4", "/", "5"}, 1));
  }
  @Test
  public void testMax() throws Exception
  {
    Double numbers[] = {10.1, 8.0, 13.5, 5.0};
    assertEquals(13.5, TemplateNumberUtils.max((Number[]) numbers).getAmount());
  }
}
