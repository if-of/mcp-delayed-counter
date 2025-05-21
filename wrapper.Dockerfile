# Wrapper stage
FROM base-mcp-image:latest

RUN apt-get update && \
    apt-get install -y --no-install-recommends python3 python3-pip python3-venv ca-certificates && \
    # Install uv using pip
    pip3 install uv && \
    # Clean up package manager cache
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN uv tool install mcp-proxy
ENV PATH="/root/.local/bin:$PATH"

ENTRYPOINT [\
    "mcp-proxy",\
    "--sse-port", "8080",\
    "--sse-host", "0.0.0.0",\
    "--pass-environment",\
    "--",\
    "java",  "-jar", "app.jar"\
]