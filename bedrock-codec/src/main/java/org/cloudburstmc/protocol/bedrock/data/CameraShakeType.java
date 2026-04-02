package org.cloudburstmc.protocol.bedrock.data;

/**
 * CameraShake is sent by the server to make the camera shake client-side. This feature was added
 * for map- making partners.
 */
public enum CameraShakeType {
    POSITIONAL,
    ROTATIONAL
}
