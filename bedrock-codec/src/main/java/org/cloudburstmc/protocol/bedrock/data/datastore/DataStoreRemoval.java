package org.cloudburstmc.protocol.bedrock.data.datastore;

import lombok.Data;

/**
 * Represents a removal from a data store.
 */
@Data
public class DataStoreRemoval implements DataStoreAction {

    /**
     * The name of the data store being removed.
     */
    private String dataStoreName;

    @Override
    public int getType() {
        return 2;
    }
}
