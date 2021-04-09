package com.spun.util.servlets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.spun.util.StringUtils;

public class ValidationError extends RuntimeException
{
  private static final long           serialVersionUID = 1L;
  public static final ValidationError EMPTY            = new ValidationError();
  private HashMap<String, String>     errors           = new HashMap<String, String>();
  private HashSet<String>             assertions       = null;
  private ValidationTracker           iterator         = null;
  public ValidationError(Enum<?> enumumations[])
  {
    this.assertions = new HashSet<String>();
    for (Enum<?> e : enumumations)
    {
      this.assertions.add(e.toString());
    }
  }
  public ValidationError(String assertions[])
  {
    this.assertions = new HashSet<String>();
    if (assertions != null)
    {
      this.assertions.addAll(Arrays.asList(assertions));
    }
  }
  public ValidationError()
  {
    this.assertions = new HashSet<String>();
  }
  public String getMessage()
  {
    return toString();
  }
  public String toString()
  {
    return "Validation(s) failed " + errors.keySet().toString() + " - " + errors.values().toString();
  }
  public ValidationTracker getTracker()
  {
    if (iterator == null)
    {
      iterator = new ValidationTracker(this);
    }
    return iterator;
  }
  public void set(Enum<?> assertion, boolean isOk, String errorDescription)
  {
    setError(assertion.toString(), !isOk, errorDescription);
  }
  public void setError(String assertion, boolean isError, String errorDescription)
  {
    if (isError && !StringUtils.isNonZero(errorDescription))
    { throw new Error("You can not use empty error descriptions"); }
    assertValidAssertion(assertion);
    if (isError)
    {
      errors.put(assertion, errorDescription);
    }
    else
    {
      errors.remove(assertion);
    }
  }
  public void setIfValid(String assertion, boolean isError, String errorDescription)
  {
    if (!isOk())
    { return; }
    setError(assertion, isError, errorDescription);
  }
  public ValidationError add(String prefix, int index, ValidationError error)
  {
    return add(getPrefixForIndex(prefix, index), error);
  }
  public ValidationError addForRange(String prefix, int startInclusive, int endExclusive, ValidationError error)
  {
    for (int i = startInclusive; i < endExclusive; i++)
    {
      add(getPrefixForIndex(prefix, i), error);
    }
    return this;
  }
  public static String getPrefixForIndex(String prefix, int index)
  {
    return prefix + "[" + index + "]";
  }
  public ValidationError add(String prefix, ValidationError error)
  {
    prefix = StringUtils.isEmpty(prefix) ? "" : (prefix + ".");
    String[] assertions = StringUtils.toArray(error.assertions);
    for (int i = 0; i < assertions.length; i++)
    {
      this.assertions.add(prefix + assertions[i]);
    }
    Iterator<?> iterator = error.errors.keySet().iterator();
    while (iterator.hasNext())
    {
      String key = (String) iterator.next();
      errors.put(prefix + key, error.errors.get(key));
    }
    return this;
  }
  public boolean isOk()
  {
    return (errors.size() == 0);
  }
  private void assertValidAssertion(String assertion)
  {
    if (!this.assertions.contains(assertion))
    {
      //My_System.warning("Assertion '" + assertion + "' not found from " + assertions.toString());
      throw new Error("Assertion '" + assertion + "' not found from " + assertions.toString());
    }
  }
  public boolean isValid(Enum<?> e)
  {
    return isValid(e.toString());
  }
  public boolean isValid(String assertion)
  {
    assertValidAssertion(assertion);
    return (errors.get(assertion) == null);
  }
  public int size()
  {
    return errors.size();
  }
  public String getErrorDescription(Enum<?> assertion)
  {
    return getErrorDescription(assertion.toString());
  }
  /**
   * This get the description of the error
   * @return The text description of the error or "" if the assertion was valid
   **/
  public String getErrorDescription(String assertion)
  {
    assertValidAssertion(assertion);
    String errorDescription = (String) errors.get(assertion);
    return (errorDescription == null) ? "" : errorDescription;
  }
  public String[] getAllErrorTitles()
  {
    return StringUtils.toArray(errors.keySet());
  }
  public void assertValid()
  {
    if (!isOk())
    { throw this; }
  }
  public boolean isOnlyProblem(Enum<?> e)
  {
    String assertion = e.toString();
    assertValidAssertion(assertion);
    return (errors.get(assertion) != null) && errors.size() == 1;
  }
}