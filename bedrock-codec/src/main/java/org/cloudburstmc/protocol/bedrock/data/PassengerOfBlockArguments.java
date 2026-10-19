package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Placement and pose of an actor riding a block.
 *
 * @param blockPos      the block position
 * @param offset        the actor's offset from the block
 * @param rotation      the actor's rotation
 * @param rotationLimit the permitted rotation limit
 * @param emoteType     the actor's pose
 * @since v2225
 */
public record PassengerOfBlockArguments(Vector3i blockPos, Vector3f offset, float rotation, float rotationLimit, EmoteType emoteType) {

    /**
     * Poses available while riding a block.
     *
     * @since v2225
     */
    public enum EmoteType {
        STANDING,
        RIDING,
        LAYING
    }
}
