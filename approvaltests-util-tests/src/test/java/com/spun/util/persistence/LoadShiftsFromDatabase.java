package com.spun.util.persistence;

import com.spun.util.DateUtils;
import com.spun.util.database.SQLQuery;

import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

// Loader of lists of shifts
class LoadShiftsFromDatabase implements SqlLoader<List<Shift>>
{
  private Calendar day;
  public LoadShiftsFromDatabase(Calendar day)
  {
    // Loads lots of stuff from DB
    this.day = day;
  }
  @Override
  public List<Shift> load()
  {
    return null;
  }
  @Override
  public SQLQuery getQuery()
  {
    // Get all shifts for today
    SQLQuery query = new SQLQuery();
    String shifts = query.addFrom("SHIFTS");
    query.addSelect(shifts + ".*");
    query.addWhere(
        shifts + ".FOR_DAY = '" + new java.sql.Date(DateUtils.asTimestamp(day.getTime()).getTime()) + "'");
    return query;
  }
  @Override
  public Statement getStatement()
  {
    return null;
  }
}
