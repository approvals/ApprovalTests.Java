package com.spun.util.database.automaticsetter;

import java.sql.SQLException;
import java.sql.Statement;

import com.spun.util.MySystem;
import com.spun.util.database.AutomaticVariableSetter;
import com.spun.util.database.DatabaseObject;

public class EndDateVariableSetter
  implements AutomaticVariableSetter
{
  public static final EndDateVariableSetter INSTANCE = new EndDateVariableSetter();
  private EndDateVariableSetter () {}
  /***********************************************************************/
  public synchronized void setFor(DatabaseObject forObject, int atStage, Statement stmt) throws SQLException
  {
    // Grab a lock
    if (atStage == AutomaticVariableSetter.INSERT)
    {
      String endDateSQL = "UPDATE " + forObject.getMetadata().getTableName() +  " " + 
                          "SET end_date = '" + ((AddDateAware)forObject).getAddDate() + "' " +
                          "WHERE end_date IS NULL " +
                          "  AND " + ((EndDateAware)forObject).getEffectivityKey();
      MySystem.query("END DATE", endDateSQL);
     	int rowsChanged = stmt.executeUpdate(endDateSQL);
  		if (rowsChanged > 1)
  		{
  		  	MySystem.query("endDateSQL", endDateSQL);
  		  	throw new Error("item_makeup:setEndDate set " + rowsChanged + " rows");
  		}
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}