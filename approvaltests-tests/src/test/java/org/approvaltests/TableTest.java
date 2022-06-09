package org.approvaltests;

import org.approvaltests.legacycode.Range;
import org.approvaltests.utils.MarkdownTable;
import org.junit.jupiter.api.Test;

public class TableTest {
    @Test
    void abilityModifier() {
        Approvals.verify(MarkdownTable.create(Range.get(1, 20), a -> getModifier(a), "Score", "Modifier"));
    }

    private Integer getModifier(Integer ability) {
        return ability / 2 - 5;
    }
}
