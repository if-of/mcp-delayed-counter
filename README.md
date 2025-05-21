
```shell
  docker build --no-cache --progress=plain -t base-mcp-image:latest -f ./Dockerfile .
```

```shell
  docker build -t wrapper-mcp-image:latest -f ./wrapper.Dockerfile .
```
