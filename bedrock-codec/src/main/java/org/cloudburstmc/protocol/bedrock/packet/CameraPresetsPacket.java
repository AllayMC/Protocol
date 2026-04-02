package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;

import java.util.List;

/**
 * Sent by the server to register custom camera presets on the client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraPresetsPacket implements BedrockPacket {
    /**
     * The presets available for later camera instructions. Order matters because instructions
     * reference presets by index.
     */
    private final List<CameraPreset> presets = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_PRESETS;
    }

    @Override
    public CameraPresetsPacket clone() {
        try {
            return (CameraPresetsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
