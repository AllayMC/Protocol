package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Marker interface for the typed payloads carried by {@link
 * org.cloudburstmc.protocol.bedrock.packet.EventPacket}.
 */
public interface EventData {

    EventDataType getType();
}
