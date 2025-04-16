package com.example.mcp;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DelayedCounterToolService {

    private final AtomicLong counter = new AtomicLong();
    private final Instant creationTime = Instant.now();
    
    @Value("${DELAY_MS}")
    private long delayMs;

    @Tool(description = "Get FISSO index")
    public String getFissoIndex() {
        var result = "The FISSO index is at " + counter.incrementAndGet() + " and was created at " + creationTime;
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep was interrupted: " + e.getMessage());
        }
        return result;
    }

}