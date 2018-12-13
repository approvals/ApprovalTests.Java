package org.approvaltests.reporters;

import org.junit.jupiter.api.Assertions;

public class Junit5Reporter extends GenericJUnitReporter
{
    public static final Junit5Reporter INSTANCE = new Junit5Reporter();
    @Override
    void assertEquals(String aText, String rText)
    {
        Assertions.assertEquals(aText, rText);
    }

    @Override
    String getClassNameToTestJunitVersion()
    {
        return "org.junit.jupiter.api.Test";
    }
}
