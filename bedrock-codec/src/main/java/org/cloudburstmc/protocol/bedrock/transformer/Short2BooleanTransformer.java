package org.cloudburstmc.protocol.bedrock.transformer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Short2BooleanTransformer implements EntityDataTransformer<Short, Boolean> {

    public static final Short2BooleanTransformer INSTANCE = new Short2BooleanTransformer();

    @Override
    public Short serialize(BedrockCodecHelper helper, EntityDataMap map, Boolean value) {
        return (short) (value == Boolean.TRUE ? 1 : 0);
    }

    @Override
    public Boolean deserialize(BedrockCodecHelper helper, EntityDataMap map, Short value) {
        return value == 1 ? Boolean.TRUE : Boolean.FALSE;
    }
}
