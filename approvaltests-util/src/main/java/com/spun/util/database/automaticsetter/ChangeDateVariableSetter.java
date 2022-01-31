package com.spun.util.database.automaticsetter;

import com.spun.util.database.AutomaticVariableSetter;
import com.spun.util.database.DatabaseObject;

import java.sql.Statement;
import java.sql.Timestamp;

public class ChangeDateVariableSetter implements AutomaticVariableSetter
{
  public static final ChangeDateVariableSetter INSTANCE = new ChangeDateVariableSetter();
  private ChangeDateVariableSetter()
  {
  }
  public void setFor(DatabaseObject forObject, int atStage, Statement stmt)
  {
    if (atStage == AutomaticVariableSetter.UPDATE)
    {
      ((ChangeDateAware) forObject).setChangeDate(new Timestamp(System.currentTimeMillis()));
    }
    else if (atStage == AutomaticVariableSetter.INSERT)
    {
      if (forObject instanceof AddDateAware && ((AddDateAware) forObject).getAddDate() != null)
      {
        ((ChangeDateAware) forObject).setChangeDate(((AddDateAware) forObject).getAddDate());
      }
      else
      {
        ((ChangeDateAware) forObject).setChangeDate(new Timestamp(System.currentTimeMillis()));
      }
    }
  }
}
