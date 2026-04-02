package org.cloudburstmc.protocol.bedrock.data;

/**
 * Represents network permissions used in the Bedrock protocol.
 *
 * @param serverAuthSounds Whether server auth sounds.
 */
public record NetworkPermissions(boolean serverAuthSounds) {
    public static final NetworkPermissions DEFAULT = new NetworkPermissions(false);

}
