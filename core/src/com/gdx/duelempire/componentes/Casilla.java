package com.gdx.duelempire.componentes;

import com.gdx.duelempire.actores.ActorEstructura;
import com.gdx.duelempire.actores.ActorMinion;

/**
 * Created by juanjo on 24/05/2017.
 */

/**
 * La clase Casilla representa una de las 10 casillas de la linea de juego, siendo la primera y
 * la ultima las bases y de la 2ª a la 9ª personajes (o huecos vacíos).
 */
public class Casilla {
    private ActorMinion minion;
    private ActorEstructura estructura;

    public ActorMinion getMinion() {
        return minion;
    }

    public void setMinion(ActorMinion minion) {
        this.minion = minion;
    }

    public ActorEstructura getEstructura() {
        return estructura;
    }

    public void setEstructura(ActorEstructura estructura) {
        this.estructura = estructura;
    }

    /**
     * Constructor de casilla vacía
     */
    public Casilla() {
        minion = null;
        estructura = null;
    }

    /**
     * Constructor de casilla con minion
     * @param minion
     */
    public Casilla(ActorMinion minion) {
        this.minion = minion;
        this.estructura = null;
    }

    /**
     * Constructor de casilla con estructura
     * @param estructura
     */
    public Casilla(ActorEstructura estructura) {
        this.estructura = estructura;
        this.minion = null;
    }
}
