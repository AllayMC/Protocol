package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;

/**
 * Sent by the server to update the trades offered by a villager to a player. It is sent at the
 * moment that a player interacts with a villager.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateTradePacket implements BedrockPacket {
    /**
     * The ID of the trading window that the client currently has open.
     */
    private int containerId;
    /**
     * The type of the trading window. In vanilla this is typically the trading container type.
     */
    private ContainerType containerType;
    /**
     * The number of trades encoded in {@link #offers}. Modern clients generally hardcode this to
     * {@code 0}, but the field remains part of the packet.
     */
    private int size; // Hardcoded to 0

    /**
     * The unique ID of the trader entity, typically the villager whose offers are being shown.
     * {@link #tradeTier} applies to this trader.
     */
    private long traderUniqueEntityId;
    /**
     * The unique ID of the entity, usually the player, for which these trades are being updated.
     * The offers may apply only to this entity.
     */
    private long playerUniqueEntityId;
    /**
     * The name displayed at the top of the trading UI. It is usually used to represent the
     * profession of the villager in the UI.
     */
    private String displayName;
    /**
     * The network NBT compound that contains the serialised trade offers.
     */
    private NbtMap offers;
    /**
     * Specifies if a new trade was added as part of this update.
     */
    private boolean recipeAddedOnUpdate;
    /**
     * Specifies if demand-based pricing should be used for the trade offers.
     */
    private boolean usingEconomyTrade;
    /**
     * The tier of the villager that the player is trading with. The tier starts at 0 with a first
     * two offers being available, after which two additional offers are unlocked each time the tier
     * becomes one higher.
     *
     * @since v313
     */
    private int tradeTier;
    /**
     * NewTradeUI specifies if the villager should be using the new trade UI (The one added in
     * 1.11.) rather than the old one. This should usually be set to true.
     *
     * @since v313
     */
    private boolean newTradingUi;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_TRADE;
    }

    @Override
    public UpdateTradePacket clone() {
        try {
            return (UpdateTradePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
