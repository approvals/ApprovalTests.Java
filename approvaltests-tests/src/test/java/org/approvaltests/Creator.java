package org.approvaltests;

import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public  interface Creator<T> extends Function1<Queryable<String>, T> {
}

