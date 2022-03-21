package com.github.zjor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class JacksonJqServiceTest {

    private static final String JSON_FIXTURE = "{\"object\":{\"attr\":\"value\"},\"array\":[1,2,3]}";

    private ObjectMapper mapper = new ObjectMapper();
    private JacksonJqService service = new JacksonJqService(mapper);

    @Test
    public void shouldJq_sub_document() throws JsonProcessingException {
        var out = service.jq(JSON_FIXTURE, ".object");
        Assert.assertEquals("{\"attr\":\"value\"}", mapper.writeValueAsString(out));
    }

    @Test
    public void shouldJq_sub_array() throws JsonProcessingException {
        var out = service.jq(JSON_FIXTURE, ".array");
        Assert.assertEquals("[1,2,3]", mapper.writeValueAsString(out));
    }

}