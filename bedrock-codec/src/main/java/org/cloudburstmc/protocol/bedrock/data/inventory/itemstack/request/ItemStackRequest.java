package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;

/**
 * A single inventory stack request sent by the client in an {@link ItemStackRequestPacket} or
 * {@link PlayerAuthInputPacket}. Each request is either accepted or rejected by the server in the
 * corresponding item stack response entry.
 *
 * @param requestId a unique id used to match the request with the server response
 * @param actions the ordered inventory actions that make up this request
 * @param filterStrings text values that may require filtering, such as renamed anvil output
 * @param textProcessingEventOrigin the origin used when processing {@code filterStrings}
 */
public record ItemStackRequest(int requestId, ItemStackRequestAction[] actions, String[] filterStrings,
                               TextProcessingEventOrigin textProcessingEventOrigin) {
    public ItemStackRequest(int requestId, ItemStackRequestAction[] actions, String[] filterStrings) {
        this(requestId, actions, filterStrings, TextProcessingEventOrigin.BLOCK_ENTITY_DATA_TEXT);
    }
}
