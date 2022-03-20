package com.github.zjor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zjor.repository.MongoRepository;
import com.github.zjor.util.DocumentUtils;
import com.github.zjor.util.JacksonJqService;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1.0/c/{collectionName}")
public class DocumentController {

    private final MongoRepository repository;
    private final JacksonJqService jqService;
    private final ObjectMapper objectMapper;

    public DocumentController(MongoRepository mongoRepository,
                              JacksonJqService jqService,
                              ObjectMapper objectMapper) {
        this.repository = mongoRepository;
        this.jqService = jqService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Object> listCollection(@PathVariable("collectionName") String collectionName) {
        //TODO: prefix collection name with tenant
        return repository.listCollection(collectionName);
    }

    @PostMapping
    public Document createDocument(
            @PathVariable("collectionName") String collectionName,
            @RequestBody Map<String, Object> req) {
        //TODO: prefix collection name with tenant
        return repository.save(collectionName, req);
    }

    @GetMapping("id/{id}")
    public Object getById(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            @RequestParam(value = "q", required = false) String jq
    ) {
        //TODO: prefix collection name with tenant
        var doc = repository.findById(collectionName, id)
                .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
        if (StringUtils.hasText(jq)) {
            try {
                return jqService.jq(objectMapper.writeValueAsString(doc), jq);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return doc;
    }

    @GetMapping("id/{id}/**")
    public Object getDocumentPart(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            HttpServletRequest req) {
        Pattern regex = Pattern.compile(id + "/" + "(.+)");
        Matcher m = regex.matcher(req.getRequestURI());
        if (m.find()) {
            //TODO: prefix collection name with tenant
            Document document = repository.findById(collectionName, id)
                    .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
            return DocumentUtils.getDocumentPart(document, m.group(1));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
