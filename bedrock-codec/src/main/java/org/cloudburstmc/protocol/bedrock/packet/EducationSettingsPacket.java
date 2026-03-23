package org.cloudburstmc.protocol.bedrock.packet;

import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

/**
 * A packet sent by the server to update Minecraft: Education Edition related settings. It is unused
 * by the normal base game.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EducationSettingsPacket implements BedrockPacket {
    private String codeBuilderUri;

    /**
     * @since v407
     */
    private String codeBuilderTitle;

    /**
     * @since v407
     */
    private boolean canResizeCodeBuilder;

    /**
     * @since v465
     */
    private boolean disableLegacyTitle;

    /**
     * @since v465
     */
    private String postProcessFilter;

    /**
     * @since v465
     */
    private String screenshotBorderPath;

    /**
     * @since v465
     */
    private OptionalBoolean entityCapabilities;

    /**
     * @since v407
     */
    private Optional<String> overrideUri;

    private boolean quizAttached;

    /**
     * @since v465
     */
    private OptionalBoolean externalLinkSettings;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EDUCATION_SETTINGS;
    }

    @Override
    public EducationSettingsPacket clone() {
        try {
            return (EducationSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
