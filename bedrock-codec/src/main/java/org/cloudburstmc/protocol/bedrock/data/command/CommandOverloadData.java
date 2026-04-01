package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Data;

@Data
public class CommandOverloadData {
    /**
     * @since v594
     */
    private final boolean chaining;
    private final CommandParamData[] overloads;
}
