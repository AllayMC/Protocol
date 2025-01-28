package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Data;

@Data
public class CreativeItemData {
    private final ItemData item;
    private final int netId;
    private final int groupId;
}
