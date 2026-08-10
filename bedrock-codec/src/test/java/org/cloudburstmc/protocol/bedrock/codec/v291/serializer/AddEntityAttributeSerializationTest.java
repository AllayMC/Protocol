package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddEntityAttributeSerializationTest {

    private static final BedrockCodecHelper HELPER = Bedrock_v291.CODEC.createHelper();

    @Test
    void writesSyncedAttributesInMinimumValueMaximumOrder() {
        AttributeData attribute = new AttributeData("minecraft:health", -10.5f, 100.25f, 20.75f);
        ByteBuf buffer = Unpooled.buffer();
        try {
            AddEntitySerializer_v291.INSTANCE.writeAttribute(buffer, HELPER, attribute);

            assertEquals("minecraft:health", HELPER.readString(buffer));
            assertEquals(-10.5f, buffer.readFloatLE());
            assertEquals(20.75f, buffer.readFloatLE());
            assertEquals(100.25f, buffer.readFloatLE());
        } finally {
            buffer.release();
        }
    }

    @Test
    void readsSyncedAttributesInMinimumValueMaximumOrder() {
        ByteBuf buffer = Unpooled.buffer();
        try {
            HELPER.writeString(buffer, "minecraft:health");
            buffer.writeFloatLE(-10.5f);
            buffer.writeFloatLE(20.75f);
            buffer.writeFloatLE(100.25f);

            AttributeData attribute = AddEntitySerializer_v291.INSTANCE.readAttribute(buffer, HELPER);

            assertEquals("minecraft:health", attribute.name());
            assertEquals(-10.5f, attribute.minimum());
            assertEquals(20.75f, attribute.value());
            assertEquals(100.25f, attribute.maximum());
        } finally {
            buffer.release();
        }
    }
}
