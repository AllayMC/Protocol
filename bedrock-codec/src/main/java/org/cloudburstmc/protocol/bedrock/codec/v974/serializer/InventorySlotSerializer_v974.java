package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class InventorySlotSerializer_v974 implements BedrockPacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v974 INSTANCE = new InventorySlotSerializer_v974();
    private static final FullContainerName LEGACY_CONTAINER_NAME_DATA =
            new FullContainerName(ContainerSlotType.ANVIL_INPUT, null);
    private static final ItemData LEGACY_STORAGE_ITEM = ItemData.AIR;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getSlot());
        helper.writeOptionalNull(buffer, this.isLegacyContainerNameData(packet.getContainerNameData()) ? null : packet.getContainerNameData(),
                helper::writeFullContainerName);
        helper.writeOptionalNull(buffer, this.isLegacyStorageItem(packet.getStorageItem()) ? null : packet.getStorageItem(),
                helper::writeNetItemDescriptor);
        helper.writeNetItemDescriptor(buffer, packet.getItem());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, InventorySlotPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setSlot(VarInts.readUnsignedInt(buffer));
        packet.setContainerNameData(helper.readOptional(buffer, LEGACY_CONTAINER_NAME_DATA, helper::readFullContainerName));
        packet.setStorageItem(helper.readOptional(buffer, LEGACY_STORAGE_ITEM, helper::readNetItemDescriptor));
        packet.setItem(helper.readNetItemDescriptor(buffer));
    }

    private boolean isLegacyContainerNameData(FullContainerName containerNameData) {
        return LEGACY_CONTAINER_NAME_DATA.equals(containerNameData);
    }

    private boolean isLegacyStorageItem(ItemData storageItem) {
        return storageItem != null
               && storageItem.getDefinition() == ItemDefinition.AIR
               && storageItem.getDamage() == 0
               && storageItem.getCount() == 0
               && storageItem.getTag() == null
               && storageItem.getCanPlace().length == 0
               && storageItem.getCanBreak().length == 0
               && storageItem.getBlockingTicks() == 0
               && storageItem.getBlockDefinition() == null
               && !storageItem.isUsingNetId()
               && storageItem.getNetId() == 0;
    }
}
