package org.axiom.tenet;

import java.util.List;
import java.util.Map;

class TenetClass implements TenetCallable {
    final String name;
    final TenetClass superclass;
    private final Map<String, TenetFunction> methods;

    TenetClass(String name, TenetClass superclass, Map<String, TenetFunction> methods) {
        this.superclass = superclass;
        this.name = name;
        this.methods = methods;
    }

    TenetFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        TenetInstance instance = new TenetInstance(this);
        TenetFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    @Override
    public int arity() {
        TenetFunction initializer = findMethod("init");
        if (initializer == null)
            return 0;
        return initializer.arity();
    }
}
