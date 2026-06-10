package org.cloudburstmc.protocol.bedrock.data.diagnostics;

/**
 * Represents a whisker profiler scope diagnostic summary.
 *
 * @param label The label of the whisker scope.
 * @param indentation The indentation string of the whisker scope within the profiler hierarchy.
 * @param totalHighCostNS The total time, in nanoseconds, spent in the high-cost portion of the scope.
 * @param totalMidCostNS The total time, in nanoseconds, spent in the mid-cost portion of the scope.
 * @param totalLowCostNS The total time, in nanoseconds, spent in the low-cost portion of the scope.
 *
 * @since v1001
 */
public record WhiskerScopeDataSummary(String label, String indentation, long totalHighCostNS,
                                      long totalMidCostNS, long totalLowCostNS) {
}
