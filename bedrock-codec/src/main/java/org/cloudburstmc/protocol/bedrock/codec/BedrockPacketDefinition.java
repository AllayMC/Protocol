package org.cloudburstmc.protocol.bedrock.codec;

import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

import java.util.function.Supplier;

public record BedrockPacketDefinition<T extends BedrockPacket>(int id, Supplier<T> factory,
                                                               BedrockPacketSerializer<T> serializer,
                                                               PacketRecipient recipient) {
}
