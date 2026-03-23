package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthPayload;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent when a sub-client joins the server while another client is already connected to it. The
 * packet is sent as a result of split-screen game play, and allows up to four players to play using
 * the same network connection. After an initial Login packet from the 'main' client, each sub-
 * client that connects sends a SubClientLogin to request their own login.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SubClientLoginPacket implements BedrockPacket {
  /**
   * The JWT payload signed by Minecraft's authentication server. Assuming this is a valid
   * signature, it can be trusted to contain the player's identity and other information.
   */
  private AuthPayload authPayload;

  /**
   * The JWT payload signed by the client. The client can modify this, so it should not be trusted.
   */
  private String clientJwt;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.SUB_CLIENT_LOGIN;
  }

  @Override
  public SubClientLoginPacket clone() {
    try {
      return (SubClientLoginPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
