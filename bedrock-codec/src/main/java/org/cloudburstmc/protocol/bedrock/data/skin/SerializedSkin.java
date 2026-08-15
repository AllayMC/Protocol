package org.cloudburstmc.protocol.bedrock.data.skin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.*;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.JSONValue;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import static org.cloudburstmc.protocol.bedrock.util.Preconditions.checkArgument;

/**
 * The serialised form of a player skin as sent in packets such as
 * {@link org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket} and
 * {@link org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket}.
 */
@Getter
@ToString(exclude = {"geometryData"})
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializedSkin {
    private static final int PIXEL_SIZE = 4;
    private static final Color DEFAULT_COLOR = new Color(0, true);

    public static final int SINGLE_SKIN_SIZE = 64 * 32 * PIXEL_SIZE;
    public static final int DOUBLE_SKIN_SIZE = 64 * 64 * PIXEL_SIZE;
    public static final int SKIN_128_64_SIZE = 128 * 64 * PIXEL_SIZE;
    public static final int SKIN_128_128_SIZE = 128 * 128 * PIXEL_SIZE;

    /**
     * A unique identifier for the skin.
     */
    private final String skinId;
    /**
     * The legacy geometry name used by older protocol versions.
     */
    private final String geometryName;
    /**
     * The RGBA skin image data.
     */
    private final ImageData skinData;
    /**
     * The RGBA cape image data.
     */
    private final ImageData capeData;
    /**
     * The JSON geometry payload describing bones, UVs, pivots and related model data.
     */
    private final String geometryData;
    /**
     * Whether this skin is a premium marketplace skin.
     */
    private final boolean premium;
    /**
     * The JSON resource patch that points the client to the skin geometry to use.
     *
     * @since v388
     */
    private final String skinResourcePatch;
    /**
     * The animations.
     *
     * @since v388
     */
    private final List<AnimationData> animations;
    /**
     * Additional animation metadata payload.
     *
     * @since v388
     */
    private final String animationData;
    /**
     * Whether this skin was created with the in-game persona creator.
     *
     * @since v388
     */
    private final boolean persona;
    /**
     * Whether a persona cape is applied on a classic skin.
     *
     * @since v388
     */
    private final boolean capeOnClassic;
    /**
     * The cape ID.
     *
     * @since v388
     */
    private final String capeId;
    /**
     * A full identifier for the combined skin and cape.
     *
     * @since v388
     */
    private final String fullSkinId;
    /**
     * The arm width variant, typically {@code wide} or {@code slim}.
     *
     * @since v390
     */
    private final String armSize;
    /**
     * The base skin colour in hex notation.
     *
     * @since v390
     * @deprecated since v2168
     */
    private final String skinColor;
    /**
     * The base skin colour in ARGB form, replacing the legacy string representation.
     *
     * @since v2168
     */
    private final Color color;
    /**
     * The persona pieces.
     *
     * @since v390
     */
    private final List<PersonaPieceData> personaPieces;
    /**
     * The tint colors.
     *
     * @since v390
     */
    private final List<PersonaPieceTintData> tintColors;
    /**
     * The PlayFab identifier associated with the skin.
     *
     * @since v428
     */
    private final String playFabId;
    /**
     * The engine version associated with the geometry data.
     *
     * @since v465
     */
    private final String geometryDataEngineVersion;
    /**
     * Whether this skin belongs to the primary local user.
     *
     * @since v465
     */
    private final boolean primaryUser;
    /**
     * Whether this skin should override the player's locally equipped appearance.
     *
     * @since v568
     */
    private final boolean overridingPlayerAppearance;
    /**
     * Whether the skin is marked as trusted by its sender. This value is client-controlled and is
     * not a security boundary.
     *
     * @since v2168
     */
    private final boolean trusted;
    /**
     * The profile hash supplied with this skin.
     *
     * @since v2168
     */
    private final String profileHash;

    public static SerializedSkin of(String skinId, String playFabId, ImageData skinData, ImageData capeData, String geometryName,
                                    String geometryData, boolean premiumSkin) {
        skinData.checkLegacySkinSize();
        capeData.checkLegacyCapeSize();

        return new SerializedSkin(skinId, geometryName, skinData, capeData, geometryData, premiumSkin, null,
                Collections.emptyList(), "", false, false, "", "", "wide", null,
                DEFAULT_COLOR, Collections.emptyList(), Collections.emptyList(), playFabId, "0.0.0", true, true, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    String capeId, String fullSkinId) {
        return of(skinId, playFabId, skinResourcePatch, skinData, Collections.unmodifiableList(new ObjectArrayList<>(animations)),
                capeData, geometryData, animationData, premium, persona, capeOnClassic, capeId, fullSkinId,
                "wide", "#0", Collections.emptyList(), Collections.emptyList());
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    String capeId, String fullSkinId, String armSize, String skinColor,
                                    List<PersonaPieceData> personaPieces, List<PersonaPieceTintData> tintColors) {
        return of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, animationData, premium, persona, capeOnClassic, true, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors);
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String animationData, boolean premium, boolean persona, boolean capeOnClassic,
                                    boolean primaryUser, String capeId, String fullSkinId, String armSize,
                                    String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors) {
        return new SerializedSkin(skinId, null, skinData, capeData, geometryData, premium, skinResourcePatch,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), animationData, persona, capeOnClassic,
                capeId, fullSkinId, armSize, skinColor, null, personaPieces, tintColors, playFabId, "0.0.0", primaryUser, true, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors) {

        return new SerializedSkin(skinId, null, skinData, capeData, geometryData, premium, skinResourcePatch,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), animationData, persona, capeOnClassic,
                capeId, fullSkinId, armSize, skinColor, null, personaPieces, tintColors, playFabId,
                geometryDataEngineVersion, primaryUser, true, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors, boolean overridingPlayerAppearance) {

        return new SerializedSkin(skinId, null, skinData, capeData, geometryData, premium, skinResourcePatch,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), animationData, persona, capeOnClassic,
                capeId, fullSkinId, armSize, skinColor, null, personaPieces, tintColors, playFabId,
                geometryDataEngineVersion, primaryUser, overridingPlayerAppearance, true, "");
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, String skinColor, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors, boolean overridingPlayerAppearance,
                                    boolean trusted, String profileHash) {
        Color color = null;
        if (skinColor != null && !skinColor.isEmpty()) {
            color = "#0".equals(skinColor) ? new Color(0, true) :
                    new Color((int) Long.parseLong(skinColor.startsWith("#") ? skinColor.substring(1) : skinColor, 16), true);
        }
        return new SerializedSkin(skinId, null, skinData, capeData, geometryData, premium, skinResourcePatch,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), animationData, persona, capeOnClassic,
                capeId, fullSkinId, armSize, skinColor, color, personaPieces, tintColors, playFabId,
                geometryDataEngineVersion, primaryUser, overridingPlayerAppearance, trusted, profileHash);
    }

    public static SerializedSkin of(String skinId, String playFabId, String skinResourcePatch, ImageData skinData,
                                    List<AnimationData> animations, ImageData capeData, String geometryData,
                                    String geometryDataEngineVersion, String animationData, boolean premium,
                                    boolean persona, boolean capeOnClassic, boolean primaryUser, String capeId,
                                    String fullSkinId, String armSize, Color color, List<PersonaPieceData> personaPieces,
                                    List<PersonaPieceTintData> tintColors, boolean overridingPlayerAppearance, boolean trusted, String profileHash) {

        return new SerializedSkin(skinId, null, skinData, capeData, geometryData, premium, skinResourcePatch,
                Collections.unmodifiableList(new ObjectArrayList<>(animations)), animationData, persona, capeOnClassic,
                capeId, fullSkinId, armSize, null, color, personaPieces, tintColors, playFabId,
                geometryDataEngineVersion, primaryUser, overridingPlayerAppearance, trusted, profileHash);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder().skinId(this.skinId).playFabId(this.playFabId).geometryName(this.geometryName)
                .skinResourcePatch(this.skinResourcePatch).skinData(this.skinData).animations(this.animations)
                .capeData(this.capeData).geometryData(this.geometryData).geometryDataEngineVersion(this.geometryDataEngineVersion)
                .animationData(this.animationData).premium(this.premium).persona(this.persona).capeOnClassic(this.capeOnClassic)
                .primaryUser(this.primaryUser).capeId(this.capeId).fullSkinId(this.fullSkinId).armSize(this.armSize).skinColor(this.skinColor)
                .color(this.color).personaPieces(this.personaPieces).tintColors(this.tintColors)
                .overridingPlayerAppearance(this.overridingPlayerAppearance).trusted(this.trusted).profileHash(this.profileHash);
    }

    public boolean isValid() {
        return isValidSkin() && isValidResourcePatch();
    }

    private boolean isValidSkin() {
        return skinId != null && !skinId.trim().isEmpty() &&
               skinData != null && skinData.getWidth() >= 64 && skinData.getHeight() >= 32 &&
               skinData.getImage().length >= SINGLE_SKIN_SIZE;
    }

    public String getSkinResourcePatch() {
        if (skinResourcePatch == null && geometryName != null) {
            return convertLegacyGeometryName(geometryName);
        }
        return skinResourcePatch;
    }

    public String getGeometryName() {
        if (geometryName == null && skinResourcePatch != null) {
            return convertSkinPatchToLegacy(skinResourcePatch);
        }
        return geometryName;
    }

    private static String convertLegacyGeometryName(String geometryName) {
        return "{\"geometry\" : {\"default\" : \"" + JSONValue.escape(geometryName) + "\"}}";
    }

    private static String convertSkinPatchToLegacy(String skinResourcePatch) {
        checkArgument(validateSkinResourcePatch(skinResourcePatch), "Invalid skin resource patch");
        JSONObject object = (JSONObject) JSONValue.parse(skinResourcePatch);
        JSONObject geometry = (JSONObject) object.get("geometry");
        return (String) geometry.get("default");
    }

    private boolean isValidResourcePatch() {
        return skinResourcePatch != null && validateSkinResourcePatch(skinResourcePatch);
    }

    private static boolean validateSkinResourcePatch(String skinResourcePatch) {
        try {
            JSONObject object = (JSONObject) JSONValue.parse(skinResourcePatch);
            JSONObject geometry = (JSONObject) object.get("geometry");
            return geometry.containsKey("default") && geometry.get("default") instanceof String;
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    /**
     * @deprecated since v2168, use color
     */
    public String getSkinColor() {
        if ((skinColor == null || skinColor.isEmpty()) && color != null) {
            if (color.getAlpha() == 0) {
                return  "#0";
            } else {
                return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            }
        }
        return skinColor;
    }

    /**
     * Gets the base skin colour in ARGB form.
     *
     * @return the base skin colour, or {@code null} when no colour is available
     *
     * @since v2168
     */
    public Color getColor() {
        if (color == null && skinColor != null && !skinColor.isEmpty()) {
            if (skinColor.equals("#0")) {
                return new Color(0, true);
            } else {
                return new Color((int) Long.parseLong(skinColor.startsWith("#") ? skinColor.substring(1) : skinColor, 16), true);
            }
        }
        return color;
    }

    public String getFullSkinId() {
        return fullSkinId == null ? skinId + capeId : fullSkinId;
    }

    public static class Builder {
        private String skinId;
        private String playFabId;
        private String geometryName;
        private String skinResourcePatch;
        private ImageData skinData;
        private List<AnimationData> animations;
        private ImageData capeData;
        private String geometryData;
        private String animationData;
        private boolean premium;
        private boolean persona;
        private boolean capeOnClassic;
        private String capeId;
        private String fullSkinId;
        private String armSize;
        private String skinColor;
        private Color color;
        private List<PersonaPieceData> personaPieces;
        private List<PersonaPieceTintData> tintColors;
        private String geometryDataEngineVersion;
        private boolean primaryUser;
        private boolean overridingPlayerAppearance;
        private boolean trusted = true;
        private String profileHash;

        Builder() {
        }

        public Builder skinId(String skinId) { this.skinId = skinId; return this; }
        public Builder playFabId(String playFabId) { this.playFabId = playFabId; return this; }
        public Builder geometryName(String geometryName) { this.geometryName = geometryName; return this; }
        public Builder skinResourcePatch(String skinResourcePatch) { this.skinResourcePatch = skinResourcePatch; return this; }
        public Builder skinData(ImageData skinData) { this.skinData = skinData; return this; }
        public Builder animations(List<AnimationData> animations) { this.animations = animations; return this; }
        public Builder capeData(ImageData capeData) { this.capeData = capeData; return this; }
        public Builder geometryData(String geometryData) { this.geometryData = geometryData; return this; }
        public Builder animationData(String animationData) { this.animationData = animationData; return this; }
        public Builder premium(boolean premium) { this.premium = premium; return this; }
        public Builder persona(boolean persona) { this.persona = persona; return this; }
        public Builder capeOnClassic(boolean capeOnClassic) { this.capeOnClassic = capeOnClassic; return this; }
        public Builder capeId(String capeId) { this.capeId = capeId; return this; }
        public Builder fullSkinId(String fullSkinId) { this.fullSkinId = fullSkinId; return this; }
        public Builder armSize(String armSize) { this.armSize = armSize; return this; }
        public Builder skinColor(String skinColor) { this.skinColor = skinColor; return this; }
        public Builder color(Color color) { this.color = color; return this; }
        public Builder personaPieces(List<PersonaPieceData> personaPieces) { this.personaPieces = personaPieces; return this; }
        public Builder tintColors(List<PersonaPieceTintData> tintColors) { this.tintColors = tintColors; return this; }
        public Builder geometryDataEngineVersion(String version) { this.geometryDataEngineVersion = version; return this; }
        public Builder primaryUser(boolean primaryUser) { this.primaryUser = primaryUser; return this; }
        public Builder overridingPlayerAppearance(boolean overridingPlayerAppearance) { this.overridingPlayerAppearance = overridingPlayerAppearance; return this; }
        public Builder trusted(boolean trusted) { this.trusted = trusted; return this; }
        public Builder profileHash(String profileHash) { this.profileHash = profileHash; return this; }

        public SerializedSkin build() {
            if (playFabId == null) playFabId = "";
            if (animations == null) animations = Collections.emptyList();
            if (animationData == null) animationData = "";
            if (capeData == null) capeData = ImageData.EMPTY;
            if (capeId == null) capeId = "";
            if (fullSkinId == null) fullSkinId = skinId + capeId;
            if (armSize == null) armSize = "wide";
            if (geometryDataEngineVersion == null) geometryDataEngineVersion = "0.0.0";
            if (skinColor == null) skinColor = "#0";
            if (personaPieces == null) personaPieces = Collections.emptyList();
            if (tintColors == null) tintColors = Collections.emptyList();
            if (profileHash == null) profileHash = "";

            Color resolvedColor = color;
            if (resolvedColor == null) {
                resolvedColor = "#0".equals(skinColor) ? new Color(0, true) :
                        new Color((int) Long.parseLong(skinColor.startsWith("#") ? skinColor.substring(1) : skinColor, 16), true);
            }
            if (skinResourcePatch == null) {
                return new SerializedSkin(skinId, geometryName, skinData, capeData, geometryData, premium, null,
                        Collections.unmodifiableList(new ObjectArrayList<>(animations)), animationData, persona, capeOnClassic,
                        capeId, fullSkinId, armSize, skinColor, resolvedColor, personaPieces, tintColors, playFabId,
                        geometryDataEngineVersion, primaryUser, overridingPlayerAppearance, trusted, profileHash);
            }
            return SerializedSkin.of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData,
                    geometryDataEngineVersion, animationData, premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId,
                    armSize, resolvedColor, personaPieces, tintColors, overridingPlayerAppearance, trusted, profileHash);
        }

        @Override
        public String toString() {
            return "SerializedSkin.Builder(skinId=" + this.skinId + ", playFabId=" + this.playFabId +
                    ", geometryName=" + this.geometryName + ", skinResourcePatch=" + this.skinResourcePatch +
                    ", skinData=" + this.skinData + ", animations=" + this.animations + ", capeData=" + this.capeData +
                    ", geometryData=" + this.geometryData + ", animationData=" + this.animationData +
                    ", premium=" + this.premium + ", persona=" + this.persona + ", capeOnClassic=" + this.capeOnClassic +
                    ", capeId=" + this.capeId + ", fullSkinId=" + this.fullSkinId + ", armSize=" + this.armSize +
                    ", skinColor=" + this.skinColor + ", color=" + this.color + ", personaPieces=" + this.personaPieces +
                    ", tintColors=" + this.tintColors + ", geometryDataEngineVersion=" + this.geometryDataEngineVersion +
                    ", primaryUser=" + this.primaryUser + ", overridingPlayerAppearance=" + this.overridingPlayerAppearance +
                    ", trusted=" + this.trusted + ", profileHash=" + this.profileHash + ")";
        }
    }
}
