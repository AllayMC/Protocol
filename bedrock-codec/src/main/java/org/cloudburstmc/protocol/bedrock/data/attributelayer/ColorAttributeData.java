package org.cloudburstmc.protocol.bedrock.data.attributelayer;

/**
 * AttributeData represents a polymorphic attribute value.
 *
 * @param value     The value.
 * @param operation The operation.
 */
public record ColorAttributeData(Color255RGBA value, Operation operation) implements AttributeData {

    public interface Color255RGBA {
    }

    public record StringColor(String value) implements Color255RGBA {
    }

    public record ArrayColor(int[] value) implements Color255RGBA {
    }

    public enum Operation {
        OVERRIDE, ALPHA_BLEND, ADD, SUBTRACT, MULTIPLY
    }
}
