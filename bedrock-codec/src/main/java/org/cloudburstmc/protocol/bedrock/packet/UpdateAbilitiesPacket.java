package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AbilityLayer;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;

import java.util.List;

/**
 * A packet sent from the server to the client to update the abilities of the player. It, along with
 * the UpdateAdventureSettings packet, are replacements of the AdventureSettings packet since
 * v1.19.10.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateAbilitiesPacket implements BedrockPacket, PlayerAbilityHolder {
    /**
     * The unique ID of the player whose abilities are being updated.
     */
    private long uniqueEntityId;
    /**
     * The player permission level currently granted to the player.
     */
    private PlayerPermission playerPermission;
    /**
     * The command permission level currently granted to the player.
     */
    private CommandPermission commandPermission;
    /**
     * The full set of advertised ability layers for the player.
     */
    private List<AbilityLayer> abilityLayers = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_ABILITIES;
    }

    @Override
    public UpdateAbilitiesPacket clone() {
        try {
            return (UpdateAbilitiesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
