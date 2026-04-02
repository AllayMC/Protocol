package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.Optional;

/**
 * A packet sent by the server to update Minecraft: Education Edition related settings. It is unused
 * by the normal base game.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EducationSettingsPacket implements BedrockPacket {
    /**
     * CodeBuilderDefaultURI is the default URI that the code builder is ran on. Using this, a Code
     * Builder program can make code directly affect the server.
     */
    private String codeBuilderUri;
    /**
     * Specifies whether the world has an Education Edition quiz attached to it.
     */
    private boolean quizAttached;
    /**
     * The title of the code builder shown when connected to the CodeBuilderDefaultURI.
     *
     * @since v407
     */
    private String codeBuilderTitle;
    /**
     * Specifies if clients connected to the world should be able to resize the code builder when it
     * is opened.
     *
     * @since v407
     */
    private boolean canResizeCodeBuilder;
    /**
     * An optional URI that overrides {@link #codeBuilderUri} for the current session.
     *
     * @since v407
     */
    private Optional<String> overrideUri;
    /**
     * Specifies whether the older legacy title bar should be hidden when opening Code Builder.
     *
     * @since v465
     */
    private boolean disableLegacyTitle;
    /**
     * The name of the post-process filter that should be applied while the education tools are
     * active.
     *
     * @since v465
     */
    private String postProcessFilter;
    /**
     * The resource path to the screenshot border shown by the education screenshot flow.
     *
     * @since v465
     */
    private String screenshotBorderPath;
    /**
     * Optional Education Edition setting that controls whether clients may modify world blocks.
     *
     * @since v465
     */
    private OptionalBoolean entityCapabilities;
    /**
     * Optional flag controlling whether external links from education content are enabled.
     *
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
