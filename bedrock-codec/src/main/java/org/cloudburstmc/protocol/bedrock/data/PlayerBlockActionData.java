package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * A single block interaction entry carried in
 * {@link org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket#playerActions}.
 */
@Data
public class PlayerBlockActionData {
    /**
     * The action to perform.
     */
    PlayerActionType action;
    /**
     * The block position affected by the action. It is only present for break-related actions such as
     * {@link PlayerActionType#START_BREAK}, {@link PlayerActionType#ABORT_BREAK},
     * {@link PlayerActionType#CONTINUE_BREAK}, {@link PlayerActionType#BLOCK_PREDICT_DESTROY} and
     * {@link PlayerActionType#BLOCK_CONTINUE_DESTROY}.
     */
    Vector3i blockPosition;
    /**
     * The face of the block affected by the action. It is only present for break-related actions.
     */
    int face;
}
