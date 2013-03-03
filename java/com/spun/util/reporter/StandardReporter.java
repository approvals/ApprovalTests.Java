package com.spun.util.reporter;

import com.spun.util.ObjectUtils;

/***********************************************************************/
public class StandardReporter implements Reporter
{
  /***********************************************************************/
  public void throwError(Throwable error)
  {
    throw ObjectUtils.throwAsError(error);
  }
}
