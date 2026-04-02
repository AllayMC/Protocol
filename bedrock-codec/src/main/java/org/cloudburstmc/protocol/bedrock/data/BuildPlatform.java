package org.cloudburstmc.protocol.bedrock.data;

/**
 * Platform identifiers reported by clients, for example in login and player list data.
 */
public enum BuildPlatform {

    UNKNOWN,
    /**
     * Android.
     */
    GOOGLE,
    /**
     * iOS.
     */
    IOS,
    /**
     * macOS.
     */
    OSX,
    /**
     * Fire OS devices such as Kindle and Fire TV.
     */
    AMAZON,
    /**
     * Gear VR.
     */
    GEAR_VR,
    /**
     * HoloLens.
     */
    HOLOLENS,
    /**
     * Windows UWP / Microsoft Store client.
     */
    UWP,
    /**
     * Desktop Win32 client, historically used by Education Edition.
     */
    WIN32,
    /**
     * Dedicated server.
     */
    DEDICATED,
    /**
     * Apple TV.
     */
    TV_OS,
    /**
     * PlayStation.
     */
    SONY,
    /**
     * Nintendo Switch.
     */
    NX,
    /**
     * Xbox.
     */
    XBOX,
    /**
     * Windows Phone.
     */
    WINDOWS_PHONE,
    /**
     * Linux.
     */
    LINUX;

    private static final BuildPlatform[] VALUES = values();

    public static BuildPlatform from(int id) {
        return id > 0 && id < VALUES.length ? VALUES[id] : VALUES[0];
    }
}
