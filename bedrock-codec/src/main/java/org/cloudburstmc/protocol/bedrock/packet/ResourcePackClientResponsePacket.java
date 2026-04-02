package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the client in response to resource packets sent by the server. It is used to let the
 * server know what action needs to be taken for the client to have all resource packs ready and
 * set.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackClientResponsePacket implements BedrockPacket {
    /**
     * The resource-pack identifiers referenced by the current {@link #status}. For
     * {@link Status#SEND_PACKS}, each entry is the pack UUID combined with its version.
     */
    private final List<String> packIds = new ObjectArrayList<>();
    /**
     * The client's current state in the resource-pack download/acceptance flow.
     */
    private Status status;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_CLIENT_RESPONSE;
    }

    public enum Status {
        /**
         * Default/unset status.
         */
        NONE,
        /**
         * The client refused the required packs.
         */
        REFUSED,
        /**
         * The server should send the packs listed in {@link #packIds}.
         */
        SEND_PACKS,
        /**
         * The client has downloaded every required pack.
         */
        HAVE_ALL_PACKS,
        /**
         * The client has finished applying the pack set.
         */
        COMPLETED
    }

    @Override
    public ResourcePackClientResponsePacket clone() {
        try {
            return (ResourcePackClientResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
