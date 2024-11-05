package com.moigferdsrte.let_them_here;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Iterator;

import static net.minecraft.server.command.CommandManager.literal;

public class ClearCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatchers){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("clear_hostile_mobs")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("run"), false);
                    return execute((ServerCommandSource)context.getSource(), (Collection<? extends HostileEntity>) EntityArgumentType.getEntities(context, "targets"));
                })));
    }

    private static int execute(ServerCommandSource source, Collection<? extends HostileEntity> targets) {
        Iterator<? extends HostileEntity> var2 = targets.iterator();
        while(var2.hasNext()) {
            HostileEntity entity = (HostileEntity)var2.next();
            if (entity.hasCustomName()) entity.kill();
        }
        source.sendFeedback(() -> {
            return Text.translatable("commands.kill.success.multiple", new Object[]{targets.size()});
        }, true);

        return targets.size();
    }

}
