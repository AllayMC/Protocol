package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.StructureBlockUpdateSerializer_v776;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureBlockType;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureEditorData;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRedstoneSaveMode;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class StructureBlockUpdateSerializer_v2168 extends StructureBlockUpdateSerializer_v776 {

    public static final StructureBlockUpdateSerializer_v2168 INSTANCE = new StructureBlockUpdateSerializer_v2168();

    @Override
    protected StructureEditorData readEditorData(ByteBuf buffer, BedrockCodecHelper helper) {
        String name = helper.readString(buffer);
        String filteredName = helper.readString(buffer);
        String dataField = helper.readString(buffer);
        boolean includingPlayers = buffer.readBoolean();
        boolean boundingBoxVisible = buffer.readBoolean();
        StructureBlockType type = StructureBlockType.from(VarInts.readInt(buffer));
        StructureSettings settings = helper.readStructureSettings(buffer);
        StructureRedstoneSaveMode redstoneSaveMode = StructureRedstoneSaveMode.from(buffer.readUnsignedByte());
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
        buffer.writeByte(data.redstoneSaveMode().ordinal());
    }
}
