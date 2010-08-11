package com.tinkerpop.gremlin.compiler.functions.g.lme;

import com.tinkerpop.gremlin.compiler.context.GremlinScriptContext;
import com.tinkerpop.gremlin.compiler.functions.AbstractFunction;
import com.tinkerpop.gremlin.compiler.operations.Operation;
import com.tinkerpop.gremlin.compiler.types.Atom;

import java.util.Collection;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ExceptFunction extends AbstractFunction<Boolean> {

    private static final String FUNCTION_NAME = "except";

    public Atom<Boolean> compute(final List<Operation> arguments, final GremlinScriptContext context) throws RuntimeException {
        if (arguments.size() != 1)
            throw new RuntimeException(this.createUnsupportedArgumentMessage());

        final Object object = arguments.get(0).compute().getValue();
        final Object check = context.getCurrentPoint();
        
        if (check instanceof Collection) {
            return new Atom<Boolean>(!((Collection) check).contains(object));
        } else if (check instanceof Iterable) {
            for (Object check2 : (Iterable) check) {
                if (check2.equals(object))
                    return new Atom<Boolean>(false);
            }
            return new Atom<Boolean>(true);
        } else {
            return new Atom<Boolean>(!check.equals(object));
        }
    }

    public String getFunctionName() {
        return FUNCTION_NAME;
    }
}
