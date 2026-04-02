package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent in response to the GameTestRequest packet, with a boolean indicating whether the
 * test was successful or not, and an error string if the test failed.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class GameTestResultsPacket implements BedrockPacket {
    /**
     * Whether the requested test completed successfully.
     */
    private boolean successful;
    /**
     * The error that occurred. If Succeeded is true, this field is empty.
     */
    private String error;
    /**
     * The name of the test that produced this result.
     */
    private String testName;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GAME_TEST_RESULTS;
    }

    @Override
    public GameTestResultsPacket clone() {
        try {
            return (GameTestResultsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
