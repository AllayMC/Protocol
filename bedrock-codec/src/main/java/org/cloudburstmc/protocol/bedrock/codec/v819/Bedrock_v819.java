package org.cloudburstmc.protocol.bedrock.codec.v819;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v776.BedrockCodecHelper_v776;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.LevelSoundEventSerializer_v786;
import org.cloudburstmc.protocol.bedrock.codec.v818.Bedrock_v818;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class Bedrock_v819 extends Bedrock_v818 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v818.SOUND_EVENTS
            .toBuilder()
            .replace(561, SoundEvent.RECORD_LAVA_CHICKEN)
            .insert(562, SoundEvent.UNDEFINED)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v818.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.HEARTBEAT_SOUND_EVENT, new TypeMapTransformer<>(SOUND_EVENTS))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v818.CODEC.toBuilder()
            .minecraftVersion("1.21.93")
            .protocolVersion(819)
            .helper(() -> new BedrockCodecHelper_v776(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v786(SOUND_EVENTS))
            .build();

}
