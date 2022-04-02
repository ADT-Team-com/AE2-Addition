package com.ae.addition;

import io.github.crucible.grimoire.common.api.grimmix.Grimmix;
import io.github.crucible.grimoire.common.api.grimmix.GrimmixController;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;
import io.github.crucible.grimoire.common.api.mixin.ConfigurationType;

@Grimmix(id = "ae2addition")
public class GrimorireModCore extends GrimmixController {

    @Override
    public void buildMixinConfigs(IConfigBuildingEvent event) {
        event.createBuilder("ae2addition/mixins.ae2addition.json")
                .mixinPackage("com.ae.addition.mixins")
                .commonMixins("common.*")
                .clientMixins("client.*")
                .refmap("@MIXIN_REFMAP@")
                .verbose(true)
                .required(true)
                .configurationType(ConfigurationType.MOD)
                .build();
    }
}