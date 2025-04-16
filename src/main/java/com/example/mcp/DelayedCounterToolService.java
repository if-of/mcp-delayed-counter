
package com.example.mcp;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class DelayedCounterToolService {

    private final AtomicLong counter = new AtomicLong();
    private final Instant creationTime = Instant.now();

    @Tool(description = "Get FISSO index")
    public String getFissoIndex() {
        var result = "The FISSO index is at " + counter.incrementAndGet() + " and was created at " + creationTime;
        try {
            Thread.sleep(30000); // Sleep for 30 seconds (30000 milliseconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep was interrupted: " + e.getMessage());
        }
        return result;
    }

}