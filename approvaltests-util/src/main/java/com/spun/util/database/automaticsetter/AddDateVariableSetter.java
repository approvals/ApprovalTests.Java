package com.spun.util.database.automaticsetter;

import com.spun.util.database.AutomaticVariableSetter;
import com.spun.util.database.DatabaseObject;

import java.sql.Statement;
import java.sql.Timestamp;

public class AddDateVariableSetter implements AutomaticVariableSetter
{
  public static final AddDateVariableSetter INSTANCE = new AddDateVariableSetter();
  private AddDateVariableSetter()
  {
  }
  public void setFor(DatabaseObject forObject, int atStage, Statement stmt)
  {
    AddDateAware addDate = (AddDateAware) forObject;
    if (atStage == AutomaticVariableSetter.INSERT && addDate.getAddDate() == null)
    {
      if (forObject instanceof ChangeDateAware && ((ChangeDateAware) forObject).getChangeDate() != null)
      {
        ((AddDateAware) forObject).setAddDate(((ChangeDateAware) forObject).getChangeDate());
      }
      else
      {
        ((AddDateAware) forObject).setAddDate(new Timestamp(System.currentTimeMillis()));
      }
    }
  }
}
