package org.cloudburstmc.protocol.bedrock.data.biome;

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.List;

/**
 * Noise-gradient surface configuration used by the v975 biome-definition codec.
 *
 * @param nonReplaceableBlocks A list of blocks that may not be replaced.
 * @param gradientBlocks A list of noise block specifiers used by the gradient since v1001.
 * @param noise The string used to initialise the gradient noise since v1001.
 * @param firstOctave The first octave used by the gradient noise.
 * @param amplitudes A list of amplitude values used by the gradient noise.
 */
public record BiomeNoiseGradientSurfaceData(List<BlockDefinition> nonReplaceableBlocks,
                                            List<NoiseBlockSpecifier> gradientBlocks,
                                            String noise,
                                            int firstOctave,
                                            List<Float> amplitudes) {
}
