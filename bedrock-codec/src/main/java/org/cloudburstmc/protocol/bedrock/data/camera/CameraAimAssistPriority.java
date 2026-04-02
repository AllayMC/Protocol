package org.cloudburstmc.protocol.bedrock.data.camera;

/**
 * Represents a non-default priority for a specific target.
 *
 * @param name     The name.
 * @param priority The priority for this specific target.
 */
public record CameraAimAssistPriority(String name, int priority) {
}
