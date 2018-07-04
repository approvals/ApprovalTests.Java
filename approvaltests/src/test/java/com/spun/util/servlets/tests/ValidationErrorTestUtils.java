package com.spun.util.servlets.tests;

import java.util.Arrays;

import junit.framework.TestCase;

import com.spun.util.servlets.ValidationError;

public class ValidationErrorTestUtils
{
	/************************************************************************/
	public static void testErrorFailed(ValidationError error)
	{
		testError(error, false);
	}
	/************************************************************************/
	public static void testError(ValidationError error)
	{
		testError(error, true);
	}
	/************************************************************************/
	public static void testError(ValidationError error, boolean passExpected)
	{
	  if (error.isOk() !=  passExpected)
	  {
	  	TestCase.assertEquals("Validation Errors  - "  + error.toString(), passExpected, error.isOk());
	  }
	  if (error.getTracker().hasRemainingErrors())
	  {
	  	TestCase.assertFalse("HTML didn't catch all errors - " + Arrays.asList(error.getTracker().getRemainingErrors()) , error.getTracker().hasRemainingErrors());
	  }
	  
	}
	/************************************************************************/

}
