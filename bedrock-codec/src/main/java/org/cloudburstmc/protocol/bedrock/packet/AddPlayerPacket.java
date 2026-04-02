package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.command.CommandPermission;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

import java.util.List;
import java.util.UUID;

/**
 * Sent by the server to the client to make a player entity show up client-side. It is one of the
 * few entities that cannot be sent using the {@link AddEntityPacket}.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AddPlayerPacket implements BedrockPacket, PlayerAbilityHolder {
    /**
     * A map of entity metadata, which includes flags and data properties that alter in particular
     * the way the player looks. Flags include ones such as 'on fire' and 'sprinting'. The metadata
     * values are indexed by their property key.
     */
    private EntityDataMap metadata = new EntityDataMap();
    /**
     * A list of entity links that are currently active on the player. These links alter the way the
     * player shows up when first spawned in terms of it shown as riding an entity. Setting these
     * links is important for new viewers to see the player is riding another entity.
     */
    private List<EntityLinkData> entityLinks = new ObjectArrayList<>();
    /**
     * The UUID of the player. It is the same UUID that the client sent in the Login packet at the
     * start of the session. A player with this UUID must exist in the player list (built up using
     * the PlayerList packet), for it to show up in-game.
     */
    private UUID uuid;
    /**
     * The name of the player. This username is the username that will be set as the initial name
     * tag of the player.
     */
    private String username;
    /**
     * Legacy unique entity ID of the player. Unlike {@link #runtimeEntityId}, this value is not the
     * per-session runtime ID used by most gameplay packets.
     *
     * @deprecated since v534
     */
    @Deprecated
    private long uniqueEntityId;
    /**
     * The runtime ID of the player. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * An identifier only set for particular platforms when chatting (presumably only for Nintendo
     * Switch). It is otherwise an empty string, and is used to decide which players are able to
     * chat with each other.
     */
    private String platformChatId;
    /**
     * The position to spawn the player on. If the player is on a distance that the viewer cannot
     * see it, the player will still show up if the viewer moves closer.
     */
    private Vector3f position;
    /**
     * The initial velocity the player spawns with. This velocity will initiate client side movement
     * of the player.
     */
    private Vector3f motion;
    /**
     * The player pitch, yaw, and head yaw, in degrees.
     */
    private Vector3f rotation;
    /**
     * The item currently held by the player when it is first spawned for this viewer.
     */
    private ItemData hand;
    /**
     * Legacy adventure settings and permissions for the player. Newer protocol versions derive the
     * same information from {@link #abilityLayers}, but this packet still keeps the compatibility
     * structure.
     */
    private AdventureSettingsPacket adventureSettings = new AdventureSettingsPacket();
    /**
     * The device ID set in one of the files found in the storage of the device of the player. It
     * may be changed freely, so it should not be relied on for anything.
     */
    private String deviceId;
    /**
     * The build platform/device OS of the player that is about to be added, as it sent in the Login
     * packet when joining.
     *
     * @since v388
     */
    private BuildPlatform buildPlatform = BuildPlatform.UNKNOWN;
    /**
     * The game type of the player. If set to GameTypeSpectator, the player will not be shown to
     * viewers.
     *
     * @since v503
     */
    private GameType gameType;
    /**
     * The full set of ability layers advertised for the player. This should at least contain the
     * base layer and may include additional layers such as spectator or editor abilities.
     *
     * @since v534
     */
    private List<AbilityLayer> abilityLayers = new ObjectArrayList<>();
    /**
     * A list of properties that the entity inhibits. These properties define and alter specific
     * attributes of the entity.
     *
     * @since v557
     */
    private final EntityProperties properties = new EntityProperties();

    public void setUniqueEntityId(long uniqueEntityId) {
        this.uniqueEntityId = uniqueEntityId;
        this.adventureSettings.setUniqueEntityId(uniqueEntityId);
    }

    @Override
    public PlayerPermission getPlayerPermission() {
        return this.adventureSettings.getPlayerPermission();
    }

    @Override
    public void setPlayerPermission(PlayerPermission playerPermission) {
        this.adventureSettings.setPlayerPermission(playerPermission);
    }

    @Override
    public CommandPermission getCommandPermission() {
        return this.adventureSettings.getCommandPermission();
    }

    @Override
    public void setCommandPermission(CommandPermission commandPermission) {
        this.adventureSettings.setCommandPermission(commandPermission);
    }

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_PLAYER;
    }

    @Override
    public AddPlayerPacket clone() {
        try {
            return (AddPlayerPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
