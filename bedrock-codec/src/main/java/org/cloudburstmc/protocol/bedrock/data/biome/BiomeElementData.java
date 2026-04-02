package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;

/**
 * BiomeElementData are set rules to adjust the surface materials of the biome.
 *
 * @param noiseFrequencyScale The frequency scale of the noise used to adjust the surface
 *                            materials.
 * @param noiseLowerBound     The minimum noise value required to be selected.
 * @param noiseUpperBound     The maximum noise value required to be selected.
 * @param heightMinType       The expression operation to use when evaluating the minimum height.
 * @param heightMin           The minimum height expression.
 * @param heightMaxType       The expression operation to use when evaluating the maximum height.
 * @param heightMax           The maximum height expression.
 * @param adjustedMaterials   The materials to use for the surface layers of the biome if selected.
 */
public record BiomeElementData(float noiseFrequencyScale, float noiseLowerBound, float noiseUpperBound,
                               ExpressionOp heightMinType, String heightMin, ExpressionOp heightMaxType,
                               String heightMax, BiomeSurfaceMaterialData adjustedMaterials) {

    @JsonCreator
    public BiomeElementData {
    }
}
