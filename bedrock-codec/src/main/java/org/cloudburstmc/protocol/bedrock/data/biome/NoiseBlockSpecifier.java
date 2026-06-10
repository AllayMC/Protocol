package org.cloudburstmc.protocol.bedrock.data.biome;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

/**
 * Specifies a block placed by the gradient noise based on a threshold and range.
 *
 * @param noise The noise name.
 * @param threshold The noise threshold above which the block is placed.
 * @param rangeMin The minimum value of the noise range within which the block is placed.
 * @param rangeMax The maximum value of the noise range within which the block is placed.
 * @param block The block placed by this specifier.
 *
 * @since v1001
 */
public record NoiseBlockSpecifier(@Nullable String noise, float threshold, float rangeMin, float rangeMax,
                                  BlockDefinition block) {
}
