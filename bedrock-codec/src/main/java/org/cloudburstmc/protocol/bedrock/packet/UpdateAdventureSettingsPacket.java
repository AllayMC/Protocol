package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to update the client-side adventure toggles for a player. Together with
 * {@link UpdateAbilitiesPacket}, this packet replaces {@link AdventureSettingsPacket} in newer
 * protocol versions.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateAdventureSettingsPacket implements BedrockPacket {
    /**
     * Whether the player is prevented from attacking mobs.
     */
    private boolean noPvM;
    /**
     * Whether mobs are prevented from attacking the player. The reason this flag is exposed to the
     * client is not currently documented.
     */
    private boolean noMvP;
    /**
     * Whether the world is treated as immutable for the player, preventing block interaction
     * changes such as placing or breaking.
     */
    private boolean immutableWorld;
    /**
     * Whether player name tags should be rendered.
     */
    private boolean showNameTags;
    /**
     * Whether automatic jumping is enabled for the player.
     */
    private boolean autoJump;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_ADVENTURE_SETTINGS;
    }

    @Override
    public UpdateAdventureSettingsPacket clone() {
        try {
            return (UpdateAdventureSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
