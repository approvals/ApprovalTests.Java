package org.approvaltests;

import com.spun.util.Wrapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class JsonApprovalsTest {
    @Test
    void nullDateTest() {
        LocalDateWrapper localDateWrapper = new LocalDateWrapper();
        JsonApprovals.verifyAsJson(localDateWrapper, g -> g.serializeNulls());
    }

    private class LocalDateWrapper {
        public LocalDateTime getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDateTime localDate) {
            this.localDate = localDate;
        }

        private LocalDateTime localDate;
    }
}
