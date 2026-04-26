package org.cloudburstmc.protocol.bedrock.data.biome;

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.List;

/**
 * Noise-gradient surface configuration used by the v975 biome-definition codec.
 */
public record BiomeNoiseGradientSurfaceData(List<BlockDefinition> nonReplaceableBlocks,
                                            List<BlockDefinition> gradientBlocks,
                                            String noiseSeedString,
                                            int firstOctave,
                                            List<Float> amplitudes) {
}
