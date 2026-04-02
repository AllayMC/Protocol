package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.GraphicsOverrideParameterType;

import java.util.Map;

/**
 * Sent by the server to override graphics parameters on the client.
 *
 * @since v859
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class GraphicsParameterOverridePacket implements BedrockPacket {
    /**
     * BiomeIdentifier is the identifier of the biome for which the parameters apply.
     */
    private String biomeIdentifier;
    /**
     * The type of parameter being overridden.
     */
    private GraphicsOverrideParameterType parameterType;
    /**
     * Keyframed parameter values to apply for this override.
     */
    private Map<Float, Vector3f> values;
    /**
     * Reset indicates whether to reset the parameters.
     */
    private boolean reset;
    /**
     * An optional single float graphics parameter to be overridden.
     *
     * @since v924
     */
    @Nullable
    private Float floatValue;
    /**
     * An optional single Vec3 graphics parameter to be overridden.
     *
     * @since v924
     */
    @Nullable
    private Vector3f vec3Value;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GRAPHICS_PARAMETER_OVERRIDE_PACKET;
    }

    @Override
    public GraphicsParameterOverridePacket clone() {
        try {
            return (GraphicsParameterOverridePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
