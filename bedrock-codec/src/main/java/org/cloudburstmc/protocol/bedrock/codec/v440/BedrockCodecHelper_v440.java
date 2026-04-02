package org.cloudburstmc.protocol.bedrock.codec.v440;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v431.BedrockCodecHelper_v431;
import org.cloudburstmc.protocol.bedrock.data.GameRuleData;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureAnimationMode;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureMirror;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRotation;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.util.Preconditions;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class BedrockCodecHelper_v440 extends BedrockCodecHelper_v431 {

    public BedrockCodecHelper_v440(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                   TypeMap<ContainerSlotType> containerSlotTypes) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes);
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(gameRule, "gameRule");

        Object value = gameRule.value();
        int id = this.gameRuleType.getId(value.getClass());

        writeString(buffer, gameRule.name());
        buffer.writeBoolean(gameRule.editable());
        VarInts.writeUnsignedInt(buffer, id);
        switch (id) {
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                VarInts.writeUnsignedInt(buffer, (int) value);
                break;
            case 3:
                buffer.writeFloatLE((float) value);
                break;
        }
    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        String name = readString(buffer);
        boolean editable = buffer.readBoolean();
        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 1:
                return new GameRuleData<>(name, editable, buffer.readBoolean());
            case 2:
                return new GameRuleData<>(name, editable, VarInts.readUnsignedInt(buffer));
            case 3:
                return new GameRuleData<>(name, editable, buffer.readFloatLE());
        }
        throw new IllegalStateException("Invalid gamerule type received");
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        String paletteName = this.readString(buffer);
        boolean ignoringEntities = buffer.readBoolean();
        boolean ignoringBlocks = buffer.readBoolean();
        Vector3i size = this.readBlockPosition(buffer);
        Vector3i offset = this.readBlockPosition(buffer);
        long lastEditedByEntityId = VarInts.readLong(buffer);
        StructureRotation rotation = StructureRotation.from(buffer.readByte());
        StructureMirror mirror = StructureMirror.from(buffer.readByte());
        StructureAnimationMode animationMode = StructureAnimationMode.from(buffer.readUnsignedByte());
        float animationSeconds = buffer.readFloatLE();
        float integrityValue = buffer.readFloatLE();
        int integritySeed = buffer.readIntLE();
        Vector3f pivot = this.readVector3f(buffer);

        return new StructureSettings(ignoringEntities, ignoringBlocks, size, offset, rotation, mirror, integrityValue,
                integritySeed, paletteName, lastEditedByEntityId, pivot, animationMode, animationSeconds, true);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        this.writeString(buffer, settings.paletteName());
        buffer.writeBoolean(settings.ignoringEntities());
        buffer.writeBoolean(settings.ignoringBlocks());
        this.writeBlockPosition(buffer, settings.size());
        this.writeBlockPosition(buffer, settings.offset());
        VarInts.writeLong(buffer, settings.lastEditedByEntityId());
        buffer.writeByte(settings.rotation().ordinal());
        buffer.writeByte(settings.mirror().ordinal());
        buffer.writeByte(settings.animationMode().ordinal());
        buffer.writeFloatLE(settings.animationSeconds());
        buffer.writeFloatLE(settings.integrityValue());
        buffer.writeIntLE(settings.integritySeed());
        this.writeVector3f(buffer, settings.pivot());
    }
}
