package com.github.zjor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zjor.ext.spring.Tenant;
import com.github.zjor.repository.TenantMongoRepository;
import com.github.zjor.util.DocumentUtils;
import com.github.zjor.util.JacksonJqService;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1.0/c/{collectionName}")
public class DocumentController {

    private final TenantMongoRepository repository;
    private final JacksonJqService jqService;
    private final ObjectMapper objectMapper;

    public DocumentController(TenantMongoRepository repository,
                              JacksonJqService jqService,
                              ObjectMapper objectMapper) {
        this.repository = repository;
        this.jqService = jqService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public Document create(
            @PathVariable("collectionName") String collectionName,
            @RequestBody Map<String, Object> req,
            @Tenant String tenant) {
        return repository.save(tenant, collectionName, req);
    }

    @GetMapping("id/{id}")
    public Object getById(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            @RequestParam(value = "jq", required = false) String jq,
            @Tenant String tenant
    ) {
        var doc = repository.findById(tenant, collectionName, id)
                .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
        return jq(doc, jq);
    }

    @PutMapping("id/{id}")
    public Object updateById(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            @RequestBody Map<String, Object> req,
            @Tenant String tenant) {
        return repository.update(tenant, collectionName, id, req);
    }

    @DeleteMapping("id/{id}")
    public Object deleteDocument(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            @Tenant String tenant) {
        return repository.deleteDocument(tenant, collectionName, id);
    }

    @GetMapping("id/{id}/**")
    public Object getDocumentPart(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            @RequestParam(value = "jq", required = false) String jq,
            HttpServletRequest req,
            @Tenant String tenant) {
        Pattern regex = Pattern.compile(id + "/" + "(.+)");
        Matcher m = regex.matcher(req.getRequestURI());
        if (m.find()) {
            Document document = repository.findById(tenant, collectionName, id)
                    .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
            return jq(DocumentUtils.getDocumentPart(document, m.group(1)), jq);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: PUT to patch part of the document
    //TODO: DELETE to remove part of the document

    private Object jq(Object json, String query) {
        if (StringUtils.hasText(query)) {
            try {
                return jqService.jq(objectMapper.writeValueAsString(json), query);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return json;
        }
    }
}
