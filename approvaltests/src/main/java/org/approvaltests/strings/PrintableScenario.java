package org.approvaltests.strings;

import org.lambda.actions.Action0WithException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        toVerify.append(printAll());
    }

    public String makeHeading(String heading, String summary, String underlineCharacter) {
        int count = heading.length();
        // create a string made up of n copies of underlineCharacter
        String underlineHeading = String.join("", Collections.nCopies(count, underlineCharacter));

        String result = heading + "\n" + underlineHeading + "\n";
        if (summary != null && !summary.isEmpty()) {
            result += summary + "\n\n";
        }
        return result;
    }

    public String printAll() {
        StringBuilder result = new StringBuilder();
        for (Printable printable : this.printables) {
            result.append(printable.toString());
            result.append("\n");
        }
        return result.toString();
    }

    public void when(String action, Action0WithException function) {
        try {
            function.call();
            when(action);
        } catch (Throwable e) {
            toVerify.append(makeHeading(action,
                    "This action threw an exception: " + e, "-"));
            toVerify.append(printAll());
        }
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

    @Override
    public String toString() {
        return print();
    }
}
