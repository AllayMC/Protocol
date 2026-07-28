package org.cloudburstmc.protocol.bedrock.transformer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Long2IntTransformer implements EntityDataTransformer<Long, Integer> {

    public static final Long2IntTransformer INSTANCE = new Long2IntTransformer();

    @Override
    public Long serialize(BedrockCodecHelper helper, EntityDataMap map, Integer value) {
        return value.longValue();
    }

    @Override
    public Integer deserialize(BedrockCodecHelper helper, EntityDataMap map, Long value) {
        return value.intValue();
    }
}
