package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Data;
import lombok.experimental.NonFinal;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents base item data used in the Bedrock protocol.
 */
@Data
final class BaseItemData implements ItemData {
    static final String[] EMPTY_ARRAY = new String[0];
    /**
     * The item definition.
     */
    private ItemDefinition definition;
    /**
     * The damage.
     */
    private final int damage;
    /**
     * The count.
     */
    private final int count;
    /**
     * The tag.
     */
    private final NbtMap tag;
    /**
     * The blocks this item can be placed on.
     */
    private final String[] canPlace;
    /**
     * The blocks this item can break.
     */
    private final String[] canBreak;
    /**
     * The blocking ticks.
     */
    private final long blockingTicks;
    /**
     * The block definition.
     */
    private final BlockDefinition blockDefinition;
    /**
     * Whether this item uses a net ID.
     */
    @NonFinal
    private boolean usingNetId;
    /**
     * The net ID.
     */
    private int netId;

    BaseItemData(ItemDefinition definition, int damage, int count, NbtMap tag, String[] canPlace, String[] canBreak, long blockingTicks, BlockDefinition blockDefinition, boolean hasNetId, int netId) {
        this.definition = definition;
        this.damage = damage;
        this.count = count;
        this.tag = tag;
        this.canPlace = canPlace == null ? EMPTY_ARRAY : canPlace;
        this.canBreak = canBreak == null ? EMPTY_ARRAY : canBreak;
        this.blockingTicks = blockingTicks;
        this.blockDefinition = blockDefinition;
        this.netId = netId;
        this.usingNetId = hasNetId;
    }

    public boolean isValid() {
        return !isNull() && definition != null && definition != ItemDefinition.AIR;
    }

    public boolean isNull() {
        return count <= 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition, damage, count, tag, Arrays.hashCode(canPlace), Arrays.hashCode(canBreak), blockingTicks,
                blockDefinition);
    }

    public boolean equals(ItemData other, boolean checkAmount, boolean checkMetadata, boolean checkUserdata) {
        return definition == other.getDefinition() &&
               (!checkAmount || count == other.getCount()) &&
               (!checkMetadata || (damage == other.getDamage() && blockingTicks == other.getBlockingTicks())) &&
               (!checkUserdata || (Objects.equals(tag, other.getTag()) && Arrays.equals(canPlace, other.getCanPlace()) && Arrays.equals(canBreak, other.getCanBreak())));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ItemData)) return false;
        return equals((ItemData) obj, true, true, true);
    }
}
