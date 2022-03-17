package com.spun.util.database;

import java.sql.Statement;

public interface AutomaticVariableSetter
{
  public static final int      INSERT           = 0;
  public static final int      INSERT_COMPLETED = 1;
  public static final int      UPDATE           = 2;
  public static final int      UPDATE_COMPLETED = 3;
  public static final int      LOAD             = 4;
  public static final String[] STAGES           = {"insert",
                                                   "insertCompleted",
                                                   "update",
                                                   "updateCompleted",
                                                   "load"};
  public void setFor(DatabaseObject forObject, int atStage, Statement stmt);
}
