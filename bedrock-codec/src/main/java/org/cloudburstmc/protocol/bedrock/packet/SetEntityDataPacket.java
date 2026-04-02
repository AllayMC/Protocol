package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;

/**
 * Sent by the server to update the entity metadata of an entity. It includes flags such as if the
 * entity is on fire, but also properties such as the air it has left until it starts drowning.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetEntityDataPacket implements BedrockPacket {
    /**
     * A map of entity metadata, which includes flags and data properties that alter in particular
     * the way the entity looks. Flags include ones such as 'on fire' and 'sprinting'. The metadata
     * values are indexed by their property key.
     */
    private EntityDataMap metadata = new EntityDataMap();
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The server tick at which the packet was sent. It is used in relation to
     * {@link CorrectPlayerMovePredictionPacket} so the client can correlate metadata changes with
     * server-authoritative movement corrections.
     *
     * @since v419
     */
    private long tick;
    /**
     * A list of properties that the entity inhibits. These properties define and alter specific
     * attributes of the entity.
     *
     * @since v557
     */
    private EntityProperties properties = new EntityProperties();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_ENTITY_DATA;
    }

    @Override
    public SetEntityDataPacket clone() {
        try {
            return (SetEntityDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
