package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to update a client-side mob property value.
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ChangeMobPropertyPacket implements BedrockPacket {
    /**
     * The unique ID of the entity whose property is being changed.
     */
    private long uniqueEntityId;
    /**
     * The name of the property being updated.
     */
    private String property;
    /**
     * The value to use when the property is a boolean.
     */
    private boolean boolValue;
    /**
     * The value to use when the property is a string.
     */
    private String stringValue;
    /**
     * The value to use when the property is an integer.
     */
    private int intValue;
    /**
     * The value to use when the property is a float.
     */
    private float floatValue;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CHANGE_MOB_PROPERTY;
    }

    @Override
    public ChangeMobPropertyPacket clone() {
        try {
            return (ChangeMobPropertyPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
