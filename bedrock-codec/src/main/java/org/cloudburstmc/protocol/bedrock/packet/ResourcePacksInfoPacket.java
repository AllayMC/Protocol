package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to inform the client on what resource packs the server has. It sends a list of
 * the resource packs it has and basic information on them like the version and description.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePacksInfoPacket implements BedrockPacket {
    /**
     * @deprecated since v729
     */
    private final List<Entry> behaviorPackInfos = new ObjectArrayList<>();

    private final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    private boolean forcedToAccept;

    /**
     * @since v662
     */
    private boolean hasAddonPacks;

    /**
     * @since v332
     */
    private boolean scriptingEnabled;

    /**
     * @since v448
     * @deprecated since v729
     */
    private boolean forcingServerPacksEnabled;

    /**
     * @since v766
     */
    private UUID worldTemplateId;

    /**
     * @since v766
     */
    private String worldTemplateVersion;

    /**
     * Force the client to disable vibrant visuals, even if the client supports it.
     *
     * @since v818
     */
    private boolean vibrantVisualsForceDisabled;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACKS_INFO;
    }

    @Data
    @AllArgsConstructor
    public static class Entry {
        private UUID packId;
        private String packVersion;
        private long packSize;
        private String contentKey;
        private String subPackName;
        private String contentId;

        /**
         * @since v332
         */
        private boolean scripting;

        /**
         * @since v422
         */
        private boolean raytracingCapable;

        /**
         * @since v712
         */
        private boolean addonPack;

        /**
         * @since v618
         */
        private String cdnUrl;
    }

    @Override
    public ResourcePacksInfoPacket clone() {
        try {
            return (ResourcePacksInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
