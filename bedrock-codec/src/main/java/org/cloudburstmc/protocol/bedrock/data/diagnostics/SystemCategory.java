package org.cloudburstmc.protocol.bedrock.data.diagnostics;

import lombok.Value;

@Value
public class SystemCategory {
    String categoryName;
    long systemIndex;
}
