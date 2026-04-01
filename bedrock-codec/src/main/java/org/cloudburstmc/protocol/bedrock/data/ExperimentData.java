package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;

@Value
public class ExperimentData {
    /**
     * @since v419
     */
    private final String name;
    /**
     * @since v419
     */
    private final boolean enabled; // ??? Always set to true
}
