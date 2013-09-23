package com.spun.util;

public enum State {
  Alabama("AL"), Alaska("AK"), Arizona("AZ"), Arkansas("AR"), California("CA"), Colorado("CO"), Connecticut("CT"),
  Delaware("DE"), DistrictOfColumbia("Washington D.C.", "DC"), Florida("FL"), Georgia("GA"), Hawaii("HI"),
  Idaho("ID"), Illinois("IL"), Indiana("IN"), Iowa("IA"), Kansas("KS"), Kentucky("KY"), Louisiana("LA"),
  Maine("ME"), Maryland("MD"), Massachusetts("MA"), Michigan("MI"), Minnesota("MN"), Mississippi("MS"),
  Missouri("MO"), Montana("MT"), Nebraska("NE"), Nevada("NV"), NewHampshire("New Hampshire", "NH"),
  NewJersey("New Jersey", "NJ"), NewMexico("New Mexico", "NM"), NewYork("New York", "NY"),
  NorthCarolina("North Carolina", "NC"), NorthDakota("North Dakota", "ND"), Ohio("OH"), Oklahoma("OK"),
  Oregon("OR"), Pennsylvania("PA"), RhodeIsland("Rhode Island", "RI"), SouthCarolina("South Carolina", "SC"),
  SouthDakota("South Dakota", "SD"), Tennessee("TN"), Texas("TX"), Utah("UT"), Vermont("VT"), Virginia("VA"),
  Washington("WA"), WestVirginia("West Virginia", "WV"), Wisconsin("WI"), Wyoming("WY");
  String        altText      = null;
  public String abbreviation = null;
  /************************************************************************/
  private State(String abbreviation)
  {
    this.abbreviation = abbreviation;
  }
  /************************************************************************/
  private State(String altText, String abbreviation)
  {
    this.altText = altText;
    this.abbreviation = abbreviation;
  }
  /************************************************************************/
  @Override
  public String toString()
  {
    return altText == null ? super.toString() : altText;
  }
  /************************************************************************/
  public static String toStandardText(String state)
  {
    // needs to return the corresponding abbreviation or, if not found, return what was passed in
    for (State s : State.values())
    {
      if (s.toString().equalsIgnoreCase(state) || s.abbreviation.equalsIgnoreCase(state)) { return s.abbreviation; }
    }
    return state;
  }
  /************************************************************************/
  public static String[] getStateAbbreviations()
  {
    State[] allStates = State.values();
    String[] abbreviations = new String[allStates.length];
    for (int i = 0; i < allStates.length; i++)
    {
      abbreviations[i] = allStates[i].abbreviation;
    }
    return abbreviations;
  }
  /************************************************************************/
  public static boolean isStateAbbreviation(String text)
  {
    for (State s : values())
    {
      if (s.getAbbreviation().equalsIgnoreCase(text)) { return true; }
    }
    return false;
  }
  /************************************************************************/
  private static String[] names = null;
  /************************************************************************/
  public static synchronized String[] getStringValues()
  {
    if (names == null)
    {
      names = (String[]) ObjectUtils.extractArray(State.values(), "toString");
    }
    return names;
  }
  /************************************************************************/
  public String getAbbreviation()
  {
    return abbreviation;
  }
  /************************************************************************/
  /************************************************************************/
}