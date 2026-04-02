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
    /**
     * The UUID of the player as sent in the Login packet when the client joined the server. It must
     * match this UUID exactly for the skin to show up on the player.
     */
    private UUID uuid;
    /**
     * The new skin to be applied on the player with the UUID in the field above. The skin,
     * including its animations, will be shown after sending it.
     */
    private SerializedSkin skin;
    /**
     * Legacy field with no current function. It may be left empty.
     */
    private String newSkinName;
    /**
     * Legacy field with no current function. It may be left empty.
     */
    private String oldSkinName;
    /**
     * Whether the skin should be marked as trusted by the receiving client.
     *
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
