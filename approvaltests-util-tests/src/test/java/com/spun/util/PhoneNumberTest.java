package com.spun.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



public class PhoneNumberTest extends TestSuite implements UseCaseTester<UseCase>
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
  public static Test suite() throws Exception
  {
    return UseCaseTesting.all(new PhoneNumberTest(), useCases);
  }
  public void testUseCase(UseCase useCase)
  {
    PhoneNumber ph = new PhoneNumber(useCase.original);
    if (useCase.usa != null)
    {
      TestCase.assertEquals("[" + useCase.original + "]USA format", useCase.usa, ph.getValueAsNorthAmerican());
    }
    else
    {
      TestCase.assertFalse("[" + useCase.original + "]Isn't USA Number", ph.isNorthAmericanNumber());
    }
    if (useCase.international != null)
    {
      TestCase.assertEquals("[" + useCase.original + " " + ph.getValueAsInternational() + "]International format",
          useCase.international, ph.getValueAsInternational());
    }
    else
    {
      TestCase.assertFalse("[" + useCase.original + "] Invalid #", ph.isValid());
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


