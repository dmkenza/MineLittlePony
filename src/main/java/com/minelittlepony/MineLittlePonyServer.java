package com.minelittlepony;

import com.kenza.KenzaInjector;
import net.fabricmc.api.ModInitializer;

public class MineLittlePonyServer  implements ModInitializer {

    @Override
    public void onInitialize() {
        KenzaInjector.INSTANCE.init();
    }
}
