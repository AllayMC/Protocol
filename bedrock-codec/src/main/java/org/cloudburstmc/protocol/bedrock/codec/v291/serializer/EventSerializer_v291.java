package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.event.*;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;
import org.cloudburstmc.protocol.bedrock.util.Preconditions;
import org.cloudburstmc.protocol.bedrock.util.TriConsumer;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;

public class EventSerializer_v291 implements BedrockPacketSerializer<EventPacket> {
    public static final EventSerializer_v291 INSTANCE = new EventSerializer_v291();

    protected static final EventDataType[] VALUES = EventDataType.values();

    protected final EnumMap<EventDataType, BiFunction<ByteBuf, BedrockCodecHelper, EventData>> readers = new EnumMap<>(EventDataType.class);
    protected final EnumMap<EventDataType, TriConsumer<ByteBuf, BedrockCodecHelper, EventData>> writers = new EnumMap<>(EventDataType.class);

    protected EventSerializer_v291() {
        this.readers.put(EventDataType.ACHIEVEMENT_AWARDED, this::readAchievementAwarded);
        this.readers.put(EventDataType.ENTITY_INTERACT, this::readEntityInteract);
        this.readers.put(EventDataType.PORTAL_BUILT, this::readPortalBuilt);
        this.readers.put(EventDataType.PORTAL_USED, this::readPortalUsed);
        this.readers.put(EventDataType.MOB_KILLED, this::readMobKilled);
        this.readers.put(EventDataType.CAULDRON_USED, this::readCauldronUsed);
        this.readers.put(EventDataType.PLAYER_DIED, this::readPlayerDied);
        this.readers.put(EventDataType.BOSS_KILLED, this::readBossKilled);
        this.readers.put(EventDataType.AGENT_COMMAND, this::readAgentCommand);
        this.readers.put(EventDataType.AGENT_CREATED, (buf, helper) -> AgentCreatedEventData.INSTANCE);
        this.readers.put(EventDataType.PATTERN_REMOVED, this::readPatternRemoved);
        this.readers.put(EventDataType.SLASH_COMMAND_EXECUTED, this::readSlashCommandExecuted);
        this.readers.put(EventDataType.FISH_BUCKETED, this::readFishBucketed);

        this.writers.put(EventDataType.ACHIEVEMENT_AWARDED, this::writeAchievementAwarded);
        this.writers.put(EventDataType.ENTITY_INTERACT, this::writeEntityInteract);
        this.writers.put(EventDataType.PORTAL_BUILT, this::writePortalBuilt);
        this.writers.put(EventDataType.PORTAL_USED, this::writePortalUsed);
        this.writers.put(EventDataType.MOB_KILLED, this::writeMobKilled);
        this.writers.put(EventDataType.CAULDRON_USED, this::writeCauldronUsed);
        this.writers.put(EventDataType.PLAYER_DIED, this::writePlayerDied);
        this.writers.put(EventDataType.BOSS_KILLED, this::writeBossKilled);
        this.writers.put(EventDataType.AGENT_COMMAND, this::writeAgentCommand);
        this.writers.put(EventDataType.AGENT_CREATED, (buf, helper, data) -> {
        });
        this.writers.put(EventDataType.PATTERN_REMOVED, this::writePatternRemoved);
        this.writers.put(EventDataType.SLASH_COMMAND_EXECUTED, this::writeSlashCommandExecuted);
        this.writers.put(EventDataType.FISH_BUCKETED, this::writeFishBucketed);
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

        function.accept(buffer, helper, eventData);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EventPacket packet) {
        packet.setUniqueEntityId(VarInts.readLong(buffer));

        int eventId = VarInts.readInt(buffer);
        Preconditions.checkElementIndex(eventId, VALUES.length, "EventDataType");
        EventDataType type = VALUES[eventId];

        packet.setUsePlayerId(buffer.readBoolean());

        BiFunction<ByteBuf, BedrockCodecHelper, EventData> function = this.readers.get(type);

        if (function == null) {
            throw new UnsupportedOperationException("Unknown event type " + type);
        }

        packet.setEventData(function.apply(buffer, helper));
    }

    protected AchievementAwardedEventData readAchievementAwarded(ByteBuf buffer, BedrockCodecHelper helper) {
        int achievementId = VarInts.readInt(buffer);
        return new AchievementAwardedEventData(achievementId);
    }

    protected void writeAchievementAwarded(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        AchievementAwardedEventData event = (AchievementAwardedEventData) eventData;
        VarInts.writeInt(buffer, event.achievementId());
    }

    protected EntityInteractEventData readEntityInteract(ByteBuf buffer, BedrockCodecHelper helper) {
        int interactionType = VarInts.readInt(buffer);
        int interactionEntityType = VarInts.readInt(buffer);
        int entityVariant = VarInts.readInt(buffer);
        int entityColor = buffer.readUnsignedByte();
        return new EntityInteractEventData(-1L, interactionType, interactionEntityType, entityVariant, entityColor);
    }

    protected void writeEntityInteract(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        EntityInteractEventData event = (EntityInteractEventData) eventData;
        VarInts.writeInt(buffer, event.interactionType());
        VarInts.writeInt(buffer, event.legacyEntityTypeId());
        VarInts.writeInt(buffer, event.variant());
        buffer.writeByte(event.paletteColor());
    }

    protected PortalBuiltEventData readPortalBuilt(ByteBuf buffer, BedrockCodecHelper helper) {
        int dimensionId = VarInts.readInt(buffer);
        return new PortalBuiltEventData(dimensionId);
    }

    protected void writePortalBuilt(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        PortalBuiltEventData event = (PortalBuiltEventData) eventData;
        VarInts.writeInt(buffer, event.dimensionId());
    }

    protected PortalUsedEventData readPortalUsed(ByteBuf buffer, BedrockCodecHelper helper) {
        int fromDimensionId = VarInts.readInt(buffer);
        int toDimensionId = VarInts.readInt(buffer);
        return new PortalUsedEventData(fromDimensionId, toDimensionId);
    }

    protected void writePortalUsed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        PortalUsedEventData event = (PortalUsedEventData) eventData;
        VarInts.writeInt(buffer, event.fromDimensionId());
        VarInts.writeInt(buffer, event.toDimensionId());
    }

    protected MobKilledEventData readMobKilled(ByteBuf buffer, BedrockCodecHelper helper) {
        long killerUniqueEntityId = VarInts.readLong(buffer);
        long victimUniqueEntityId = VarInts.readLong(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        int villagerTradeTier = VarInts.readInt(buffer);
        String villagerDisplayName = helper.readString(buffer);
        return new MobKilledEventData(killerUniqueEntityId, victimUniqueEntityId, entityDamageCause,
                villagerTradeTier, villagerDisplayName, -1);
    }

    protected void writeMobKilled(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        MobKilledEventData event = (MobKilledEventData) eventData;
        VarInts.writeLong(buffer, event.killerUniqueEntityId());
        VarInts.writeLong(buffer, event.victimUniqueEntityId());
        VarInts.writeInt(buffer, event.entityDamageCause());
        VarInts.writeInt(buffer, event.villagerTradeTier());
        helper.writeString(buffer, event.villagerDisplayName());
    }

    protected CauldronUsedEventData readCauldronUsed(ByteBuf buffer, BedrockCodecHelper helper) {
        int potionId = VarInts.readInt(buffer);
        int color = VarInts.readInt(buffer);
        int fillLevel = VarInts.readInt(buffer);
        return new CauldronUsedEventData(potionId, color, fillLevel);
    }

    protected void writeCauldronUsed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        CauldronUsedEventData event = (CauldronUsedEventData) eventData;
        VarInts.writeUnsignedInt(buffer, event.potionId());
        VarInts.writeInt(buffer, event.color());
        VarInts.writeInt(buffer, event.fillLevel());
    }

    protected PlayerDiedEventData readPlayerDied(ByteBuf buffer, BedrockCodecHelper helper) {
        int attackerEntityId = VarInts.readInt(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        return new PlayerDiedEventData(attackerEntityId, entityDamageCause, -1, false);
    }

    protected void writePlayerDied(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        PlayerDiedEventData event = (PlayerDiedEventData) eventData;
        VarInts.writeInt(buffer, event.attackerEntityId());
        VarInts.writeInt(buffer, event.entityDamageCause());
    }

    protected BossKilledEventData readBossKilled(ByteBuf buffer, BedrockCodecHelper helper) {
        long bossUniqueEntityId = VarInts.readLong(buffer);
        int playerPartySize = VarInts.readInt(buffer);
        int interactionEntityType = VarInts.readInt(buffer);
        return new BossKilledEventData(bossUniqueEntityId, playerPartySize, interactionEntityType);
    }

    protected void writeBossKilled(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        BossKilledEventData event = (BossKilledEventData) eventData;
        VarInts.writeLong(buffer, event.bossUniqueEntityId());
        VarInts.writeInt(buffer, event.playerPartySize());
        VarInts.writeInt(buffer, event.bossEntityType());
    }

    protected AgentCommandEventData readAgentCommand(ByteBuf buffer, BedrockCodecHelper helper) {
        AgentResult result = AgentResult.values()[VarInts.readInt(buffer)];
        int dataValue = VarInts.readInt(buffer);
        String command = helper.readString(buffer);
        String dataKey = helper.readString(buffer);
        String output = helper.readString(buffer);
        return new AgentCommandEventData(result, command, dataKey, dataValue, output);
    }

    protected void writeAgentCommand(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        AgentCommandEventData event = (AgentCommandEventData) eventData;
        VarInts.writeInt(buffer, event.result().ordinal());
        VarInts.writeInt(buffer, event.dataValue());
        helper.writeString(buffer, event.command());
        helper.writeString(buffer, event.dataKey());
        helper.writeString(buffer, event.output());
    }

    protected PatternRemovedEventData readPatternRemoved(ByteBuf buffer, BedrockCodecHelper helper) {
        int itemId = VarInts.readInt(buffer);
        int auxValue = VarInts.readInt(buffer);
        int patternsSize = VarInts.readInt(buffer);
        int patternIndex = VarInts.readInt(buffer);
        int patternColor = VarInts.readInt(buffer);
        return new PatternRemovedEventData(itemId, auxValue, patternsSize, patternIndex, patternColor);
    }

    protected void writePatternRemoved(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        PatternRemovedEventData event = (PatternRemovedEventData) eventData;
        VarInts.writeInt(buffer, event.itemId());
        VarInts.writeInt(buffer, event.auxValue());
        VarInts.writeInt(buffer, event.patternsSize());
        VarInts.writeInt(buffer, event.patternIndex());
        VarInts.writeInt(buffer, event.patternColor());
    }

    protected SlashCommandExecutedEventData readSlashCommandExecuted(ByteBuf buffer, BedrockCodecHelper helper) {
        int successCount = VarInts.readInt(buffer);
        VarInts.readInt(buffer);
        String commandName = helper.readString(buffer);
        List<String> outputMessages = Arrays.asList(helper.readString(buffer).split(";"));
        return new SlashCommandExecutedEventData(commandName, successCount, outputMessages);
    }

    protected void writeSlashCommandExecuted(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        SlashCommandExecutedEventData event = (SlashCommandExecutedEventData) eventData;
        VarInts.writeInt(buffer, event.successCount());
        List<String> outputMessages = event.outputMessages();
        VarInts.writeInt(buffer, outputMessages.size());
        helper.writeString(buffer, event.commandName());
        helper.writeString(buffer, String.join(";", outputMessages));
    }

    protected FishBucketedEventData readFishBucketed(ByteBuf buffer, BedrockCodecHelper helper) {
        int pattern = VarInts.readInt(buffer);
        int preset = VarInts.readInt(buffer);
        int bucketedEntityType = VarInts.readInt(buffer);
        boolean isRelease = buffer.readBoolean();
        return new FishBucketedEventData(pattern, preset, bucketedEntityType, isRelease);
    }

    protected void writeFishBucketed(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        FishBucketedEventData event = (FishBucketedEventData) eventData;
        VarInts.writeInt(buffer, event.pattern());
        VarInts.writeInt(buffer, event.preset());
        VarInts.writeInt(buffer, event.bucketedEntityType());
        buffer.writeBoolean(event.releaseEvent());
    }
}
