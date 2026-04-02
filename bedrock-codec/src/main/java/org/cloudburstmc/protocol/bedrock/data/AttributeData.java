package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.protocol.bedrock.data.attribute.AttributeModifierData;

import java.util.Collections;
import java.util.List;

/**
 * Represents a full entity attribute, including the current value, the allowed range, the default
 * range/value, and any active modifiers.
 *
 * @param name           The attribute name, for example {@code minecraft:health}.
 * @param minimum        The minimum allowed value for the attribute.
 * @param maximum        The maximum allowed value for the attribute.
 * @param value          The current attribute value applied to the entity.
 * @param defaultMinimum The default minimum value of the attribute.
 * @param defaultMaximum The default maximum value of the attribute.
 * @param defaultValue   The default value of the attribute.
 * @param modifiers      Temporary modifiers currently applied to the attribute.
 */
public record AttributeData(String name, float minimum, float maximum, float value, float defaultMinimum, float defaultMaximum, float defaultValue,
                            List<AttributeModifierData> modifiers) {
    public AttributeData(String name, float minimum, float maximum, float value) {
        this(name, minimum, maximum, value, maximum, Collections.emptyList());
    }

    public AttributeData(String name, float minimum, float maximum, float value, float defaultValue) {
        this(name, minimum, maximum, value, defaultValue, Collections.emptyList());
    }

    public AttributeData(String name, float minimum, float maximum, float value, float defaultValue, List<AttributeModifierData> modifiers) {
        this(name, minimum, maximum, value, minimum, maximum, defaultValue, modifiers);
    }
}
