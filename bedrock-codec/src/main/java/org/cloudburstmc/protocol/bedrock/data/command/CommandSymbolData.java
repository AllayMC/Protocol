package org.cloudburstmc.protocol.bedrock.data.command;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

/**
 * Represents command symbol data used in the Bedrock protocol.
 *
 * @param value       The value.
 * @param commandEnum Whether command enum.
 * @param softEnum    Whether soft enum.
 * @param postfix     Whether postfix.
 */
public record CommandSymbolData(int value, boolean commandEnum, boolean softEnum, boolean postfix) {
    private static final int ARG_FLAG_VALID = 0x100000;
    private static final int ARG_FLAG_ENUM = 0x200000;
    private static final int ARG_FLAG_POSTFIX = 0x1000000;
    private static final int ARG_FLAG_SOFT_ENUM = 0x4000000;

    public static CommandSymbolData deserialize(int type) {
        int value = type & 0xffff;
        boolean commandEnum = (type & ARG_FLAG_ENUM) != 0;
        boolean softEnum = (type & ARG_FLAG_SOFT_ENUM) != 0;
        boolean postfix = (type & ARG_FLAG_POSTFIX) != 0;
        checkArgument(postfix || (type & ARG_FLAG_VALID) != 0, "Invalid command param type: " + type);
        return new CommandSymbolData(value, commandEnum, softEnum, postfix);
    }

    public int serialize() {
        int value = this.value;
        if (commandEnum) {
            value |= ARG_FLAG_ENUM;
        }
        if (softEnum) {
            value |= ARG_FLAG_SOFT_ENUM;
        }
        if (postfix) {
            value |= ARG_FLAG_POSTFIX;
        } else {
            value |= ARG_FLAG_VALID;
        }
        return value;
    }
}
