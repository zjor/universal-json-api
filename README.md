# Universal JSON API

## Demo

[demo](https://universal-json-api.herokuapp.com/)

## RoadMap

1. Try https://avaje.io/http/ for annotation processing & javalin
2. ~~Full support for CRUD operations on documents as a whole~~
3. ~~Full support for CRUD operations on parts of documents selected by path~~
4. Log queries with AOP
5. Integration tests
6. Support for queries on collections
7. Support for multi-tenancy (manual configuration)
8. Schema support for collections
9. Mock data generation for the schema
10. IDP integration
11. Inter-service request authentication (for on-premise microservice architecture) => article
12. Demo app on freeCodeCamp
13. Articles @medium, @habr

## API Usage Examples

### Create document

```bash
curl -v -X POST http://localhost:8080/api/v1.0/c/test -d '{"message": "hello world"}' -H "Content-Type: application/json"
```

### Delete document

```bash
curl -v -X DELETE http://localhost:8080/api/v1.0/c/test/id/6238757633da222eadc7440c -H "Content-Type: application/json"
```

### List collection

```bash
curl -v -X GET http://localhost:8080/api/v1.0/c/test -H "Content-Type: application/json"
```


### Delete collection

```bash
curl -v -X DELETE http://localhost:8080/api/v1.0/c/test -H "Content-Type: application/json"
```
