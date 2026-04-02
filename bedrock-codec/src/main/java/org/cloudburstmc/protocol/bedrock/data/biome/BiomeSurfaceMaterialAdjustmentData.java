package org.cloudburstmc.protocol.bedrock.data.biome;

import java.util.List;

/**
 * BiomeSurfaceMaterial specifies the materials to use for the surface layers of the biome.
 *
 * @param biomeElements The biome elements.
 */
public record BiomeSurfaceMaterialAdjustmentData(List<BiomeElementData> biomeElements) {
}
