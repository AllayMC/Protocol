package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;

/**
 * Sent by the server to add a volume entity to the client. Volume entities describe bounded areas
 * such as fog or trigger volumes together with the metadata required to interpret them.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class AddVolumeEntityPacket implements BedrockPacket {
    /**
     * The runtime identifier of the volume entity instance.
     */
    private int id;
    /**
     * The NBT definition and metadata of the volume entity.
     */
    private NbtMap data;
    /**
     * The engine version the entity is using, for example, '1.17.0'.
     *
     * @since v465
     */
    private String engineVersion;
    /**
     * The string identifier of the volume entity type.
     *
     * @since v486
     */
    private String identifier;
    /**
     * The instance name used to distinguish this volume from other instances of the same
     * identifier.
     *
     * @since v486
     */
    private String instanceName;
    /**
     * The minimum block bounds of the volume, inclusive.
     *
     * @since v503
     */
    private Vector3i minBounds;
    /**
     * The maximum block bounds of the volume, inclusive.
     *
     * @since v503
     */
    private Vector3i maxBounds;
    /**
     * The dimension in which the volume exists.
     *
     * @since v503
     */
    private int dimension;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_VOLUME_ENTITY;
    }

    @Override
    public AddVolumeEntityPacket clone() {
        try {
            return (AddVolumeEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
