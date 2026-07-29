package org.cloudburstmc.protocol.bedrock.codec.v2168;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NBTOutputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v975.BedrockCodecHelper_v975;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.GatheringsConfigurationJoinInfo;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventorySource;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureAnimationMode;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureMirror;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureRotation;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.transformer.EntityDataTransformer;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.cloudburstmc.protocol.bedrock.util.stream.LittleEndianByteBufInputStream;
import org.cloudburstmc.protocol.bedrock.util.stream.LittleEndianByteBufOutputStream;

import java.io.IOException;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.cloudburstmc.protocol.bedrock.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.bedrock.util.Preconditions.checkNotNull;

public class BedrockCodecHelper_v2168 extends BedrockCodecHelper_v975 {

    public BedrockCodecHelper_v2168(EntityDataTypeMap entityData, TypeMap<Class<?>> gameRulesTypes, TypeMap<ItemStackRequestActionType> stackRequestActionTypes,
                                    TypeMap<ContainerSlotType> containerSlotTypes, TypeMap<Ability> abilities, TypeMap<TextProcessingEventOrigin> textProcessingEventOrigins) {
        super(entityData, gameRulesTypes, stackRequestActionTypes, containerSlotTypes, abilities, textProcessingEventOrigins);
    }

    @Override
    public void readEntityData(ByteBuf buffer, EntityDataMap entityDataMap) {
        checkNotNull(entityDataMap, "entityDataMap");

        int length = VarInts.readUnsignedInt(buffer);
        checkArgument(this.encodingSettings.maxListSize() <= 0 || length <= this.encodingSettings.maxListSize(), "Entity data size is too big: %s", length);

        for (int i = 0; i < length; i++) {
            int id = VarInts.readUnsignedInt(buffer);
            int oneOf = VarInts.readUnsignedInt(buffer);
            int type = buffer.readUnsignedByte();
            if (oneOf != type) {
                throw new IllegalArgumentException(oneOf + "!=" + type);
            }

            EntityDataFormat format = EntityDataFormat.values()[type];

            Object value;
            switch (format) {
                case BYTE:
                    value = buffer.readByte();
                    break;
                case SHORT:
                    value = buffer.readShortLE();
                    break;
                case INT:
                    value = VarInts.readInt(buffer);
                    break;
                case FLOAT:
                    value = buffer.readFloatLE();
                    break;
                case STRING:
                    value = readString(buffer);
                    break;
                case NBT:
                    value = this.readTag(buffer, Object.class);
                    break;
                case VECTOR3I:
                    value = readVector3i(buffer);
                    break;
                case LONG:
                    value = VarInts.readLong(buffer);
                    break;
                case VECTOR3F:
                    value = readVector3f(buffer);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity data type received");
            }

            EntityDataTypeMap.Definition<?>[] definitions = this.entityData.fromId(id, format);
            if (definitions != null) {
                for (EntityDataTypeMap.Definition<?> definition : definitions) {
                    //noinspection unchecked
                    EntityDataTransformer<Object, ?> transformer = (EntityDataTransformer<Object, ?>) definition.getTransformer();
                    Object transformedValue = transformer.deserialize(this, entityDataMap, value);
                    if (transformedValue != null) {
                        entityDataMap.put(definition.getType(), transformedValue);
                    }
                }
            } else {
                log.debug("Unknown entity data: {} type {} value {}", id, format, value);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeEntityData(ByteBuf buffer, EntityDataMap entityDataMap) {
        checkNotNull(entityDataMap, "entityDataMap");

        // Collect serialized entries first
        List<Map.Entry<EntityDataTypeMap.Definition<?>, Object>> serializedEntries = new LinkedList<>();

        for (Map.Entry<EntityDataType<?>, Object> entry : entityDataMap.entrySet()) {
            EntityDataTypeMap.Definition<?> definition = this.entityData.fromType(entry.getKey());

            try {
                Object value = ((EntityDataTransformer<?, Object>) definition.getTransformer())
                        .serialize(this, entityDataMap, entry.getValue());

                // Skip if transformer returns null (indicating this entry shouldn't be serialized)
                if (value == null) {
                    continue;
                }

                serializedEntries.add(new AbstractMap.SimpleEntry<>(definition, value));
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to encode EntityData " + definition.getId() + " of " + definition.getType().getTypeName(), e);
            }
        }

        VarInts.writeUnsignedInt(buffer, serializedEntries.size());

        for (Map.Entry<EntityDataTypeMap.Definition<?>, Object> entry : serializedEntries) {
            EntityDataTypeMap.Definition<?> definition = entry.getKey();
            Object value = entry.getValue();

            VarInts.writeUnsignedInt(buffer, definition.getId());
            VarInts.writeUnsignedInt(buffer, definition.getFormat().ordinal());
            buffer.writeByte(definition.getFormat().ordinal());

            switch (definition.getFormat()) {
                case BYTE:
                    buffer.writeByte((byte) value);
                    break;
                case SHORT:
                    buffer.writeShortLE((short) value);
                    break;
                case INT:
                    VarInts.writeInt(buffer, (int) value);
                    break;
                case FLOAT:
                    buffer.writeFloatLE((float) value);
                    break;
                case STRING:
                    writeString(buffer, (String) value);
                    break;
                case NBT:
                    this.writeTag(buffer, value);
                    break;
                case VECTOR3I:
                    writeVector3i(buffer, (Vector3i) value);
                    break;
                case LONG:
                    VarInts.writeLong(buffer, (long) value);
                    break;
                case VECTOR3F:
                    writeVector3f(buffer, (Vector3f) value);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown entity data type " + definition.getFormat());
            }
        }
    }

    @Override
    public ItemData readItemInstance(ByteBuf buffer) { // NetworkItemInstanceDescriptorData
        int runtimeId = VarInts.readInt(buffer);

        ItemDefinition definition = runtimeId == 0 ? ItemDefinition.AIR : this.itemDefinitions.getDefinition(runtimeId);
        if (definition == null && log.isDebugEnabled()) {
            log.debug("No ItemDefinition for runtimeId {}, did proxy not set itemDefinitions?", runtimeId);
        }

        int count = buffer.readUnsignedShortLE();
        int aux = VarInts.readUnsignedInt(buffer);

        int blockRuntimeId = VarInts.readInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace = new String[0];
        String[] canBreak = new String[0];

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));

        if (buf.isReadable()) {
            try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
                 NBTInputStream nbtStream = new NBTInputStream(stream, this.encodingSettings.maxItemNBTSize())) {
                int nbtSize = stream.readShort();

                if (nbtSize > 0) {
                    compoundTag = (NbtMap) nbtStream.readTag();
                } else if (nbtSize == -1) {
                    int tagCount = stream.readUnsignedByte();
                    if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                    compoundTag = (NbtMap) nbtStream.readTag();
                }

                int maxLength = this.encodingSettings.maxListSize();
                int length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can place entries, but maximum is %s", length, maxLength);
                canPlace = new String[length];
                for (int i = 0; i < canPlace.length; i++) {
                    canPlace[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can break entries, but maximum is %s", length, maxLength);
                canBreak = new String[length];
                for (int i = 0; i < canBreak.length; i++) {
                    canBreak[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                if (definition != null && BLOCKING_ID.equals(definition.identifier())) {
                    blockingTicks = stream.readLong();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read item user data", e);
            }
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());

            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(aux)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(runtimeId == 0 ? ItemData.AIR.getBlockDefinition() : this.blockDefinitions.getDefinition(blockRuntimeId))
                .build();
    }

    @Override
    public ItemData readNetItemDescriptor(ByteBuf buffer) { // cerealizer_NetworkItemStackDescriptor___SerializedData
        int runtimeId = buffer.readShortLE();

        ItemDefinition definition = runtimeId == 0 ? ItemDefinition.AIR : this.itemDefinitions.getDefinition(runtimeId);
        if (definition == null && log.isDebugEnabled()) {
            log.debug("No ItemDefinition for runtimeId {}, did proxy not set itemDefinitions?", runtimeId);
        }

        int count = buffer.readUnsignedShortLE();
        int aux = VarInts.readUnsignedInt(buffer);

        int netId = 0;
        boolean hasNetId = buffer.readBoolean();

        if (hasNetId) {
            netId = VarInts.readInt(buffer);
        }

        int blockRuntimeId = VarInts.readUnsignedInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace = new String[0];
        String[] canBreak = new String[0];

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));

        if (buf.isReadable()) {
            try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
                 NBTInputStream nbtStream = new NBTInputStream(stream, this.encodingSettings.maxItemNBTSize())) {
                int nbtSize = stream.readShort();

                if (nbtSize > 0) {
                    compoundTag = (NbtMap) nbtStream.readTag();
                } else if (nbtSize == -1) {
                    int tagCount = stream.readUnsignedByte();
                    if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                    compoundTag = (NbtMap) nbtStream.readTag();
                }

                int maxLength = this.encodingSettings.maxListSize();
                int length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can place entries, but maximum is %s", length, maxLength);
                canPlace = new String[length];
                for (int i = 0; i < canPlace.length; i++) {
                    canPlace[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can break entries, but maximum is %s", length, maxLength);
                canBreak = new String[length];
                for (int i = 0; i < canBreak.length; i++) {
                    canBreak[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                if (definition != null && BLOCKING_ID.equals(definition.identifier())) {
                    blockingTicks = stream.readLong();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read item user data", e);
            }
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());

            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(aux)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(runtimeId == 0 ? ItemData.AIR.getBlockDefinition() : this.blockDefinitions.getDefinition(blockRuntimeId))
                .usingNetId(hasNetId)
                .netId(netId)
                .build();
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        ItemDefinition definition = item.getDefinition();
        boolean air = isAir(definition);

        VarInts.writeInt(buffer, air ? 0 : definition.runtimeId());
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());

        VarInts.writeInt(buffer, air || item.getBlockDefinition() == null ? 0 : item.getBlockDefinition().runtimeId());

        if (air) {
            VarInts.writeUnsignedInt(buffer, 0);
        } else {
            ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
            try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
                 NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
                if (item.getTag() != null) {
                    stream.writeShort(-1);
                    stream.writeByte(1); // Hardcoded in current version
                    nbtStream.writeTag(item.getTag());
                } else {
                    userDataBuf.writeShortLE(0);
                }

                String[] canPlace = item.getCanPlace();
                stream.writeInt(canPlace.length);
                for (String aCanPlace : canPlace) {
                    stream.writeUTF(aCanPlace);
                }

                String[] canBreak = item.getCanBreak();
                stream.writeInt(canBreak.length);
                for (String aCanBreak : canBreak) {
                    stream.writeUTF(aCanBreak);
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
    }

    @Override
    public void writeNetItemDescriptor(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        ItemDefinition definition = item.getDefinition();
        boolean air = isAir(definition);

        buffer.writeShortLE(air ? 0 : definition.runtimeId());
        buffer.writeShortLE(item.getCount());
        VarInts.writeUnsignedInt(buffer, item.getDamage());

        buffer.writeBoolean(item.isUsingNetId());
        if (item.isUsingNetId()) {
            VarInts.writeInt(buffer, item.getNetId());
        }

        VarInts.writeUnsignedInt(buffer, air || item.getBlockDefinition() == null ? 0 : item.getBlockDefinition().runtimeId());

        if (air) {
            VarInts.writeUnsignedInt(buffer, 0);
        } else {
            ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
            try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
                 NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
                if (item.getTag() != null) {
                    stream.writeShort(-1);
                    stream.writeByte(1); // Hardcoded in current version
                    nbtStream.writeTag(item.getTag());
                } else {
                    userDataBuf.writeShortLE(0);
                }

                String[] canPlace = item.getCanPlace();
                stream.writeInt(canPlace.length);
                for (String aCanPlace : canPlace) {
                    stream.writeUTF(aCanPlace);
                }

                String[] canBreak = item.getCanBreak();
                stream.writeInt(canBreak.length);
                for (String aCanBreak : canBreak) {
                    stream.writeUTF(aCanBreak);
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
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, ItemStackRequestAction action) {
        byteBuf.writeByte(action.getType().ordinal());

        switch (action.getType()) {
            case TAKE:
            case PLACE:
                byteBuf.writeByte(((TransferItemStackRequestAction) action).count());
                writeStackRequestSlotInfo(byteBuf, ((TransferItemStackRequestAction) action).source());
                writeStackRequestSlotInfo(byteBuf, ((TransferItemStackRequestAction) action).destination());
                break;
            case SWAP:
                writeStackRequestSlotInfo(byteBuf, ((SwapAction) action).source());
                writeStackRequestSlotInfo(byteBuf, ((SwapAction) action).destination());
                break;
            case DROP:
                byteBuf.writeByte(((DropAction) action).count());
                writeStackRequestSlotInfo(byteBuf, ((DropAction) action).source());
                byteBuf.writeBoolean(((DropAction) action).randomly());
                break;
            case DESTROY:
                byteBuf.writeByte(((DestroyAction) action).count());
                writeStackRequestSlotInfo(byteBuf, ((DestroyAction) action).source());
                break;
            case CONSUME:
                byteBuf.writeByte(((ConsumeAction) action).count());
                writeStackRequestSlotInfo(byteBuf, ((ConsumeAction) action).source());
                break;
            case CREATE:
                byteBuf.writeByte(((CreateAction) action).slot());
                break;
            case LAB_TABLE_COMBINE:
                break;
            case BEACON_PAYMENT:
                VarInts.writeInt(byteBuf, ((BeaconPaymentAction) action).primaryEffect());
                VarInts.writeInt(byteBuf, ((BeaconPaymentAction) action).secondaryEffect());
                break;
            case MINE_BLOCK:
                VarInts.writeInt(byteBuf, ((MineBlockAction) action).hotbarSlot());
                VarInts.writeInt(byteBuf, ((MineBlockAction) action).predictedDurability());
                byteBuf.writeIntLE(((MineBlockAction) action).stackNetworkId()); // int
                break;
            case CRAFT_RECIPE:
                VarInts.writeUnsignedInt(byteBuf, ((RecipeItemStackRequestAction) action).recipeNetworkId());
                byteBuf.writeByte(((RecipeItemStackRequestAction) action).numberOfRequestedCrafts());
                break;
            case CRAFT_RECIPE_AUTO:
                VarInts.writeUnsignedInt(byteBuf, ((AutoCraftRecipeAction) action).recipeNetworkId());
                byteBuf.writeByte(((AutoCraftRecipeAction) action).numberOfRequestedCrafts()); // count duplication removed
                List<ItemDescriptorWithCount> ingredients = ((AutoCraftRecipeAction) action).ingredients();
                byteBuf.writeByte(ingredients.size());
                writeArray(byteBuf, ingredients, this::writeIngredient2);
                break;
            case CRAFT_CREATIVE:
                VarInts.writeUnsignedInt(byteBuf, ((CraftCreativeAction) action).creativeItemNetworkId());
                byteBuf.writeByte(((CraftCreativeAction) action).numberOfRequestedCrafts());
                break;
            case CRAFT_RECIPE_OPTIONAL:
                VarInts.writeUnsignedInt(byteBuf, ((CraftRecipeOptionalAction) action).recipeNetworkId());
                byteBuf.writeIntLE(((CraftRecipeOptionalAction) action).filteredStringIndex());
                break;
            case CRAFT_REPAIR_AND_DISENCHANT:
                byteBuf.writeIntLE(((CraftGrindstoneAction) action).recipeNetworkId()); // int
                byteBuf.writeByte(((CraftGrindstoneAction) action).numberOfRequestedCrafts());
                VarInts.writeInt(byteBuf, ((CraftGrindstoneAction) action).repairCost());
                break;
            case CRAFT_LOOM:
                this.writeString(byteBuf, ((CraftLoomAction) action).patternId());
                byteBuf.writeByte(((CraftLoomAction) action).timesCrafted());
                break;
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                break;
            case CRAFT_RESULTS_DEPRECATED:
                this.writeArray(byteBuf, ((CraftResultsDeprecatedAction) action).resultItems(), this::writeItemStackRequestNetworkItemInstanceDescriptor);
                byteBuf.writeByte(((CraftResultsDeprecatedAction) action).timesCrafted());
                break;
            default:
                throw new IllegalArgumentException("got " + action.getType());
        }
    }

    @Override
    protected ItemStackRequestAction readRequestActionData(ByteBuf byteBuf, ItemStackRequestActionType type) {
        int type2 = byteBuf.readByte();

        switch (type) {
            case TAKE:
                return new TakeAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case PLACE:
                return new PlaceAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case SWAP:
                return new SwapAction(
                        readStackRequestSlotInfo(byteBuf),
                        readStackRequestSlotInfo(byteBuf)
                );
            case DROP:
                return new DropAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf),
                        byteBuf.readBoolean()
                );
            case DESTROY:
                return new DestroyAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CONSUME:
                return new ConsumeAction(
                        byteBuf.readUnsignedByte(),
                        readStackRequestSlotInfo(byteBuf)
                );
            case CREATE:
                return new CreateAction(
                        byteBuf.readUnsignedByte()
                );
            case LAB_TABLE_COMBINE:
                return new LabTableCombineAction();
            case BEACON_PAYMENT:
                return new BeaconPaymentAction(
                        VarInts.readInt(byteBuf),
                        VarInts.readInt(byteBuf)
                );
            case MINE_BLOCK:
                return new MineBlockAction(
                        VarInts.readInt(byteBuf), VarInts.readInt(byteBuf), byteBuf.readIntLE() // int
                );
            case CRAFT_RECIPE:
                return new CraftRecipeAction(
                        VarInts.readUnsignedInt(byteBuf), byteBuf.readByte()
                );
            case CRAFT_RECIPE_AUTO:
                int recipeNetworkId = VarInts.readUnsignedInt(byteBuf);
                int numberOfRequestedCrafts = byteBuf.readUnsignedByte(); // count duplication removed
                List<ItemDescriptorWithCount> ingredients = new ObjectArrayList<>();
                this.readArray(byteBuf, ingredients, this::readIngredient2);
                return new AutoCraftRecipeAction(recipeNetworkId, numberOfRequestedCrafts, ingredients, numberOfRequestedCrafts);
            case CRAFT_CREATIVE:
                return new CraftCreativeAction(
                        VarInts.readUnsignedInt(byteBuf), byteBuf.readByte()
                );
            case CRAFT_RECIPE_OPTIONAL:
                return new CraftRecipeOptionalAction(
                        VarInts.readUnsignedInt(byteBuf), byteBuf.readIntLE()
                );
            case CRAFT_REPAIR_AND_DISENCHANT:
                return new CraftGrindstoneAction(
                        byteBuf.readIntLE(), byteBuf.readByte(), VarInts.readInt(byteBuf) // int
                );
            case CRAFT_LOOM:
                return new CraftLoomAction(
                        this.readString(byteBuf), byteBuf.readUnsignedByte()
                );
            case CRAFT_NON_IMPLEMENTED_DEPRECATED:
                return new CraftNonImplementedAction();
            case CRAFT_RESULTS_DEPRECATED:
                return new CraftResultsDeprecatedAction(
                        this.readArray(byteBuf, new ItemData[0], this::readItemStackRequestNetworkItemInstanceDescriptor),
                        byteBuf.readUnsignedByte()
                );
            default:
                throw new IllegalArgumentException("got " + type);
        }
    }

    @Override
    protected ItemStackRequestSlotData readStackRequestSlotInfo(ByteBuf buffer) {
        FullContainerName containerName = this.readFullContainerName(buffer);
        return new ItemStackRequestSlotData(
                containerName.container(),
                buffer.readUnsignedByte(),
                buffer.readIntLE(),
                containerName
        );
    }

    @Override
    protected void writeStackRequestSlotInfo(ByteBuf buffer, ItemStackRequestSlotData data) {
        this.writeFullContainerName(buffer, data.containerName());
        buffer.writeByte(data.slot());
        buffer.writeIntLE(data.stackNetworkId());
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        writeNetItemDescriptor(buffer, item);
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        return readNetItemDescriptor(buffer);
    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        String skinId = this.readString(buffer);
        String playFabId = this.readString(buffer);
        String skinResourcePatch = this.readString(buffer);
        ImageData skinData = this.readImage(buffer, ImageData.SKIN_PERSONA_SIZE);

        List<AnimationData> animations = new ObjectArrayList<>();
        this.readArray(buffer, animations, (b, h) -> this.readAnimationData(b));

        ImageData capeData = this.readImage(buffer, ImageData.SINGLE_SKIN_SIZE);
        String geometryData = this.readStringMaxLen(buffer, this.encodingSettings.maxGeometryDataSize());
        String geometryDataEngineVersion = this.readString(buffer);
        String animationData = this.readString(buffer);
        String capeId = this.readString(buffer);
        String fullSkinId = this.readString(buffer);

        String armSize = buffer.readUnsignedByte() == 1 ? "wide" : "slim";
        String skinColor = String.format("#%08x", buffer.readIntLE());

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        this.readArray(buffer, personaPieces, (buf, h) -> {
            String pieceId = this.readString(buf);
            String pieceType = PersonaPieceType.values()[buf.readIntLE()].getSerializeName();
            String packId = this.readUuid(buf).toString();
            boolean isDefault = buf.readBoolean();
            String productId = this.readString(buf);
            return new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId);
        });

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        this.readArray(buffer, tintColors, (buf, h) -> {
            String pieceType = PersonaPieceType.fromName(this.readString(buf)).getSerializeName();
            List<String> colors = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                colors.add(String.format("#%08x", buf.readIntLE()));
            }
            return new PersonaPieceTintData(pieceType, colors);
        });

        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        boolean primaryUser = buffer.readBoolean();
        boolean overridingPlayerAppearance = buffer.readBoolean();

        boolean trusted = "true".equalsIgnoreCase(this.readString(buffer));
        String profileHash = this.readString(buffer);

        return SerializedSkin.of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, geometryDataEngineVersion,
                animationData, premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors,
                overridingPlayerAppearance, trusted, profileHash);
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        requireNonNull(skin, "Skin is null");

        this.writeString(buffer, skin.getSkinId());
        this.writeString(buffer, skin.getPlayFabId());
        this.writeString(buffer, skin.getSkinResourcePatch());
        this.writeImage(buffer, skin.getSkinData());

        List<AnimationData> animations = skin.getAnimations();
        VarInts.writeUnsignedInt(buffer, animations.size());
        for (AnimationData animation : animations) {
            this.writeAnimationData(buffer, animation);
        }

        this.writeImage(buffer, skin.getCapeData());
        this.writeString(buffer, skin.getGeometryData());
        this.writeString(buffer, skin.getGeometryDataEngineVersion());
        this.writeString(buffer, skin.getAnimationData());
        this.writeString(buffer, skin.getCapeId());
        this.writeString(buffer, skin.getFullSkinId());

        buffer.writeByte("slim".equalsIgnoreCase(skin.getArmSize()) ? 0 : 1);
        buffer.writeIntLE(parseSkinColor(skin.getSkinColor()));

        List<PersonaPieceData> pieces = skin.getPersonaPieces();
        VarInts.writeUnsignedInt(buffer, pieces.size());
        for (PersonaPieceData piece : pieces) {
            this.writeString(buffer, piece.id());
            buffer.writeIntLE(PersonaPieceType.fromName(piece.type()).ordinal());
            this.writeUuid(buffer, UUID.fromString(piece.packId()));
            buffer.writeBoolean(piece.isDefault());
            this.writeString(buffer, piece.productId());
        }

        List<PersonaPieceTintData> tints = skin.getTintColors();
        VarInts.writeUnsignedInt(buffer, tints.size());
        for (PersonaPieceTintData tint : tints) {
            this.writeString(buffer, tint.type());
            List<String> colors = tint.colors();
            if (colors.size() != 4) {
                throw new IllegalArgumentException("Expected 4 colors in PersonaPieceTintData");
            }
            for (String color : colors) {
                buffer.writeIntLE((int) Long.parseLong(color.startsWith("#") ? color.substring(1) : color, 16));
            }
        }

        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isCapeOnClassic());
        buffer.writeBoolean(skin.isPrimaryUser());

        buffer.writeBoolean(skin.isOverridingPlayerAppearance());

        this.writeString(buffer, Boolean.toString(skin.isTrusted()));
        this.writeString(buffer, skin.getProfileHash());
    }

    @Override
    public AnimationData readAnimationData(ByteBuf buffer) {
        ImageData image = this.readImage(buffer, ImageData.ANIMATION_SIZE);
        AnimatedTextureType textureType = TEXTURE_TYPES[VarInts.readUnsignedInt(buffer)];
        float frames = buffer.readFloatLE();
        AnimationExpressionType expressionType = EXPRESSION_TYPES[VarInts.readUnsignedInt(buffer)];
        return new AnimationData(image, textureType, frames, expressionType);
    }

    @Override
    public void writeAnimationData(ByteBuf buffer, AnimationData animation) {
        this.writeImage(buffer, animation.image());
        VarInts.writeUnsignedInt(buffer, animation.textureType().ordinal());
        buffer.writeFloatLE(animation.frames());
        VarInts.writeUnsignedInt(buffer, animation.expressionType().ordinal());
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        int requestId = VarInts.readInt(buffer);
        List<ItemStackRequestAction> actions = new ObjectArrayList<>();

        this.readArray(buffer, actions, byteBuf -> {
            ItemStackRequestActionType type = this.stackRequestActionTypes.getType(VarInts.readUnsignedInt(byteBuf));
            return readRequestActionData(byteBuf, type);
        }, this.getEncodingSettings().maxInventoryActionsOrRequests());

        List<String> filteredStrings = new ObjectArrayList<>();
        this.readArray(buffer, filteredStrings, this::readString);

        int originVal = buffer.readIntLE();
        TextProcessingEventOrigin origin = originVal == -1 ? null : this.textProcessingEventOrigins.getType(originVal);
        return new ItemStackRequest(requestId, actions.toArray(new ItemStackRequestAction[0]), filteredStrings.toArray(new String[0]), origin);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        VarInts.writeInt(buffer, request.requestId());

        this.writeArray(buffer, request.actions(), (byteBuf, action) -> {
            VarInts.writeUnsignedInt(byteBuf, this.stackRequestActionTypes.getId(action.getType()));
            writeRequestActionData(byteBuf, action);
        });

        this.writeArray(buffer, request.filterStrings(), this::writeString);

        TextProcessingEventOrigin origin = request.textProcessingEventOrigin();
        buffer.writeIntLE(origin == null ? -1 : this.textProcessingEventOrigins.getId(origin));
    }

    @Override
    public ItemDescriptorWithCount readIngredient(ByteBuf buffer) {
        ItemDescriptorType type = DESCRIPTOR_TYPES[VarInts.readUnsignedInt(buffer)];
        ItemDescriptor descriptor = this.readItemDescriptor(buffer, type);
        int count = VarInts.readInt(buffer);
        return new ItemDescriptorWithCount(descriptor, count);
    }

    protected ItemDescriptorWithCount readIngredient2(ByteBuf buffer) {
        ItemDescriptorType type = DESCRIPTOR_TYPES[VarInts.readUnsignedInt(buffer)];
        buffer.readUnsignedByte();

        ItemDescriptor descriptor;
        switch (type) {
            case INVALID:
                descriptor = InvalidDescriptor.INSTANCE;
                break;
            case DEFAULT:
                String id = this.readString(buffer);
                int aux = VarInts.readInt(buffer);
                ItemDefinition definition = this.itemDefinitions.getDefinition(id);
                if (definition == null && log.isDebugEnabled()) {
                    log.debug("No ItemDefinition for id {}, did proxy not set itemDefinitions?", id);
                }
                descriptor = new DefaultDescriptor(definition, aux);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(this.readString(buffer), buffer.readShortLE());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(this.readString(buffer));
                break;
            default:
                throw new UnsupportedOperationException("ItemDescriptorType");
        }

        return new ItemDescriptorWithCount(descriptor, buffer.readUnsignedShortLE());
    }

    @Override
    public void writeIngredient(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        VarInts.writeUnsignedInt(buffer, Math.min(ingredient.descriptor().getType().ordinal(), 1));
        this.writeItemDescriptor(buffer, ingredient.descriptor());
        VarInts.writeInt(buffer, ingredient.count());
    }

    protected void writeIngredient2(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        ItemDescriptor descriptor = ingredient.descriptor();
        VarInts.writeUnsignedInt(buffer, descriptor.getType().ordinal());
        buffer.writeByte(descriptor.getType().ordinal());

        switch (descriptor.getType()) {
            case INVALID:
                break;
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) descriptor;
                this.writeString(buffer, defaultDescriptor.itemId().identifier());
                VarInts.writeInt(buffer, defaultDescriptor.auxValue());
                break;
            case MOLANG:
                MolangDescriptor molangDescriptor = (MolangDescriptor) descriptor;
                this.writeString(buffer, molangDescriptor.tagExpression());
                buffer.writeShortLE(molangDescriptor.molangVersion());
                break;
            case ITEM_TAG:
                ItemTagDescriptor tagDescriptor = (ItemTagDescriptor) descriptor;
                this.writeString(buffer, tagDescriptor.itemTag());
                break;
            default:
                throw new UnsupportedOperationException("ItemDescriptorType");
        }

        buffer.writeShortLE(ingredient.count());
    }

    @Override
    protected ItemDescriptor readItemDescriptor(ByteBuf buffer, ItemDescriptorType type) {
        ItemDescriptor descriptor;
        if (type != ItemDescriptorType.INVALID) {
            String desc = this.readString(buffer);
            type = ItemDescriptorType.fromName(desc);
        }

        switch (type) {
            case INVALID:
                int aux_ = VarInts.readInt(buffer);
                descriptor = InvalidDescriptor.INSTANCE;
                break;
            case DEFAULT:
                String id = this.readString(buffer);
                int aux = VarInts.readInt(buffer);
                ItemDefinition definition = this.itemDefinitions.getDefinition(id);
                if (definition == null && log.isDebugEnabled()) {
                    log.debug("No ItemDefinition for id {}, did proxy not set itemDefinitions?", id);
                }
                descriptor = new DefaultDescriptor(definition, aux);
                break;
            case MOLANG:
                descriptor = new MolangDescriptor(this.readString(buffer), buffer.readShortLE());
                break;
            case ITEM_TAG:
                descriptor = new ItemTagDescriptor(this.readString(buffer));
                int aux__ = VarInts.readInt(buffer);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return descriptor;
    }

    @Override
    protected void writeItemDescriptor(ByteBuf buffer, ItemDescriptor descriptor) {
        if (descriptor.getType() != ItemDescriptorType.INVALID) {
            this.writeString(buffer, descriptor.getType().getSerializeName());
        }

        switch (descriptor.getType()) {
            case INVALID:
                VarInts.writeInt(buffer, 32767);
                break;
            case DEFAULT:
                DefaultDescriptor defaultDescriptor = (DefaultDescriptor) descriptor;
                this.writeString(buffer, defaultDescriptor.itemId().identifier());
                VarInts.writeInt(buffer, defaultDescriptor.auxValue());
                break;
            case MOLANG:
                MolangDescriptor molangDescriptor = (MolangDescriptor) descriptor;
                this.writeString(buffer, molangDescriptor.tagExpression());
                buffer.writeShortLE(molangDescriptor.molangVersion());
                break;
            case ITEM_TAG:
                ItemTagDescriptor tagDescriptor = (ItemTagDescriptor) descriptor;
                this.writeString(buffer, tagDescriptor.itemTag());
                VarInts.writeInt(buffer, 32767);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        String paletteName = this.readString(buffer);
        boolean ignoringEntities = buffer.readBoolean();
        boolean ignoringBlocks = buffer.readBoolean();
        boolean nonTickingPlayersAndTickingAreasEnabled = buffer.readBoolean();
        Vector3i size = this.readBlockPosition(buffer);
        Vector3i offset = this.readBlockPosition(buffer);
        long lastEditedByEntityId = VarInts.readLong(buffer);
        StructureRotation rotation = StructureRotation.from(buffer.readUnsignedByte());
        StructureMirror mirror = StructureMirror.from(buffer.readUnsignedByte());
        StructureAnimationMode animationMode = StructureAnimationMode.from(buffer.readUnsignedByte());
        float animationSeconds = buffer.readFloatLE();
        float integrityValue = buffer.readFloatLE();
        int integritySeed = buffer.readIntLE();
        Vector3f pivot = this.readVector3f(buffer);

        return new StructureSettings(ignoringEntities, ignoringBlocks, size, offset, rotation, mirror,
                integrityValue, integritySeed, paletteName, lastEditedByEntityId, pivot, animationMode,
                animationSeconds, nonTickingPlayersAndTickingAreasEnabled);
    }

    @Override
    protected ItemStackResponseSlot readItemEntry(ByteBuf buffer) {
        int slot = buffer.readUnsignedByte();
        int hotbarSlot = buffer.readUnsignedByte();
        int count = buffer.readUnsignedByte();
        int stackNetworkId = buffer.readBoolean() && buffer.readBoolean() ? VarInts.readInt(buffer) : 0;
        String customName = this.readString(buffer);
        String filteredCustomName = this.readString(buffer);
        int durabilityCorrection = VarInts.readInt(buffer);
        return new ItemStackResponseSlot(slot, hotbarSlot, count, stackNetworkId,
                customName, durabilityCorrection, filteredCustomName);

    }

    @Override
    protected void writeItemEntry(ByteBuf buffer, ItemStackResponseSlot itemEntry) {
        buffer.writeByte(itemEntry.getSlot());
        buffer.writeByte(itemEntry.getHotbarSlot());
        buffer.writeByte(itemEntry.getCount());
        buffer.writeBoolean(true);
        this.writeOptional(buffer, id -> id > 0, itemEntry.getStackNetworkId(), VarInts::writeInt);
        this.writeString(buffer, itemEntry.getCustomName());
        this.writeString(buffer, itemEntry.getFilteredCustomName());
        VarInts.writeInt(buffer, itemEntry.getDurabilityCorrection());
    }

    private ItemData readItemStackRequestNetworkItemInstanceDescriptor(ByteBuf buffer) {
        ItemDescriptorType type = DESCRIPTOR_TYPES[VarInts.readUnsignedInt(buffer)];

        int typeStr = buffer.readUnsignedByte();

        ItemDescriptor descriptor = InvalidDescriptor.INSTANCE;
        if (type != ItemDescriptorType.INVALID) {
            String id = this.readString(buffer);

            int aux = VarInts.readInt(buffer);
            ItemDefinition definition = this.itemDefinitions.getDefinition(id);
            if (definition == null &&log.isDebugEnabled()) {
                log.debug("No ItemDefinition for id {}, did proxy not set itemDefinitions?", id);
            }
            descriptor = new DefaultDescriptor(definition, aux);
        }

        ItemDefinition definition = descriptor == InvalidDescriptor.INSTANCE ? ItemData.AIR.getDefinition() : ((DefaultDescriptor) descriptor).itemId();
        int aux = descriptor == InvalidDescriptor.INSTANCE ? 0 : ((DefaultDescriptor) descriptor).auxValue();

        int count = buffer.readShortLE();

        int blockRuntimeId = VarInts.readUnsignedInt(buffer);

        NbtMap compoundTag = null;
        long blockingTicks = 0;
        String[] canPlace = new String[0];
        String[] canBreak = new String[0];

        ByteBuf buf = buffer.readSlice(VarInts.readUnsignedInt(buffer));

        if (buf.isReadable()) {
            try (LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream(buf);
                 NBTInputStream nbtStream = new NBTInputStream(stream, this.encodingSettings.maxItemNBTSize())) {
                int nbtSize = stream.readShort();

                if (nbtSize > 0) {
                    compoundTag = (NbtMap) nbtStream.readTag();
                } else if (nbtSize == -1) {
                    int tagCount = stream.readUnsignedByte();
                    if (tagCount != 1) throw new IllegalArgumentException("Expected 1 tag but got " + tagCount);
                    compoundTag = (NbtMap) nbtStream.readTag();
                }

                int maxLength = this.encodingSettings.maxListSize();
                int length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can place entries, but maximum is %s", length, maxLength);
                canPlace = new String[length];
                for (int i = 0; i < canPlace.length; i++) {
                    canPlace[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                length = stream.readInt();
                checkArgument(maxLength <= 0 || length <= maxLength, "Tried to read %s can break entries, but maximum is %s", length, maxLength);
                canBreak = new String[length];
                for (int i = 0; i < canBreak.length; i++) {
                    canBreak[i] = stream.readUTFMaxLen(this.encodingSettings.maxItemStackTagLength());
                }

                if (definition != null && BLOCKING_ID.equals(definition.identifier())) {
                    blockingTicks = stream.readLong();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read item user data", e);
            }
        }

        if (buf.isReadable()) {
            log.info("Item user data has {} readable bytes left", buf.readableBytes());

            if (log.isDebugEnabled()) {
                log.debug("Item data:\n{}", ByteBufUtil.prettyHexDump(buf.readerIndex(0)));
            }
        }

        return ItemData.builder()
                .definition(definition)
                .damage(aux)
                .count(count)
                .tag(compoundTag)
                .canPlace(canPlace)
                .canBreak(canBreak)
                .blockingTicks(blockingTicks)
                .blockDefinition(definition.runtimeId() == 0 ? ItemData.AIR.getBlockDefinition() : this.blockDefinitions.getDefinition(blockRuntimeId))
                .build();
    }

    private void writeItemStackRequestNetworkItemInstanceDescriptor(ByteBuf buffer, ItemData item) {
        requireNonNull(item, "item is null!");

        ItemDefinition definition = item.getDefinition();
        boolean air = isAir(definition);

        VarInts.writeUnsignedInt(buffer, air ? 0 : 1); //descriptor type
        buffer.writeByte(air ? 0 : 1); // type again
        if (!air) {
            this.writeString(buffer, definition.identifier());
            VarInts.writeInt(buffer, item.getDamage());
        }

        buffer.writeShortLE(item.getCount());

        VarInts.writeUnsignedInt(buffer, air || item.getBlockDefinition() == null ? 0 : item.getBlockDefinition().runtimeId());

        if (air) {
            VarInts.writeUnsignedInt(buffer, 0);
        } else {
            ByteBuf userDataBuf = ByteBufAllocator.DEFAULT.ioBuffer();
            try (LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream(userDataBuf);
                 NBTOutputStream nbtStream = new NBTOutputStream(stream)) {
                if (item.getTag() != null) {
                    stream.writeShort(-1);
                    stream.writeByte(1); // Hardcoded in current version
                    nbtStream.writeTag(item.getTag());
                } else {
                    userDataBuf.writeShortLE(0);
                }

                String[] canPlace = item.getCanPlace();
                stream.writeInt(canPlace.length);
                for (String aCanPlace : canPlace) {
                    stream.writeUTF(aCanPlace);
                }

                String[] canBreak = item.getCanBreak();
                stream.writeInt(canBreak.length);
                for (String aCanBreak : canBreak) {
                    stream.writeUTF(aCanBreak);
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
    }

    @Override
    public void writeGatheringsConfiguration(ByteBuf buf, BedrockCodecHelper h, GatheringsConfigurationJoinInfo info) {
        h.writeUuid(buf, info.experienceId());
        h.writeString(buf, info.experienceName());
        h.writeOptionalNull(buf, info.worldId(), h::writeUuid);
        h.writeOptionalNull(buf, info.worldName(), h::writeString);
        h.writeString(buf, info.creatorId());
        h.writeOptionalNull(buf, info.targetId(), h::writeUuid);
        h.writeOptionalNull(buf, info.scenarioId(), h::writeString);
        h.writeOptionalNull(buf, info.serverId(), h::writeString);
    }

    @Override
    public GatheringsConfigurationJoinInfo readGatheringsConfiguration(ByteBuf buf, BedrockCodecHelper h) {
        return new GatheringsConfigurationJoinInfo(
                h.readUuid(buf),
                h.readString(buf),
                h.readOptional(buf, null, h::readUuid),
                h.readOptional(buf, null, h::readString),
                h.readString(buf),
                h.readOptional(buf, null, h::readUuid),
                h.readOptional(buf, null, h::readString),
                h.readOptional(buf, null, h::readString)
        );
    }

    @Override
    public InventorySource readSource(ByteBuf buffer) {
        InventorySource.Type type = InventorySource.Type.byId(VarInts.readUnsignedInt(buffer));

        int containerId = 0;
        InventorySource.Flag flag = null;
        if (buffer.readBoolean() && buffer.readBoolean()) {
            containerId = buffer.readByte();
        }
        if (buffer.readBoolean() && buffer.readBoolean()) {
            flag = InventorySource.Flag.values()[VarInts.readUnsignedInt(buffer)];
        }
        return switch (type) {
            case CONTAINER -> InventorySource.fromContainerWindowId(containerId);
            case GLOBAL -> InventorySource.fromGlobalInventory();
            case WORLD_INTERACTION -> {
                if (flag == null) {
                    throw new IllegalStateException();
                }
                yield InventorySource.fromWorldInteraction(flag);
            }
            case CREATIVE -> InventorySource.fromCreativeInventory();
            case NON_IMPLEMENTED_TODO -> InventorySource.fromNonImplementedTodo(containerId);
            case UNTRACKED_INTERACTION_UI -> InventorySource.fromUntrackedInteractionUI(containerId);
            default -> InventorySource.fromInvalid();
        };
    }

    @Override
    public void writeSource(ByteBuf buffer, InventorySource inventorySource) {
        requireNonNull(inventorySource, "InventorySource was null");
        VarInts.writeUnsignedInt(buffer, inventorySource.type().id());

        buffer.writeBoolean(true);
        switch (inventorySource.type()) {
            case CONTAINER:
            case NON_IMPLEMENTED_TODO:
                buffer.writeBoolean(true);
                buffer.writeByte(inventorySource.containerId());
                break;
            default:
                buffer.writeBoolean(false);
                break;
        }

        buffer.writeBoolean(true);
        if (inventorySource.type() == InventorySource.Type.WORLD_INTERACTION) {
            buffer.writeBoolean(true);
            VarInts.writeUnsignedInt(buffer, inventorySource.flag().ordinal());
        } else {
            buffer.writeBoolean(false);
        }
    }

    private static int parseSkinColor(String color) {
        if (color == null || color.isBlank()) {
            return 0;
        }
        String value = color.charAt(0) == '#' ? color.substring(1) : color;
        return (int) Long.parseLong(value, 16);
    }
}
