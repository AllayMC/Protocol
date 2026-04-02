package org.cloudburstmc.protocol.bedrock.data.map;

/**
 * Represents map pixel used in the Bedrock protocol.
 *
 * @param pixel Colour value of pixel
 * @param index Pixel index in map.
 */
public record MapPixel(int pixel, int index) {
}
