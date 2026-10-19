package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * A sound operation carried by ClientboundUpdateSoundDataPacket's single event variant.
 * The v1001 decoder also uses {@link StopSoundData} to represent its legacy stop-only action.
 * Codecs from v2168 through v2193 carry operations in separate packet fields.
 *
 * @since v2225
 */
public interface SoundData {

    SoundDataType getType();
}
