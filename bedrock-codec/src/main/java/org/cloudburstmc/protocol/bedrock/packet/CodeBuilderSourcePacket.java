package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.codebuilder.CodeBuilderCategoryType;
import org.cloudburstmc.protocol.bedrock.data.codebuilder.CodeBuilderCodeStatus;
import org.cloudburstmc.protocol.bedrock.data.codebuilder.CodeBuilderOperationType;

/**
 * An Education Edition packet sent by the client to the server to run an operation with a code
 * builder.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CodeBuilderSourcePacket implements BedrockPacket {
    /**
     * Distinguishes the Code Builder operation being performed.
     */
    private CodeBuilderOperationType operation;
    /**
     * Distinguishes the category of the Code Builder operation being performed.
     */
    private CodeBuilderCategoryType category;
    /**
     * Legacy string payload associated with the operation.
     *
     * @deprecated since v685
     */
    @Deprecated
    private String value;
    /**
     * The current Code Builder status reported with the operation.
     *
     * @since v685
     */
    private CodeBuilderCodeStatus codeStatus;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CODE_BUILDER_SOURCE;
    }

    @Override
    public CodeBuilderSourcePacket clone() {
        try {
            return (CodeBuilderSourcePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
