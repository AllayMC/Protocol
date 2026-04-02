package org.cloudburstmc.protocol.bedrock.data.clock;

import java.util.List;

/**
 * Represents initialize registry data used in the Bedrock protocol.
 *
 * @param clockData The clock data.
 */
public record InitializeRegistryData(List<WorldClockData> clockData) implements SyncWorldClocksPayload {

}
