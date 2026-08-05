package org.cloudburstmc.protocol.bedrock.data;

/**
 * Platform identifiers reported by clients, for example in login and player list data.
 */
public enum BuildPlatform {

    UNKNOWN(-1),
    /**
     * Android.
     */
    GOOGLE(1),
    /**
     * iOS.
     */
    IOS(2),
    /**
     * macOS.
     */
    OSX(3),
    /**
     * Fire OS devices such as Kindle and Fire TV.
     */
    AMAZON(4),
    /**
     * Gear VR.
     */
    GEAR_VR(5),
    /**
     * HoloLens.
     */
    HOLOLENS(6),
    /**
     * Windows UWP / Microsoft Store client.
     */
    UWP(7),
    /**
     * Desktop Win32 client, historically used by Education Edition.
     */
    WIN32(8),
    /**
     * Dedicated server.
     */
    DEDICATED(9),
    /**
     * Apple TV.
     */
    TV_OS(10),
    /**
     * PlayStation.
     */
    SONY(11),
    /**
     * Nintendo Switch.
     */
    NX(12),
    /**
     * Xbox.
     */
    XBOX(13),
    /**
     * Windows Phone.
     */
    WINDOWS_PHONE(14),
    /**
     * Linux.
     */
    LINUX(15);

    private static final BuildPlatform[] VALUES = values();
    private final int id;

    BuildPlatform(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BuildPlatform from(int id) {
        for (BuildPlatform value : VALUES) {
            if (value.id == id) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
