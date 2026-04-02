package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AdventureSetting;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.EnumSet;
import java.util.Set;

/**
 * Sent by the server to update game-play related features, in particular permissions to access
 * these features for the client. It includes allowing the player to fly, build and mine, and attack
 * entities. Most of these flags should be checked server-side instead of using this packet only.
 * The client may also send this packet to the server when it updates one of these settings through
 * the in-game settings interface. The server should verify if the player actually has permission to
 * update those settings.
 *
 * @deprecated Removed in 1.19.30 (553). Use {@link UpdateAbilitiesPacket} and {@link
 * UpdateAdventureSettingsPacket} instead.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class AdventureSettingsPacket implements BedrockPacket {
    /**
     * A combined set of adventure and action-permission flags, such as flight, building, opening
     * containers, or attacking entities.
     */
    private final Set<AdventureSetting> settings = EnumSet.noneOf(AdventureSetting.class);
    /**
     * The command permission level exposed to the client. This controls which categories of
     * commands the player is allowed to use.
     */
    private CommandPermission commandPermission = CommandPermission.ANY;
    /**
     * The permission level shown for the player in the player list, such as visitor, member, or
     * operator.
     */
    private PlayerPermission playerPermission = PlayerPermission.VISITOR;
    /**
     * PlayerUniqueID is a unique identifier of the player. This must be filled out with the entity
     * unique ID of the player.
     */
    private long uniqueEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADVENTURE_SETTINGS;
    }

    @Override
    public AdventureSettingsPacket clone() {
        try {
            return (AdventureSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
