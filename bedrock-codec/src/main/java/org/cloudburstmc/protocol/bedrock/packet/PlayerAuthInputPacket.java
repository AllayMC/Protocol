package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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
    /**
     * The rotation reported by the client. The X and Y components correspond to pitch and yaw.
     */
    private Vector3f rotation;
    /**
     * Holds the position that the player reports it has.
     */
    private Vector3f position;
    /**
     * The movement vector produced from directional input, typically the WASD keys or controller
     * stick.
     */
    private Vector2f motion;
    /**
     * A combination of bit flags that together specify the way the player moved last tick. It is a
     * combination of the flags above.
     */
    private final Set<PlayerAuthInputData> inputData = EnumSet.noneOf(PlayerAuthInputData.class);
    /**
     * Specifies the way that the client inputs data to the screen. It is one of the constants that
     * may be found above.
     */
    private InputMode inputMode;
    /**
     * Specifies the way that the player is playing. The values it holds, which are rather random,
     * may be found above.
     */
    private ClientPlayMode playMode;
    /**
     * The vr gaze direction.
     *
     * @deprecated since v748
     */
    @Deprecated
    private Vector3f vrGazeDirection;
    /**
     * The server tick at which the packet was sent. It is used in relation to
     * {@link CorrectPlayerMovePredictionPacket}.
     *
     * @since v419
     */
    private long tick;
    /**
     * The position delta reported by the client relative to its previous state.
     *
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
     * The interaction model currently used by the client.
     *
     * @since v527
     */
    private InputInteractionModel inputInteractionModel;
    /**
     * The analogue movement vector produced from controller input.
     *
     * @since v575
     */
    private Vector2f analogMoveVector;
    /**
     * The unique ID of the vehicle that the client predicts the player to be riding.
     *
     * @since v649
     */
    private long predictedVehicle;
    /**
     * The rotation of the vehicle that the player is in, if any.
     *
     * @since v662
     */
    private Vector2f vehicleRotation;
    /**
     * The pitch/yaw pair the client intends to use for interactions. This may differ from
     * {@link #rotation} for VR or custom cameras.
     *
     * @since v748
     */
    private Vector2f interactRotation;
    /**
     * The vector that represents the camera's forward direction which can be used to transform
     * movement to be camera relative.
     *
     * @since v748
     */
    private Vector3f cameraOrientation;
    /**
     * The raw movement vector before permissions, movement modifiers, or analogue normalisation are
     * applied.
     *
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
