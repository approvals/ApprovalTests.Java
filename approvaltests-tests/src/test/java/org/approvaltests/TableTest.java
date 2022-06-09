package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.core.Verifiable;
import org.approvaltests.core.VerifyParameters;
import org.approvaltests.legacycode.Range;
import org.approvaltests.strings.MarkdownCompatible;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;

public class TableTest {
    @Test
    void abilityModifier() {
        Approvals.verify(Table.create(Range.get(1, 20), a -> getModifier(a), "Score", "Modifier"));
    }

    private Integer getModifier(Integer ability) {
        return ability / 2 - 5;
    }


    private static class Table implements Verifiable, MarkdownCompatible {
        private String markdown;

        public static <I, O> Table create(I[] inputs, Function1<I, O> o, String column1, String column2) {
            Table table = new Table();
            table.markdown = String.format("| %s | %s |\n", column1, column2);
            table.markdown += "| --- | --- |\n";
            for (I input : inputs) {
                table.markdown += String.format("| %s | %s |\n", input, o.call(input));
            }
            return table;
        }

        @Override
        public VerifyParameters getVerifyParameters(Options options) {
            return new VerifyParameters(options.forFile().withExtension(".md"));
        }

        @Override
        public String toString() {
            return toMarkdown();
        }

        @Override
        public String toMarkdown() {
            return markdown;
        }
    }
}
