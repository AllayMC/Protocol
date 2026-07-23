package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.PlayerUpdateEntityOverridesSerializer_v786;
import org.cloudburstmc.protocol.bedrock.packet.PlayerUpdateEntityOverridesPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class PlayerUpdateEntityOverridesSerializer_v2168 extends PlayerUpdateEntityOverridesSerializer_v786 {

    public static final PlayerUpdateEntityOverridesSerializer_v2168 INSTANCE = new PlayerUpdateEntityOverridesSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerUpdateEntityOverridesPacket packet) {
        VarInts.writeLong(buffer, packet.getEntityUniqueId());
        VarInts.writeUnsignedInt(buffer, packet.getPropertyIndex());
        VarInts.writeUnsignedInt(buffer, packet.getUpdateType().ordinal());
        buffer.writeByte(packet.getUpdateType().ordinal());
        if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_INT_OVERRIDE)) {
            buffer.writeIntLE(packet.getIntValue());
        } else if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_FLOAT_OVERRIDE)) {
            buffer.writeFloatLE(packet.getFloatValue());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerUpdateEntityOverridesPacket packet) {
        packet.setEntityUniqueId(VarInts.readLong(buffer));
        packet.setPropertyIndex(VarInts.readUnsignedInt(buffer));

        int type = VarInts.readUnsignedInt(buffer);
        if (type != buffer.readUnsignedByte()) {
            throw new IllegalStateException("type != legacy type");
        }

        packet.setUpdateType(PlayerUpdateEntityOverridesPacket.UpdateType.values()[type]);
        if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_INT_OVERRIDE)) {
            packet.setIntValue(buffer.readIntLE());
        } else if (packet.getUpdateType().equals(PlayerUpdateEntityOverridesPacket.UpdateType.SET_FLOAT_OVERRIDE)) {
            packet.setFloatValue(buffer.readFloatLE());
        }
    }
}