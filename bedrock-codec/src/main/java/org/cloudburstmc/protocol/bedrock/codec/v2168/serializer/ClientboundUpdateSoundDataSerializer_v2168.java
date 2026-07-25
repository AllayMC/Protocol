package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.ClientboundUpdateSoundDataSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.data.sound.*;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundUpdateSoundDataPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class ClientboundUpdateSoundDataSerializer_v2168 extends ClientboundUpdateSoundDataSerializer_v1001 {

    public static final ClientboundUpdateSoundDataSerializer_v2168 INSTANCE = new ClientboundUpdateSoundDataSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        buffer.writeLongLE(packet.getServerSoundHandle());

        helper.writeOptionalNull(buffer, packet.getStop(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
        });
        helper.writeOptionalNull(buffer, packet.getVolume(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
            buf.writeFloatLE(d.volume());
        });
        helper.writeOptionalNull(buffer, packet.getPitch(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
            buf.writeFloatLE(d.pitch());
        });
        helper.writeOptionalNull(buffer, packet.getFade(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
            buf.writeFloatLE(d.targetVolume());
            buf.writeFloatLE(d.duration());
        });
        helper.writeOptionalNull(buffer, packet.getSeekTo(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
            buf.writeFloatLE(d.seconds());
        });
        helper.writeOptionalNull(buffer, packet.getPause(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
        });
        helper.writeOptionalNull(buffer, packet.getResume(), (buf, d) -> {
            VarInts.writeUnsignedInt(buffer, 0);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundUpdateSoundDataPacket packet) {
        packet.setServerSoundHandle(buffer.readLongLE());

        packet.setStop(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new StopSoundData();
        }));
        packet.setVolume(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new SetVolumeSoundData(buf.readFloatLE());
        }));
        packet.setPitch(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new SetPitchSoundData(buf.readFloatLE());
        }));
        packet.setFade(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new FadeSoundData(buf.readFloatLE(), buf.readFloatLE());
        }));
        packet.setSeekTo(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new SeekToSoundData(buf.readFloatLE());
        }));
        packet.setPause(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new PauseSoundData();
        }));
        packet.setResume(helper.readOptional(buffer, null, (buf, h) -> {
            VarInts.readUnsignedInt(buf);
            return new ResumeSoundData();
        }));
    }
}
