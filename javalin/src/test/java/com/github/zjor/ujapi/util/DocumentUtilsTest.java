package com.github.zjor.ujapi.util;

import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
}