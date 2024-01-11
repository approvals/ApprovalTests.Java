package org.approvaltests.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Callable;

public class PrintableScenario {


    private final StringBuilder toVerify = new StringBuilder();
    private final String name;
    private final String description;
    private final ArrayList<Printable> printables = new ArrayList<>();

    public PrintableScenario(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public PrintableScenario(String name) {
        this(name, "");
    }

    public void given(Printable... printables) {
        arrange(printables);
    }

    public void arrange(Printable... printables) {
        this.printables.addAll(Arrays.asList(printables));
        toVerify.append(makeHeading(name, description, "="));
        toVerify.append("\n");
        toVerify.append(printAll());
    }

    public String makeHeading(String heading, String summary, String underlineCharacter) {
        int count = heading.length();
        // create a string made up of n copies of string "="
        String underlineHeading = String.join("", Collections.nCopies(count, underlineCharacter));
        return heading + "\n" + underlineHeading + "\n" + summary + "\n";
    }

    public String printAll() {
        StringBuilder result = new StringBuilder();
        for (Printable printable : this.printables) {
            result.append(printable.toString());
            result.append("\n");
        }
        return result.toString();
    }

    public void when(String action, Callable function) throws Exception {
        function.call();
        when(action);
    }

    public void when(String action) {
        act(action);
    }

    public void act(String action) {
        toVerify.append(makeHeading(action, "", "-"));
        toVerify.append(printAll());
    }
    public String then() {
        return print();
    }

    public String print() {
        return toVerify.toString();
    }
}
