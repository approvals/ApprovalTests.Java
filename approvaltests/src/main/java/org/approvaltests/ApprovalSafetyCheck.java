package org.approvaltests;

import org.approvaltests.core.Options;
import org.lambda.actions.Action1;

public class ApprovalSafetyCheck {
    public static final ThreadLocal<Action1<Options>> guardRailCheck = ThreadLocal.withInitial(Action1::doNothing);

    public static void checkGuardRails(Options options) {
        Action1<Options> check = guardRailCheck.get();
        guardRailCheck.set(Action1.doNothing());
        check.call(options);
    }

    public static void setGuardRailCheck(Action1<Options> check) {
        guardRailCheck.set(check);
    }
}