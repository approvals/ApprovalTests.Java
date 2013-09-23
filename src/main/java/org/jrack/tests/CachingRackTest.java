package org.jrack.tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.jrack.RackEnvironment;
import org.jrack.utils.RackCache;

public class CachingRackTest extends TestCase
{
  public void testCache() throws Exception
  {
    HelloWorldRack rack = new HelloWorldRack();
    RackCache cache = new RackCache();
    cache.add(rack, "HelloWorld");
    Map<String, Object> input = new HashMap<String, Object>();
    input.put(RackEnvironment.PATH_INFO, "HelloWorld");
    cache.call(input);
    cache.call(input);
    assertEquals(1, rack.calls);
  }
}
