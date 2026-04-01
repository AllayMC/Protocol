package org.cloudburstmc.protocol.bedrock.codec.v898.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.TextSerializer_v685;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextSerializer_v898 extends TextSerializer_v685 {

    public static final TextSerializer_v898 INSTANCE = new TextSerializer_v898();
    private static final Logger log = LoggerFactory.getLogger(TextSerializer_v898.class);

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        TextPacket.Type type = packet.getType();
        buffer.writeBoolean(packet.isNeedsTranslation());
        String text;

        switch (type) {
            case RAW:
            case TIP:
            case SYSTEM:
                buffer.writeByte(0); // MessageOnly
                helper.writeString(buffer, "raw");
                helper.writeString(buffer, "tip");
                helper.writeString(buffer, "systemMessage");
                helper.writeString(buffer, "textObjectWhisper");
                helper.writeString(buffer, "textObjectAnnouncement");
                helper.writeString(buffer, "textObject");
                buffer.writeByte(type.ordinal());
                text = packet.getMessage();
                if (text.isEmpty()) {
                    text = " ";
                    if (log.isDebugEnabled()) {
                        log.debug("TextPacket of " + type + " with empty message");
                    }
                }
                helper.writeString(buffer, text);
                break;
            case JSON:
            case WHISPER_JSON:
            case ANNOUNCEMENT_JSON:
                buffer.writeByte(0); // MessageOnly
                helper.writeString(buffer, "raw");
                helper.writeString(buffer, "tip");
                helper.writeString(buffer, "systemMessage");
                helper.writeString(buffer, "textObjectWhisper");
                helper.writeString(buffer, "textObjectAnnouncement");
                helper.writeString(buffer, "textObject");
                buffer.writeByte(type.ordinal());
                text = packet.getMessage();
                if (text.isEmpty()) {
                    text = " ";
                    if (log.isDebugEnabled()) {
                        log.debug("TextPacket of " + type + " with empty message");
                    }
                }
                helper.writeString(buffer, text);
                break;
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                buffer.writeByte(1); // AuthorAndMessage
                helper.writeString(buffer, "chat");
                helper.writeString(buffer, "whisper");
                helper.writeString(buffer, "announcement");
                buffer.writeByte(type.ordinal());
                helper.writeString(buffer, packet.getSourceName());
                text = packet.getMessage();
                if (text.isEmpty()) {
                    text = " ";
                    if (log.isDebugEnabled()) {
                        log.debug("TextPacket of " + type + " with empty message");
                    }
                }
                helper.writeString(buffer, text);
                break;
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                buffer.writeByte(2); // MessageAndParams
                helper.writeString(buffer, "translate");
                helper.writeString(buffer, "popup");
                helper.writeString(buffer, "jukeboxPopup");
                buffer.writeByte(type.ordinal());
                text = packet.getMessage();
                if (text.isEmpty()) {
                    text = " ";
                    if (log.isDebugEnabled()) {
                        log.debug("TextPacket of " + type + " with empty message");
                    }
                }
                helper.writeString(buffer, text);
                helper.writeArray(buffer, packet.getParameters(), helper::writeString);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TextType " + type);
        }

        helper.writeString(buffer, packet.getXuid());
        helper.writeString(buffer, packet.getPlatformChatId());
        String filtered = packet.getFilteredMessage();
        helper.writeOptional(buffer, (s -> !s.isEmpty()), filtered, (buf, codecHelper, s) -> codecHelper.writeString(buf, s));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        boolean needsTranslation = buffer.readBoolean();
        packet.setNeedsTranslation(needsTranslation);

        switch (buffer.readByte()) {
            case 0: // MessageOnly
                for (int i = 0; i < 6; i++) {
                    helper.readString(buffer);
                }

                TextPacket.Type type = TextPacket.Type.values()[buffer.readUnsignedByte()];
                packet.setType(type);
                packet.setMessage(helper.readString(buffer));
                break;
            case 1: // AuthorAndMessage
                for (int i = 0; i < 3; i++) {
                    helper.readString(buffer);
                }

                packet.setType(TextPacket.Type.values()[buffer.readUnsignedByte()]);
                packet.setSourceName(helper.readString(buffer));
                packet.setMessage(helper.readString(buffer));
                break;
            case 2: // MessageAndParams
                for (int i = 0; i < 3; i++) {
                    helper.readString(buffer);
                }

                packet.setType(TextPacket.Type.values()[buffer.readUnsignedByte()]);
                String text2 = helper.readString(buffer);
                ObjectList<String> parameters = new ObjectArrayList<>();
                helper.readArray(buffer, parameters, helper::readString);
                packet.setMessage(text2);
                packet.setParameters(parameters);
                break;
            default:
                throw new UnsupportedOperationException("Not oneOf<MessageOnly, AuthorAndMessage, MessageAndParams>");
        }

        packet.setXuid(helper.readString(buffer));
        packet.setPlatformChatId(helper.readString(buffer));
        String filtered = helper.readOptional(buffer, "", (buf, codecHelper) -> codecHelper.readString(buf));
        packet.setFilteredMessage(filtered);
    }
}
