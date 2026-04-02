package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ExperimentData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to send the order in which resource packs and behavior packs should be applied
 * (and downloaded) by the client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackStackPacket implements BedrockPacket {
    /**
     * Specifies if the client must accept the server resource packs in order to join. If true, the
     * client may only proceed by downloading the packs or cancelling the connection.
     */
    private boolean forcedToAccept;
    /**
     * The behaviour packs that should be applied before any resource packs. The order in this list
     * is the order in which the client applies them.
     */
    private final List<Entry> behaviorPacks = new ObjectArrayList<>();
    /**
     * The resource packs that the client should download and apply. The order in this list is the
     * order in which they are applied.
     */
    private final List<Entry> resourcePacks = new ObjectArrayList<>();
    /**
     * Holds a list of experiments that are either enabled or disabled in the world that the player
     * spawns in. It is not clear why experiments are sent both here and in the StartGame packet.
     */
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    /**
     * BaseGameVersion is the vanilla version that the client should set its resource pack stack to.
     *
     * @since v388
     */
    private String gameVersion;
    /**
     * Specifies if any experiments were previously toggled in this world. It is probably used for
     * some kind of metrics.
     *
     * @since v419
     */
    private boolean experimentsPreviouslyToggled;
    /**
     * IncludeEditorPacks specifies if vanilla editor packs should be included in the resource pack
     * stack when connecting to an editor world.
     *
     * @since v671
     */
    private boolean hasEditorPacks;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_STACK;
    }

    /**
     * A single pack entry in the stack.
     *
     * @param packId The UUID string that identifies the pack.
     * @param packVersion The version string of the pack.
     * @param subPackName The optional sub-pack to apply for packs that expose multiple sub-packs.
     */
    public record Entry(String packId, String packVersion, String subPackName) {
    }

    @Override
    public ResourcePackStackPacket clone() {
        try {
            return (ResourcePackStackPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
