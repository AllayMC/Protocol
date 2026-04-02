package org.cloudburstmc.protocol.bedrock.data.attributelayer;

/**
 * AttributeLayerSettings represents settings for an attribute layer.
 *
 * @param layerName The layer name.
 * @param dimension The dimension.
 * @param settings  The settings.
 */
public record UpdateAttributeLayerSettingsData(String layerName, int dimension,
                                               AttributeLayerSettings settings) implements AttributeLayerSyncPayload {

}
