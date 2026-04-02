package org.cloudburstmc.protocol.bedrock.codec.v776.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v554.serializer.StructureBlockUpdateSerializer_v554;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureBlockType;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureEditorData;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRedstoneSaveMode;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StructureBlockUpdateSerializer_v776 extends StructureBlockUpdateSerializer_v554 {
    public static final StructureBlockUpdateSerializer_v776 INSTANCE = new StructureBlockUpdateSerializer_v776();

    @Override
    protected StructureEditorData readEditorData(ByteBuf buffer, BedrockCodecHelper helper) {
        String name = helper.readString(buffer);
        String filteredName = helper.readString(buffer);
        String dataField = helper.readString(buffer);
        boolean includingPlayers = buffer.readBoolean();
        boolean boundingBoxVisible = buffer.readBoolean();
        StructureBlockType type = StructureBlockType.from(VarInts.readInt(buffer));
        StructureSettings settings = helper.readStructureSettings(buffer);
        StructureRedstoneSaveMode redstoneSaveMode = StructureRedstoneSaveMode.from(VarInts.readInt(buffer));
        return new StructureEditorData(name, includingPlayers, boundingBoxVisible, type, settings, dataField,
                redstoneSaveMode, filteredName);
    }

    @Override
    protected void writeEditorData(ByteBuf buffer, BedrockCodecHelper helper, StructureEditorData data) {
        helper.writeString(buffer, data.name());
        helper.writeString(buffer, data.filteredName());
        helper.writeString(buffer, data.dataField());
        buffer.writeBoolean(data.includingPlayers());
        buffer.writeBoolean(data.boundingBoxVisible());
        VarInts.writeInt(buffer, data.type().ordinal());
        helper.writeStructureSettings(buffer, data.settings());
        VarInts.writeInt(buffer, data.redstoneSaveMode().ordinal());
    }
}
