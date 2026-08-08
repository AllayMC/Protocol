package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.protocol.bedrock.data.skin.PersonaPieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonaPieceTypeTest {

    @Test
    void usesCurrentProtocolNamesForCompoundPersonaPieces() {
        assertEquals("facialhair", PersonaPieceType.FACIAL_HAIR.getSerializeName());
        assertEquals("faceaccessory", PersonaPieceType.FACE_ACCESSORY.getSerializeName());
        assertEquals("leftleg", PersonaPieceType.LEFT_LEG.getSerializeName());
        assertEquals("rightleg", PersonaPieceType.RIGHT_LEG.getSerializeName());
        assertEquals("leftarm", PersonaPieceType.LEFT_ARM.getSerializeName());
        assertEquals("rightarm", PersonaPieceType.RIGHT_ARM.getSerializeName());
        assertEquals("classicskin", PersonaPieceType.CLASSIC_SKIN.getSerializeName());
    }

    @Test
    void resolvesInternalPersonaPieceNames() {
        assertEquals(PersonaPieceType.FACIAL_HAIR, PersonaPieceType.fromName("persona_facial_hair"));
        assertEquals(PersonaPieceType.CLASSIC_SKIN, PersonaPieceType.fromName("persona_classic_skin"));
    }
}
