# Universal JSON API

## API Usage Examples

### Create document

```bash
curl -v -X POST http://localhost:8080/api/v1.0/c/test -d '{"message": "hello world"}' -H "Content-Type: application/json"
```

### Delete collection

```bash
curl -v -X DELETE http://localhost:8080/api/v1.0/c/test -H "Content-Type: application/json"
```
