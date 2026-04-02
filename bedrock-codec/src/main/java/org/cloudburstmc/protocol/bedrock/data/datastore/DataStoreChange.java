package org.cloudburstmc.protocol.bedrock.data.datastore;

import lombok.Data;

/**
 * Represents a change to a data store property value.
 */
@Data
public class DataStoreChange implements DataStoreAction {

    /**
     * The name of the data store.
     */
    private String dataStoreName;
    /**
     * The property that changed.
     */
    private String property;
    /**
     * The new property value.
     */
    private Object newValue;
    /**
     * The update count.
     */
    private int updateCount;

    @Override
    public int getType() {
        return 1;
    }
}
