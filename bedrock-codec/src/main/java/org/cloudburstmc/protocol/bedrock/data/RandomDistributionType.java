package org.cloudburstmc.protocol.bedrock.data;

/**
 * Enumerates random distribution type values used in the Bedrock protocol.
 */
public enum RandomDistributionType {
    SINGLE_VALUED,
    UNIFORM,
    GAUSSIAN,
    INVERSE_GAUSSIAN,
    FIXED_GRID,
    JITTERED_GRID,
    TRIANGLE
}
