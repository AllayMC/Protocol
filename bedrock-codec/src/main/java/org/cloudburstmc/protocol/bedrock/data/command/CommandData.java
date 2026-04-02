package org.cloudburstmc.protocol.bedrock.data.command;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Command holds the data that a command requires to be shown to a player client-side. The command
 * is shown in the /help command and auto-completed using this data.
 *
 * @param name        The name of the command. The command may be executed using this name, and will be shown in
 *                    the /help list with it. It currently seems that the client crashes if the Name contains
 *                    uppercase letters.
 * @param description The description of the command. It is shown in the /help list and when starting to write a
 *                    command.
 * @param flags       A combination of flags not currently known. Leaving the Flags field empty appears to work.
 * @param permission  The command permission level required to execute the command.
 * @param aliases     Optional aliases exposed through a command enum.
 * @param overloads   A list of command overloads that specify the ways in which a command may be executed. The
 *                    overloads may be completely different.
 * @param subcommands Chained subcommands that can prefix another command, such as the chained parts
 *                    of {@code /execute}.
 */
public record CommandData(String name, String description, Set<Flag> flags, CommandPermission permission, CommandEnumData aliases,
                          CommandOverloadData[] overloads, List<ChainedSubCommandData> subcommands) {
    public String toString() {
        StringBuilder overloads = new StringBuilder("[\r\n");

        for (CommandOverloadData overload : this.overloads) {
            overloads.append("    [\r\n");
            overloads.append("       chaining=").append(overload.chaining()).append("\r\n");
            for (CommandParamData parameter : overload.overloads()) {
                overloads.append("       ").append(parameter).append("\r\n");
            }
            overloads.append("    ]\r\n");
        }
        overloads.append("]\r\n");

        StringBuilder builder = new StringBuilder("CommandData(\r\n");
        List<?> objects = Arrays.asList("name=" + name, "description=" + description,
                "flags=" + Arrays.toString(flags.toArray()), "permission=" + permission, "aliases=" + aliases, "subcommands=" + Arrays.toString(subcommands.toArray()),
                "overloads=" + overloads);

        for (Object object : objects) {
            builder.append("    ").append(Objects.toString(object).replaceAll("\r\n", "\r\n    ")).append("\r\n");
        }
        return builder.append(")").toString();
    }

    // Bit flags
    public enum Flag {
        TEST_USAGE, // 1
        HIDDEN_FROM_COMMAND_BLOCK, // 2
        HIDDEN_FROM_PLAYER, // 4
        HIDDEN_FROM_AUTOMATION, // 8
        LOCAL_SYNC, // 16
        EXECUTE_DISALLOWED, // 32
        MESSAGE_TYPE, // 64
        NOT_CHEAT,// 128
        ASYNC // 256
    }
}
