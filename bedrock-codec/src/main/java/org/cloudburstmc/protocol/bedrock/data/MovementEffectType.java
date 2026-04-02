package org.cloudburstmc.protocol.bedrock.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * MovementEffect is sent by the server to the client to update specific movement effects to allow
 * the client to predict its movement. For example, fireworks used during gliding will send this
 * packet to tell the client the exact duration of the boost.
 */
@Getter
@RequiredArgsConstructor
public enum MovementEffectType {

    INVALID(-1),
    GLIDE_BOOST(0),
    DOLPHIN_BOOST(1);

    /**
     * The ID.
     */
    private final int id;

    private static final MovementEffectType[] VALUES = values();

    public static MovementEffectType byId(int id) {
        for (MovementEffectType type : VALUES) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
