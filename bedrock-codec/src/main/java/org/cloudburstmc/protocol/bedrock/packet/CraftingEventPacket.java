package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;
import java.util.UUID;

/**
 * Sent by the client when it crafts a particular item. This packet may be fully ignored, as the
 * InventoryTransaction packet provides all the information required.
 *
 * @since v630
 * @deprecated Use {@link InventoryTransactionPacket} instead.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class CraftingEventPacket implements BedrockPacket {
    /**
     * The item stacks consumed by the crafting action.
     */
    private final List<ItemData> inputs = new ObjectArrayList<>();
    /**
     * The item stacks produced by the crafting action.
     */
    private final List<ItemData> outputs = new ObjectArrayList<>();
    /**
     * The container window ID where crafting took place.
     */
    private byte containerId;
    /**
     * The crafting context used for the recipe.
     */
    private CraftingType type;
    /**
     * The UUID of the crafted recipe.
     */
    private UUID uuid;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_EVENT;
    }

    @Override
    public CraftingEventPacket clone() {
        try {
            return (CraftingEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
