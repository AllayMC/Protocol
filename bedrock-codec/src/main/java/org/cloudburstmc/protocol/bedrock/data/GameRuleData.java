package org.cloudburstmc.protocol.bedrock.data;

/**
 * GameRule contains game rule data.
 *
 * @param name     The name of the game rule.
 * @param editable Whether editable.
 * @param value    The new value of the game rule. This is either a bool, uint32 or float32.
 */
public record GameRuleData<T>(String name, boolean editable, T value) {
    public GameRuleData(String name, T value) {
        this(name, false, value);
    }

    @Override
    public String toString() {
        return this.name + '=' + this.value;
    }
}
