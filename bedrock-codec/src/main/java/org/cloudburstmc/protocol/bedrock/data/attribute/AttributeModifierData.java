package org.cloudburstmc.protocol.bedrock.data.attribute;

/**
 * AttributeModifier temporarily buffs/debuffs a given attribute until the modifier is used. In
 * vanilla, these are mainly used for effects.
 *
 * @param id           The unique ID of the modifier. It is used to identify the modifier in the packet.
 * @param name         The name of the attribute that is modified.
 * @param amount       The amount of difference between the current value of the attribute and the new value.
 * @param operation    The operation that is performed on the attribute. It can be addition, multiply base, multiply
 *                     total or cap.
 * @param operand      Operand ... TODO: Figure out what this field is used for.
 * @param serializable Serializable ... TODO: Figure out what this field is used for.
 */
public record AttributeModifierData(String id, String name, float amount, AttributeOperation operation, int operand, boolean serializable) {
}
