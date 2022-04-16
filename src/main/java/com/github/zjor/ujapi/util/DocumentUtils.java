package com.github.zjor.ujapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentUtils {

    public static Optional<JsonNode> getDocumentPart(Document document, String pathQuery) {
        List<String> parts = Arrays.asList(pathQuery.split("/"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readValue(document.toJson(), JsonNode.class);
            Iterator<String> it = parts.iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (json != null && json.has(key)) {
                    json = json.get(key);
                } else {
                    return Optional.empty();
                }
            }
            return Optional.ofNullable(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document updateDocumentPart(Document document, String path, Object part) {
        Map<String, Object> node = new HashMap<>();
        final var root = node;

        var pathParts = Arrays.asList(path.split("/"));
        if (pathParts.isEmpty()) {
            return document;
        }

        for (String key : pathParts.subList(0, pathParts.size() - 1)) {
            if (key != null && key.length() > 0) {
                var next = new HashMap<String, Object>();
                node.put(key, next);
                node = next;
            }
        }

        var last = pathParts.get(pathParts.size() - 1);
        node.put(last, part);

        document.putAll(root);
        return document;
    }

    public static Document deleteDocumentPart(Document document, String path) {
        var pathParts = Arrays.asList(path.split("/"));
        if (pathParts.isEmpty()) {
            return document;
        }

        Map<String, Object> node = document;
        for (var pathPart : pathParts.subList(0, pathParts.size() - 1)) {
            if (node.containsKey(pathPart) && node.get(pathPart) instanceof Map) {
                node = (Map<String, Object>) node.get(pathPart);
            } else {
                return document;
            }
        }
        var last = pathParts.get(pathParts.size() - 1);
        node.remove(last);
        return document;
    }

    public static Map<String, Object> asMap(Document document, String... excludeKeys) {
        Set<String> excludeKeySet = new HashSet<>(Arrays.asList(excludeKeys));
        return document.entrySet().stream()
                .filter(entry -> !excludeKeySet.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue()
                ));
    }

}
