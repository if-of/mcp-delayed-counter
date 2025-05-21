package com.example.mcp.config;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.Content;
import io.modelcontextprotocol.spec.McpSchema.LoggingLevel;
import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToolProvider {

    @Value("${DELAY_MS:5000}")
    private long delayMs;
    @Autowired
    private ObjectMapper objectMapper;

    public SyncToolSpecification provide(McpSyncServer mcpSyncServer) {
        var counter = new AtomicLong();
        var creationTime = Instant.now();

        var schema = """
            {
              "type" : "object",
              "id" : "urn:jsonschema:Operation",
              "properties" : {
                "string" : {
                  "type" : "string"
                },
                "number" : {
                  "type" : "number"
                }
              }
            }
            """;
        return new McpServerFeatures.SyncToolSpecification(
            new Tool("get-current-fisso-index", "Get current value of FISSO index", schema),
            (exchange, arguments) -> {
                String serializedArguments;
                try {
                    serializedArguments = objectMapper.writeValueAsString(arguments);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                var granularity = 10;
                var multiplier = 100d / granularity;
                var delay = delayMs / granularity;
                for (int i = 1; i <= granularity; i++) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    mcpSyncServer.loggingNotification(
                        LoggingMessageNotification.builder()
                            .data("Tool execution %f".formatted(i * multiplier))
                            .level(LoggingLevel.INFO)
                            .logger("fisso-progress-logger")
                            .build()
                    );
                }

                var message = "The FISSO index is at %s and was created at %s. Delay: %s. Params: %s"
                    .formatted(counter.incrementAndGet(), creationTime, delayMs, serializedArguments);
                var result = new TextContent(message);
                var results = List.<Content>of(result);
                return new CallToolResult(results, false);
            }
        );
    }

}