package com.maciej916.maessentials.commands;

import com.maciej916.maessentials.data.DataManager;
import com.maciej916.maessentials.data.PlayerData;
import com.maciej916.maessentials.libs.Methods;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;

public class CommandUnmute {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("unmute").requires(source -> source.hasPermissionLevel(2));
        builder
            .executes(context -> unmute(context))
            .then(Commands.argument("targetPlayer", EntityArgument.players())
                .executes(context -> unmuteArgs(context)));

        dispatcher.register(builder);
    }

    private static int unmute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        player.sendMessage(Methods.formatText("command.maessentials.player.provide", TextFormatting.DARK_RED));
        return Command.SINGLE_SUCCESS;
    }

    private static int unmuteArgs(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        ServerPlayerEntity requestedPlayer = EntityArgument.getPlayer(context, "targetPlayer");

        player.sendMessage(Methods.formatText("command.maessentials.unmute.player", TextFormatting.WHITE, requestedPlayer.getDisplayName()));
        requestedPlayer.sendMessage(Methods.formatText("command.maessentials.unmute.target", TextFormatting.WHITE));

        PlayerData playerData = DataManager.getPlayerData(requestedPlayer);
        playerData.unmute();
        DataManager.savePlayerData(playerData);

        return Command.SINGLE_SUCCESS;
    }
}