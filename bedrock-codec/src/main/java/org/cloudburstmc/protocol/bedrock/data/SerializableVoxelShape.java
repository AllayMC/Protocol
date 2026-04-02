package org.cloudburstmc.protocol.bedrock.data;

import java.util.List;

/**
 * VoxelShape represents a voxel shape with cells and coordinate axes.
 *
 * @param cells        The grid of cells representing solid and empty regions.
 * @param xCoordinates A list of X axis coordinates for the shape.
 * @param yCoordinates A list of Y axis coordinates for the shape.
 * @param zCoordinates A list of Z axis coordinates for the shape.
 */
public record SerializableVoxelShape(SerializableCells cells, List<Float> xCoordinates, List<Float> yCoordinates,
                                     List<Float> zCoordinates) {
    /**
     * Packed voxel cell storage metadata.
     *
     * @param xSize   The size of the X axis in cells.
     * @param ySize   The size of the Y axis in cells.
     * @param zSize   The size of the Z axis in cells.
     * @param storage The packed cell storage.
     */
    public record SerializableCells(short xSize, short ySize, short zSize, List<Short> storage) {
    }
}
