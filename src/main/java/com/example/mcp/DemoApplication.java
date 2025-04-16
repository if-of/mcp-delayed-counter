package com.example.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public ToolCallbackProvider weatherTools(DelayedCounterToolService delayedCounterToolService) {
        return MethodToolCallbackProvider.builder().toolObjects(delayedCounterToolService).build();
    }

}
