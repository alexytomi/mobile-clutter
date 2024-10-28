package example;

import arc.*;
import arc.graphics.*;
import arc.math.Mathf;
import arc.Core;
import arc.Events;
import arc.scene.event.Touchable;
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

public class ExampleJavaMod extends Mod {
  GameState state = new GameState();
  WidgetGroup mobileUI = new WidgetGroup();
  CoreItemsDisplay coreItemsDisplay = new CoreItemsDisplay();

  public void init() {
    Log.info("Loaded ExampleJavaMod constructor.");

    // TODO: Add a warning message that this mod is only intended for mobile and may
    // break things on other platforms
    // Events.on(ClientLoadEvent.class, null);
    try {
      Events.on(WorldLoadEvent.class, e -> {
        Log.info("Loading UI stuff");
        this.mobileUI.addChild(coreItemsDisplay);
        // don't mind me blatantly copying code from HudFragment on how it builds the
        // coreinfo table
        this.mobileUI.fill(mobileUI -> {
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
          mobileUI.collapser(v -> v.add().height(36f), () -> state.isPaused() && !Vars.netServer.isWaitingForPlayers())
              .row();
          mobileUI.table(coreItemsUI -> {
            Log.info("Loading core UI stuff");// core items
            coreItemsUI.top().collapser(coreItemsDisplay, () -> true).fillX().row(); // TODO: Add a config option to
                                                                                     // enable Core Items Info instead
                                                                                     // of always setting this to true
          }).row();

        });
        Core.scene.add(this.mobileUI);
        this.mobileUI.visibility = (() -> true);
        this.mobileUI.touchable = Touchable.childrenOnly;
        this.mobileUI.setFillParent(true);
        Log.info(mobileUI);
      });
    } catch (Exception e) {
      Log.info(mobileUI);

    }
    Log.info(mobileUI.visible);
    Log.info(mobileUI);
  }

}
