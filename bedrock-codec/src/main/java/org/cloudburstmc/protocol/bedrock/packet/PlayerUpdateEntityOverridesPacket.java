package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to modify an entity's properties individually.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerUpdateEntityOverridesPacket implements BedrockPacket {
    /**
     * The unique ID of the entity. The unique ID is a value that remains consistent across
     * different sessions of the same world, but most servers simply fill the runtime ID of the
     * entity out for this field.
     */
    private long entityUniqueId;
    /**
     * The index of the property to modify. The index is unique for each property of an entity.
     */
    private int propertyIndex;
    /**
     * The kind of override change to apply.
     */
    private UpdateType updateType;
    /**
     * The new integer value of the property. It is only used when Type is set to
     * PlayerUpdateEntityOverridesTypeInt.
     */
    private int intValue;
    /**
     * The new float value of the property. It is only used when Type is set to
     * PlayerUpdateEntityOverridesTypeFloat.
     */
    private float floatValue;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_UPDATE_ENTITY_OVERRIDES;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PlayerUpdateEntityOverridesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum UpdateType { // TODO: changelog says it's now None|Neighbors|Connections
        /**
         * Clear all overrides for the property.
         */
        CLEAR_OVERRIDES,
        /**
         * Remove the existing override for the property.
         */
        REMOVE_OVERRIDE,
        /**
         * Set the property override to the value in {@link PlayerUpdateEntityOverridesPacket#intValue}.
         */
        SET_INT_OVERRIDE,
        /**
         * Set the property override to the value in {@link PlayerUpdateEntityOverridesPacket#floatValue}.
         */
        SET_FLOAT_OVERRIDE
    }
}
