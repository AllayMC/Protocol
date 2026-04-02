package org.cloudburstmc.protocol.bedrock.data;

/**
 * ExperimentData holds data on an experiment that is either enabled or disabled.
 *
 * @param name    The name of the experiment.
 * @param enabled Specifies if the experiment is enabled. Vanilla typically sets this to {@code true}
 *                for any experiment sent in the packet.
 */
public record ExperimentData(String name, boolean enabled) {
}
