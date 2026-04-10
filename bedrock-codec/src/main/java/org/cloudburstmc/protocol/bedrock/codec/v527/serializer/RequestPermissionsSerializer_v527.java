package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.PlayerPermission;
import org.cloudburstmc.protocol.bedrock.packet.RequestPermissionsPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.util.Set;

@NoArgsConstructor
public class RequestPermissionsSerializer_v527 implements BedrockPacketSerializer<RequestPermissionsPacket> {

    private static final PlayerPermission[] VALUES = PlayerPermission.values();
    private static final Ability[] CUSTOM_PERMISSIONS = {
            Ability.BUILD,
            Ability.MINE,
            Ability.DOORS_AND_SWITCHES,
            Ability.OPEN_CONTAINERS,
            Ability.ATTACK_PLAYERS,
            Ability.ATTACK_MOBS,
            Ability.OPERATOR_COMMANDS,
            Ability.TELEPORT
    };

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RequestPermissionsPacket packet) {
        buffer.writeLongLE(packet.getUniqueEntityId());
        VarInts.writeInt(buffer, packet.getPermissions().ordinal());
        this.serializeBitset(buffer, packet.getCustomPermissions());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RequestPermissionsPacket packet) {
        packet.setUniqueEntityId(buffer.readLongLE());
        packet.setPermissions(VALUES[VarInts.readInt(buffer)]);
        this.deserializeBitset(buffer, packet.getCustomPermissions());
    }

    private void serializeBitset(ByteBuf buffer, Set<Ability> customPermissions) {
        int flags = 0;
        for (int i = 0; i < CUSTOM_PERMISSIONS.length; i++) {
            if (customPermissions.contains(CUSTOM_PERMISSIONS[i])) {
                flags |= 1 << i;
            }
        }
        buffer.writeShortLE(flags);
    }

    private void deserializeBitset(ByteBuf buffer, Set<Ability> customPermissions) {
        int flags = buffer.readUnsignedShortLE();
        for (int i = 0; i < CUSTOM_PERMISSIONS.length; i++) {
            if ((flags & (1 << i)) != 0) {
                customPermissions.add(CUSTOM_PERMISSIONS[i]);
            }
        }
    }
}
