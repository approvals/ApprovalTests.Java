package com.spun.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PhoneNumberTest
{
  private static UseCase useCases[] = {new UseCase("858-775-2868", "(858)775-2868", "+1.858.775.2868"),
                                       new UseCase("(800)351-7765", "(800)351-7765", "+1.800.351.7765"),
                                       new UseCase("858-755", null, null),
                                       new UseCase("1858.775.2868", "(858)775-2868", "+1.858.775.2868"),
                                       new UseCase("+1(858)775-2868", "(858)775-2868", "+1.858.775.2868"),
                                       new UseCase("+598.123.4567x858", null, "+598.123.456.7x858"),
                                       new UseCase("+27 1234 5678 ext 4", null, "+27.123.456.78x4"),
                                       new UseCase("2069316941", "(206)931-6941", "+1.206.931.6941"),
                                       new UseCase("27 1234 567 ext 4", null, null),
                                       new UseCase("+5+98.123.4567Xxx8x58", null, "+598.123.456.7x858"),};
  public static Stream<Arguments> getUseCases()
  {
    return Stream.of(useCases).map(u -> Arguments.of(u));
  }
  @ParameterizedTest
  @MethodSource("getUseCases")
  public void testUseCase(UseCase useCase)
  {
    PhoneNumber ph = new PhoneNumber(useCase.original);
    if (useCase.usa != null)
    {
      assertEquals(useCase.usa, ph.getValueAsNorthAmerican(), "[" + useCase.original + "]USA format");
    }
    else
    {
      assertFalse(ph.isNorthAmericanNumber(), "[" + useCase.original + "]Isn't USA Number");
    }
    if (useCase.international != null)
    {
      assertEquals(useCase.international, ph.getValueAsInternational(),
          "[" + useCase.original + " " + ph.getValueAsInternational() + "]International format");
    }
    else
    {
      assertFalse(ph.isValid(), "[" + useCase.original + "] Invalid #");
    }
  }
}

class UseCase
{
  public String original      = null;
  public String usa           = null;
  public String international = null;
  public UseCase(String original, String usa, String international)
  {
    this.original = original;
    this.usa = usa;
    this.international = international;
  }
  @Override
  public String toString()
  {
    return original;
  }
}
