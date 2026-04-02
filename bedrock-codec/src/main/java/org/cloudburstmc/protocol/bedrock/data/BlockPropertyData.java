package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.nbt.NbtMap;

/**
 * BlockActorData is sent by the server to update data of a block entity client-side, for example
 * the data of a chest.
 *
 * @param name       The name.
 * @param properties The properties.
 */
public record BlockPropertyData(String name, NbtMap properties) {
}
