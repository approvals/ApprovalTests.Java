package com.spun.util.servlets;

import com.spun.util.servlets.EmailServer.EmailLoader;

public class EasyEmailLoader implements EmailLoader
{
  
      private final String subject;
      private final String text;
      private final String html;
      /***********************************************************************/
      public EasyEmailLoader(String subject,String text,String html)
      {
        this.subject = subject;
        this.text = text;
        this.html = html;
      }
      /***********************************************************************/
      public void load(EmailServer server)
      {
          server.setEmailHtmlBody(html);
          server.setEmailSubject(subject);
          server.setEmailTextBody(text);
      }
      /***********************************************************************/
}
