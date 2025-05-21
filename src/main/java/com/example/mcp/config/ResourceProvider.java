package com.example.mcp.config;

import java.util.List;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.SyncResourceSpecification;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;
import io.modelcontextprotocol.spec.McpSchema.Resource;
import io.modelcontextprotocol.spec.McpSchema.ResourceContents;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import org.springframework.stereotype.Component;

@Component
public class ResourceProvider {

    private static final String CONTENT = """
        The Fisso Index: A Comprehensive Test Metric
        
        The Fisso Index (FI) is a synthetic, composite metric engineered for rigorous testing and simulation environments. Unlike real-world indices tied to specific markets or fixed economic indicators, the Fisso Index is designed to be highly configurable and reactive, providing a dynamic and multifaceted signal for evaluating system behavior, performance under stress, data pipeline integrity, and the effectiveness of monitoring and alerting systems. It serves as a central, observable value that encapsulates the interplay of various simulated factors within a testbed.
        
        Purpose:
        The primary purpose of the Fisso Index is to offer a versatile, single-value representation of a complex, simulated environment or system state. Testers can use the FI to: 1. Benchmark Performance: Observe how the FI behaves under different load conditions, configurations, or test scenarios. 2. Validate Monitoring & Alerting: Configure monitoring systems to track the FI and trigger alerts based on predefined thresholds or anomalies. 3. Simulate Real-World Variability: Introduce controlled noise, trends, or event-driven shocks to the factors influencing the FI to test system resilience and responsiveness. 4. Analyze Correlations: Study how changes in specific input variables or simulated events impact the overall FI, aiding in root cause analysis during testing. 5. Stress Testing & Capacity Planning: Determine the point at which the system (or the simulated environment) begins to degrade, reflected by a decline or erratic behavior in the FI.
        
        Methodology (Illustrative & Configurable):
        The Fisso Index is calculated as a weighted combination of several underlying, often abstract, sub-indices. The specific sub-indices, their calculation methods, and their weights are fully configurable at the commencement of any test run, allowing for tailored simulation. A typical configuration might include: Simulated Operational Health (SOH), which reflects the stability and availability of key simulated system components (e.g., inverse of error rates, simulated service uptime percentage). Resource Utilization Efficiency (RUE), which measures the effectiveness of simulated resource allocation (e.g., a score based on CPU load, memory usage, or network throughput, where scores peak at optimal utilization and decline if under or over-utilized). Data Throughput & Quality (DTQ), which represents the volume of simulated data processed and its integrity (e.g., a combination of simulated transactions per second and a data validation success rate). External Stimulus Factor (ESF), which is an input representing external, simulated conditions such as market sentiment in a financial test, environmental conditions in a logistics test, or user behavior patterns in a web application test. This can be driven by predefined scripts or external test data feeds.
        The composite FI is typically calculated using a formula that can incorporate linear combinations, non-linear functions, thresholds, and dependencies between sub-indices. A simplified example formula could involve summing weighted sub-indices plus noise. The index is usually scaled to a convenient range, such as 0 to 100.
        
        Characteristics:
        The behavior of the Fisso Index is dictated by its configuration and the test scenario, but it can be designed to exhibit characteristics such as: Volatility, which can be high or low depending on the sensitivity of its components and the level of simulated activity/instability. Trends, showing upward or downward movement over time, simulating system improvements or degradation. Event Sensitivity, designed to react noticeably to specific simulated events or injections of test data anomalies. Lagged Response, where certain configurations can introduce delays in the FI's reaction to changes in underlying factors. Statefulness, where the index's value might depend not only on current inputs but also on recent history or accumulated state.
        
        In essence, the Fisso Index is a blank canvas for test designers to create a meaningful, dynamic metric tailored to the specific needs and complexities of the system or environment being tested. It provides a clear, quantifiable signal for observing the impact of test conditions and validating system responses.
        """;

    public SyncResourceSpecification provide() {
        return new McpServerFeatures.SyncResourceSpecification(
            new Resource("custom://resource", "get-fisso-full-description", "Get FISSO index full description", "mime-type", null),
            (exchange, request) -> {
                var content = new TextResourceContents("custom://resource", "mime-type", CONTENT);
                var contents = List.<ResourceContents>of(content);
                return new ReadResourceResult(contents);
            }
        );
    }

}
