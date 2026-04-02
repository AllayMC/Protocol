package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v354.serializer.EventSerializer_v354;
import org.cloudburstmc.protocol.bedrock.data.event.*;
import org.cloudburstmc.protocol.common.util.VarInts;

public class EventSerializer_v388 extends EventSerializer_v354 {
    public static final EventSerializer_v388 INSTANCE = new EventSerializer_v388();

    protected EventSerializer_v388() {
        super();
        this.readers.put(EventDataType.ENTITY_DEFINITION_TRIGGER, this::readEntityDefinitionTrigger);
        this.readers.put(EventDataType.RAID_UPDATE, this::readRaidUpdate);
        this.readers.put(EventDataType.MOVEMENT_ANOMALY, this::readMovementAnomaly);
        this.readers.put(EventDataType.MOVEMENT_CORRECTED, this::readMovementCorrected);
        this.writers.put(EventDataType.ENTITY_DEFINITION_TRIGGER, this::writeEntityDefinitionTrigger);
        this.writers.put(EventDataType.RAID_UPDATE, this::writeRaidUpdate);
        this.writers.put(EventDataType.MOVEMENT_ANOMALY, this::writeMovementAnomaly);
        this.writers.put(EventDataType.MOVEMENT_CORRECTED, this::writeMovementCorrected);
    }

    @Override
    protected MobKilledEventData readMobKilled(ByteBuf buffer, BedrockCodecHelper helper) {
        long killerUniqueEntityId = VarInts.readLong(buffer);
        long victimUniqueEntityId = VarInts.readLong(buffer);
        int killerEntityType = VarInts.readInt(buffer);
        int entityDamageCause = VarInts.readInt(buffer);
        int villagerTradeTier = VarInts.readInt(buffer);
        String villagerDisplayName = helper.readString(buffer);
        return new MobKilledEventData(killerUniqueEntityId, victimUniqueEntityId, entityDamageCause,
                villagerTradeTier, villagerDisplayName, killerEntityType);
    }

    @Override
    protected void writeMobKilled(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        MobKilledEventData event = (MobKilledEventData) eventData;
        VarInts.writeLong(buffer, event.killerUniqueEntityId());
        VarInts.writeLong(buffer, event.victimUniqueEntityId());
        VarInts.writeInt(buffer, event.killerEntityType());
        VarInts.writeInt(buffer, event.entityDamageCause());
        VarInts.writeInt(buffer, event.villagerTradeTier());
        helper.writeString(buffer, event.villagerDisplayName());
    }

    protected EntityDefinitionTriggerEventData readEntityDefinitionTrigger(ByteBuf buffer, BedrockCodecHelper helper) {
        String eventName = helper.readString(buffer);
        return new EntityDefinitionTriggerEventData(eventName);
    }

    protected void writeEntityDefinitionTrigger(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        EntityDefinitionTriggerEventData event = (EntityDefinitionTriggerEventData) eventData;
        helper.writeString(buffer, event.eventName());
    }

    protected RaidUpdateEventData readRaidUpdate(ByteBuf buffer, BedrockCodecHelper helper) {
        int currentRaidWave = VarInts.readInt(buffer);
        int totalRaidWaves = VarInts.readInt(buffer);
        boolean wonRaid = buffer.readBoolean();
        return new RaidUpdateEventData(currentRaidWave, totalRaidWaves, wonRaid);
    }

    protected void writeRaidUpdate(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        RaidUpdateEventData event = (RaidUpdateEventData) eventData;
        VarInts.writeInt(buffer, event.currentWave());
        VarInts.writeInt(buffer, event.totalWaves());
        buffer.writeBoolean(event.winner());
    }

    protected MovementAnomalyEventData readMovementAnomaly(ByteBuf buffer, BedrockCodecHelper helper) {
        byte eventType = buffer.readByte();
        float cheatingScore = buffer.readFloatLE();
        float averagePositionDelta = buffer.readFloatLE();
        float totalPositionDelta = buffer.readFloatLE();
        float minPositionDelta = buffer.readFloatLE();
        float maxPositionDelta = buffer.readFloatLE();
        return new MovementAnomalyEventData(eventType, cheatingScore, averagePositionDelta, totalPositionDelta,
                minPositionDelta, maxPositionDelta);
    }

    protected void writeMovementAnomaly(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        MovementAnomalyEventData event = (MovementAnomalyEventData) eventData;
        buffer.writeByte(event.eventType());
        buffer.writeFloatLE(event.cheatingScore());
        buffer.writeFloatLE(event.averagePositionDelta());
        buffer.writeFloatLE(event.totalPositionDelta());
        buffer.writeFloatLE(event.minPositionDelta());
        buffer.writeFloatLE(event.maxPositionDelta());
    }

    protected MovementCorrectedEventData readMovementCorrected(ByteBuf buffer, BedrockCodecHelper helper) {
        float positionDelta = buffer.readFloatLE();
        float cheatingScore = buffer.readFloatLE();
        float scoreThreshold = buffer.readFloatLE();
        float distanceThreshold = buffer.readFloatLE();
        int durationThreshold = VarInts.readInt(buffer);
        return new MovementCorrectedEventData(positionDelta, cheatingScore, scoreThreshold, distanceThreshold,
                durationThreshold);
    }

    protected void writeMovementCorrected(ByteBuf buffer, BedrockCodecHelper helper, EventData eventData) {
        MovementCorrectedEventData event = (MovementCorrectedEventData) eventData;
        buffer.writeFloatLE(event.positionDelta());
        buffer.writeFloatLE(event.cheatingScore());
        buffer.writeFloatLE(event.scoreThreshold());
        buffer.writeFloatLE(event.distanceThreshold());
        VarInts.writeInt(buffer, event.durationThreshold());
    }
}
