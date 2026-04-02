package org.cloudburstmc.protocol.bedrock.data.command;

import java.util.Map;
import java.util.Set;

/**
 * Represents an enum in a command usage. The enum typically has a type and a set of options that
 * are valid. A value that is not one of the options results in a failure during execution.
 *
 * @param name   The enum type name shown to the client.
 * @param values The option values in the enum and any constraints attached to each option.
 * @param isSoft Whether the enum is dynamic and can later be updated through
 *               {@link org.cloudburstmc.protocol.bedrock.packet.UpdateSoftEnumPacket}.
 */
public record CommandEnumData(String name, Map<String, Set<CommandEnumConstraint>> values, boolean isSoft) {
}
