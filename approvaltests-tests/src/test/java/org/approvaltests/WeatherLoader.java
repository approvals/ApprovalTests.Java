package org.approvaltests;

import com.spun.util.io.NetUtils;
import com.spun.util.persistence.ExecutableQuery;

public class WeatherLoader implements ExecutableQuery
{
  private static final String PATH = "http://api.wunderground.com/weatherstation/WXCurrentObXML.asp";
  private final String        weatherStationId;
  public WeatherLoader(String weatherStationId)
  {
    this.weatherStationId = weatherStationId;
  }
  /* (non-Javadoc)
   * @see org.approvaltests.tests.ExecutableQuery#getQuery()
   */
  public String getQuery()
  {
    return "ID=" + weatherStationId;
  }
  /* (non-Javadoc)
   * @see org.approvaltests.tests.ExecutableQuery#executeQuery(java.lang.String)
   */
  public String executeQuery(String query)
  {
    String url = "HTTP://api.wunderground.com/weatherstation/WXCurrentObXML.asp";
    String html = NetUtils.loadWebPage(url, query);
    return html;
  }
}
