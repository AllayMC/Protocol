package org.cloudburstmc.protocol.bedrock.codec.v897.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v671.serializer.EventSerializer_v671;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.EventSerializer_v685;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;
import org.cloudburstmc.protocol.bedrock.data.event.EventDataType;
import org.cloudburstmc.protocol.bedrock.data.event.ItemUsedEventData;
import org.cloudburstmc.protocol.bedrock.data.event.SlashCommandExecutedEventData;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.cloudburstmc.protocol.common.util.TriConsumer;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class EventSerializer_v897 extends EventSerializer_v685 {

    public static final EventSerializer_v897 INSTANCE = new EventSerializer_v897();

    public EventSerializer_v897() {
        super();
        this.readers.put(EventDataType.SLASH_COMMAND_EXECUTED, this::readSlashCommandExecuted);
        this.writers.put(EventDataType.SLASH_COMMAND_EXECUTED, this::writeSlashCommandExecuted);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EventPacket packet) {
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        EventData eventData = packet.getEventData();
        VarInts.writeInt(buffer, eventData.getType().ordinal());
        buffer.writeBoolean(packet.isUsePlayerId());

        TriConsumer<ByteBuf, BedrockCodecHelper, EventData> function = this.writers.get(eventData.getType());

        if (function == null) {
            throw new UnsupportedOperationException("Unknown event type " + eventData.getType());
        }

        VarInts.writeUnsignedInt(buffer, eventData.getType().ordinal());

        function.accept(buffer, helper, eventData);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EventPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));

        int eventId = VarInts.readInt(buffer);
        Preconditions.checkElementIndex(eventId, VALUES.length, "EventDataType");
        EventDataType type = VALUES[eventId];

        packet.setUsePlayerId(buffer.readBoolean());

        VarInts.readUnsignedInt(buffer);

        BiFunction<ByteBuf, BedrockCodecHelper, EventData> function = this.readers.get(type);

        if (function == null) {
            throw new UnsupportedOperationException("Unknown event type " + type);
        }

        packet.setEventData(function.apply(buffer, helper));
    }
}