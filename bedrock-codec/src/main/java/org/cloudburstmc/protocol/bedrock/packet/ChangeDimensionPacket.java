package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to send a dimension change screen client-side. Once the screen
 * is cleared client-side, the client will send a PlayerAction packet with
 * PlayerActionDimensionChangeDone.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ChangeDimensionPacket implements BedrockPacket {
    /**
     * The dimension ID that the client should change to. The fog colour will change depending on
     * the target dimension. This value must differ from the dimension the player is currently in;
     * sending the current dimension again will leave the client stuck on the dimension change
     * screen.
     */
    private int dimension;
    /**
     * The position in the new dimension that the player is spawned in.
     */
    private Vector3f position;
    /**
     * Specifies if the dimension change was respawn based, meaning that the player died in one
     * dimension and got respawned into another. The client will send a PlayerAction packet with
     * PlayerActionDimensionChangeRequest if it dies in another dimension, indicating that it needs
     * a DimensionChange packet with Respawn set to true.
     */
    private boolean respawn;
    /**
     * A unique identifier for the loading screen shown while this dimension change is in progress.
     * The client reports the state of that screen through {@link ServerboundLoadingScreenPacket},
     * so the server can delay packets until the transfer has finished. This should be unique for
     * each dimension change. Serialized as an absent optional value when {@code null}.
     *
     * @since v712
     */
    private Integer loadingScreenId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CHANGE_DIMENSION;
    }

    @Override
    public ChangeDimensionPacket clone() {
        try {
            return (ChangeDimensionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
