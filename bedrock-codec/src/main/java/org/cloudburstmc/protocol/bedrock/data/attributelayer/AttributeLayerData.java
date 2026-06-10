package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Represents a complete attribute layer.
 *
 * @param layerName  The layer name.
 * @param noiseName  The optional name of the noise used by the layer since v1001.
 * @param dimension  The dimension.
 * @param settings   The layer's settings.
 * @param attributes The attributes.
 */
public record AttributeLayerData(String layerName, @Nullable String noiseName, int dimension,
                                 AttributeLayerSettings settings, List<EnvironmentAttributeData> attributes) {

    public AttributeLayerData(String layerName, int dimension, AttributeLayerSettings settings,
                              List<EnvironmentAttributeData> attributes) {
        this(layerName, null, dimension, settings, attributes);
    }
}
