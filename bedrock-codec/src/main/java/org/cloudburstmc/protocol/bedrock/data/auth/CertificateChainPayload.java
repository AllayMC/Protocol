package org.cloudburstmc.protocol.bedrock.data.auth;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Represents certificate chain payload used in the Bedrock protocol.
 */
public class CertificateChainPayload implements AuthPayload {

    /**
     * The chain.
     */
    @Getter
    private final List<String> chain;
    /**
     * The type.
     */
    private final AuthType type;

    public CertificateChainPayload(List<String> chain) {
        this(chain, AuthType.UNKNOWN);
    }

    public CertificateChainPayload(List<String> chain, AuthType type) {
        this.chain = chain;
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public AuthType getAuthType() {
        return type;
    }

}
