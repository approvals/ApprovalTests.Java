package org.approvaltests;

import com.spun.util.io.NetUtils;
import com.spun.util.persistence.ExecutableQuery;

public class PokemonLoader implements ExecutableQuery
{
  private static final String PATH = "https://pokeapi.co/api/v2/";
  private final int           ability;
  public PokemonLoader(int abilityNumber)
  {
    this.ability = abilityNumber;
  }
  /* (non-Javadoc)
   * @see org.approvaltests.tests.ExecutableQuery#getQuery()
   */
  public String getQuery()
  {
    return PATH + "ability/" + ability;
  }
  /* (non-Javadoc)
   * @see org.approvaltests.tests.ExecutableQuery#executeQuery(java.lang.String)
   */
  public String executeQuery(String query)
  {
    return NetUtils.readWebpage(query);
  }
}
