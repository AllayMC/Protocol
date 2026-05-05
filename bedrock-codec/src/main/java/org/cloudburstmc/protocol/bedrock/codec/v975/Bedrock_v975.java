package org.cloudburstmc.protocol.bedrock.codec.v975;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.AvailableCommandsSerializer_v898;
import org.cloudburstmc.protocol.bedrock.codec.v944.Bedrock_v944;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class Bedrock_v975 extends Bedrock_v944 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v944.ENTITY_FLAGS
            .toBuilder()
            .insert(127, EntityFlag.USES_LEGACY_FRICTION)
            .insert(128, EntityFlag.USES_UNIFORM_AIR_DRAG)
            .insert(129, EntityFlag.NAMEPLATE_DEPTH_TESTED)
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

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v944.PARTICLE_TYPES.toBuilder()
            .insert(101, ParticleType.SULFUR_CUBE)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v944.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v944.COMMAND_PARAMS.toBuilder()
            .remove(134217728)
            .shift(86, 92, 1)
            .insert(86, CommandParam.CLOCK_TIME_MARKER_NAME)
            .insert(134217728, CommandParam.CHAINED_COMMAND)
            .build();

    protected static final TypeMap<MemoryCategoryCounter.Category> MEMORY_CATEGORY_TYPES = Bedrock_v944.MEMORY_CATEGORY_TYPES.toBuilder()
            .remove(68)
            .shift(60, 68, 1)
            .insert(60, MemoryCategoryCounter.Category.RENDERING_RENDER_REGISTRY)
            .build();

    protected static final TypeMap<TextProcessingEventOrigin> TEXT_PROCESSING_ORIGINS = Bedrock_v944.TEXT_PROCESSING_ORIGINS.toBuilder()
            .insert(15, TextProcessingEventOrigin.DATA_DRIVEN_UI)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v944.ENTITY_DATA
            .toBuilder()
            .insert(EntityDataTypes.RESERVED_139, 139, EntityDataFormat.LONG)
            .insert(EntityDataTypes.NAMEPLATE_RENDER_DISTANCE_MAX, 140, EntityDataFormat.FLOAT)
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .update(EntityDataTypes.HEARTBEAT_SOUND_EVENT, new TypeMapTransformer<>(SOUND_EVENTS))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v944.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(975)
            .minecraftVersion("1.26.20")
            .helper(() -> new BedrockCodecHelper_v975(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v898(COMMAND_PARAMS))
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v975.INSTANCE)
            .updateSerializer(ClientboundAttributeLayerSyncPacket.class, ClientboundAttributeLayerSyncSerializer_v975.INSTANCE)
            .updateSerializer(PrimitiveShapesPacket.class, PrimitiveShapesSerializer_v975.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v975.INSTANCE)
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v975.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v975(ENTITY_EVENTS))
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v975.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v975(SOUND_EVENTS))
            .updateSerializer(LocatorBarPacket.class, LocatorBarSerializer_v975.INSTANCE)
            .updateSerializer(MobEquipmentPacket.class, MobEquipmentSerializer_v975.INSTANCE)
            .updateSerializer(MoveEntityAbsolutePacket.class, MoveEntityAbsoluteSerializer_v975.INSTANCE)
            .updateSerializer(MovementPredictionSyncPacket.class, MovementPredictionSyncSerializer_v975.INSTANCE)
            .updateSerializer(PartyChangedPacket.class, PartyChangedSerializer_v975.INSTANCE)
            .updateSerializer(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsSerializer_v975.INSTANCE)
            .updateSerializer(PlaySoundPacket.class, PlaySoundSerializer_v975.INSTANCE)
            .updateSerializer(ServerboundDiagnosticsPacket.class, new ServerboundDiagnosticsSerializer_v975(MEMORY_CATEGORY_TYPES))
            .updateSerializer(UpdateClientOptionsPacket.class, UpdateClientOptionsSerializer_v975.INSTANCE)
            .registerPacket(ServerStoreInfoPacket::new, ServerStoreInfoSerializer_v975.INSTANCE, 346, PacketRecipient.CLIENT)
            .registerPacket(ServerPresenceInfoPacket::new, ServerPresenceInfoSerializer_v975.INSTANCE, 347, PacketRecipient.CLIENT)
            .build();
}
