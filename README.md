# Universal JSON API

## Demo

[demo](https://universal-json-api.herokuapp.com/)

## RoadMap

1. ~~Full support for CRUD operations on documents as a whole~~
2. Full support for CRUD operations on parts of documents selected by path
3. Support for queries on collections
4. Support for multi-tenancy (manual configuration)
5. Schema support for collections
6. Mock data generation for the schema
7. IDP integration
8. Inter-service request authentication (for on-premise microservice architecture)
9. Demo app on freeCodeCamp

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
