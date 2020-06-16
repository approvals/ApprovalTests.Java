package org.approvaltests;

import org.junit.jupiter.api.Test;

public class PokemonTest
{
  @Test
  public void testAbility()
  {
    Approvals.verify(new PokemonLoader(5));
  }
}
