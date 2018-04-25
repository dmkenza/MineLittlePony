package com.minelittlepony.pony.data;

import com.minelittlepony.model.player.PlayerModels;

public enum PonyRace implements ITriggerPixelMapped<PonyRace> {

    HUMAN(0, PlayerModels.HUMAN, false, false),
    EARTH(0xf9b131, PlayerModels.PONY,false, false),
    PEGASUS(0x88caf0, PlayerModels.PONY, true, false),
    UNICORN(0xd19fe4, PlayerModels.PONY, false, true),
    ALICORN(0xfef9fc, PlayerModels.PONY, true, true),
    CHANGELING(0x282b29, PlayerModels.PONY, true, true),
    ZEBRA(0xd0cccf, PlayerModels.PONY, false, false),
    REFORMED_CHANGELING(0xcaed5a, PlayerModels.PONY, true, true),
    GRIFFIN(0xae9145, PlayerModels.PONY, true, false),
    HIPPOGRIFF(0xd6ddac, PlayerModels.PONY, true, false);

    private boolean wings, horn;

    private int triggerPixel;
    
    private PlayerModels model;

    PonyRace(int triggerPixel, PlayerModels model, boolean wings, boolean horn) {
        this.triggerPixel = triggerPixel;
        
        this.wings = wings;
        this.horn = horn;
        this.model = model;
    }

    /**
     * Returns true if this pony has a horn (and by extension can cast magic).
     * @return
     */
    public boolean hasHorn() {
        return horn;
    }

    /**
     * Returns true if this pony has wings. If it has wings, it can fly, of course.
     */
    public boolean hasWings() {
        return wings;
    }

    /**
     * Returns true if this is a human.
     */
    public boolean isHuman() {
        return this == HUMAN;
    }

    /**
     * Gets the model type associated with this species.
     */
    public PlayerModels getModel() {
        return model;
    }

    /**
     * Gets the actual race determined by the given pony level.
     * PonyLevel.HUMANS would force all races to be humans.
     * PonyLevel.BOTH is no change.
     * PonyLevel.PONIES (should) return a pony if this is a human. Don't be fooled, though. It doesn't.
     */
    public PonyRace getEffectiveRace(PonyLevel level) {
        if (level == PonyLevel.HUMANS) return HUMAN;
        return this;
    }

    @Override
    public int getTriggerPixel() {
        return triggerPixel;
    }
}
