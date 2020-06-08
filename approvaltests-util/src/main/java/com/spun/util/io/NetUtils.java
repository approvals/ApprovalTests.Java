package com.spun.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.spun.util.ObjectUtils;
import com.spun.util.logger.SimpleLogger;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

/**
 * A static class of convenience functions for Files
 **/
public class NetUtils
{
  
  public static void ftpUpload(FTPConfig config, String directory, File file, String remoteFileName)
      throws IOException
  {
    FTPClient server = new FTPClient();
    server.connect(config.host, config.port);
    assertValidReplyCode(server.getReplyCode(), server);
    server.login(config.userName, config.password);
    assertValidReplyCode(server.getReplyCode(), server);
    assertValidReplyCode(server.cwd(directory), server);
    server.setFileTransferMode(FTP.IMAGE_FILE_TYPE);
    server.setFileType(FTP.IMAGE_FILE_TYPE);
    server.storeFile(remoteFileName, new FileInputStream(file));
    assertValidReplyCode(server.getReplyCode(), server);
    server.sendNoOp();
    server.disconnect();
  }
  
  public static void ftpUpload(FTPConfig config, String directory, File file) throws IOException
  {
    ftpUpload(config, directory, file, file.getName());
  }
  
  public static void sftpUpload(FTPConfig config, File file, String remoteFileName) throws IOException
  {
    SshClient ssh = new SshClient();
    SftpClient sftp = sshLogin(config, ssh);
    sftp.mkdirs(remoteFileName.substring(0, remoteFileName.lastIndexOf("/")));
    sftp.put(new FileInputStream(file), remoteFileName);
    sftp.quit();
    ssh.disconnect();
  }
  /************************************************************************/
  private static SftpClient sshLogin(FTPConfig config, SshClient ssh) throws IOException
  {
    ssh.setSocketTimeout(60000);
    ssh.connect(config.host, new IgnoreHostKeyVerification());
    PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
    pwd.setUsername(config.userName);
    pwd.setPassword(config.password);
    ssh.authenticate(pwd);
    SftpClient sftp = ssh.openSftpClient();
    return sftp;
  }
  /************************************************************************/
  public static File sftpDownload(FTPConfig config, File file, String remoteFileName) throws IOException
  {
    SshClient ssh = new SshClient();
    SftpClient sftp = sshLogin(config, ssh);
    sftp.get(remoteFileName, new FileOutputStream(file));
    sftp.quit();
    ssh.disconnect();
    return file;
  }
  
  private static void assertValidReplyCode(int code, FTPClient ftp)
  {
    if (FTPReply.isPositiveCompletion(code))
    {
      //good
      SimpleLogger.variable("Good Completion code " + code);
    }
    else if (FTPReply.isPositiveIntermediate(code))
    {
      // do nothing
      SimpleLogger.variable("Good Intermediate code " + code);
    }
    else if (FTPReply.isPositivePreliminary(code))
    {
      // do nothing
      SimpleLogger.variable("Good Preliminary code " + code);
    }
    else
    {
      // bad
      throw new Error("Problem encountered with FTP Server, returned Code " + code + ", replied '"
          + ftp.getReplyString() + "'");
    }
  }
  
  /************************************************************************/
  public static String loadWebPage(String url, String parameters)
  {
    try
    {
      HttpClient client = new HttpClient();
      GetMethod method = new GetMethod(url);
      method.setQueryString(parameters);
      client.executeMethod(method);
      String html = method.getResponseBodyAsString();
      return html;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}