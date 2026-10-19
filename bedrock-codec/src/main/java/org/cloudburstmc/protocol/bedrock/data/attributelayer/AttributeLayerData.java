package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * A complete environment attribute layer.
 *
 * @param layerName  the layer identifier
 * @param noiseName  the optional layer noise name, since v1001; deprecated since v2225,
 *                   when noise names are carried by individual {@link EnvironmentAttributeData} entries
 * @param dimension the dimension containing the layer
 * @param settings  the layer settings
 * @param attributes the attributes belonging to the layer
 * @since v944
 */
public record AttributeLayerData(String layerName, @Nullable String noiseName, int dimension,
                                 AttributeLayerSettings settings, List<EnvironmentAttributeData> attributes) {

    public AttributeLayerData(String layerName, int dimension, AttributeLayerSettings settings,
                              List<EnvironmentAttributeData> attributes) {
        this(layerName, null, dimension, settings, attributes);
    }
}
