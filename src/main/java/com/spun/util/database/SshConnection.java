package com.spun.util.database;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.sshtools.j2ssh.SshClient;

/***********************************************************************/
public class SshConnection implements Connection {
	private Connection con;
	private SshClient ssh;

	/***********************************************************************/
	private void closeSsh() {
		ssh.disconnect();
		ssh = null;
	}

	/***********************************************************************/
	public SshConnection(SshClient ssh, Connection connection) {
		this.ssh = ssh;
		this.con = connection;
	}

	/***********************************************************************/
	public int getHoldability() throws SQLException {
		return con.getHoldability();
	}

	/***********************************************************************/
	public int getTransactionIsolation() throws SQLException {
		return con.getTransactionIsolation();
	}

	/***********************************************************************/
	public void clearWarnings() throws SQLException {
		con.clearWarnings();
	}

	/***********************************************************************/
	public void close() throws SQLException {
		if (con != null) {
			con.close();
			closeSsh();
			con = null;
		}
	}

	/***********************************************************************/
	public void commit() throws SQLException {
		con.commit();
	}

	/***********************************************************************/
	public void rollback() throws SQLException {
		con.rollback();
	}

	/***********************************************************************/
	public boolean getAutoCommit() throws SQLException {
		return con.getAutoCommit();
	}

	/***********************************************************************/
	public boolean isClosed() throws SQLException {
		return con.isClosed();
	}

	/***********************************************************************/
	public boolean isReadOnly() throws SQLException {
		return con.isReadOnly();
	}

	/***********************************************************************/
	public void setHoldability(int holdability) throws SQLException {
		con.setHoldability(holdability);
	}

	/***********************************************************************/
	public void setTransactionIsolation(int level) throws SQLException {
		con.setTransactionIsolation(level);
	}

	/***********************************************************************/
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		con.setAutoCommit(autoCommit);
	}

	/***********************************************************************/
	public void setReadOnly(boolean readOnly) throws SQLException {
		con.setReadOnly(readOnly);
	}

	/***********************************************************************/
	public String getCatalog() throws SQLException {
		return con.getCatalog();
	}

	/***********************************************************************/
	public void setCatalog(String catalog) throws SQLException {
		con.setCatalog(catalog);
	}

	/***********************************************************************/
	public DatabaseMetaData getMetaData() throws SQLException {
		return con.getMetaData();
	}

	/***********************************************************************/
	public SQLWarning getWarnings() throws SQLException {
		return con.getWarnings();
	}

	/***********************************************************************/
	public Savepoint setSavepoint() throws SQLException {
		return con.setSavepoint();
	}

	/***********************************************************************/
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		con.releaseSavepoint(savepoint);
	}

	/***********************************************************************/
	public void rollback(Savepoint savepoint) throws SQLException {
		con.rollback(savepoint);
	}

	/***********************************************************************/
	public Statement createStatement() throws SQLException {
		return con.createStatement();
	}

	/***********************************************************************/
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return con.createStatement(resultSetType, resultSetConcurrency);
	}

	/***********************************************************************/
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/***********************************************************************/
	public Map getTypeMap() throws SQLException {
		return con.getTypeMap();
	}

	/***********************************************************************/
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		con.setTypeMap(arg0);
	}

	/***********************************************************************/
	public String nativeSQL(String sql) throws SQLException {
		return con.nativeSQL(sql);
	}

	/***********************************************************************/
	public CallableStatement prepareCall(String sql) throws SQLException {
		return con.prepareCall(sql);
	}

	/***********************************************************************/
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return con.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	/***********************************************************************/
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return con.prepareStatement(sql, autoGeneratedKeys);
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return con.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return con.prepareStatement(sql, columnIndexes);
	}

	/***********************************************************************/
	public Savepoint setSavepoint(String name) throws SQLException {
		return con.setSavepoint(name);
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return con.prepareStatement(sql, columnNames);
	}

	/***********************************************************************/
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}

	/***********************************************************************/
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		return con.createArrayOf(arg0, arg1);
	}

	/***********************************************************************/
	public Blob createBlob() throws SQLException {
		return con.createBlob();
	}

	/***********************************************************************/
	public Clob createClob() throws SQLException {
		return con.createClob();
	}

	/***********************************************************************/
	public NClob createNClob() throws SQLException {
		return con.createNClob();
	}

	/***********************************************************************/
	public SQLXML createSQLXML() throws SQLException {
		return con.createSQLXML();
	}

	/***********************************************************************/
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		return con.createStruct(arg0, arg1);
	}

	/***********************************************************************/
	public Properties getClientInfo() throws SQLException {
		return con.getClientInfo();
	}

	/***********************************************************************/
	public String getClientInfo(String arg0) throws SQLException {
		return con.getClientInfo(arg0);
	}

	/***********************************************************************/
	public boolean isValid(int arg0) throws SQLException {
		return con.isValid(arg0);
	}

	/***********************************************************************/
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		con.setClientInfo(arg0);
	}

	/***********************************************************************/
	public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
		con.setClientInfo(arg0, arg1);
	}

	/***********************************************************************/
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return con.isWrapperFor(arg0);
	}

	/***********************************************************************/
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return con.unwrap(arg0);
	}

	/***********************************************************************/
	/***********************************************************************/
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
	}

	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// TODO Auto-generated method stub
	}

	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
	}
}
