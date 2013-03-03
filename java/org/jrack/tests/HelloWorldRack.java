package org.jrack.tests;

import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;

public class HelloWorldRack implements JRack
{
  public int calls = 0;
  public RackResponse call(Map<String, Object> input)
  {
    calls++;
    return RackResponseUtils.standardHtml("HelloWorld");
  }
}
