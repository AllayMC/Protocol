package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtList;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.List;
import java.util.UUID;

/**
 * Sent by the server to send information about the world the player will be spawned in. It contains
 * information about the position the player spawns in, and information about the world in general
 * such as its game rules.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(
        doNotUseGetters = true,
        exclude = {"itemDefinitions", "blockPalette"})
public class StartGamePacket implements BedrockPacket {
    /**
     * Game rules currently active in the world. Their values may be boolean, integer, or float
     * depending on the rule.
     */
    private final List<GameRuleData<?>> gamerules = new ObjectArrayList<>();
    /**
     * The unique ID of the player. The unique ID is a value that remains consistent across
     * different sessions of the same world, but most servers simply fill the runtime ID of the
     * entity out for this field.
     */
    private long uniqueEntityId;
    /**
     * The runtime ID of the player. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * PlayerGameMode is the game mode the player currently has. It is a value from 0-4, with 0
     * being survival mode, 1 being creative mode, 2 being adventure mode, 3 being survival
     * spectator and 4 being creative spectator. This field may be set to 5 to make the client fall
     * back to the game mode set in the WorldGameMode field.
     */
    private GameType playerGameType;
    /**
     * The spawn position of the player in the world. In servers this is often the same as the
     * world's spawn position found below.
     */
    private Vector3f playerPosition;
    /**
     * The player's pitch and yaw rotation, in degrees.
     */
    private Vector2f rotation;
    // Level settings start
    /**
     * The world seed used for terrain generation.
     */
    private long seed;
    /**
     * The dimension the player is spawning into, for example overworld, nether, or end.
     */
    private int dimensionId;
    /**
     * The generator type used for the world, such as infinite, flat, nether, or end.
     */
    private int generatorId;
    /**
     * The default game type for newly joining players, and the fallback when
     * {@link #playerGameType} requests it.
     */
    private GameType levelGameType;
    /**
     * The difficulty of the world. It is a value from 0-3, with 0 being peaceful, 1 being easy, 2
     * being normal and 3 being hard.
     */
    private int difficulty;
    /**
     * The world spawn block position. This affects things like compass direction even if the player
     * spawns elsewhere.
     */
    private Vector3i defaultSpawn;
    /**
     * AchievementsDisabled defines if achievements are disabled in the world. The client crashes if
     * this value is set to true while the player's or the world's game mode is creative, and it's
     * recommended to simply always set this to false as a server.
     */
    private boolean achievementsDisabled;
    /**
     * DayCycleLockTime is the time at which the day cycle was locked if the day cycle is disabled
     * using the respective game rule. The client will maintain this time as long as the day cycle
     * is disabled.
     */
    private int dayCycleStopTime;
    /**
     * Education Edition offer region metadata.
     */
    private int eduEditionOffers;
    /**
     * EducationFeaturesEnabled specifies if the world has education edition features enabled, such
     * as the blocks or entities specific to education edition.
     */
    private boolean eduFeaturesEnabled;
    /**
     * The level specifying the intensity of the rain falling. When set to 0, no rain falls at all.
     */
    private float rainLevel;
    /**
     * The level specifying the intensity of the thunder. This may actually be set independently
     * from the RainLevel, meaning dark clouds can be produced without rain.
     */
    private float lightningLevel;
    /**
     * Whether the world is marked as a multiplayer game.
     */
    private boolean multiplayerGame;
    /**
     * Whether LAN broadcasting is enabled for the world.
     */
    private boolean broadcastingToLan;
    /**
     * XBLBroadcastMode is the mode used to broadcast the joined game across Xbox Live. When set to
     * 0, the 'Invite' button in the pause screen is grayed out and players cannot invite their
     * friends to the Xbox Live multiplayer session they're currently in. It only applies to worlds
     * and has no effect on external servers.
     */
    private GamePublishSetting xblBroadcastMode;
    /**
     * The mode used to broadcast the joined game across the platform.
     */
    private GamePublishSetting platformBroadcastMode;
    /**
     * Specifies if commands are enabled for the player. It is recommended to always set this to
     * true on the server, as setting it to false means the player cannot, under any circumstance,
     * use a command.
     */
    private boolean commandsEnabled;
    /**
     * TexturePackRequired specifies if the texture pack the world might hold is required, meaning
     * the client was forced to download it before joining.
     */
    private boolean texturePacksRequired;
    /**
     * Holds a list of experiments that are either enabled or disabled in the world that the player
     * spawns in.
     */
    private final List<ExperimentData> experiments = new ObjectArrayList<>();
    /**
     * Specifies if the world had the bonus map setting enabled when generating it. It does not have
     * any effect client-side.
     */
    private boolean bonusChestEnabled;
    /**
     * Whether newly joining players should start with a map.
     */
    private boolean startingWithMap;
    /**
     * Whether players are trusted by default for world interactions.
     */
    private boolean trustingPlayers;
    /**
     * The default player permission.
     */
    private PlayerPermission defaultPlayerPermission;
    /**
     * ServerChunkTickRadius is the radius around the player in which chunks are ticked. Most
     * servers set this value to a fixed number, as it does not necessarily affect anything client-
     * side.
     */
    private int serverChunkTickRange;
    /**
     * Whether the world's behaviour pack is locked and cannot be disabled.
     */
    private boolean behaviorPackLocked;
    /**
     * Whether the world's resource pack is locked and cannot be disabled.
     */
    private boolean resourcePackLocked;
    /**
     * Specifies if the world from the server was from a locked world template. For servers this
     * should always be set to false.
     */
    private boolean fromLockedWorldTemplate;
    /**
     * Whether only Microsoft account gamertags should be used for the session.
     */
    private boolean usingMsaGamertagsOnly;

    // Level settings end
    /**
     * A base64 encoded world ID that is used to identify the world.
     */
    private String levelId;
    /**
     * The world name shown to the client for the current session.
     */
    private String levelName;
    /**
     * A UUID-like content identity string specific to the premium world template that may have
     * been used to generate the world. Servers should usually leave this empty.
     */
    private String premiumWorldTemplateId;
    /**
     * Specifies if the world was a trial world, meaning features are limited and there is a time
     * limit on the world.
     */
    private boolean trial;

    // SyncedPlayerMovementSettings start
    /**
     * Whether block breaking is handled server-authoritatively.
     */
    boolean serverAuthoritativeBlockBreaking;
    // SyncedPlayerMovementSettings end
    /**
     * The total time that has elapsed since the start of the world.
     */
    private long currentTick;
    /**
     * The seed used to seed the random used to produce enchantments in the enchantment table. Note
     * that the exact correct random implementation must be used to produce the correct results both
     * client- and server-side.
     */
    private int enchantmentSeed;
    /**
     * The block palette.
     */
    private NbtList<NbtMap> blockPalette;
    /**
     * The multiplayer correlation ID.
     */
    private String multiplayerCorrelationId;
    /**
     * Specifies if the world from the server was from a world template. For servers this should
     * always be set to false.
     *
     * @since v313
     */
    private boolean fromWorldTemplate;
    /**
     * Whether platform-locked content has already been confirmed by the client.
     *
     * @since v332
     */
    private boolean platformLockedContentConfirmed;
    /**
     * Whether the world template locks settings in the world options UI.
     *
     * @since v332
     */
    private boolean worldTemplateOptionLocked;
    /**
     * The item definitions.
     *
     * @since v361
     * @deprecated since v776. Use ItemComponentPacket instead.
     */
    @Deprecated
    private List<ItemDefinition> itemDefinitions = new ObjectArrayList<>();
    /**
     * OnlySpawnV1Villagers is a hack that Mojang put in place to preserve backwards compatibility
     * with old villagers. The bool is never actually read though, so it has no functionality.
     *
     * @since v361
     */
    private boolean onlySpawningV1Villagers;
    /**
     * The vanilla version.
     *
     * @since v388
     */
    private String vanillaVersion;
    /**
     * @since v388
     * @deprecated since v818. {@link AuthoritativeMovementMode#SERVER_WITH_REWIND} is now the default
     * movement mode.
     */
    @Deprecated
    private AuthoritativeMovementMode authoritativeMovementMode;
    /**
     * Specifies if the biome that the player spawns in is user-defined through behaviour packs or
     * built in to the base game.
     *
     * @since v407
     */
    private SpawnBiomeType spawnBiomeType;
    /**
     * The custom biome name.
     *
     * @since v407
     */
    private String customBiomeName;
    /**
     * EducationProductID is a UUID used to identify the education edition server instance. It is
     * generally unique for education edition servers.
     *
     * @since v407
     */
    private String educationProductionId;
    /**
     * The width of the world when using a limited world type.
     *
     * @since v407
     */
    private int limitedWorldWidth;
    /**
     * The depth of the world when using a limited world type.
     *
     * @since v407
     */
    private int limitedWorldHeight;
    /**
     * Whether the world uses the new nether generation introduced in 1.16.
     *
     * @since v407
     */
    private boolean netherType;
    /**
     * Specifies if experimental gameplay should be force enabled. For servers this should always be
     * set to false.
     *
     * @since v407
     */
    private OptionalBoolean forceExperimentalGameplay;
    /**
     * ServerAuthoritativeInventory specifies if the server authoritative inventory system is
     * enabled. This is a new system introduced in 1.16. Backwards compatibility with the inventory
     * transactions has to some extent been preserved, but will eventually be removed.
     *
     * @since v407
     */
    private boolean inventoriesServerAuthoritative;
    /**
     * Specifies if any experiments were previously toggled in this world. It is probably used for
     * some kind of metrics.
     *
     * @since v419
     */
    private boolean experimentsPreviouslyToggled;
    /**
     * The block properties.
     *
     * @since v419
     */
    private final List<BlockPropertyData> blockProperties = new ObjectArrayList<>();
    /**
     * The rewind history size.
     *
     * @since v428
     */
    private int rewindHistorySize;
    /**
     * The name of the server software. Used for telemetry within the Bedrock client.
     *
     * @since v440
     */
    private String serverEngine;
    /**
     * EducationSharedResourceURI is an education edition feature that transmits education resource
     * settings to clients.
     *
     * @since v465
     */
    private EduSharedUriResource eduSharedUriResource = EduSharedUriResource.EMPTY;
    /**
     * A XXHash64 of all block states by their compound tag. <b>The exact way this is calculated is
     * not currently known.</b>
     *
     * <p>A value of 0 will not be validated by the client.
     *
     * @since v475
     */
    private long blockRegistryChecksum;
    /**
     * PropertyData contains properties that should be applied on the player. These properties are
     * the same as the ones that are sent in the SyncActorProperty packet.
     *
     * @since v527
     */
    private NbtMap playerPropertyData;
    /**
     * A UUID that identifies the template that was used to generate the world. Servers that do not
     * use a world based off of a template can set this to an empty UUID.
     *
     * @since v527
     */
    private UUID worldTemplateId;
    /**
     * A value to dictate the type of editor mode, a special mode recently introduced adding
     * "powerful tools for editing worlds, intended for experienced creators."
     *
     * @since v534
     */
    private WorldType editorWorldType = WorldType.NON_EDITOR;
    /**
     * Specifies the level of restriction applied to in-game chat.
     *
     * @since v544
     */
    private ChatRestrictionLevel chatRestrictionLevel;
    /**
     * DisablePlayerInteractions is true if the client should ignore other players when interacting
     * with the world.
     *
     * @since v544
     */
    private boolean disablingPlayerInteractions;
    /**
     * Whether persona skins are disabled for this game session.
     *
     * @since v544
     */
    private boolean disablingPersonas;
    /**
     * CustomSkinsDisabled is true if custom skins are disabled for the current game session.
     *
     * @since v544
     */
    private boolean disablingCustomSkins;
    /**
     * Enables client side chunk generation
     *
     * @since v544
     */
    private boolean clientSideGenerationEnabled;
    /**
     * Specifies if players will be sent a chat message when using certain emotes.
     *
     * @since v567
     */
    private boolean emoteChatMuted;
    /**
     * Whether block runtime IDs should be replaced by 32-bit hashes of the NBT block state.
     * Unlike runtime IDs, these hashes should remain stable across versions and make support for
     * data-driven/custom blocks easier.
     *
     * @since v582
     */
    private boolean blockNetworkIdsHashed;
    /**
     * A value to dictate if the world was created as a project in the editor mode. The
     * functionality of this field is currently unknown.
     *
     * @since v582
     */
    private boolean createdInEditor;
    /**
     * A value to dictate if the world was exported from editor mode. The functionality of this
     * field is currently unknown.
     *
     * @since v582
     */
    private boolean exportedFromEditor;
    /**
     * The network permissions.
     *
     * @since v589
     */
    private NetworkPermissions networkPermissions = NetworkPermissions.DEFAULT;
    /**
     * Whether the world is in hardcore mode. In hardcore mode, the player cannot respawn after
     * dying.
     *
     * @since v671
     */
    private boolean hardcore;
    /**
     * The server identifier for telemetry.
     *
     * @since v685
     */
    private String serverId;
    /**
     * The world identifier for telemetry.
     *
     * @since v685
     */
    private String worldId;
    /**
     * The scenario identifier for telemetry.
     *
     * @since v685
     */
    private String scenarioId;
    /**
     * The owner identifier for telemetry.
     *
     * @since v818
     */
    private String ownerId;
    /**
     * Whether the newer tick-based death systems are enabled.
     *
     * @since v827
     */
    private boolean tickDeathSystemsEnabled;
    /**
     * Whether optional server join information follows in newer protocol versions.
     *
     * @since v924
     */
    private boolean hasServerJoinInformation;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.START_GAME;
    }

    @Override
    public StartGamePacket clone() {
        try {
            return (StartGamePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
