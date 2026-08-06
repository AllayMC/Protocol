package org.cloudburstmc.protocol.bedrock.codec.v975;

import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandParamMappingTest {

    @Test
    void mapsRelativeFloatParametersToTheirProtocolIds() {
        assertNull(Bedrock_v975.COMMAND_PARAMS.getTypeUnsafe(2));
        assertEquals(CommandParam.FLOAT, Bedrock_v975.COMMAND_PARAMS.getType(3));
        assertEquals(CommandParam.R_VALUE, Bedrock_v975.COMMAND_PARAMS.getType(4));
    }
}
