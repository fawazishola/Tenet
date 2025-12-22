package org.axiom.tenet;

import java.util.List;

interface TenetCallable {
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}
