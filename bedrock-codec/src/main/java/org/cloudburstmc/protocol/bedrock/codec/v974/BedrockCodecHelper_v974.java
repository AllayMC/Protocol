package org.cloudburstmc.protocol.bedrock.codec.v974;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v944.BedrockCodecHelper_v944;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.cloudburstmc.protocol.bedrock.util.stream.LittleEndianByteBufInputStream;
import org.cloudburstmc.protocol.bedrock.util.stream.LittleEndianByteBufOutputStream;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.cloudburstmc.protocol.bedrock.util.Preconditions.checkArgument;

public class BedrockCodecHelper_v974 extends BedrockCodecHelper_v944 {

    public BedrockCodecHelper_v974(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes,
                                   TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                   TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities,
                                   TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public void writeNetItemDescriptor(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        ItemDefinition definition = item.getDefinition();
        buffer.writeShortLE(definition.runtimeId());
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());

        buffer.writeBoolean(item.isUsingNetId());
        if (item.isUsingNetId()) {
            VarInts.writeUnsignedInt(buffer, 0);
            VarInts.writeInt(buffer, item.getNetId());
        }

        VarInts.writeUnsignedInt(buffer, item.getBlockDefinition() == null ? 0 : item.getBlockDefinition().runtimeId());

        ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
             NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
            if (item.getTag() != null) {
                stream.writeShort(-1);
                stream.writeByte(1);
                nbtStream.writeTag(item.getTag());
            } else {
                userDataBuf.writeShortLE(0);
            }

            String[] canPlace = item.getCanPlace();
            stream.writeInt(canPlace.length);
            for (String place : canPlace) {
                stream.writeUTF(place);
            }

            String[] canBreak = item.getCanBreak();
            stream.writeInt(canBreak.length);
            for (String value : canBreak) {
                stream.writeUTF(value);
            }

            if (BLOCKING_ID.equals(definition.identifier())) {
                stream.writeLong(item.getBlockingTicks());
            }

            VarInts.writeUnsignedInt(buffer, userDataBuf.readableBytes());
            buffer.writeBytes(userDataBuf);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write item user data", e);
        } finally {
            userDataBuf.release();
        }
    }

    @Override
    public ItemData readNetItemDescriptor(ByteBuf buffer) {
        int runtimeId = buffer.readUnsignedShortLE();
        ItemDefinition definition = runtimeId == 0 ? ItemDefinition.AIR : this.getItemDefinitions().getDefinition(runtimeId);
        int count = buffer.readUnsignedShortLE();
        int damage = VarInts.readUnsignedInt(buffer);

        boolean hasNetId = buffer.readBoolean();
        int netId = 0;
        if (hasNetId) {
            VarInts.readUnsignedInt(buffer);
            netId = VarInts.readInt(buffer);
        }

        int blockRuntimeId = VarInts.readUnsignedInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace = new String[0];
        String[] canBreak = new String[0];

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));
        if (!buf.isReadable()) {
            return ItemData.builder()
                    .definition(definition)
                    .damage(damage)
                    .count(count)
                    .tag(compoundTag)
                    .canPlace(canPlace)
                    .canBreak(canBreak)
                    .blockingTicks(blockingTicks)
                    .blockDefinition(this.getBlockDefinitions().getDefinition(blockRuntimeId))
                    .usingNetId(hasNetId)
                    .netId(netId)
                    .build();
        }

        try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
             NBTInputStream nbtStream = new NBTInputStream(stream, this.getEncodingSettings().maxItemNBTSize())) {
            int nbtSize = stream.readShort();

            if (nbtSize > 0) {
                compoundTag = (NbtMap) nbtStream.readTag();
            } else if (nbtSize == -1) {
                int tagCount = stream.readUnsignedByte();
                if (tagCount != 1) {
                    throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                }
                compoundTag = (NbtMap) nbtStream.readTag();
            }

            int maxLength = this.getEncodingSettings().maxListSize();
            int length = stream.readInt();
            checkArgument(maxLength <= 0 || length <= maxLength,
                    "Tried to read %s can place entries, but maximum is %s", length, maxLength);
            canPlace = new String[length];
            for (int i = 0; i < canPlace.length; i++) {
                canPlace[i] = stream.readUTFMaxLen(this.getEncodingSettings().maxItemStackTagLength());
            }

            length = stream.readInt();
            checkArgument(maxLength <= 0 || length <= maxLength,
                    "Tried to read %s can break entries, but maximum is %s", length, maxLength);
            canBreak = new String[length];
            for (int i = 0; i < canBreak.length; i++) {
                canBreak[i] = stream.readUTFMaxLen(this.getEncodingSettings().maxItemStackTagLength());
            }

            if (definition != null && BLOCKING_ID.equals(definition.identifier())) {
                blockingTicks = stream.readLong();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read item user data", e);
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());
            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(damage)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(this.getBlockDefinitions().getDefinition(blockRuntimeId))
                .usingNetId(hasNetId)
                .netId(netId)
                .build();
    }
}
