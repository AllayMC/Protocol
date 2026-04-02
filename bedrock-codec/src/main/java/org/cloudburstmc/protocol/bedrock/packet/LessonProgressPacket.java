package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ee.LessonAction;

/**
 * A packet sent by the server to the client to inform the client of updated progress on a lesson.
 * This packet only functions on the Minecraft: Education Edition version of the game.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LessonProgressPacket implements BedrockPacket {
    /**
     * The lesson progress action the client should display.
     */
    private LessonAction action;
    /**
     * The score the client should use when displaying the progress.
     */
    private int score;
    /**
     * The identifier of the lesson activity whose progress is being updated.
     */
    private String activityId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LESSON_PROGRESS;
    }

    @Override
    public LessonProgressPacket clone() {
        try {
            return (LessonProgressPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
