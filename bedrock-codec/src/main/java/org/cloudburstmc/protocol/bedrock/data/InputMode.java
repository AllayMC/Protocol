package org.cloudburstmc.protocol.bedrock.data;

/**
 * Input device categories reported by the client in packets such as
 * {@link org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket}.
 */
public enum InputMode {
    UNDEFINED,
    /**
     * Mouse and keyboard input.
     */
    MOUSE,
    /**
     * Touchscreen input.
     */
    TOUCH,
    /**
     * Gamepad input.
     */
    GAMEPAD,
    /**
     * Motion-controller input.
     *
     * @deprecated since v859
     */
    @Deprecated
    MOTION_CONTROLLER;

    private static final InputMode[] VALUES = values();

    public static InputMode from(int id) {
        return VALUES[id];
    }
}
