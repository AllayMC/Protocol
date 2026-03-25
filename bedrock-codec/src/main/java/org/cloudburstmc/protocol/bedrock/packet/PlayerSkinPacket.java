package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.common.PacketSignal;
import java.util.UUID;

/**
 * Sent by the client to the server when it updates its own skin using the in-game skin picker. It
 * is relayed by the server, or sent if the server changes the skin of a player on its own accord.
 * Note that the packet can only be sent for players that are in the player list at the time of
 * sending.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerSkinPacket implements BedrockPacket {
    private UUID uuid;
    private SerializedSkin skin;
    private String newSkinName;
    private String oldSkinName;
    /**
     * @since v390
     */
    private boolean trustedSkin;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_SKIN;
    }

    @Override
    public PlayerSkinPacket clone() {
        try {
            return (PlayerSkinPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
