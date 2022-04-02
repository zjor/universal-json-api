package com.github.zjor.util;


import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DocumentUtilsTest {

    @Test
    public void shouldGetDocumentPart() {
        Map<String, Object> map = new HashMap<>();
        map.put("root", Collections.singletonMap("child1", Collections.singletonMap("child2", "value")));
        Document doc = new Document(map);
        Object part = DocumentUtils.getDocumentPart(doc, "root/child1/child2");
        Assert.assertEquals("value", part);
    }

    @Test
    public void shouldGetDocumentPart_return_null_if_not_found() {
        Map<String, Object> map = new HashMap<>();
        map.put("root", Collections.singletonMap("child1", Collections.singletonMap("child2", "value")));
        Document doc = new Document(map);
        Object part = DocumentUtils.getDocumentPart(doc, "root/nothing/child2");
        Assert.assertNull(part);
    }

    @Test
    public void shouldGetDocumentPart_return_null_if_path_too_long() {
        Map<String, Object> map = new HashMap<>();
        map.put("root", Collections.singletonMap("child1", Collections.singletonMap("child2", "value")));
        Document doc = new Document(map);
        Object part = DocumentUtils.getDocumentPart(doc, "root/child1/child2/child3");
        Assert.assertNull(part);
    }

    @Test
    public void shouldGetDocumentPart_return_collection() {
        Map<String, Object> map = new HashMap<>();
        map.put("root", Collections.singletonMap("child1", Collections.singletonMap("child2", Arrays.asList("one", "two", "three"))));
        Document doc = new Document(map);
        Object part = DocumentUtils.getDocumentPart(doc, "root/child1/child2");
        Assert.assertEquals(Arrays.asList("one", "two", "three"), part);
    }

    @Test
    public void shouldUpdateDocumentPart_override_existing_node_with_map() {
        var doc = new Document(Map.of("name", "Mike", "job", "n/a"));
        var updated = DocumentUtils.updateDocumentPart(doc, "job",
                Map.of("title", "engineer", "company", "acme"));
        Assert.assertEquals("engineer", ((Map<String, Object>)updated.get("job")).get("title"));
    }

    @Test
    public void shouldUpdateDocumentPart_override_existing_node_with_list() {
        var doc = new Document(Map.of("name", "Mike", "job", "n/a"));
        var updated = DocumentUtils.updateDocumentPart(doc, "job",
                List.of("manager", "engineer"));
        Assert.assertEquals("engineer", ((List<String>)updated.get("job")).get(1));
    }

}