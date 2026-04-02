package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.Data;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents command param data used in the Bedrock protocol.
 */
@Data
public class CommandParamData {
    /**
     * The name.
     */
    private String name;
    /**
     * Whether optional.
     */
    private boolean optional;
    /**
     * The enum data.
     */
    private CommandEnumData enumData;
    /**
     * The type.
     */
    private CommandParam type;
    /**
     * The postfix.
     */
    private String postfix;
    /**
     * The options.
     */
    private final Set<CommandParamOption> options = EnumSet.noneOf(CommandParamOption.class);
}
