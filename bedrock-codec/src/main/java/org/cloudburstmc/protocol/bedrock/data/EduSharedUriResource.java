package org.cloudburstmc.protocol.bedrock.data;

/**
 * EducationSharedResourceURI is an education edition feature that is used for transmitting
 * education resource settings to clients. It contains a button name and a link URL.
 *
 * @param buttonName the button label shown to the player
 * @param linkUri the URI opened when that button is used
 */
public record EduSharedUriResource(String buttonName, String linkUri) {
    public static final EduSharedUriResource EMPTY = new EduSharedUriResource("", "");

}
