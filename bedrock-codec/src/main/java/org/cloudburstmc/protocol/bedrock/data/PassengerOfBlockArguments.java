package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

public record PassengerOfBlockArguments(Vector3i blockPos, Vector3f offset, float rotation, float rotationLimit, EmoteType emoteType) {

    public enum EmoteType {
        STANDING,
        RIDING,
        LAYING
    }
}
