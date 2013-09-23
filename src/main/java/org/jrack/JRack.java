package org.jrack;

import java.util.Map;

public interface JRack
{
  RackResponse call(Map<String, Object> environment) throws Exception;
}
