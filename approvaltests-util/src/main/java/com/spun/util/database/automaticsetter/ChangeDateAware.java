package com.spun.util.database.automaticsetter;

import java.sql.Timestamp;

public interface ChangeDateAware
{
  public boolean setChangeDate(Timestamp time);

  public Timestamp getChangeDate();
}