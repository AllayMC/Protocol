package org.cloudburstmc.protocol.bedrock.data.skin;

import java.util.List;

/**
 * PersonaPieceTintColour describes the tint colours of a specific piece of a persona skin.
 *
 * @param type   The type.
 * @param colors An array of four colours written in hex notation (note, that unlike the SkinColor field in
 *               the ClientData struct, this is actually ARGB, not just RGB). The colours refer to different
 *               parts of the skin piece. The 'persona_eyes' may have the following colours:
 *               ["#ffa12722","#ff2f1f0f","#ff3aafd9","#0"] The first hex colour represents the tint colour of
 *               the iris, the second hex colour represents the eyebrows and the third represents the sclera.
 *               The fourth is #0 because there are only 3 parts of the persona_eyes skin piece.
 */
public record PersonaPieceTintData(String type, List<String> colors) {
}
