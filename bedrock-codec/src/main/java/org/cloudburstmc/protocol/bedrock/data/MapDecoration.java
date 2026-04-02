package org.cloudburstmc.protocol.bedrock.data;

/**
 * Represents a fixed decoration on a map: Its position or other properties do not change
 * automatically client-side.
 *
 * @param image    The decoration image/type identifier. It controls the icon shape, and for some
 *                 icons their default colour.
 * @param rotation The serialized rotation value for the decoration. It is transmitted as a single
 *                 byte and maps to the 16 fixed directions that a map decoration may face.
 * @param xOffset  The offset on the X axis in pixels of the decoration.
 * @param yOffset  The offset on the Y axis in pixels of the decoration.
 * @param label    The name of the map decoration. This name may be of any value.
 * @param color    The colour of the map decoration. Some map decoration types have a specific
 *                 colour set automatically, whereas others may be changed.
 */
public record MapDecoration(int image, int rotation, int xOffset, int yOffset, String label, int color) {
}
