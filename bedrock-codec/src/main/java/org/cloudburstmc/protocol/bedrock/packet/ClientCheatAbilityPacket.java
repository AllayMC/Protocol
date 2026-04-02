package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Functions the same as {@link UpdateAbilitiesPacket}. The protocol split these packets despite
 * carrying the same player ability data.
 *
 * @since v567
 * @deprecated since v594
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class ClientCheatAbilityPacket implements BedrockPacket, PlayerAbilityHolder {

    /**
     * The unique id of the player whose ability data is being updated.
     */
    private long uniqueEntityId;
    /**
     * The player's permission level.
     */
    private PlayerPermission playerPermission;
    /**
     * The player's command permission level.
     */
    private CommandPermission commandPermission;
    /**
     * Ability layers that define what the player is allowed to do.
     */
    private List<AbilityLayer> abilityLayers = new ObjectArrayList<>();

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CHEAT_ABILITY;
    }

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public ClientCheatAbilityPacket clone() {
        try {
            return (ClientCheatAbilityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
