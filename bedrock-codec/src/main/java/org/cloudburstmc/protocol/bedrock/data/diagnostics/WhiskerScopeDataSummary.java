package org.cloudburstmc.protocol.bedrock.data.diagnostics;

public record WhiskerScopeDataSummary(String label, String indentation, long totalHighCostNS,
                                      long totalMidCostNS, long totalLowCostNS) {
}
