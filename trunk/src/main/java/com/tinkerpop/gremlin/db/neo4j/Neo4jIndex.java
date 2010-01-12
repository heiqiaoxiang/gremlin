package com.tinkerpop.gremlin.db.neo4j;

import com.tinkerpop.gremlin.model.Element;
import com.tinkerpop.gremlin.model.Index;
import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @version 0.1
 */
public class Neo4jIndex implements Index {

    private IndexService indexService;
    public Set<String> indexKeys;
    public boolean indexAll = true;


    public Neo4jIndex(IndexService indexService) {
        this.indexService = indexService;
        this.indexKeys = new HashSet<String>();
    }

    public void put(String key, Object value, Element element) {
        if (this.indexAll || this.indexKeys.contains(key)) {
            if (element instanceof Neo4jVertex) {
                Node node = (Node) ((Neo4jVertex) element).getRawElement();
                this.indexService.index(node, key, value);
            }
        }
    }

    public Set<Element> get(String key, Object value) {
        Iterable<Node> itty = this.indexService.getNodes(key, value);
        if (null != itty) {
            Iterator<Node> itty2 = itty.iterator();
            if (itty2.hasNext()) {
                Set<Element> elements = new HashSet<Element>();
                while (itty2.hasNext()) {
                    elements.add(new Neo4jVertex(itty2.next(), this));
                }
                return elements;
            }
        }
        return null;
    }

    public void remove(String key, Object value, Element element) {
        if (element instanceof Neo4jVertex) {
            Node node = (Node) ((Neo4jVertex) element).getRawElement();
            this.indexService.removeIndex(node, key, value);
        }
    }

    public void indexAll(boolean indexAll) {
        this.indexAll = indexAll;
    }

    public void addIndexKey(String key) {
        this.indexKeys.add(key);
    }

    public void removeIndexKey(String key) {
        this.indexKeys.remove(key);
        // TODO: drop index in LuceneIndexService
    }

    protected void shutdown() {
        indexService.shutdown();
    }
}