package com.maciej916.maessentials.commands;

import com.maciej916.maessentials.libs.Methods;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class CommandDay {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("day").requires(source -> source.hasPermissionLevel(2));
        builder.executes(context -> day(context));
        dispatcher.register(builder);
    }

    private static int day(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();

        for (ServerWorld serverworld : context.getSource().getServer().getWorlds()) {
            serverworld.setDayTime(2000);
        }

        player.sendMessage(Methods.formatText("day.maessentials.success"));
        return Command.SINGLE_SUCCESS;
    }
}