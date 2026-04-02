package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistCategories;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistCategory;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistOperation;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraAimAssistPresetDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to the client to provide a list of categories and presets that can be used
 * when sending a CameraAimAssist packet or a CameraInstruction including aim assist.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraAimAssistPresetsPacket implements BedrockPacket {
    /**
     * A list of categories which can be referenced by one of the Presets.
     *
     * @deprecated since v800 (1.21.80). Use {@link #categoryDefinitions} instead.
     */
    @Deprecated
    private final List<CameraAimAssistCategories> categories = new ObjectArrayList<>();
    /**
     * A list of presets which define a base for how aim assist should behave.
     */
    private final List<CameraAimAssistPresetDefinition> presets = new ObjectArrayList<>();
    /**
     * The operation to perform with the preset definitions, such as replacing or appending them.
     *
     * @since v776
     */
    private CameraAimAssistOperation operation;
    /**
     * Category definitions referenced by the presets. This supersedes the deprecated legacy
     * {@link #categories} list.
     *
     * @since v800 (1.21.80)
     */
    private final List<CameraAimAssistCategory> categoryDefinitions = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_AIM_ASSIST_PRESETS;
    }

    @Override
    public CameraAimAssistPresetsPacket clone() {
        try {
            return (CameraAimAssistPresetsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
