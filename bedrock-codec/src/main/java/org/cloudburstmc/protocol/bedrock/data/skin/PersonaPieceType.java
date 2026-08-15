package org.cloudburstmc.protocol.bedrock.data.skin;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates persona-skin piece types and the protocol names used to represent them.
 *
 * @since v2168
 */
public enum PersonaPieceType {

    UNKNOWN("unknown", "persona_unknown"),
    SKELETON("skeleton", "persona_skeleton"),
    BODY("body", "persona_body"),
    SKIN("skin", "persona_skin"),
    BOTTOM("bottom", "persona_bottom"),
    FEET("feet", "persona_feet"),
    DRESS("dress", "persona_dress"),
    TOP("top", "persona_top"),
    HIGH_PANTS("high_pants", "persona_high_pants"),
    HANDS("hands", "persona_hand"),
    OUTERWEAR("outerwear", "persona_outerwear"),
    FACIAL_HAIR("facialhair", "persona_facial_hair"),
    MOUTH("mouth", "persona_mouth"),
    EYES("eyes", "persona_eyes"),
    HAIR("hair", "persona_hair"),
    HOOD("hood", "persona_hood"),
    BACK("back", "persona_back"),
    FACE_ACCESSORY("faceaccessory", "persona_face_accessory"),
    HEAD("head", "persona_head"),
    LEGS("legs", "persona_legs"),
    LEFT_LEG("leftleg", "persona_left_leg"),
    RIGHT_LEG("rightleg", "persona_right_leg"),
    ARMS("arms", "persona_arms"),
    LEFT_ARM("leftarm", "persona_left_arm"),
    RIGHT_ARM("rightarm", "persona_right_arm"),
    CAPES("capes", "persona_capes"),
    CLASSIC_SKIN("classicskin", "persona_classic_skin"),
    EMOTE("emote", "persona_emote"),
    UNSUPPORTED("unsupported", "unsupported");

    /**
     * The canonical name serialized for this persona piece type.
     */
    @Getter
    private final String serializeName;
    /**
     * The alternate protocol name accepted when decoding this persona piece type.
     */
    private final String type;

    private static final Map<String, PersonaPieceType> serializeNames = new HashMap<>(values().length * 2, 1);
    static {
        for (PersonaPieceType value : values()) {
            serializeNames.put(value.serializeName, value);
            serializeNames.put(value.type, value);
        }
    }

    PersonaPieceType(String serializeName, String type) {
        this.serializeName = serializeName;
        this.type = type;
    }

    /**
     * Resolves a canonical or alternate protocol name to its persona piece type.
     *
     * @param serializeName the protocol name to resolve
     * @return the matching persona piece type
     * @throws IllegalArgumentException if the supplied name is unknown
     */
    public static PersonaPieceType fromName(String serializeName) {
        PersonaPieceType value = serializeNames.get(serializeName);
        if (value == null) {
            throw new IllegalArgumentException(serializeName);
        }
        return value;
    }
}
