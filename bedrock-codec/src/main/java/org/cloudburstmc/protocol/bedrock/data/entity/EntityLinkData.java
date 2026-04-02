package org.cloudburstmc.protocol.bedrock.data.entity;

/**
 * Represents a link between two entities, typically being one entity riding another.
 *
 * @param from                   The unique ID of the ridden entity, such as a boat or minecart.
 * @param to                     The unique ID of the rider or passenger entity linked to {@code from}.
 * @param type                   The way the two entities are linked.
 * @param immediate              Whether the link should be removed immediately, for example when a
 *                               mount dies.
 * @param riderInitiated         Whether the link was initiated by the rider itself.
 * @param vehicleAngularVelocity The angular velocity of the vehicle currently being ridden.
 */
public record EntityLinkData(long from, long to, Type type, boolean immediate, boolean riderInitiated, float vehicleAngularVelocity) {
    @Deprecated
    public EntityLinkData(long from, long to, Type type, boolean immediate) {
        this(from, to, type, immediate, false, 0f);
    }

    @Deprecated
    public EntityLinkData(long from, long to, Type type, boolean immediate, boolean riderInitiated) {
        this(from, to, type, immediate, riderInitiated, 0f);
    }

    public enum Type {
        REMOVE,
        RIDER,
        PASSENGER;

        private static final Type[] VALUES = values();

        public static Type byId(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            }
            throw new UnsupportedOperationException("Unknown EntityLinkData.Type ID: " + id);
        }
    }
}
