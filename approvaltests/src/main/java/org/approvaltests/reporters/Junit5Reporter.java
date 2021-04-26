package org.approvaltests.reporters;

public class Junit5Reporter extends AbstractJUnitReporter {

    public Junit5Reporter() {
        super("org.junit.jupiter.api.Assertions");
    }
}
