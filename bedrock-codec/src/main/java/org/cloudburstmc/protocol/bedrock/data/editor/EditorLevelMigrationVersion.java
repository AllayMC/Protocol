package org.cloudburstmc.protocol.bedrock.data.editor;

/**
 * Editor world migration state sent in the level settings of StartGame.
 *
 * @since v2225
 */
public enum EditorLevelMigrationVersion {
    LEGACY,
    EDITOR_ACHIEVEMENTS
}
