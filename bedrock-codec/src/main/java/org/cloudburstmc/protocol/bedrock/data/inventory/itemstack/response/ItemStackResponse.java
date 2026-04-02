package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;

import java.util.List;

/**
 * Represents an individual response to a {@link ItemStackRequest}
 * sent as part of {@link org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket}.
 *
 * @param success    success specifies if the request with the requestId below was successful. If
 *                   this is the case, the containers below will have information on what slots
 *                   ended up changing. If not, the container info will be empty.
 * @param result     Replaces the success boolean as of v419.
 * @param requestId  requestId is the unique ID of the request that this response is in reaction to.
 *                   If rejected, the client will undo the actions from the request with this ID.
 * @param containers containers holds information on the containers that had their contents changed
 *                   as a result of the request.
 */
public record ItemStackResponse(boolean success, ItemStackResponseStatus result, int requestId,
                                List<ItemStackResponseContainer> containers) {

    @Deprecated
    public ItemStackResponse(boolean success, int requestId, List<ItemStackResponseContainer> containers) {
        this(success, success ? ItemStackResponseStatus.OK : ItemStackResponseStatus.ERROR, requestId, containers);
    }

    public ItemStackResponse(ItemStackResponseStatus result, int requestId, List<ItemStackResponseContainer> containers) {
        this(false, result, requestId, containers);
    }

}
