package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import java.util.List;

/**
 * Represents update attribute layers data used in the Bedrock protocol.
 *
 * @param attributeLayers The attribute layers.
 */
public record UpdateAttributeLayersData(List<AttributeLayerData> attributeLayers) implements AttributeLayerSyncPayload {

}
