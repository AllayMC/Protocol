package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.annotation.NetEaseOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Sent by NetEase servers to confirm which custom skins have passed censorship checks.
 */
@NetEaseOnly
@Data
@EqualsAndHashCode(doNotUseGetters = true)
public class ConfirmSkinPacket implements BedrockPacket {
    private List<SkinEntry> entries = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CONFIRM_SKIN;
    }

    @Override
    public ConfirmSkinPacket clone() {
        try {
            return (ConfirmSkinPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Data
    public static class SkinEntry {
        private boolean valid;
        private UUID uuid;
        private byte[] skinBytes;
        private String uidStr;
        private String geoStr;
    }
}
