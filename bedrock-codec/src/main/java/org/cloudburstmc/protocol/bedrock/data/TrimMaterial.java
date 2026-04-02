package org.cloudburstmc.protocol.bedrock.data;

/**
 * Describes an armor trim material entry advertised in {@link
 * org.cloudburstmc.protocol.bedrock.packet.TrimDataPacket}.
 *
 * @param materialId the protocol identifier of the trim material, for example {@code netherite}
 * @param color      the formatting code used when displaying the material in trim-related UI
 * @param itemName   the identifier of the item representing this material, for example {@code
 *                   minecraft:netherite_ingot}
 */
public record TrimMaterial(String materialId, String color, String itemName) {
}
