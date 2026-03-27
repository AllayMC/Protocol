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
@Deprecated
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AdventureSettingsPacket implements BedrockPacket {
    private final Set<AdventureSetting> settings = EnumSet.noneOf(AdventureSetting.class);
    private CommandPermission commandPermission = CommandPermission.ANY;
    private PlayerPermission playerPermission = PlayerPermission.VISITOR;
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
