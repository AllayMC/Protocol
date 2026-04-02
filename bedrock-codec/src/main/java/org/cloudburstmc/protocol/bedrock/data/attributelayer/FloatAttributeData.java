package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * AttributeData represents a polymorphic attribute value.
 *
 * @param value         The value.
 * @param operation     The operation.
 * @param constraintMin FloatConstraintMin is the optional minimum constraint for float attributes.
 * @param constraintMax FloatConstraintMax is the optional maximum constraint for float attributes.
 */
public record FloatAttributeData(float value, Operation operation, @Nullable Float constraintMin,
                                 @Nullable Float constraintMax) implements AttributeData {

    public enum Operation {
        OVERRIDE, ALPHA_BLEND, ADD, SUBTRACT, MULTIPLY, MINIMUM, MAXIMUM
    }
}
