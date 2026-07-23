package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v944.serializer.PlayerAuthInputSerializer_v944;
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData;
import org.cloudburstmc.protocol.bedrock.data.PlayerBlockActionData;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class PlayerAuthInputSerializer_v2168 extends PlayerAuthInputSerializer_v944 {

    public static final PlayerAuthInputSerializer_v2168 INSTANCE = new PlayerAuthInputSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        Vector3f rotation = packet.getRotation();
        buffer.writeFloatLE(rotation.getX());
        buffer.writeFloatLE(rotation.getY());
        helper.writeVector3f(buffer, packet.getPosition());
        buffer.writeFloatLE(packet.getMotion().getX());
        buffer.writeFloatLE(packet.getMotion().getY());
        buffer.writeFloatLE(rotation.getZ());

        buffer.writeBoolean(true);
        VarInts.writeUnsignedInt(buffer, packet.getInputData().size());
        for (PlayerAuthInputData flag : packet.getInputData()) {
            VarInts.writeInt(buffer, flag.ordinal());
        }

        VarInts.writeUnsignedInt(buffer, packet.getInputMode().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getPlayMode().ordinal());
        VarInts.writeInt(buffer, packet.getInputInteractionModel().ordinal());
        helper.writeVector2f(buffer, packet.getInteractRotation());
        VarInts.writeUnsignedLong(buffer, packet.getTick());
        helper.writeVector3f(buffer, packet.getDelta());
        buffer.writeBoolean(true);
        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_INTERACTION)) {
            buffer.writeBoolean(true);
            this.writeItemUseTransaction(buffer, helper, packet.getItemUseTransaction());
        } else {
            buffer.writeBoolean(false);
        }
        buffer.writeBoolean(true);
        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_ITEM_STACK_REQUEST)) {
            buffer.writeBoolean(true);
            helper.writeItemStackRequest(buffer, packet.getItemStackRequest());
        } else {
            buffer.writeBoolean(false);
        }
        buffer.writeBoolean(true);
        if (packet.getInputData().contains(PlayerAuthInputData.PERFORM_BLOCK_ACTIONS)) {
            buffer.writeBoolean(true);
            VarInts.writeInt(buffer, packet.getPlayerActions().size());
            for (PlayerBlockActionData actionData : packet.getPlayerActions()) {
                writePlayerBlockActionData(buffer, helper, actionData);
            }
        } else {
            buffer.writeBoolean(false);
        }
        buffer.writeBoolean(true);
        if (packet.getInputData().contains(PlayerAuthInputData.IN_CLIENT_PREDICTED_IN_VEHICLE)) {
            buffer.writeBoolean(true);
            helper.writeVector2f(buffer, packet.getVehicleRotation());
        } else {
            buffer.writeBoolean(false);
        }
        buffer.writeBoolean(true);
        if (packet.getInputData().contains(PlayerAuthInputData.IN_CLIENT_PREDICTED_IN_VEHICLE)) {
            buffer.writeBoolean(true);
            VarInts.writeLong(buffer, packet.getPredictedVehicle());
        } else {
            buffer.writeBoolean(false);
        }
        helper.writeVector2f(buffer, packet.getAnalogMoveVector());
        helper.writeVector3f(buffer, packet.getCameraOrientation());
        helper.writeVector2f(buffer, packet.getRawMoveVector());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        float x = buffer.readFloatLE();
        float y = buffer.readFloatLE();
        packet.setPosition(helper.readVector3f(buffer));
        packet.setMotion(Vector2f.from(buffer.readFloatLE(), buffer.readFloatLE()));
        float z = buffer.readFloatLE();
        packet.setRotation(Vector3f.from(x, y, z));

        if (buffer.readBoolean()) {
            int count = VarInts.readUnsignedInt(buffer);
            for (int i = 0; i < count; i++) {
                int index = VarInts.readInt(buffer);
                packet.getInputData().add(PlayerAuthInputData.values()[index]);
            }
        }

        packet.setInputMode(INPUT_MODES[VarInts.readUnsignedInt(buffer)]);
        packet.setPlayMode(CLIENT_PLAY_MODES[VarInts.readUnsignedInt(buffer)]);
        packet.setInputInteractionModel(VALUES[VarInts.readInt(buffer)]);
        packet.setInteractRotation(helper.readVector2f(buffer));
        packet.setTick(VarInts.readUnsignedLong(buffer));
        packet.setDelta(helper.readVector3f(buffer));
        if (buffer.readBoolean() && buffer.readBoolean()) {
            packet.setItemUseTransaction(this.readItemUseTransaction(buffer, helper));
        }
        if (buffer.readBoolean() && buffer.readBoolean()) {
            packet.setItemStackRequest(helper.readItemStackRequest(buffer));
        }
        if (buffer.readBoolean() && buffer.readBoolean()) {
            helper.readArray(buffer, packet.getPlayerActions(), this::readPlayerBlockActionData, 100);
        }
        if (buffer.readBoolean() && buffer.readBoolean()) {
            packet.setVehicleRotation(helper.readVector2f(buffer));
        }
        if (buffer.readBoolean() && buffer.readBoolean()) {
            packet.setPredictedVehicle(VarInts.readLong(buffer));
        }
        packet.setAnalogMoveVector(helper.readVector2f(buffer));
        packet.setCameraOrientation(helper.readVector3f(buffer));
        packet.setRawMoveVector(helper.readVector2f(buffer));
    }
}
