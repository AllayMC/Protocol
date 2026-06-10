package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

@Value
public class NoiseBlockSpecifier {

    String noise;
    float threshold;
    float rangeMin;
    float rangeMax;
    BlockDefinition block;
}
