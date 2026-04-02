package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make a title, subtitle or action bar shown to a player. It has several
 * fields that allow setting the duration of the titles.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetTitlePacket implements BedrockPacket {
    /**
     * The title action the client should perform, such as setting the title text, subtitle, action
     * bar, or timings.
     */
    private Type type;
    /**
     * The text of the title, which has a different meaning depending on the ActionType that the
     * packet has. The text is the text of a title, subtitle or action bar, depending on the type
     * set.
     */
    private String text;
    /**
     * FadeInDuration is the duration that the title takes to fade in on the screen of the player.
     * It is measured in 20ths of a second (AKA in ticks).
     */
    private int fadeInTime;
    /**
     * The duration that the title should remain visible before fading out, measured in ticks.
     */
    private int stayTime;
    /**
     * FadeOutDuration is the duration that the title takes to fade out of the screen of the player.
     * It is measured in 20ths of a second (AKA in ticks).
     */
    private int fadeOutTime;
    /**
     * The XBOX Live user ID of the player, which will remain consistent as long as the player is
     * logged in with the XBOX Live account. It is empty if the user is not logged into its XBL
     * account.
     *
     * @since v448
     */
    private String xuid;
    /**
     * Either the platform-specific online ID of the player or an empty string.
     *
     * @since v448
     */
    private String platformOnlineId;
    /**
     * A profanity-filtered version of {@link #text}. Clients with text filtering enabled prefer
     * this value when it is present.
     *
     * @since v712
     */
    private String filteredTitleText = "";

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_TITLE;
    }

    public enum Type {
        CLEAR,
        RESET,
        TITLE,
        SUBTITLE,
        ACTIONBAR,
        TIMES,
        TITLE_JSON,
        SUBTITLE_JSON,
        ACTIONBAR_JSON
    }

    @Override
    public SetTitlePacket clone() {
        try {
            return (SetTitlePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
