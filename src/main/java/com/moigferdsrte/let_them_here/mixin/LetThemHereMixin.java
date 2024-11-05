package com.moigferdsrte.let_them_here.mixin;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class LetThemHereMixin {
	@Inject(method = "checkDespawn", at = @At("HEAD"), cancellable = true)
	public void desSelect(CallbackInfo ci){
		MobEntity mobEntity = (MobEntity) (Object) this;
		if (mobEntity.getWorld().getDifficulty() == Difficulty.PEACEFUL && mobEntity.hasCustomName() && mobEntity instanceof HostileEntity){
			mobEntity.setAttacking(false);
			ci.cancel();
		}
	}
}