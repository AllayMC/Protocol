package org.cloudburstmc.protocol.bedrock.data.auth;

import lombok.Getter;
import org.cloudburstmc.protocol.common.util.Preconditions;

import java.util.Objects;

/**
 * Represents an authentication token used for various network services in Minecraft: Bedrock
 * Edition. A Token may be issued using [AuthorizationEnvironment.Token] with a [TokenConfig]. As
 * each Token has expiration, it is recommended to use a [TokenSource] so it can be renewed
 * subsequently when it becomes invalid.
 */
public class TokenPayload implements AuthPayload {

    /**
     * The token.
     */
    @Getter
    private final String token;
    /**
     * The type.
     */
    private final AuthType type;

    public TokenPayload(String token, AuthType type) {
        Preconditions.checkArgument(type != AuthType.UNKNOWN, "TokenPayload cannot be of type UNKNOWN");
        this.token = token;
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public AuthType getAuthType() {
        return type;
    }
}
