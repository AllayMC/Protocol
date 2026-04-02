package org.cloudburstmc.protocol.bedrock.data.command;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;

import java.util.List;

/**
 * Represents chained sub command data used in the Bedrock protocol.
 */
@Data
public class ChainedSubCommandData {
    /**
     * The name.
     */
    private final String name;
    /**
     * The values.
     */
    private final List<Value> values = new ObjectArrayList<>();

    public record Value(String first, String second) {
    }
}
