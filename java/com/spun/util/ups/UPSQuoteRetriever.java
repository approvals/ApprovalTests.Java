package com.spun.util.ups;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class UPSQuoteRetriever
{
  /***********************************************************************/
  public InputStream getResponse(HttpClient client, PostMethod post) throws HttpException, IOException
  {
    //    My_System.markerIn();
    //    My_System.variable(post.getURI().toString());
    //    My_System.variable("parameters", post.getParameters());
    client.setConnectionTimeout(30000);
    client.setTimeout(30000);
    client.executeMethod(post);
    InputStream r = post.getResponseBodyAsStream();
    //    My_System.markerOut();
    return r;
  }
  /***********************************************************************/
  /***********************************************************************/
}
