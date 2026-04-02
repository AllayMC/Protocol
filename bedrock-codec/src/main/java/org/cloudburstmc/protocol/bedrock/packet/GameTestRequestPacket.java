package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Sent by the client to request that a game test be executed.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class GameTestRequestPacket implements BedrockPacket {
    /**
     * The maximum number of tests to run in one batch.
     */
    private int maxTestsPerBatch;
    /**
     * The number of times the test should be repeated.
     */
    private int repeatCount;
    /**
     * The rotation to apply when placing the test structure.
     */
    private int rotation;
    /**
     * Whether the test should stop immediately when an error occurs.
     */
    private boolean stoppingOnFailure;
    /**
     * The block position at which the test should be executed.
     */
    private Vector3i testPos;
    /**
     * The number of tests to lay out per row when running batches.
     */
    private int testsPerRow;
    /**
     * The name of the test to execute.
     */
    private String testName;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GAME_TEST_REQUEST;
    }

    @Override
    public GameTestRequestPacket clone() {
        try {
            return (GameTestRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
