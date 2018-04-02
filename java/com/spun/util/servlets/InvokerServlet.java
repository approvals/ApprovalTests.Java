package com.spun.util.servlets;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spun.util.ObjectUtils;
import com.spun.util.logger.SimpleLogger;

/**
 * The default servlet-invoking servlet for most web applications,
 * used to serve requests to servlets that have not been registered
 * in the web application deployment descriptor.
 *
 * @author Craig R. McClanahan
 * @version $Revision$ $Date$
 */
public final class InvokerServlet extends HttpServlet
{
  private static final long    serialVersionUID = 1L;
  HashMap<String, HttpServlet> servlets         = new HashMap<String, HttpServlet>();
  private String               mask;
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    serveRequest(request, response);
  }
  public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    serveRequest(request, response);
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    serveRequest(request, response);
  }
  /**
   * Initialize this servlet.
   */
  public void init() throws ServletException
  {
    mask = getServletConfig().getInitParameter("mask");
    SimpleLogger.variable("Mask", mask);
  }
  /***********************************************************************/
  public void destroy()
  {
    for (HttpServlet servlet : servlets.values())
    {
      servlet.destroy();
    }
    super.destroy();
  }
  /***********************************************************************/
  public void serveRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException
  {
    String pathInfo = request.getPathInfo();
    String servletClass = pathInfo.substring(1);
    int slash = servletClass.indexOf('/');
    if (slash >= 0)
    {
      //      pathInfo = servletClass.substring(slash);
      servletClass = servletClass.substring(0, slash);
    }
    else
    {
      //      pathInfo = "";
    }
    //    My_System.variable("servletClass",servletClass);
    if (!servletClass.startsWith(mask))
    {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, servletClass);
      return;
    }
    HttpServlet servlet;
    synchronized (this)
    {
      servlet = servlets.get(servletClass);
      if (servlet == null)
      {
        try
        {
          servlet = (HttpServlet) Class.forName(servletClass).newInstance();
          servlet.init(getServletConfig());
        }
        catch (Throwable e)
        {
          ObjectUtils.throwAsError(e);
        }
        servlets.put(servletClass, servlet);
      }
    }
    servlet.service(request, response);
  }
}
