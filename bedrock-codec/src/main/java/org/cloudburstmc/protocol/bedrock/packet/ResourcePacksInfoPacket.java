package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;
import java.util.UUID;

/**
 * Sent by the server to inform the client on what resource packs the server has. It sends a list of
 * the resource packs it has and basic information on them like the version and description.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePacksInfoPacket implements BedrockPacket {
    /**
     * Legacy behaviour-pack entries announced by the server.
     *
     * @deprecated since v729
     */
    @Deprecated
    private final List<Entry> behaviorPackInfos = new ObjectArrayList<>();

    /**
     * Resource-pack entries the client may need before joining.
     */
    private final List<Entry> resourcePackInfos = new ObjectArrayList<>();
    /**
     * Whether the client must accept the advertised server packs in order to continue joining.
     */
    private boolean forcedToAccept;
    /**
     * Whether any advertised packs require scripting support.
     *
     * @since v332
     */
    private boolean scriptingEnabled;
    /**
     * Whether the server explicitly forces its packs to remain enabled.
     *
     * @since v448
     * @deprecated since v729
     */
    @Deprecated
    private boolean forcingServerPacksEnabled;
    /**
     * Whether any advertised packs include add-on content.
     *
     * @since v662
     */
    private boolean hasAddonPacks;
    /**
     * WorldTemplateUUID is the UUID of the template that has been used to generate the world.
     * Templates can be downloaded from the marketplace or installed via '.mctemplate' files. If the
     * world was not generated from a template, this field is empty.
     *
     * @since v766
     */
    private UUID worldTemplateId;
    /**
     * The version of the world template that has been used to generate the world. If the world was
     * not generated from a template, this field is empty.
     *
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
        /**
         * The UUID of the pack entry.
         */
        private UUID packId;
        /**
         * The version string of the pack entry.
         */
        private String packVersion;
        /**
         * The compressed size of the pack in bytes.
         */
        private long packSize;
        /**
         * Marketplace/content key information used when downloading protected packs.
         */
        private String contentKey;
        /**
         * The selected sub-pack name, or an empty string for the default sub-pack.
         */
        private String subPackName;
        /**
         * Content identifier supplied by the marketplace/backend for this pack.
         */
        private String contentId;
        /**
         * Whether this pack contains scripts.
         *
         * @since v332
         */
        private boolean scripting;
        /**
         * Whether this pack can enable ray tracing assets on supported clients.
         *
         * @since v422
         */
        private boolean raytracingCapable;
        /**
         * CDN URL from which the client may fetch this pack directly.
         *
         * @since v618
         */
        private String cdnUrl;
        /**
         * Whether this pack is an add-on pack rather than a pure resource pack.
         *
         * @since v712
         */
        private boolean addonPack;
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
