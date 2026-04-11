package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.UpdateClientOptionsSerializer_v786;
import org.cloudburstmc.protocol.bedrock.packet.UpdateClientOptionsPacket;
import org.cloudburstmc.protocol.bedrock.util.OptionalBoolean;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateClientOptionsSerializer_v974 extends UpdateClientOptionsSerializer_v786 {
    public static final UpdateClientOptionsSerializer_v974 INSTANCE = new UpdateClientOptionsSerializer_v974();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateClientOptionsPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptional(buffer, OptionalBoolean::isPresent, packet.getFilterProfanityChange(),
                (buf, aHelper, value) -> buf.writeBoolean(value.getAsBoolean()));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateClientOptionsPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setFilterProfanityChange(helper.readOptional(buffer, OptionalBoolean.empty(),
                buf -> OptionalBoolean.of(buf.readBoolean())));
    }
}
