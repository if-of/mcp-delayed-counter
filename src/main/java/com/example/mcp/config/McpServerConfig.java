package com.example.mcp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig {

    @Autowired
    private PromptProvider promptProvider;
    @Autowired
    private ResourceProvider resourceProvider;
    @Autowired
    private ToolProvider toolProvider;

    @Bean
    public McpSyncServer mcpServer() {
        var transportProvider = new StdioServerTransportProvider(new ObjectMapper());
        var mcpServer = McpServer.sync(transportProvider)
            .serverInfo("my-server", "1.0.0")
            .capabilities(ServerCapabilities.builder()
                .resources(true, true)
                .tools(true)
                .prompts(true)
                .logging()
                .build())
            .build();

        mcpServer.addPrompt(promptProvider.provide());
        mcpServer.addResource(resourceProvider.provide());
        mcpServer.addTool(toolProvider.provide(mcpServer));

        return mcpServer;

    }

}
