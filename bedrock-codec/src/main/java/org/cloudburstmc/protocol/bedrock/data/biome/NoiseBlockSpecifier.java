package org.cloudburstmc.protocol.bedrock.data.biome;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

public record NoiseBlockSpecifier(@Nullable String noise, float threshold, float rangeMin, float rangeMax,
                                  BlockDefinition block) {
}
