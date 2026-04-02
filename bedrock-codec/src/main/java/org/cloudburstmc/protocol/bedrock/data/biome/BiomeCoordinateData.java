package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;
import org.cloudburstmc.protocol.bedrock.data.RandomDistributionType;

/**
 * BiomeCoordinate specifies coordinate rules for where features can be scattered in the biome.
 *
 * @param minValueType The expression operation to use when evaluating the minimum value.
 * @param minValue     The minimum value expression.
 * @param maxValueType The expression operation to use when evaluating the maximum value.
 * @param maxValue     The maximum value expression.
 * @param gridOffset   The offset of the grid, used for fixed grid and jittered grid distributions.
 * @param gridStepSize The step size of the grid, used for fixed grid and jittered grid
 *                     distributions.
 * @param distribution The random distribution to use for this coordinate rule.
 */
public record BiomeCoordinateData(ExpressionOp minValueType, String minValue, ExpressionOp maxValueType,
                                  String maxValue, long gridOffset, long gridStepSize,
                                  RandomDistributionType distribution) {

    @JsonCreator
    public BiomeCoordinateData {
    }
}
