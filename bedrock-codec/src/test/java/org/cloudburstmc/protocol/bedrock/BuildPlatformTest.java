package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.protocol.bedrock.data.BuildPlatform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildPlatformTest {

    @Test
    void usesProtocolIdsInsteadOfEnumOrdinals() {
        assertEquals(-1, BuildPlatform.UNKNOWN.getId());
        assertEquals(BuildPlatform.UNKNOWN, BuildPlatform.from(-1));
        assertEquals(BuildPlatform.GOOGLE, BuildPlatform.from(1));
        assertEquals(BuildPlatform.LINUX, BuildPlatform.from(15));
        assertEquals(BuildPlatform.UNKNOWN, BuildPlatform.from(0));
    }
}
