package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData;
import org.cloudburstmc.protocol.bedrock.data.command.SoftEnumUpdateType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to update a soft enum, also known as a dynamic enum, previously sent in the
 * AvailableCommands packet. It is sent whenever the enum should get new options or when some of its
 * options should be removed. The UpdateSoftEnum packet will apply for enums that have been set in
 * the AvailableCommands packet with the 'Dynamic' field of the CommandEnum set to true.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateSoftEnumPacket implements BedrockPacket {
    /**
     * The soft enum definition to update. Its name must match the dynamic enum previously sent in
     * {@link AvailableCommandsPacket}.
     */
    private CommandEnumData softEnum;
    /**
     * The kind of update to apply to {@link #softEnum}, such as adding, removing, or replacing
     * options.
     */
    private SoftEnumUpdateType type;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_SOFT_ENUM;
    }

    @Override
    public UpdateSoftEnumPacket clone() {
        try {
            return (UpdateSoftEnumPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
