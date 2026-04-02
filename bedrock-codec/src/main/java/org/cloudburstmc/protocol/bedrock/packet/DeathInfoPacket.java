package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Sent by the server when a player dies. It carries the strings shown on the death screen,
 * including the death cause and the list of translated message lines.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DeathInfoPacket implements BedrockPacket {
    /**
     * The string key describing the cause of death, such as suffocation or suicide.
     */
    private String causeAttackName;
    /**
     * The death messages shown on the death screen.
     */
    private final List<String> messageList = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DEATH_INFO;
    }

    @Override
    public DeathInfoPacket clone() {
        try {
            return (DeathInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
