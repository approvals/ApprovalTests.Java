package com.spun.util;

/**
	* Phone number validation, and formatter.
	* this class is immutable.
	**/
public class PhoneNumber
{
  public static final int      USA                 = 0;
  public static final String[] COUNTRY_CODES       = {"1",
                                                      "20",
                                                      "212",
                                                      "213",
                                                      "216",
                                                      "218",
                                                      "220",
                                                      "221",
                                                      "222",
                                                      "223",
                                                      "224",
                                                      "225",
                                                      "226",
                                                      "227",
                                                      "228",
                                                      "229",
                                                      "230",
                                                      "231",
                                                      "232",
                                                      "233",
                                                      "234",
                                                      "235",
                                                      "236",
                                                      "237",
                                                      "238",
                                                      "239",
                                                      "240",
                                                      "241",
                                                      "242",
                                                      "243",
                                                      "244",
                                                      "245",
                                                      "246",
                                                      "247",
                                                      "248",
                                                      "249",
                                                      "250",
                                                      "251",
                                                      "252",
                                                      "253",
                                                      "254",
                                                      "255",
                                                      "256",
                                                      "257",
                                                      "258",
                                                      "260",
                                                      "261",
                                                      "262",
                                                      "263",
                                                      "264",
                                                      "265",
                                                      "266",
                                                      "267",
                                                      "268",
                                                      "269",
                                                      "27",
                                                      "290",
                                                      "291",
                                                      "297",
                                                      "298",
                                                      "299",
                                                      "30",
                                                      "31",
                                                      "32",
                                                      "33",
                                                      "34",
                                                      "350",
                                                      "351",
                                                      "352",
                                                      "353",
                                                      "354",
                                                      "355",
                                                      "356",
                                                      "357",
                                                      "358",
                                                      "359",
                                                      "36",
                                                      "370",
                                                      "371",
                                                      "372",
                                                      "373",
                                                      "374",
                                                      "375",
                                                      "376",
                                                      "377",
                                                      "378",
                                                      "380",
                                                      "381",
                                                      "385",
                                                      "386",
                                                      "387",
                                                      "389",
                                                      "39",
                                                      "40",
                                                      "41",
                                                      "420",
                                                      "421",
                                                      "423",
                                                      "43",
                                                      "44",
                                                      "45",
                                                      "46",
                                                      "47",
                                                      "48",
                                                      "49",
                                                      "500",
                                                      "501",
                                                      "502",
                                                      "503",
                                                      "504",
                                                      "505",
                                                      "506",
                                                      "507",
                                                      "508",
                                                      "509",
                                                      "51",
                                                      "52",
                                                      "53",
                                                      "5399",
                                                      "54",
                                                      "55",
                                                      "56",
                                                      "57",
                                                      "58",
                                                      "590",
                                                      "591",
                                                      "592",
                                                      "593",
                                                      "594",
                                                      "595",
                                                      "596",
                                                      "597",
                                                      "598",
                                                      "599",
                                                      "60",
                                                      "61",
                                                      "618",
                                                      "62",
                                                      "63",
                                                      "64",
                                                      "65",
                                                      "66",
                                                      "670",
                                                      "672",
                                                      "673",
                                                      "674",
                                                      "675",
                                                      "676",
                                                      "677",
                                                      "678",
                                                      "679",
                                                      "680",
                                                      "681",
                                                      "682",
                                                      "683",
                                                      "684",
                                                      "685",
                                                      "686",
                                                      "687",
                                                      "688",
                                                      "689",
                                                      "690",
                                                      "691",
                                                      "692",
                                                      "7",
                                                      "808",
                                                      "81",
                                                      "82",
                                                      "84",
                                                      "850",
                                                      "852",
                                                      "853",
                                                      "855",
                                                      "856",
                                                      "86",
                                                      "870",
                                                      "871",
                                                      "872",
                                                      "873",
                                                      "874",
                                                      "878",
                                                      "880",
                                                      "881",
                                                      "8816",
                                                      "8817",
                                                      "88213",
                                                      "88216",
                                                      "886",
                                                      "90",
                                                      "91",
                                                      "92",
                                                      "93",
                                                      "94",
                                                      "95",
                                                      "960",
                                                      "961",
                                                      "962",
                                                      "963",
                                                      "964",
                                                      "965",
                                                      "966",
                                                      "967",
                                                      "968",
                                                      "970",
                                                      "971",
                                                      "972",
                                                      "973",
                                                      "974",
                                                      "975",
                                                      "976",
                                                      "977",
                                                      "98",
                                                      "992",
                                                      "993",
                                                      "994",
                                                      "995",
                                                      "996",
                                                      "998"};
  private static final String[] REASONS = {"Phone Number Too Long or Too Short",
                                                      "US Number must be length 10",
                                                      "Unknown Country Code"};
  /** The original value. */
  private String               originalValue       = null;
  private int                  countryCode         = USA;
  private String               strippedValue       = null;
  private String               invalidReason       = null;
  private int                  nonValidCountryCode = 0;
  public PhoneNumber(String originalValue)
  {
    originalValue = StringUtils.loadNullableString(originalValue);
    this.originalValue = originalValue;
    if (this.originalValue == null)
    { return; }
    this.strippedValue = stripPhoneNumber(this.originalValue);
    this.countryCode = getCountryCode(strippedValue);
    this.invalidReason = validate(countryCode, strippedValue);
    if (invalidReason != null)
    {
      nonValidCountryCode = countryCode;
      countryCode = -1;
    }
  }
  private static String stripPhoneNumber(String number)
  {
    if (number == null)
    { return null; }
    boolean xAppended = false;
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < number.length(); i++)
    {
      char c = number.charAt(i);
      switch (c)
      {
        case '0' :
        case '1' :
        case '2' :
        case '3' :
        case '4' :
        case '5' :
        case '6' :
        case '7' :
        case '8' :
        case '9' :
          result.append(c);
          break;
        case '+' :
          if (result.length() == 0)
          {
            result.append(c);
          }
          break;
        case 'X' :
        case 'x' : {
          if (!xAppended)
          {
            result.append('x');
            xAppended = true;
          }
          break;
        }
        default :
          break;
      }
    }
    char c = result.length() == 0 ? ' ' : result.charAt(0);
    switch (c)
    {
      case '+' :
        break; // do nothing
      case '1' :
        result.insert(0, "+");
        break; // insert a 1
      default :
        result.insert(0, "+1"); // if neither insert a +1
    }
    return result.toString();
  }
  private static int getCountryCode(String strippedNumber)
  {
    String alt = strippedNumber.substring(1);
    for (int i = 0; i < COUNTRY_CODES.length; i++)
    {
      String code = COUNTRY_CODES[i];
      if (alt.startsWith(code))
      { return i; }
    }
    return -1;
  }
  private static String validate(int countryCode, String stripedNumber)
  {
    return (countryCode == USA)
        ? validateNorthAmerican(countryCode, stripedNumber)
        : validateInternational(countryCode, stripedNumber);
  }
  private static String validateInternational(int countryCode, String strippedNumber)
  {
    if (countryCode == -1)
      return "Invalid country code";
    String body = extractPhoneBody(countryCode, strippedNumber);
    String prefix = COUNTRY_CODES[countryCode];
    int nl = prefix.length() + body.length();
    return (nl > 15) || (nl < 9) ? REASONS[0] : null;
  }
  private static String validateNorthAmerican(int countryCode, String strippedNumber)
  {
    if (countryCode != USA)
      return "Invalid country code";
    String body = extractPhoneBody(countryCode, strippedNumber);
    return (body.length() != 10) ? REASONS[0] : null;
  }
  private static String extractPhoneBody(int countryCode, String strippedNumber)
  {
    if (countryCode == -1)
      throw new IllegalArgumentException("Invalid country code");
    String prefix = "+" + COUNTRY_CODES[countryCode];
    String body = strippedNumber.substring(prefix.length());
    int indexOfExt = body.indexOf('x');
    if (indexOfExt > -1)
    {
      body = body.substring(0, indexOfExt);
    }
    return body;
  }
  public String getValue()
  {
    if (isValid() && strippedValue != null)
    {
      return (isNorthAmericanNumber()) ? getValueAsNorthAmerican() : getValueAsInternational();
    }
    else
    {
      return originalValue;
    }
  }
  public String getValueAsUps()
  {
    return getValueAsUps(countryCode, this.strippedValue);
  }
  private static String getValueAsUps(int countryCode, String strippedNumber)
  {
    if (strippedNumber == null)
    { return null; }
    if (countryCode < 0)
    { throw new IllegalArgumentException("# '" + strippedNumber + "' is not an international number"); }
    String body = extractPhoneBody(countryCode, strippedNumber);
    String prefix = COUNTRY_CODES[countryCode];
    if (countryCode == USA)
    {
      return body;
    }
    else
    {
      return prefix + body;
    }
  }
  private static String getValueAsNorthAmerican(int countryCode, String stripped)
  {
    if (countryCode != USA)
      throw new IllegalArgumentException("# '" + stripped + "' is not a NorthAmerican number");
    StringBuffer number = new StringBuffer(stripped.substring(2));
    number.insert(6, "-");
    number.insert(3, ")");
    number.insert(0, "(");
    return number.toString();
  }
  private static String getValueAsInternational(int countryCode, String stripped)
  {
    if (stripped == null)
    { return null; }
    if (countryCode < USA)
    { throw new IllegalArgumentException("# '" + stripped + "' is not an international number"); }
    StringBuffer number = new StringBuffer(stripped);
    int intlLength = COUNTRY_CODES[countryCode].length() + 1;
    int bodyLength = extractPhoneBody(countryCode, stripped).length();
    if (bodyLength > 6)
    {
      number.insert(intlLength + 6, ".");
    }
    if (bodyLength > 3)
    {
      number.insert(intlLength + 3, ".");
    }
    number.insert(intlLength, ".");
    return number.toString();
  }
  public String getValueAsNorthAmerican()
  {
    return getValueAsNorthAmerican(this.countryCode, this.strippedValue);
  }
  public String getValueAsInternational()
  {
    return getValueAsInternational(this.countryCode, this.strippedValue);
  }
  public String getPartiallyFormattedAsInternational()
  {
    return getValueAsInternational(this.nonValidCountryCode, this.strippedValue);
  }
  public boolean isValid()
  {
    return (this.invalidReason == null);
  }
  public String getInvalidReason()
  {
    return this.invalidReason;
  }
  public boolean isNorthAmericanNumber()
  {
    return this.countryCode == USA;
  }
  public String getOriginalText()
  {
    return originalValue;
  }
  public String toString()
  {
    return getValue();
  }
  public Country getCountry()
  {
    return Country.UnitedStates;
  }
}
