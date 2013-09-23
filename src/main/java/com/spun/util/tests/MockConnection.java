/*
 * Created on Apr 5, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.tests;

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

/**
 * @author Llewellyn Falco
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class MockConnection implements Connection {
	public int autoCommitCount = 0;
	public int commitCount = 0;

	/***********************************************************************/
	public int getHoldability() throws SQLException {
		return 0;
	}

	/***********************************************************************/
	public int getTransactionIsolation() throws SQLException {
		return 0;
	}

	/***********************************************************************/
	public void clearWarnings() throws SQLException {
	}

	/***********************************************************************/
	public void close() throws SQLException {
	}

	/***********************************************************************/
	public void commit() throws SQLException {
		commitCount++;
	}

	/***********************************************************************/
	public void rollback() throws SQLException {
	}

	/***********************************************************************/
	public boolean getAutoCommit() throws SQLException {
		return false;
	}

	/***********************************************************************/
	public boolean isClosed() throws SQLException {
		return false;
	}

	/***********************************************************************/
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	/***********************************************************************/
	public void setHoldability(int arg0) throws SQLException {
	}

	/***********************************************************************/
	public void setTransactionIsolation(int arg0) throws SQLException {
	}

	/***********************************************************************/
	public void setAutoCommit(boolean arg0) throws SQLException {
		autoCommitCount++;
	}

	/***********************************************************************/
	public void setReadOnly(boolean arg0) throws SQLException {
	}

	/***********************************************************************/
	public String getCatalog() throws SQLException {
		return null;
	}

	/***********************************************************************/
	public void setCatalog(String arg0) throws SQLException {
	}

	/***********************************************************************/
	public DatabaseMetaData getMetaData() throws SQLException {
		return null;
	}

	/***********************************************************************/
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	/***********************************************************************/
	public Savepoint setSavepoint() throws SQLException {
		return null;
	}

	/***********************************************************************/
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
	}

	/***********************************************************************/
	public void rollback(Savepoint arg0) throws SQLException {
	}

	/***********************************************************************/
	public Statement createStatement() throws SQLException {
		return null;
	}

	/***********************************************************************/
	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public Map getTypeMap() throws SQLException {
		return null;
	}

	/***********************************************************************/
	public String nativeSQL(String arg0) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public CallableStatement prepareCall(String arg0) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public Savepoint setSavepoint(String arg0) throws SQLException {
		return null;
	}

	/***********************************************************************/
	public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
		return null;
	}

	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClientInfo(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		// TODO Auto-generated method stub
	}

	public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
		// TODO Auto-generated method stub
	}

	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		// TODO Auto-generated method stub
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

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