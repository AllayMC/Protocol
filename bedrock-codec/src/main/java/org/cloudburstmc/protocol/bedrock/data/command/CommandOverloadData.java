package org.cloudburstmc.protocol.bedrock.data.command;

/**
 * Represents an overload of a command. This overload can be compared to function overloading in
 * languages such as java. It represents a single usage of the command. A command may have multiple
 * different overloads, which are handled differently.
 *
 * @param chaining  Chaining determines if the parameters use chained subcommands or not.
 * @param overloads The overloads.
 */
public record CommandOverloadData(boolean chaining, CommandParamData[] overloads) {
}
