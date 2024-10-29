package example;

import arc.*;
import arc.graphics.*;
import arc.math.Mathf;
import arc.Core;
import arc.Events;
import arc.scene.event.Touchable;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.*;
import mindustry.core.GameState;
import mindustry.game.EventType.*;
import mindustry.game.EventType.WorldLoadEndEvent;
import mindustry.mod.*;
import mindustry.ui.*;
import mindustry.ui.fragments.HudFragment;
import mindustry.Vars;
import mindustry.Vars.*;
import mindustry.world.blocks.power.PowerGraph;
public class ExampleJavaMod extends Mod {
  WidgetGroup mobileUI = new WidgetGroup();
  CoreItemsDisplay coreItemsDisplay = new CoreItemsDisplay();
  Bar bar = new Bar();  
  public void init() {
    Log.info("Loaded ExampleJavaMod constructor.");
    this.mobileUI.visibility = (() -> true);
    this.mobileUI.touchable = Touchable.childrenOnly;
    this.mobileUI.setFillParent(true);
    createInGameGroup();
    Core.scene.add(this.mobileUI);
    Log.info(mobileUI);
    Events.on(ResetEvent.class, clearCoreItemsDisplay -> {
      coreItemsDisplay.resetUsed();
      coreItemsDisplay.clear();
    });

    // TODO: Add a warning message that this mod is only intended for mobile and may
    // break things on other platforms
    // Events.on(ClientLoadEvent.class, null);

  }

  private void createInGameGroup() {
    Log.info("Loading UI stuff");
    

    // Log.info("update");
    this.mobileUI.fill(mobileUI -> {
      mobileUIProperties(mobileUI);
      buildCoreItemsDisplay(mobileUI);

    });

  }
    // don't mind me blatantly copying code from HudFragment on how it builds the
    // coreinfo table
  private void mobileUIProperties(Table mobileUI) {
    // for future me, this is where you put your UI stuff so they go in the mobileUI
    // WidgetGroup instead of hudGroup which IMO is jank. Maybe make another
    // WidgetGroup for non-gameplay UI elements in the future.
    mobileUI.top();
    if (Core.settings.getBool("macnotch"))
      mobileUI.margin(Vars.macNotchHeight);
    mobileUI.name = "ClutterUI";
    mobileUI.visible(() -> true);
    // pauseHeight is 36f according to
    // mindustry.ui.fragments.HudFragment.pauseheght. It is private and final so I
    // cannot call it.
    this.mobileUI.addChild(coreItemsDisplay);

  }

  private void buildCoreItemsDisplay(Table mobileUI) {
    mobileUI.collapser(v -> v.add().height(36f), () -> Vars.state.isPaused() && !Vars.netServer.isWaitingForPlayers())
        .row();
    mobileUI.table(coreItemsUI -> {
      // Log.info("State.isGame:" + Vars.state.isGame());// core items
      coreItemsUI.top().collapser(coreItemsDisplay, () -> Vars.state.isGame()).fillX().row(); // TODO: Add a config
                                                                                              // option
                                                                                              // option
      // to
      // enable Core Items Info instead
      // of always setting this to true
    }).row();
  }//TODO: Continue tring to make a powerbar UI that can be dragged around
  private void buildPowerBar(Table mobileUI) {
    mobileUI.collapser(v -> v.add().height(36f), () -> Vars.state.isPaused() && !Vars.netServer.isWaitingForPlayers())
        .row();
    mobileUI.table(coreItemsUI -> {
      
      coreItemsUI.top().collapser(coreItemsDisplay, () -> Vars.state.isGame()).fillX().row(); 
    }).row();
  }
}
