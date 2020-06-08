package com.spun.util.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.context.Context;

import com.spun.util.ByteArrayDataSource;
import com.spun.util.StringUtils;
import com.spun.util.ArrayUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.ParseCall;

public class EmailServer
{
  private EmailLoader             loader           = null;
  private String                  emailSubject     = null;
  private String                  emailHtmlBody    = null;
  private String                  emailTextBody    = null;
  private ArrayList<String>       bcc              = new ArrayList<String>();
  private String                  to[];
  private String                  from;
  private String                  smtpServer;
  private ArrayList<MimeBodyPart> parts            = new ArrayList<MimeBodyPart>();
  private ArrayList<MimeBodyPart> images           = new ArrayList<MimeBodyPart>();
  private static boolean          mockTransport    = false;
  public static Message           lastMockSentItem = null;
  private String                  gmailUser;
  private String                  gmailPass;
  
  public static void setMockTransport(boolean mockTransport)
  {
    EmailServer.mockTransport = mockTransport;
    lastMockSentItem = null;
  }
  public static Message getLastSentItem()
  {
    return lastMockSentItem;
  }
  
  public static boolean isEmailValid(String email)
  {
    try
    {
      new javax.mail.internet.InternetAddress(email);
      return email.indexOf("@") != -1;
    }
    catch (javax.mail.internet.AddressException e)
    {
      return false;
    }
    catch (Throwable e)
    {
      return false;
    }
  }
  
  public EmailServer(String smtpServer, String velocityTemplate, ParseCall parser, ContextAware contextAware,
      String to, String from)
  {
    this(smtpServer, velocityTemplate, parser, contextAware, new String[]{to}, from);
  }
  
  public EmailServer(String smtpServer, String velocityTemplate, ParseCall parser, ContextAware contextAware,
      String[] to, String from)
  {
    this(smtpServer, new VelocityEmailLoader(velocityTemplate, parser, contextAware), to, from);
  }
  
  public EmailServer(String smtpServer, EmailLoader loader, String[] to, String from)
  {
    this.smtpServer = smtpServer;
    this.loader = loader;
    this.to = to;
    this.from = from;
  }
  
  public void addTo(String email)
  {
    to = (String[]) ArrayUtils.addToArray(to, email);
  }
  
  public void clearTo()
  {
    to = new String[0];
  }
  
  public void addBCC(String email)
  {
    bcc.add(email);
  }
  
  public void clearBCC()
  {
    bcc = new ArrayList<String>();
  }
  
  public void send() throws Exception
  {
      send(false, null, null);
  }
  
  public void send(boolean useGoogle, String user, String pass) throws Exception
  {

    gmailUser = user;
    gmailPass = pass;

    Properties props = System.getProperties();
    props.put("mail.smtp.host", smtpServer);

    Session session = null;
		 
    if(useGoogle) {
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", smtpServer);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable",        "true");
        props.put("mail.smtp.ssl",                    "true");

        session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(gmailUser, gmailPass);
                }
            }
        );
        session.setDebug(false);
    } else {
        session = Session.getDefaultInstance(props);
    }

    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(from));
    for (int i = 0; i < to.length; i++)
    {
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
    }
    for (int i = 0; i < bcc.size(); i++)
    {
      msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc.get(i)));
      //      My_System.variable("BBC - '"+bcc.get(i).toString()+"'");
    }
    msg.setHeader("X-Priority","3");
    msg.setHeader("X-MSMail-Priority","Normal");
    msg.setHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.3138");
    msg.setHeader("X-MimeOLE","Produced By Microsoft MimeOLE V6.00.2900.3138");
    msg.setHeader("X-Virus-Scanned","Symantec AntiVirus Scan Engine");
    msg.setSentDate(new Date());
    loader.load(this);
    Multipart mp = new MimeMultipart("alternative");
    
    if (emailTextBody != null)
    {
      MimeBodyPart part = new MimeBodyPart();
      part.getAllHeaders();
      part.setText(emailTextBody, "iso-8859-1");
      part.setHeader("Content-Transfer-Encoding", "Quoted-printable");
      mp.addBodyPart(part);
    }
    if (emailHtmlBody != null)
    {
      MimeBodyPart part = new MimeBodyPart();
      part.setDataHandler(new DataHandler(new ByteArrayDataSource(emailHtmlBody, "text/html")));
      part.setHeader("Content-Transfer-Encoding", "Quoted-printable");
      mp.addBodyPart(part);
      for (int i = 0; i < images.size(); i++)
      {
        mp.addBodyPart((MimeBodyPart) images.get(i));
      }
    }
    if (parts.size() != 0)
    {
      Multipart mp1 = mp;
      mp = new MimeMultipart("mixed");
      //Add Text Part
      MimeBodyPart part = new MimeBodyPart();
      part.setContent(mp1);
      mp.addBodyPart(part);
      for (int i = 0; i < parts.size(); i++)
      {
        mp.addBodyPart((MimeBodyPart) parts.get(i));
      }
    }
    msg.setContent(mp);
    msg.setSubject(emailSubject);
    msg.saveChanges(); // don't forget this
    if (!mockTransport)
    {
      Transport.send(msg);
    }
    else
    {
      lastMockSentItem = msg;
    }
  }
  
  public void addPart(MimeBodyPart part)
  {
    parts.add(part);
  }
  
  public static void addExcelFile(EmailServer email, String excelFileContent, String fileName)
      throws MessagingException
  {
    MimeBodyPart part = new MimeBodyPart();
    part.setDataHandler(new DataHandler(new ByteArrayDataSource(excelFileContent, "application/octet-stream")));
    // Define the encoding (choose between BASE64, UUENCODE, QUOTED-PRINTABLE) 
    part.setHeader("Content-Transfer-Encoding", "QUOTED-PRINTABLE");
    part.setFileName(fileName);
    email.addPart(part);
  }
  
  public void addHTMLImage(String file, String url) throws FileNotFoundException, MessagingException
  {
    String fileName = file.substring(file.lastIndexOf(File.separator) + 1);
    //My_System.variable("fileName = " + fileName);
    FileInputStream fileInputStream = new FileInputStream(file);
    addHTMLImage(url, fileName, fileInputStream);
  }
  
  public void addHTMLImage(String url, String fileName, InputStream inputStream) throws MessagingException
  {
    MimeBodyPart part = new MimeBodyPart();
    url = url == null ? "" : url;
    part.setDataHandler(new DataHandler(new ByteArrayDataSource(inputStream, "image/gif")));
    // Define the encoding (choose between BASE64, UUENCODE, QUOTED-PRINTABLE) 
    part.setHeader("Content-Transfer-Encoding", "BASE64");
    part.addHeader("Content-Location", url + fileName);
    part.setFileName(fileName);
    images.add(part);
  }
  
  
  public static interface EmailLoader
  {
    public void load(EmailServer server);
  }
  public static class VelocityEmailLoader implements EmailLoader, ContextAware
  {
    public static String TEXT_BODY    = "TextBody";
    public static String HTML_BODY    = "HtmlBody";
    public static String SUBJECT      = "Subject";
    private String       template     = null;
    private ContextAware contextAware = null;
    private String       currentEmailPart;
    private ParseCall    parser;
    
    public VelocityEmailLoader(String template, ParseCall parser, ContextAware contextAware)
    {
      this.template = template;
      this.contextAware = contextAware;
      this.parser = parser;
    }
    
    public void load(EmailServer server)
    {
      try
      {
        server.emailTextBody = getTextBody();
        server.emailHtmlBody = getHtmlBody();
        server.emailSubject = getSubject();
      }
      catch (Throwable t)
      {
        throw new Error(t);
      }
    }
    
    public String getTextBody()
    {
      currentEmailPart = TEXT_BODY;
      String result = parser.parse(template, this);
      return StringUtils.loadNullableString(result);
    }
    
    public String getHtmlBody()
    {
      currentEmailPart = HTML_BODY;
      String result = parser.parse(template, this);
      return StringUtils.loadNullableString(result);
    }
    
    public String getSubject()
    {
      currentEmailPart = SUBJECT;
      String result = parser.parse(template, this);
      return StringUtils.loadNullableString(result);
    }
    
    public void setupContext(Context context)
    {
      contextAware.setupContext(context);
      context.put("emailPart", currentEmailPart);
    }
  }
  public void setEmailHtmlBody(String emailHtmlBody)
  {
    this.emailHtmlBody = emailHtmlBody;
  }
  public void setEmailSubject(String emailSubject)
  {
    this.emailSubject = emailSubject;
  }
  public void setEmailTextBody(String emailTextBody)
  {
    this.emailTextBody = emailTextBody;
  }
}
