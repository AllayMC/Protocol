package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the client to the server to send chat messages, and by the server to the client to
 * forward or send messages, which may be chat, popups, tips etc.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TextPacket implements BedrockPacket {
    private Type type;
    private String sourceName;
    private String message;
    private List<String> parameters = new ObjectArrayList<>();
    private boolean needsTranslation;
    private String xuid;
    private String platformChatId = "";
    /**
     * @since v685
     */
    private String filteredMessage = "";

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
