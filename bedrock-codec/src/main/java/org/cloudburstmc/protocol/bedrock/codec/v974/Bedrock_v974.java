package org.cloudburstmc.protocol.bedrock.codec.v974;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v944.Bedrock_v944;
import org.cloudburstmc.protocol.bedrock.codec.v974.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class Bedrock_v974 extends Bedrock_v944 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v944.ENTITY_FLAGS
            .toBuilder()
            .insert(127, EntityFlag.USES_LEGACY_FRICTION)
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v944.ENTITY_EVENTS
            .toBuilder()
            .insert(81, EntityEventType.HURT_WITHOUT_RECEIVING_DAMAGE)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v944.SOUND_EVENTS
            .toBuilder()
            .replace(599, SoundEvent.PUSHED_BY_PLAYER)
            .insert(600, SoundEvent.BOUNCE)
            .insert(601, SoundEvent.UNDEFINED)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v944.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .update(EntityDataTypes.HEARTBEAT_SOUND_EVENT, new TypeMapTransformer<>(SOUND_EVENTS))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v944.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(974)
            .minecraftVersion("1.26.20")
            .helper(() -> new BedrockCodecHelper_v974(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v974.INSTANCE)
            .updateSerializer(DebugDrawerPacket.class, DebugDrawerSerializer_v974.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v974.INSTANCE)
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v974.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v974(ENTITY_EVENTS))
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v974.INSTANCE)
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v974(SOUND_EVENTS))
            .updateSerializer(LocatorBarPacket.class, LocatorBarSerializer_v974.INSTANCE)
            .updateSerializer(MobEquipmentPacket.class, MobEquipmentSerializer_v974.INSTANCE)
            .updateSerializer(MovementPredictionSyncPacket.class, MovementPredictionSyncSerializer_v974.INSTANCE)
            .updateSerializer(PartyChangedPacket.class, PartyChangedSerializer_v974.INSTANCE)
            .updateSerializer(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsSerializer_v974.INSTANCE)
            .updateSerializer(PlaySoundPacket.class, PlaySoundSerializer_v974.INSTANCE)
            .updateSerializer(ServerboundDiagnosticsPacket.class, new ServerboundDiagnosticsSerializer_v974(MEMORY_CATEGORY_TYPES))
            .updateSerializer(UpdateClientOptionsPacket.class, UpdateClientOptionsSerializer_v974.INSTANCE)
            .registerPacket(ServerStoreInfoPacket::new, ServerStoreInfoSerializer_v974.INSTANCE, 346, PacketRecipient.CLIENT)
            .registerPacket(ServerPresenceInfoPacket::new, ServerPresenceInfoSerializer_v974.INSTANCE, 347, PacketRecipient.CLIENT)
            .build();
}
