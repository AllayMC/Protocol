package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;

import java.util.EnumSet;
import java.util.Set;

/**
 * A packet sent from the client to the server to request permissions that the client does not
 * currently have. It can only be sent by operators and host in vanilla Minecraft.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RequestPermissionsPacket implements BedrockPacket {
    /**
     * The unique entity ID of the player whose permission level is being requested.
     */
    private long uniqueEntityId;
    /**
     * The coarse permission level being requested.
     */
    private PlayerPermission permissions;
    /**
     * Custom permission flags requested in addition to {@link #permissions}.
     */
    private final Set<Ability> customPermissions = EnumSet.noneOf(Ability.class);

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_PERMISSIONS;
    }

    @Override
    public RequestPermissionsPacket clone() {
        try {
            return (RequestPermissionsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
