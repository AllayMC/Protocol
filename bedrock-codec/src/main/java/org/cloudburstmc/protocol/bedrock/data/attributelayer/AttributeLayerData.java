package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import java.util.List;

/**
 * Represents a complete attribute layer.
 *
 * @param layerName  The layer name.
 * @param dimension  The dimension.
 * @param settings   The layer's settings.
 * @param attributes The attributes.
 */
public record AttributeLayerData(String layerName, int dimension, AttributeLayerSettings settings, List<EnvironmentAttributeData> attributes) {

}
