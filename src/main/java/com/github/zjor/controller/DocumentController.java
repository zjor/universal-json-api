package com.github.zjor.controller;

import com.github.zjor.repository.MongoRepository;
import com.github.zjor.util.DocumentUtils;
import org.bson.Document;
import org.springframework.http.HttpStatus;
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

    public DocumentController(MongoRepository mongoRepository) {
        this.repository = mongoRepository;
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
    public Document getById(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id
    ) {
        //TODO: prefix collection name with tenant
        return repository.findById(collectionName, id)
                .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
    }

    @GetMapping("id/{id}/p/**")
    public Object getDocumentPart(
            @PathVariable("collectionName") String collectionName,
            @PathVariable("id") String id,
            HttpServletRequest req) {
        Pattern regex = Pattern.compile(id + "/p/" + "(.+)");
        Matcher m = regex.matcher(req.getRequestURI());
        if (m.find()) {
            Document document = repository.findById(collectionName, id)
                    .orElseThrow(() -> new NotFoundException(collectionName + ":" + id));
            return DocumentUtils.getDocumentPart(document, m.group(1));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
