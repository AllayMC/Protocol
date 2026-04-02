package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import java.util.List;

/**
 * Represents remove environment attributes data used in the Bedrock protocol.
 *
 * @param layerName  The layer name.
 * @param dimension  The dimension.
 * @param attributes The attributes.
 */
public record RemoveEnvironmentAttributesData(String layerName, int dimension, List<String> attributes) implements AttributeLayerSyncPayload {

}
