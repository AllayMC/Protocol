package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to update the entity metadata of an entity. It includes flags such as if the
 * entity is on fire, but also properties such as the air it has left until it starts drowning.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetEntityDataPacket implements BedrockPacket {
    private EntityDataMap metadata = new EntityDataMap();
    private long runtimeEntityId;
    /**
     * @since v419
     */
    private long tick;
    /**
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
