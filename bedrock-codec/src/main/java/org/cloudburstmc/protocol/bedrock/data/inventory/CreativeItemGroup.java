package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Data;

@Data
public class CreativeItemGroup {
    private final CreativeItemCategory category;
    private final String name;
    private final ItemData icon;
}
