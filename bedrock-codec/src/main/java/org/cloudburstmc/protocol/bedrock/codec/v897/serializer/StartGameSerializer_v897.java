package org.cloudburstmc.protocol.bedrock.codec.v897.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v827.serializer.StartGameSerializer_v827;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

public class StartGameSerializer_v897 extends StartGameSerializer_v827 {

    public static final StartGameSerializer_v897 INSTANCE = new StartGameSerializer_v897();

    @Override
    protected void readBeforeNetworkPermissions(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        packet.setNetworkPermissions(this.readNetworkPermissions(buffer, helper));
    }

    @Override
    protected void writeBeforeNetworkPermissions(ByteBuf buffer, BedrockCodecHelper helper, StartGamePacket packet) {
        this.writeNetworkPermissions(buffer, helper, packet.getNetworkPermissions());
    }
}
