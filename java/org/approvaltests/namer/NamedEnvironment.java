package org.approvaltests.namer;

public class NamedEnvironment implements AutoCloseable
{
  public NamedEnvironment(String info)
  {
    NamerFactory.additionalInformation = info;
  }
  @Override
  public void close()
  {
    NamerFactory.getAndClearAdditionalInformation();
  }
}