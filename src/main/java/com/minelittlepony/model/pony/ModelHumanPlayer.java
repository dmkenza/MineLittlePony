package com.minelittlepony.model.pony;

import com.minelittlepony.model.AbstractPonyModel;

public class ModelHumanPlayer extends AbstractPonyModel {

    public ModelHumanPlayer(boolean smallArms) {
        super(smallArms);
    }

    @Override
    protected boolean doCancelRender() {
        return true;
    }

}
