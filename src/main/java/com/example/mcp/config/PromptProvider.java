package com.example.mcp.config;

import java.util.List;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptSpecification;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.Prompt;
import io.modelcontextprotocol.spec.McpSchema.PromptArgument;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import org.springframework.stereotype.Component;

@Component
public class PromptProvider {

    public SyncPromptSpecification provide() {
        return new McpServerFeatures.SyncPromptSpecification(
            new Prompt(
                "get-fisso-index-prompt",
                "Get FISSO index prompt",
                List.of(new PromptArgument("code", "FISSO index code", true))
            ),
            (exchange, request) -> {
                var code = request.arguments().get("code");
                var description = "Simple test response for Fisso Index status.";
                List<PromptMessage> messages = List.of(
                    new PromptMessage(
                        Role.ASSISTANT,
                        new TextContent("Fisso Index test endpoint reached. Status: OK. Code: %s".formatted(code))
                    )
                );

                return new GetPromptResult(description, messages);
            }
        );
    }

}
