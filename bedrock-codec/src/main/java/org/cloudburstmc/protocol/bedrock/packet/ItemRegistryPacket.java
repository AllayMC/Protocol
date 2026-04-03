package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;

import java.util.List;

/**
 * Sent by the server to publish the item registry for the current session.
 * This packet provides the client with the item definitions it must use, including any attached
 * client-side component data for custom items.
 * Formerly, this payload was sent as the {@code ItemComponent} packet; protocol v776 (1.21.60)
 * extended it to include the item definitions themselves rather than only component data.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ItemRegistryPacket implements BedrockPacket {

    /**
     * All item definitions available in the session.
     * The registry is expected to be complete for the connected client; omitting built-in items can
     * crash some Bedrock clients. Depending on the protocol version, each entry may also carry the
     * item's runtime ID, version, and custom component data.
     */
    private final List<ItemDefinition> items = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_REGISTRY;
    }

    @Override
    public ItemRegistryPacket clone() {
        try {
            return (ItemRegistryPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
