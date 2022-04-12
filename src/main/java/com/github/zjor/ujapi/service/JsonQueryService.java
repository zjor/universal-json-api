package com.github.zjor.ujapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.Scope;
import net.thisptr.jackson.jq.Versions;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JsonQueryService {

    private final ObjectMapper mapper;

    public JsonQueryService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<JsonNode> query(Document source, String query) {
        try {
            JsonQuery compiledQuery = JsonQuery.compile(query, Versions.JQ_1_6);
            List<JsonNode> out = new LinkedList<>();

            JsonNode json = mapper.readValue(source.toJson(), JsonNode.class);

            compiledQuery.apply(Scope.newEmptyScope(), json, out::add);

            if (out.isEmpty()) {
                return Optional.empty();
            } else if (out.size() == 1) {
                JsonNode node = out.get(0);
                if (node instanceof NullNode) {
                    return Optional.empty();
                } else {
                    return Optional.of(out.get(0));
                }
            } else {
                return Optional.of(new ArrayNode(JsonNodeFactory.instance, out));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
