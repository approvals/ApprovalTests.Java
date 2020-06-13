package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class CountryTest
{
  @Test
  public void allCountries()
  {
    Approvals.verifyAll("Countries", Country.getStringValues());
  }
}
