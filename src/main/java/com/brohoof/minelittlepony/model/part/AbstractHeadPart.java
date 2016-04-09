package com.brohoof.minelittlepony.model.part;

import com.brohoof.minelittlepony.PonyData;
import com.brohoof.minelittlepony.model.AbstractPonyModel;
import com.brohoof.minelittlepony.model.BodyPart;

import net.minecraft.client.renderer.GlStateManager;

public abstract class AbstractHeadPart implements IPonyPart {

    protected final AbstractPonyModel pony;

    public AbstractHeadPart(AbstractPonyModel pony) {
        this.pony = pony;
    }

    @Override
    public void render(PonyData data, float scale) {
        pony.transform(BodyPart.HEAD);
        GlStateManager.translate(pony.bipedHead.offsetX, pony.bipedHead.offsetY, pony.bipedHead.offsetZ);
        pony.bipedHead.postRender(scale);
    }

    @Override
    public void animate(PonyData metadata, float move, float swing, float tick, float horz, float vert) {

    }
}
