package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents a feature that is consolidated into a single feature for the biome.
 *
 * @param scatter     Scatter defines how the feature is scattered in the biome.
 * @param feature     The feature.
 * @param identifier  The identifier.
 * @param pass        The pass.
 * @param internalUse CanUseInternal is true if the feature can use internal features.
 */
public record BiomeConsolidatedFeatureData(BiomeScatterParamData scatter, String feature, String identifier,
                                           String pass, boolean internalUse) {

    @JsonCreator
    public BiomeConsolidatedFeatureData {
    }
}
