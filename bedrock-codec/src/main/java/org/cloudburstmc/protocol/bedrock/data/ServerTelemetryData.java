package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;

@Data
public class ServerTelemetryData {

    public static final ServerTelemetryData EMPTY = new ServerTelemetryData("", "", "", "");

    private final String serverId;
    private final String scenarioId;
    private final String worldId;
    private final String ownerId;
}
