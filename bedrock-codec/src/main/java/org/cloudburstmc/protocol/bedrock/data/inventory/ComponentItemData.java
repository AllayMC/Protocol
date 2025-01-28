package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentItemData {
    private String name;
    private NbtMap data;
    /**
     * @since v776
     */
    private short itemId;
    /**
     * @since v776
     */
    private boolean componentBased;
    /**
     * @since v776
     */
    private int version;

    public static ComponentItemData fromLegacyItem(String name, NbtMap data) {
        ComponentItemData item = new ComponentItemData();
        item.setName(name);
        item.setData(data);
        item.setVersion(data.getInt("version"));
        return item;
    }
}
