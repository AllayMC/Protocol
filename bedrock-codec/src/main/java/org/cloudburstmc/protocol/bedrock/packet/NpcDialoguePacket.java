package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet that allows the client to display dialog boxes for interacting with NPCs.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class NpcDialoguePacket implements BedrockPacket {

    /**
     * The unique ID of the NPC being requested.
     */
    private long uniqueEntityId;
    /**
     * The type of action for the packet.
     */
    private Action action;
    /**
     * The text that the client should see.
     */
    private String dialogue;
    /**
     * The identifier of the scene. If this is left empty, the client will use the last scene sent
     * to it. https://docs.microsoft.com/en-us/minecraft/creator/documents/npcdialogue.
     */
    private String sceneName;
    /**
     * NPCName is the name of the NPC to be displayed to the client.
     */
    private String npcName;
    /**
     * The JSON definition of the buttons or actions shown in the NPC dialogue screen.
     */
    private String actionJson;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NPC_DIALOGUE;
    }

    public enum Action {
        OPEN,
        CLOSE
    }

    @Override
    public NpcDialoguePacket clone() {
        try {
            return (NpcDialoguePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
