package org.approvaltests;

import com.spun.util.JsonUtils;
import com.spun.util.io.NetUtils;
import com.spun.util.persistence.ExecutableCommand;

public class PokemonLoader implements ExecutableCommand
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
  public String getCommand()
  {
    return PATH + "ability/" + ability;
  }

  /* (non-Javadoc)
   * @see org.approvaltests.tests.ExecutableQuery#executeQuery(java.lang.String)
   */
  public String executeCommand(String command)
  {
    return JsonUtils.prettyPrint(NetUtils.readWebpage(command));
  }
}
