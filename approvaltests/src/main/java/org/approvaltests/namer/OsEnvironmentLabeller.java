package org.approvaltests.namer;

import org.lambda.functions.Function0;

public class OsEnvironmentLabeller implements Function0<String>
{
  @Override
  public String call()
  {
    return System.getProperty("os.name").replace(' ', '_');
  }
}
