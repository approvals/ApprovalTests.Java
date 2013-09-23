package org.jrack.utils;

import java.util.HashMap;
import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackEnvironment;
import org.jrack.RackResponse;

public class RackCache implements JRack
{
  Map<String, JRack>                racks  = new HashMap<String, JRack>();
  private Map<String, RackResponse> caches = new HashMap<String, RackResponse>();
  @Override
  public RackResponse call(Map<String, Object> input) throws Exception
  {
    String path = (String) input.get(RackEnvironment.PATH_INFO);
    RackResponse response = caches.get(path);
    if (response == null)
    {
      JRack rack = racks.get(path);
      response = rack.call(input);
      caches.put(path, response);
    }
    return response;
  }
  public void add(JRack rack, String string)
  {
    racks.put(string, rack);
  }
}
