package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.RequiredArgsConstructor;

/**
 * Enumerates entity data format values used in the Bedrock protocol.
 */
@RequiredArgsConstructor
public enum EntityDataFormat {
    BYTE,
    SHORT,
    INT,
    FLOAT,
    STRING,
    NBT,
    VECTOR3I,
    LONG,
    VECTOR3F
}
