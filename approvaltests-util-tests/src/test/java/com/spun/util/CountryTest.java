package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.Country;

public class CountryTest
{
  @Test
  public void allCountries()
  {
    Approvals.verifyAll("Countries", Country.getStringValues());
  }
}
