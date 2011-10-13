package com.tinkerpop.gremlin.groovy.transform

import com.tinkerpop.blueprints.pgm.Graph
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.Gremlin
import com.tinkerpop.gremlin.test.UtilitiesTest

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
class MapStepTest extends com.tinkerpop.gremlin.test.transform.MapStepTest {

    Graph g = TinkerGraphFactory.createTinkerGraph();
    static {
        Gremlin.load();
    }

    public void testCompliance() {
        UtilitiesTest.testCompliance(this.getClass());
    }

    public void test_g_v1_map() {
        super.test_g_v1_map(g.v(1)._.map)
    }

    public void test_g_v1_outXknowsX_map() {
        super.test_g_v1_outXknowsX_map(g.v(1).out('knows').map);
    }
}
