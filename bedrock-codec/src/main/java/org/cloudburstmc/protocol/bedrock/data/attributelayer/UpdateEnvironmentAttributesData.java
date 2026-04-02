package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import java.util.List;

/**
 * Represents update environment attributes data used in the Bedrock protocol.
 *
 * @param layerName  The layer name.
 * @param dimension  The dimension.
 * @param attributes The attributes.
 */
public record UpdateEnvironmentAttributesData(String layerName, int dimension,
                                              List<EnvironmentAttributeData> attributes) implements AttributeLayerSyncPayload {

}
