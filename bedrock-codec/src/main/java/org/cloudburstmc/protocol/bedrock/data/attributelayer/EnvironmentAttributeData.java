package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;

/**
 * Represents an environment attribute with optional transition data.
 *
 * @param attributeName          The name of the attribute.
 * @param from                   The attribute value transitioned from.
 * @param attribute              The current attribute value.
 * @param to                     The attribute value transitioned to.
 * @param CurrentTransitionTicks The current transition tick count.
 * @param TotalTransitionTicks   The total transition tick count.
 * @param easing                 The easing.
 */
public record EnvironmentAttributeData(String attributeName, @Nullable AttributeData from, AttributeData attribute,
                                       @Nullable AttributeData to, int CurrentTransitionTicks,
                                       int TotalTransitionTicks, CameraEase easing) {
}
