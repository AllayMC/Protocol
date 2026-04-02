package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

/**
 * Sent by the client every time it joins the server and when it equips new emotes. It may be used
 * by the server to find out which emotes the client has available. If the player has no emotes
 * equipped, this packet is not sent. Under certain circumstances, this packet is also sent from the
 * server to the client, but I was unable to find when this is done.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EmoteListPacket implements BedrockPacket {
    /**
     * PlayerRuntimeID is the runtime ID of the player that owns the emote pieces below. If sent by
     * the client, this player runtime ID is always that of the player itself.
     */
    private long runtimeEntityId;
    /**
     * The list of emote piece identifiers currently equipped by the player.
     */
    private final List<UUID> pieceIds = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EMOTE_LIST;
    }

    @Override
    public EmoteListPacket clone() {
        try {
            return (EmoteListPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
