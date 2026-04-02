package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server when a player picks up an item entity. It makes the item entity disappear to
 * viewers and shows the pick-up animation.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TakeItemEntityPacket implements BedrockPacket {
    /**
     * ItemEntityRuntimeID is the entity runtime ID of the item that is being taken by another
     * entity. It will disappear to viewers after showing the pick-up animation.
     */
    private long itemRuntimeEntityId;
    /**
     * The runtime ID of the entity that picked up the item. This is usually a player, but other
     * entities such as mobs may also take item entities.
     */
    private long runtimeEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TAKE_ITEM_ENTITY;
    }

    @Override
    public TakeItemEntityPacket clone() {
        try {
            return (TakeItemEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
