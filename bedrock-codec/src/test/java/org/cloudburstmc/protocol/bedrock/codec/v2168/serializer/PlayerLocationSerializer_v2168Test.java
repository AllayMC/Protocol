package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.packet.PlayerLocationPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlayerLocationSerializer_v2168Test {

    @Test
    void writesLocatorBarTypeInTheTrailingTypeField() {
        ByteBuf buffer = Unpooled.buffer();
        try {
            PlayerLocationPacket packet = new PlayerLocationPacket();
            packet.setTargetEntityId(42);
            packet.setType(PlayerLocationPacket.Type.HIDE);

            PlayerLocationSerializer_v2168.INSTANCE.serialize(buffer, null, packet);

            assertEquals(42, VarInts.readLong(buffer));
            assertEquals(PlayerLocationPacket.Type.HIDE.ordinal(), VarInts.readUnsignedInt(buffer));
            assertEquals(PlayerLocationPacket.Type.HIDE.ordinal(), VarInts.readInt(buffer));
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }
}
