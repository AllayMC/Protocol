package org.cloudburstmc.protocol.bedrock.data.attributelayer;

/**
 * AttributeData represents a polymorphic attribute value.
 *
 * @param value     Whether value.
 * @param operation The operation.
 */
public record BoolAttributeData(boolean value, Operation operation) implements AttributeData {

    public enum Operation {
        OVERRIDE, ALPHA_BLEND, AND, NAND, OR, NOR, XOR, XNOR
    }
}
