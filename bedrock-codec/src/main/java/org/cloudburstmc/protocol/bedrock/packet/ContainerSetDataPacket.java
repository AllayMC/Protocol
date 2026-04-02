package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to update specific data of a single container, meaning a block such as a
 * furnace or a brewing stand. This data is usually used by the client to display certain features
 * client-side.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ContainerSetDataPacket implements BedrockPacket {

    public static final int FURNACE_TICK_COUNT = 0;
    public static final int FURNACE_LIT_TIME = 1;
    public static final int FURNACE_LIT_DURATION = 2;
    public static final int FURNACE_STORED_XP = 3;
    public static final int FURNACE_FUEL_AUX = 4;

    public static final int BREWING_STAND_BREW_TIME = 0;
    public static final int BREWING_STAND_FUEL_AMOUNT = 1;
    public static final int BREWING_STAND_FUEL_TOTAL = 2;

    /**
     * The ID of the window that should have its data set. The player must have a window open with
     * the window ID passed, or nothing will happen.
     */
    private byte windowId;
    /**
     * The property key to update. Its meaning depends on the kind of container and matches the
     * constants declared in this class.
     */
    private int property;
    /**
     * The value of the property. Its use differs per property.
     */
    private int value;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CONTAINER_SET_DATA;
    }

    @Override
    public ContainerSetDataPacket clone() {
        try {
            return (ContainerSetDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
