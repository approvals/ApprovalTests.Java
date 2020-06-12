package com.spun.util.servlets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.spun.util.StringUtils;

public class ValidationTracker implements Serializable
{
  private static final long serialVersionUID = -5910435589128935375L;
  private ArrayList<String> errors           = null;
  private ValidationError   validationError  = null;
  
  public ValidationTracker(ValidationError validationError)
  {
    this.validationError = validationError;
    errors = new ArrayList<String>();
    errors.addAll(Arrays.asList(validationError.getAllErrorTitles()));
  }
  
  public boolean isValid(Enum<?> assertion)
  {
    return isValid(assertion.toString());
  }
  
  public boolean isValid(String assertion)
  {
    errors.remove(assertion);
    return validationError.isValid(assertion);
  }
  
  public boolean isValidForIndex(String prefix, int index, String assertion)
  {
    return isValid(ValidationError.getPrefixForIndex(prefix, index) + "." + assertion);
  }
  
  public String[] getRemainingErrors()
  {
    return StringUtils.toArray(errors);
  }
  
  public boolean hasRemainingErrors()
  {
    boolean hasRemainingErrors = !errors.isEmpty();
    if (hasRemainingErrors) { throw new Error("HTML did not catch following errors: " + errors.toString()); }
    return hasRemainingErrors;
  }
  
  public ValidationError getValidationError()
  {
    return validationError;
  }
  
  
}