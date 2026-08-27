package org.cloudburstmc.protocol.bedrock.data.attributelayer;


public record NoiseAlignment(Type type, int value) {

    public enum Type {
        MIN_LOCAL_TRANSITION_END
    }
}
