package com.minelittlepony.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minelittlepony.MineLittlePony;
import com.minelittlepony.PonyManager;
import com.minelittlepony.ducks.IPlayerInfo;
import com.minelittlepony.pony.data.Pony;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

@Mixin(NetworkPlayerInfo.class)
public abstract class MixinNetworkPlayerInfo implements IPlayerInfo {

    @Shadow
    private String skinType;

    @Shadow
    public abstract ResourceLocation getLocationSkin();

    @Shadow
    public abstract GameProfile getGameProfile();

    @Inject(method = "getSkinType()Ljava/lang/String;", at = @At("RETURN"), cancellable = true)
    private void getSkinType(CallbackInfoReturnable<String> info) {
        info.setReturnValue(getPony().getRace(false).getModel().getId(usesSlimArms()));
    }

    @Override
    public Pony getPony() {
        ResourceLocation skin = getLocationSkin();
        if (skin == null) {
            return MineLittlePony.getInstance().getManager().getDefaultPony(getGameProfile().getId());
        }
        return MineLittlePony.getInstance().getManager().getPony(skin, getGameProfile().getId());
    }

    @Override
    public boolean usesSlimArms() {
        if (skinType == null) return PonyManager.isSlimSkin(getGameProfile().getId());
        return "slim".equals(skinType);
    }
}
