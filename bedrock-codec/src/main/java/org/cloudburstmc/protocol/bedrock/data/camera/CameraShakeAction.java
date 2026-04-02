package org.cloudburstmc.protocol.bedrock.data.camera;

/**
 * CameraShake is sent by the server to make the camera shake client-side. This feature was added
 * for map- making partners.
 */
public enum CameraShakeAction {
    ADD,
    STOP
}
