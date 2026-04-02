package org.cloudburstmc.protocol.bedrock.data.skin;

/**
 * Represents a piece of a persona skin. All pieces are sent separately.
 *
 * @param id        The ID.
 * @param type      The type.
 * @param packId    A UUID that identifies the pack that the persona piece belongs to.
 * @param isDefault Whether default.
 * @param productId A UUID that identifies the piece when it comes to purchases. It is empty for pieces that have
 *                  the 'IsDefault' field set to true.
 */
public record PersonaPieceData(String id, String type, String packId, boolean isDefault, String productId) {
}
