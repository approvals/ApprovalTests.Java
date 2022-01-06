package com.spun.util.io;

import com.spun.util.ObjectUtils;
import com.spun.util.logger.SimpleLogger;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SshUtils
{
  public static void ftpUpload(FTPConfig config, String directory, File file, String remoteFileName)
  {
    try
    {
      FTPClient server = new FTPClient();
      server.connect(config.host, config.port);
      assertValidReplyCode(server.getReplyCode(), server);
      server.login(config.userName, config.password);
      assertValidReplyCode(server.getReplyCode(), server);
      assertValidReplyCode(server.cwd(directory), server);
      server.setFileTransferMode(FTP.BINARY_FILE_TYPE);
      server.setFileType(FTP.BINARY_FILE_TYPE);
      server.storeFile(remoteFileName, new FileInputStream(file));
      assertValidReplyCode(server.getReplyCode(), server);
      server.sendNoOp();
      server.disconnect();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void ftpUpload(FTPConfig config, String directory, File file)
  {
    ftpUpload(config, directory, file, file.getName());
  }
  public static void sftpUpload(FTPConfig config, File file, String remoteFileName)
  {
    try
    {
      SshClient ssh = new SshClient();
      SftpClient sftp = sshLogin(config, ssh);
      sftp.mkdirs(remoteFileName.substring(0, remoteFileName.lastIndexOf("/")));
      sftp.put(new FileInputStream(file), remoteFileName);
      sftp.quit();
      ssh.disconnect();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static SftpClient sshLogin(FTPConfig config, SshClient ssh)
  {
    try
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
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static File sftpDownload(FTPConfig config, File file, String remoteFileName)
  {
    try
    {
      SshClient ssh = new SshClient();
      SftpClient sftp = sshLogin(config, ssh);
      sftp.get(remoteFileName, new FileOutputStream(file));
      sftp.quit();
      ssh.disconnect();
      return file;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
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
}
