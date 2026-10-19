package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.v2225.Bedrock_v2225;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMatchmakingStatePacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMatchmakingStatePacket.MatchmakingState;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundMatchmakingStatePacket.MatchmakingStateOptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientboundMatchmakingStateSerializer_v2225Test {

    private static Stream<Arguments> options() {
        return Stream.of(
                Arguments.of("absent options", null, "00"),
                Arguments.of("absent fields", new MatchmakingStateOptions(null, null), "010000"),
                Arguments.of("name only", new MatchmakingStateOptions("P", null), "0101015000"),
                Arguments.of("empty name", new MatchmakingStateOptions("", null), "01010000"),
                Arguments.of("false only", new MatchmakingStateOptions(null, false), "01000100"),
                Arguments.of("true only", new MatchmakingStateOptions(null, true), "01000101"),
                Arguments.of("name and false", new MatchmakingStateOptions("P", false), "010101500100"),
                Arguments.of("name and true", new MatchmakingStateOptions("P", true), "010101500101")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("options")
    void writesOptionalFieldPresence(String name, MatchmakingStateOptions options, String suffix) {
        ClientboundMatchmakingStatePacket packet = new ClientboundMatchmakingStatePacket();
        packet.setState(MatchmakingState.MATCH_FOUND);
        packet.setDestinationName("hub");
        packet.setOptions(options);

        ByteBuf buffer = Unpooled.buffer();
        try {
            ClientboundMatchmakingStateSerializer_v2225.INSTANCE.serialize(
                    buffer, Bedrock_v2225.CODEC.createHelper(), packet);
            assertEquals("0203687562" + suffix, ByteBufUtil.hexDump(buffer));
        } finally {
            buffer.release();
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("options")
    void readsOptionalFieldPresence(String name, MatchmakingStateOptions options, String suffix) {
        ByteBuf buffer = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump("0203687562" + suffix));
        try {
            ClientboundMatchmakingStatePacket packet = new ClientboundMatchmakingStatePacket();
            ClientboundMatchmakingStateSerializer_v2225.INSTANCE.deserialize(
                    buffer, Bedrock_v2225.CODEC.createHelper(), packet);
            assertEquals(MatchmakingState.MATCH_FOUND, packet.getState());
            assertEquals("hub", packet.getDestinationName());
            assertEquals(options, packet.getOptions());
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }
}
