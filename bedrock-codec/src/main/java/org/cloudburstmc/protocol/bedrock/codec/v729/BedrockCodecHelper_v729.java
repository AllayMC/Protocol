package org.cloudburstmc.protocol.bedrock.codec.v729;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v712.BedrockCodecHelper_v712;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class BedrockCodecHelper_v729 extends BedrockCodecHelper_v712 {

    public BedrockCodecHelper_v729(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                   TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public void writeFullContainerName(ByteBuf buffer, FullContainerName containerName) {
        this.writeContainerSlotType(buffer, containerName.container());
        this.writeOptionalNull(buffer, containerName.dynamicId(), ByteBuf::writeIntLE);
    }

    @Override
    public FullContainerName readFullContainerName(ByteBuf buffer) {
        ContainerSlotType container = this.readContainerSlotType(buffer);
        Integer dynamicId = this.readOptional(buffer, null, ByteBuf::readIntLE);
        return new FullContainerName(container, dynamicId);
    }
}