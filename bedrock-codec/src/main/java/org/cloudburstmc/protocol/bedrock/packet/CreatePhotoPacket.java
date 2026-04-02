package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A packet that allows players to export photos from their portfolios into items in their
 * inventory. This packet only works on the Education Edition version of Minecraft.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CreatePhotoPacket implements BedrockPacket {
    /**
     * The unique ID of the entity creating the photo item.
     */
    private long id;
    /**
     * The name of the photo.
     */
    private String photoName;
    /**
     * The item name shown for the created photo item.
     */
    private String photoItemName;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CREATE_PHOTO;
    }

    @Override
    public CreatePhotoPacket clone() {
        try {
            return (CreatePhotoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
