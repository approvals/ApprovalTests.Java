package com.spun.util.database;

import java.io.IOException;
import java.sql.Connection;
import org.apache.commons.logging.LogFactory;
import com.spun.util.DatabaseConfiguration;
import com.spun.util.DatabaseConfigurationWrapper;
import com.spun.util.ObjectUtils;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.forwarding.ForwardingClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;


public class SshDatabaseWrapper implements DatabaseConfigurationWrapper
{

  public static String SSH = "Ssh";
  private static int counter = 32001; 

  static 
  {
    DatabaseConfiguration.registerWrapper(SSH, new SshDatabaseWrapper());
  }

  public synchronized Connection makeConnection(String database, DatabaseConfiguration originalConfiguration)
  {
    int port = counter++;
    DatabaseConfiguration config = new DatabaseConfiguration(originalConfiguration.getDataSourceName(), originalConfiguration.getDriver(), originalConfiguration.getProtocol(), "localhost", "" + port, database, originalConfiguration.getUserName(), originalConfiguration.getPassword(), originalConfiguration.getType());
    try
    {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        SshClient ssh = new SshClient();
        ssh.setSocketTimeout(60000);
        ssh.connect(originalConfiguration.getServer(), new IgnoreHostKeyVerification());
        PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
        pwd.setUsername(originalConfiguration.getUserName());
        pwd.setPassword(originalConfiguration.getPassword());
        ssh.authenticate(pwd);
        ForwardingClient client = ssh.getForwardingClient();
        client.addLocalForwarding(config.getProtocol(), "0.0.0.0", config.getPort(), "localhost", originalConfiguration.getPort());
        client.startLocalForwarding(config.getProtocol());
        return new SshConnection(ssh, config.makeConnection());
    }
    catch (IOException ie)
    {
      throw ObjectUtils.throwAsError(ie);
    }
  }


  
}
