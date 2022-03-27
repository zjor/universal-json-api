package com.github.zjor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DocumentUtils {

    public static Object getDocumentPart(Document document, String pathQuery) {
        List<String> parts = Arrays.asList(pathQuery.split("/"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object json = mapper.readValue(document.toJson(), Object.class);
            Iterator<String> it = parts.iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (json != null && json instanceof Map && ((Map<?, ?>) json).containsKey(key)) {
                    json = ((Map<?, ?>) json).get(key);
                } else {
                    return null;
                }
            }
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
