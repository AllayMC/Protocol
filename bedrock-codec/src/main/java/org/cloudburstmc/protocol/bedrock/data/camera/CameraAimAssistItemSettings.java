package org.cloudburstmc.protocol.bedrock.data.camera;

/**
 * CameraAimAssistItemSettings defines settings for how specific items should behave when using aim
 * assist.
 *
 * @param itemId   The item ID.
 * @param category The identifier of a category to use which has been defined by a CameraAimAssistCategory. Only
 *                 categories defined in the Categories slice used by the CameraAimAssistPreset can be used
 *                 here.
 */
public record CameraAimAssistItemSettings(String itemId, String category) {
}
