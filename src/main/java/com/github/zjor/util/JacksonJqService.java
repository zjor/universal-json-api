package com.github.zjor.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thisptr.jackson.jq.BuiltinFunctionLoader;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.Scope;
import net.thisptr.jackson.jq.Versions;

import java.util.ArrayList;
import java.util.List;

public class JacksonJqService {

    private final ObjectMapper objectMapper;

    public JacksonJqService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode jq(String json, String query) {
        Scope scope = Scope.newEmptyScope();
        BuiltinFunctionLoader.getInstance().loadFunctions(Versions.JQ_1_6, scope);
        try {
            JsonQuery q = JsonQuery.compile(query, Versions.JQ_1_6);
            JsonNode in = objectMapper.readTree(json);
            final List<JsonNode> out = new ArrayList<>();
            q.apply(scope, in, out::add);
            if (out.size() == 1) {
                return out.get(0);
            } else {
                return objectMapper.createArrayNode().addAll(out);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
