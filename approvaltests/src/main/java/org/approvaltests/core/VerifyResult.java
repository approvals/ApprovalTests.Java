package org.approvaltests.core;

public enum VerifyResult {
                          SUCCESS, FAILURE;
  public static VerifyResult from(boolean success)
  {
    return success ? SUCCESS : FAILURE;
  }
  public boolean isSuccessful()
  {
    return this == SUCCESS;
  }
  public boolean isFailure()
  {
    return this == FAILURE;
  }
}
