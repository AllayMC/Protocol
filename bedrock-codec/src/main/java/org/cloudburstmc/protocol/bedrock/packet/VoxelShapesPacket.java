package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.SerializableVoxelShape;

import java.util.List;
import java.util.Map;

/**
 * Sent by the server to send voxel shape data to the client.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class VoxelShapesPacket implements BedrockPacket {
    /**
     * The voxel shapes known to the client.
     */
    private List<SerializableVoxelShape> shapes;
    /**
     * Maps shape names to the numeric IDs used by the voxel shape list.
     */
    private Map<String, Integer> nameMap;
    /**
     * The number of custom shapes.
     *
     * @since v944
     */
    private int customShapeCount;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.VOXEL_SHAPES;
    }

    @Override
    public VoxelShapesPacket clone() {
        try {
            return (VoxelShapesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
