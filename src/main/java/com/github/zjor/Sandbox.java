package com.github.zjor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thisptr.jackson.jq.BuiltinFunctionLoader;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.Scope;
import net.thisptr.jackson.jq.Versions;

import java.util.ArrayList;
import java.util.List;

public class Sandbox {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        Scope scope = Scope.newEmptyScope();
        BuiltinFunctionLoader.getInstance().loadFunctions(Versions.JQ_1_6, scope);
        JsonQuery q = JsonQuery.compile(".settings", Versions.JQ_1_6);
        JsonNode in = MAPPER.readTree("{\"settings\":[12, 15, 23],\"name\":\"jackson\",\"timestamp\":1418785331123}");
        final List<JsonNode> out = new ArrayList<>();
        q.apply(scope, in, out::add);
        System.out.println(MAPPER.writeValueAsString(out));
    }
}
