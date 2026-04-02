package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.NpcRequestType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it interacts with an NPC. The packet is specifically made for Education
 * Edition, where NPCs are available to use.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NpcRequestPacket implements BedrockPacket {
    /**
     * The runtime ID of the NPC entity that the player interacted with. It is the same as sent by
     * the server when spawning the entity.
     */
    private long runtimeEntityId;
    /**
     * The type of the request, which depends on the permission that the player has. It will be
     * either a type that indicates that the NPC should show its dialog, or that it should open the
     * editing window.
     */
    private NpcRequestType requestType;
    /**
     * The command string configured on the NPC. This may contain multiple commands, depending on
     * what action the player selected in the NPC UI.
     */
    private String command;
    /**
     * The type of the action to execute.
     */
    private int actionType;
    /**
     * The name of the scene. This can be left empty to specify the last scene that the player was
     * sent.
     *
     * @since v448
     */
    private String sceneName;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NPC_REQUEST;
    }

    @Override
    public NpcRequestPacket clone() {
        try {
            return (NpcRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
