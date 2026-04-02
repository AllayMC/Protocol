package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by both the client and the server. It is a way to let scripts communicate with the server,
 * so that the client can let the server know it triggered an event, or the other way around.
 * ScriptCustomEvent is deprecated as of 1.20.10.
 *
 * @deprecated since v594
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class ScriptCustomEventPacket implements BedrockPacket {
    /**
     * The name of the event. The script and the server will use this event name to identify the
     * data that is sent.
     */
    private String eventName;
    /**
     * The event payload associated with {@link #eventName}. Its format is application-defined and
     * depends on the script event channel in use.
     */
    private String data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SCRIPT_CUSTOM_EVENT;
    }

    @Override
    public ScriptCustomEventPacket clone() {
        try {
            return (ScriptCustomEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
