package com.spun.util;

import java.util.HashMap;

public class StateToPostalCode
{
  /*
   * Utility that maps postal code to full state names.
   */
  public static final String             ALABAMA              = "Alabama";
  public static final String             ALASKA               = "Alaska";
  public static final String             ARIZONA              = "Arizona";
  public static final String             ARKANSAS             = "Arkansas";
  public static final String             CALIFORNIA           = "California";
  public static final String             COLORADO             = "Colorado";
  public static final String             CONNECTICUT          = "Connecticut";
  public static final String             DELAWARE             = "Delaware";
  public static final String             DISTRICT_OF_COLUMBIA = "District Of Columbia";
  public static final String             FLORIDA              = "Florida";
  public static final String             GEORGIA              = "Georgia";
  public static final String             HAWAII               = "Hawaii";
  public static final String             IDAHO                = "Idaho";
  public static final String             ILLINOIS             = "Illinois";
  public static final String             INDIANA              = "Indiana";
  public static final String             IOWA                 = "Iowa";
  public static final String             KANSAS               = "Kansas";
  public static final String             KENTUCKY             = "Kentucky";
  public static final String             LOUISIANA            = "Louisiana";
  public static final String             MAINE                = "Maine";
  public static final String             MARYLAND             = "Maryland";
  public static final String             MASSACHUSETTS        = "Massachusetts";
  public static final String             MICHIGAN             = "Michigan";
  public static final String             MINNESOTA            = "Minnesota";
  public static final String             MISSISSIPPI          = "Mississippi";
  public static final String             MISSOURI             = "Missouri";
  public static final String             MONTANA              = "Montana";
  public static final String             NEBRASKA             = "Nebraska";
  public static final String             NEVADA               = "Nevada";
  public static final String             NEW_HAMPSHIRE        = "New Hampshire";
  public static final String             NEW_JERSEY           = "New Jersey";
  public static final String             NEW_MEXICO           = "New Mexico";
  public static final String             NEW_YORK             = "New York";
  public static final String             NORTH_CAROLINA       = "North Carolina";
  public static final String             NORTH_DAKOTA         = "North Dakota";
  public static final String             OHIO                 = "Ohio";
  public static final String             OKLAHOMA             = "Oklahoma";
  public static final String             OREGON               = "Oregon";
  public static final String             PENNSYLVANIA         = "Pennsylvania";
  public static final String             RHODE_ISLAND         = "Rhode Island";
  public static final String             SOUTH_CAROLINA       = "South Carolina";
  public static final String             SOUTH_DAKOTA         = "South Dakota";
  public static final String             TENNESSEE            = "Tennessee";
  public static final String             TEXAS                = "Texas";
  public static final String             UTAH                 = "Utah";
  public static final String             VERMONT              = "Vermont";
  public static final String             VIRGINIA             = "Virginia";
  public static final String             WASHINGTON           = "Washington";
  public static final String             WEST_VIRGINIA        = "West Virginia";
  public static final String             WISCONSIN            = "Wisconsin";
  public static final String             WYOMING              = "Wyoming";
  private static HashMap<String, String> map                  = new HashMap<String, String>();
  private static HashMap<String, String> map2                 = new HashMap<String, String>();
  static
  {
    map.put(ALABAMA, "AL");
    map.put(ALASKA, "AK");
    map.put(ARIZONA, "AZ");
    map.put(ARKANSAS, "AR");
    map.put(CALIFORNIA, "CA");
    map.put(COLORADO, "CO");
    map.put(CONNECTICUT, "CT");
    map.put(DELAWARE, "DE");
    map.put(DISTRICT_OF_COLUMBIA, "DC");
    map.put(FLORIDA, "FL");
    map.put(GEORGIA, "GA");
    map.put(HAWAII, "HI");
    map.put(IDAHO, "ID");
    map.put(ILLINOIS, "IL");
    map.put(INDIANA, "IN");
    map.put(IOWA, "IA");
    map.put(KANSAS, "KS");
    map.put(KENTUCKY, "KT");
    map.put(LOUISIANA, "LA");
    map.put(MAINE, "ME");
    map.put(MARYLAND, "MD");
    map.put(MASSACHUSETTS, "MA");
    map.put(MICHIGAN, "MI");
    map.put(MINNESOTA, "MN");
    map.put(MISSISSIPPI, "MS");
    map.put(MISSOURI, "MO");
    map.put(MONTANA, "MT");
    map.put(NEBRASKA, "NE");
    map.put(NEVADA, "NV");
    map.put(NEW_HAMPSHIRE, "NH");
    map.put(NEW_JERSEY, "NJ");
    map.put(NEW_MEXICO, "NM");
    map.put(NEW_YORK, "NY");
    map.put(NORTH_CAROLINA, "NC");
    map.put(NORTH_DAKOTA, "ND");
    map.put(OHIO, "OH");
    map.put(OKLAHOMA, "OK");
    map.put(OREGON, "OR");
    map.put(PENNSYLVANIA, "PA");
    map.put(RHODE_ISLAND, "RI");
    map.put(SOUTH_CAROLINA, "SC");
    map.put(SOUTH_DAKOTA, "SD");
    map.put(TENNESSEE, "TN");
    map.put(TEXAS, "TX");
    map.put(UTAH, "UT");
    map.put(VERMONT, "VT");
    map.put(VIRGINIA, "VA");
    map.put(WASHINGTON, "WA");
    map.put(WEST_VIRGINIA, "WV");
    map.put(WISCONSIN, "WI");
    map.put(WYOMING, "WY");
    map2.put("AL", ALABAMA);
    map2.put("AK", ALASKA);
    map2.put("AZ", ARIZONA);
    map2.put("AR", ARKANSAS);
    map2.put("CA", CALIFORNIA);
    map2.put("CO", COLORADO);
    map2.put("CT", CONNECTICUT);
    map2.put("DE", DELAWARE);
    map2.put("DC", DISTRICT_OF_COLUMBIA);
    map2.put("FL", FLORIDA);
    map2.put("GA", GEORGIA);
    map2.put("HI", HAWAII);
    map2.put("ID", IDAHO);
    map2.put("IL", ILLINOIS);
    map2.put("IN", INDIANA);
    map2.put("IA", IOWA);
    map2.put("KS", KANSAS);
    map2.put("KT", KENTUCKY);
    map2.put("LA", LOUISIANA);
    map2.put("ME", MAINE);
    map2.put("MD", MARYLAND);
    map2.put("MA", MASSACHUSETTS);
    map2.put("MI", MICHIGAN);
    map2.put("MN", MINNESOTA);
    map2.put("MS", MISSISSIPPI);
    map2.put("MO", MISSOURI);
    map2.put("MT", MONTANA);
    map2.put("NE", NEBRASKA);
    map2.put("NV", NEVADA);
    map2.put("NH", NEW_HAMPSHIRE);
    map2.put("NJ", NEW_JERSEY);
    map2.put("NM", NEW_MEXICO);
    map2.put("NY", NEW_YORK);
    map2.put("NC", NORTH_CAROLINA);
    map2.put("ND", NORTH_DAKOTA);
    map2.put("OH", OHIO);
    map2.put("OK", OKLAHOMA);
    map2.put("OR", OREGON);
    map2.put("PA", PENNSYLVANIA);
    map2.put("RI", RHODE_ISLAND);
    map2.put("SC", SOUTH_CAROLINA);
    map2.put("SD", SOUTH_DAKOTA);
    map2.put("TN", TENNESSEE);
    map2.put("TX", TEXAS);
    map2.put("UT", UTAH);
    map2.put("VT", VERMONT);
    map2.put("VA", VIRGINIA);
    map2.put("WA", WASHINGTON);
    map2.put("WV", WEST_VIRGINIA);
    map2.put("WI", WISCONSIN);
    map2.put("WY", WYOMING);
  }
  public static String getPostalCodeByStateName(String state)
  {
    return (String) map.get(state);
  }

  public static String getStateNameByPostalCode(String code)
  {
    return (String) map2.get(code);
  }
}
