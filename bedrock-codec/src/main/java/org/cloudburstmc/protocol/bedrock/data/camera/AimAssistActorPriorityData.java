package org.cloudburstmc.protocol.bedrock.data.camera;

/**
 * CameraAimAssistActorPriorityData represents priority data for aim assist actor targeting.
 *
 * @param presetIndex   The index of the aim assist preset.
 * @param categoryIndex The index of the aim assist category.
 * @param actorIndex    The index of the actor.
 * @param priorityValue The priority value.
 */
public record AimAssistActorPriorityData(int presetIndex, int categoryIndex, int actorIndex, int priorityValue) {

}
