package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.cloudburstmc.protocol.bedrock.data.CoordinateEvaluationOrder;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;

import java.util.List;

/**
 * Represents biome scatter param data used in the Bedrock protocol.
 *
 * @param coordinates        The coordinates.
 * @param evalOrder          The eval order.
 * @param chancePercentType  The chance percent type.
 * @param chancePercent      The chance percent.
 * @param chanceNumerator    The chance numerator.
 * @param changeDenominator  The change denominator.
 * @param iterationsType     The iterations type.
 * @param iterations         The iterations.
 */
public record BiomeScatterParamData(List<BiomeCoordinateData> coordinates, CoordinateEvaluationOrder evalOrder,
                                    ExpressionOp chancePercentType, String chancePercent, int chanceNumerator,
                                    int changeDenominator, ExpressionOp iterationsType, String iterations) {

    @JsonCreator
    public BiomeScatterParamData {
    }
}
