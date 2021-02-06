package com.minelittlepony.client.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

import com.minelittlepony.client.GuiPonySettings;

public class MineLPModMenuFactory implements ModMenuApi {

    @Override
    public String getModId() {
        return "minelp";
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiPonySettings::new;
    }
}
