package org.jrack;

import java.nio.CharBuffer;
import java.util.Map;

public class RackResponse
{
  private CharSequence              response;
  private final Map<String, String> headers;
  private int                       status;
  public RackResponse(int status, Map<String, String> headers, String response)
  {
    this(status, headers, (CharSequence) response);
  }
  public RackResponse(int status, Map<String, String> headers, char[] byteResponse)
  {
    this(status, headers, CharBuffer.wrap(byteResponse));
  }
  public RackResponse(int status, Map<String, String> headers, CharSequence response)
  {
    this.status = status;
    this.headers = headers;
    this.response = response;
  }
  public CharSequence getResponse()
  {
    return response;
  }
  public Map<String, String> getHeaders()
  {
    return headers;
  }
  public int getStatus()
  {
    return status;
  }
}
