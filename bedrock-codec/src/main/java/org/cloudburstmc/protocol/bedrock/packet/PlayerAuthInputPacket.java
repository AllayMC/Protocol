package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to allow for server authoritative movement. It is used to synchronise the
 * player input with the position server-side. The client sends this packet when the
 * ServerAuthoritativeMovementMode field in the StartGame packet is set to true, instead of the
 * MovePlayer packet. The client will send this packet once every tick.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerAuthInputPacket implements BedrockPacket {
    private Vector3f rotation; // head rot after motion
    private Vector3f position;
    private Vector2f motion;
    private final Set<PlayerAuthInputData> inputData = EnumSet.noneOf(PlayerAuthInputData.class);
    private InputMode inputMode;
    private ClientPlayMode playMode;

    /**
     * @deprecated since v748
     */
    private Vector3f vrGazeDirection;

    /**
     * @since v419
     */
    private long tick;

    /**
     * @since v419
     */
    private Vector3f delta;

    /**
     * {@link #inputData} must contain {@link PlayerAuthInputData#PERFORM_ITEM_INTERACTION} in order
     * for this to not be null.
     *
     * @since v428
     */
    private ItemUseTransaction itemUseTransaction;

    /**
     * {@link #inputData} must contain {@link PlayerAuthInputData#PERFORM_ITEM_STACK_REQUEST} in order
     * for this to not be null.
     *
     * @since v428
     */
    private ItemStackRequest itemStackRequest;

    /**
     * {@link #inputData} must contain {@link PlayerAuthInputData#PERFORM_BLOCK_ACTIONS} in order for
     * this to not be empty.
     *
     * @since v428
     */
    private final List<PlayerBlockActionData> playerActions = new ObjectArrayList<>();

    /**
     * @since v527
     */
    private InputInteractionModel inputInteractionModel;

    /**
     * @since v748
     */
    private Vector2f interactRotation;

    /**
     * @since v575
     */
    private Vector2f analogMoveVector;

    /**
     * @since v649
     */
    private long predictedVehicle;

    /**
     * @since v662
     */
    private Vector2f vehicleRotation;

    /**
     * @since v748
     */
    private Vector3f cameraOrientation;

    /**
     * @since v766
     */
    private Vector2f rawMoveVector;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_AUTH_INPUT;
    }

    @Override
    public PlayerAuthInputPacket clone() {
        try {
            return (PlayerAuthInputPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
