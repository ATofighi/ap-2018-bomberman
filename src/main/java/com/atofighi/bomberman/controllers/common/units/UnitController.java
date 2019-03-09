package com.atofighi.bomberman.controllers.common.units;

import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.models.units.Unit;
import com.atofighi.bomberman.util.Rectangle;
import com.atofighi.bomberman.views.util.Paintable;

public abstract class UnitController {
    public abstract Unit get();

    public abstract void destroy(Bomberman destroyer);

    public abstract Rectangle getRectangle();
}
