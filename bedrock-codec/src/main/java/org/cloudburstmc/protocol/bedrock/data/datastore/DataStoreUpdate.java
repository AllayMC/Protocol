package org.cloudburstmc.protocol.bedrock.data.datastore;

import lombok.Data;

/**
 * Represents a client-requested update to a property stored in a named data store.
 */
@Data
public class DataStoreUpdate implements DataStoreAction {

    /**
     * The name of the target data store.
     */
    private String dataStoreName;
    /**
     * The top-level property being updated.
     */
    private String property;
    /**
     * The nested path within {@link #property}.
     */
    private String path;
    /**
     * The value to write at {@link #path}.
     */
    private Object data;
    /**
     * The update count for {@link #property}.
     */
    private int updateCount;
    /**
     * The update count for the path.
     *
     * @since v924
     */
    private int pathUpdateCount;

    @Override
    public int getType() {
        return 0;
    }
}
