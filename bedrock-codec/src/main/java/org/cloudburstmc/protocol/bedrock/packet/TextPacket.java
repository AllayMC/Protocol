package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.annotation.NetEaseOnly;

import java.util.List;

/**
 * Sent by the client to the server to send chat messages, and by the server to the client to
 * forward or send messages, which may be chat, popups, tips etc.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TextPacket implements BedrockPacket {
    /**
     * The text category carried by this packet. Clientbound packets may use chat, popup, tip, and
     * translation variants, while clientbound-originated packets should normally use
     * {@link Type#CHAT}.
     */
    private Type type;
    /**
     * The name of the source of the messages. This source is displayed in text types such as the
     * TextTypeChat and TextTypeWhisper, where typically the username is shown.
     */
    private String sourceName;
    /**
     * The message of the packet. This field is set for each TextType and is the main component of
     * the packet.
     */
    private String message;
    /**
     * A list of parameters that should be filled into the message. These parameters are only
     * written if the type of the packet is TextTypeTranslation, TextTypeTip, TextTypePopup or
     * TextTypeJukeboxPopup.
     */
    private List<String> parameters = new ObjectArrayList<>();
    /**
     * Specifies if any of the messages need to be translated. It seems that where % is found in
     * translatable text types, these are translated regardless of this bool. Translatable text
     * types include TextTypeTranslation, TextTypeTip, TextTypePopup and TextTypeJukeboxPopup.
     */
    private boolean needsTranslation;
    /**
     * The XBOX Live user ID of the player that sent the message. It is only set for packets of
     * TextTypeChat. When sent to a player, the player will only be shown the chat message if a
     * player with this XUID is present in the player list and not muted, or if the XUID is empty.
     */
    private String xuid;
    /**
     * An identifier only set for particular platforms when chatting (presumably only for Nintendo
     * Switch). It is otherwise an empty string, and is used to decide which players are able to
     * chat with each other.
     */
    private String platformChatId = "";
    /**
     * A filtered version of Message with all the profanity removed. The client will use this over
     * Message if this field is not empty and they have the "Filter Profanity" setting enabled.
     *
     * @since v685
     */
    private String filteredMessage = "";
    /**
     * Extra trailing text data used by the NetEase client for chat and popup messages.
     */
    @NetEaseOnly
    private String unknown = "";

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TEXT;
    }

    public enum Type {
        RAW,
        CHAT,
        TRANSLATION,
        POPUP,
        JUKEBOX_POPUP,
        TIP,
        SYSTEM,
        WHISPER,
        ANNOUNCEMENT,
        WHISPER_JSON,
        JSON,
        /**
         * @since v554
         */
        ANNOUNCEMENT_JSON
    }

    @Override
    public TextPacket clone() {
        try {
            return (TextPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
