package org.cloudburstmc.protocol.bedrock.data.auth;

/**
 * Represents an authenticated login payload extracted from the Bedrock login chain.
 */
public interface AuthPayload {

    /**
     * Returns the authentication type of the player.
     *
     * @return the authentication type
     */
    AuthType getAuthType();
}
