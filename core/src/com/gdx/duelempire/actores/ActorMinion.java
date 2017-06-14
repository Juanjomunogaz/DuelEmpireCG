package com.gdx.duelempire.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.duelempire.DuelEmpire_CardGame;
import com.gdx.duelempire.componentes.Casilla;


/**
 * Created by juanjo on 03/05/2017.
 */

/**
 * Clase objeto de tipo personaje
 */
public class ActorMinion extends Actor {
    private Texture base;
    private float relacionx, relaciony;// representan la escala respecto a la pantalla
    private int defensa, ataque;
    private float movimiento;
    private float ratioAtaque;
    private float tamanox, tamanoy;
    private int casilla;
    private int propietario;//1 o 2
    private Sound hit, defeat;
    private Label lSalud; // label asociado


    public int getSalud() {
        return defensa;
    }

    public void setSalud(int defensa) {
        this.defensa = defensa;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public float getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public float getRatioAtaque() {
        return ratioAtaque;
    }

    public void setRatioAtaque(float ratioAtaque) {
        this.ratioAtaque = ratioAtaque;
    }

    public int getCasilla() {
        return casilla;
    }

    public void setCasilla(int casilla) {
        this.casilla = casilla;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(char propietario) {
        this.propietario = propietario;
    }

    public Label getLabelSalud() {
        return lSalud;
    }

    public void setTextoLabel(String texto) {
        lSalud.setText(texto);
    }


    /**
     * Constructor de minion
     *
     * @param propietario 1 player1, 2 player2
     * @param casilla     casilla de invocación (suele ser la 1)
     * @param nombre
     * @param base
     * @param relacionx
     * @param relaciony
     * @param tamanox
     * @param tamanoy
     * @param defensa
     * @param ataque
     * @param ratioAtaque
     * @param movimiento
     */
    public ActorMinion(int propietario, int casilla, String nombre, Texture base, float relacionx, float relaciony, float tamanox, float tamanoy, int defensa, int ataque, float ratioAtaque, float movimiento, DuelEmpire_CardGame juego, Skin skin) {
        this.propietario = propietario;
        this.casilla = casilla;
        this.setName(nombre);
        this.base = base;
        this.relaciony = relaciony;
        this.relacionx = relacionx;
        this.tamanox = tamanox;
        this.tamanoy = tamanoy;
        this.defensa = defensa;
        this.ataque = ataque;
        this.movimiento = movimiento;
        this.ratioAtaque = ratioAtaque;
        contadorMovimiento = 0f;
        segundo = 0f;
        puedeDesplazarse = false;// No se puede mover hasta que no pase su 'movimiento'
        puedeAtacar = false;
        //Se cargan los sonidos
        hit = juego.getManager().get("music/hit.mp3");
        defeat = juego.getManager().get("music/defeat.mp3");
        lSalud = new Label(ataque + "-" + defensa, skin);
        switch (Gdx.app.getType()) {
            case Android:
                lSalud.setFontScale(1f);
                break;
            case Desktop:
                lSalud.setFontScale(0.7f);
                break;
        }
    }

    private float contadorMovimiento;//Contador de movimiento del esbirro
    private float contadorRatio;//Contador de ratio de ataque del esbirro
    private boolean puedeAtacar;
    private float segundo;
    private boolean puedeDesplazarse;//Para comprobar si ha pasado ya el tiempo de movimiento
    private int tiempoVidaMinion;

    //Usado para actualizar el actor
    @Override
    public void act(float delta) {
        super.act(delta);
        segundo += delta;
        if (segundo >= 1) {
            tiempoVidaMinion += 1;
            segundo = 0;
        }
        contadorMovimiento += delta;
        if ((contadorMovimiento >= movimiento)) {
            puedeDesplazarse = true;
            contadorMovimiento = 0;
        }
        contadorRatio += delta;
        if (contadorRatio >= ratioAtaque) {
            contadorRatio = 0;
            puedeAtacar = true;
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        batch.draw(base, getX(), getY(), tamanox / relacionx, tamanoy / relaciony);
    }

    /**
     * Método para actualizar un actor sincronizandolo con el resto
     *
     * @param casillas
     * @param posicion
     */
    public void actualizarAccionMinion(Casilla casillas[], int posicion) {
        /* ya que el ataque y el movimiento son independientes, hay que analizarlo según el esbirro tenga
        * ratioAtaque >= movimiento o al revés*/
        if (ratioAtaque >= movimiento) {
            if (puedeDesplazarse) {//si ya puede moverse, puede o atacar o desplazarse
                if (propietario == 1) {//minion de player 1
                    //se mira la casilla si está vacía de delante del minion o está en la de delante de la base enemiga
                    if (!(casillas[casilla].getMinion() == null) || casilla >= casillas.length - 1) {
                        Gdx.app.log("No se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                        if (casillas[casilla].getMinion() != null && casillas[casilla].getMinion().getPropietario() == 2 && puedeAtacar) {
                            Gdx.app.log("Minion ataca minion: ", "");
                            minionAtacaMinion(casillas[casilla - 1].getMinion(), casillas[casilla].getMinion(), casillas);
                            puedeAtacar = false;
                        } else if (casillas[casilla].getEstructura() != null && casillas[casilla].getEstructura().getTipo().equals("Player2") && puedeAtacar) {
                            Gdx.app.log("Minion ataca estructura: ", "");
                            minionAtacaEstructura(casillas[casilla - 1].getMinion(), casillas[casilla].getEstructura());
                            puedeAtacar = false;
                        }
                    } else {//si estaba vacía se mueve hacia adelante
                        Gdx.app.log("Se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                        casillas[casilla].setMinion(casillas[casilla - 1].getMinion());
                        casillas[casilla - 1].setMinion(null);
                        casilla++;
                        setX(getX() + (580f / relacionx) / (casillas.length));/*se adelanta su posición x, se hace sobre 580f
                    para que el movimiento de la última casilla no sea sobre la base enemiga y deje 60f pixeles de
                    margen*/
                        lSalud.setPosition(getX() + tamanox / 3f / relacionx, getY() + tamanoy / relaciony);
                    }
                } else {// minion de player 2
                    Gdx.app.log("Minion P2: ", "casilla " + casilla);
                    //se mira la casilla si está vacía de delante del minion o está en la de delante de la base del jugado
                    if (!(casillas[casilla - 2].getMinion() == null) || casilla <= 2) {
                        Gdx.app.log("No se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                        if (casillas[casilla - 2].getMinion() != null && casillas[casilla - 2].getMinion().getPropietario() == 1) {
                            Gdx.app.log("Minion ataca minion: ", "");
                            minionAtacaMinion(casillas[casilla - 1].getMinion(), casillas[casilla - 2].getMinion(), casillas);
                        } else if (casillas[casilla - 2].getEstructura() != null && casillas[casilla - 2].getEstructura().getTipo().equals("Player1")) {
                            Gdx.app.log("Minion ataca estructura: ", "");
                            minionAtacaEstructura(casillas[casilla - 1].getMinion(), casillas[casilla - 2].getEstructura());
                        }
                    } else {//si estaba vacía se mueve hacia adelante
                        Gdx.app.log("Se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                        casillas[casilla - 2].setMinion(casillas[casilla - 1].getMinion());
                        casillas[casilla - 1].setMinion(null);
                        casilla--;
                        setX(getX() - (580f / relacionx) / (casillas.length));/*se adelanta su posición x, se hace sobre 600f
                    para que el movimiento de la última casilla no sea sobre la base enemiga y deje 40f pixeles de
                    margen*/
                        lSalud.setPosition(getX() + tamanox / 3f / relacionx, getY() + tamanoy / relaciony);
                    }
                }
                puedeDesplazarse = false;/*si ya se ha desplazado hay que esperar a que vuelva a pasar el
            tiempo de desplazamiento*/
            }
        } else {
            if (propietario == 1) {
                //se mira la casilla si está vacía de delante del minion o está en la de delante de la base enemiga
                if (!(casillas[casilla].getMinion() == null) || casilla >= casillas.length - 1) {
                    if (casillas[casilla].getMinion() != null && casillas[casilla].getMinion().getPropietario() == 2 && puedeAtacar) {
                        Gdx.app.log("Minion ataca minion: ", "");
                        minionAtacaMinion(casillas[casilla - 1].getMinion(), casillas[casilla].getMinion(), casillas);
                        puedeAtacar = false;
                    } else if (casillas[casilla].getEstructura() != null && casillas[casilla].getEstructura().getTipo().equals("Player2") && puedeAtacar) {
                        Gdx.app.log("Minion ataca estructura: ", "");
                        minionAtacaEstructura(casillas[casilla - 1].getMinion(), casillas[casilla].getEstructura());
                        puedeAtacar = false;
                    }
                } else if (puedeDesplazarse) {//si estaba vacía se mueve hacia adelante
                    Gdx.app.log("Se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                    casillas[casilla].setMinion(casillas[casilla - 1].getMinion());
                    casillas[casilla - 1].setMinion(null);
                    casilla++;
                    setX(getX() + (580f / relacionx) / (casillas.length));/*se adelanta su posición x, se hace sobre 600f
                    para que el movimiento de la última casilla no sea sobre la base enemiga y deje 40f pixeles de
                    margen*/
                    lSalud.setPosition(getX() + tamanox / 3f / relacionx, getY() + tamanoy / relaciony);
                    puedeDesplazarse = false;
                }
            } else {//si el propietario es player2
                Gdx.app.log("No se puede mover el minion: ", "de la casilla " + casilla);
                if (!(casillas[casilla - 2].getMinion() == null) || casilla <= 1) {
                    Gdx.app.log("minion P2: ", "casilla " + casilla);
                    //Gdx.app.log("No se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                    if (casillas[casilla - 1].getMinion() != null && casillas[casilla - 2].getMinion().getPropietario() == 1 && puedeAtacar) {
                        Gdx.app.log("Minion P2 ataca minion: ", "");
                        minionAtacaMinion(casillas[casilla - 1].getMinion(), casillas[casilla - 2].getMinion(), casillas);
                        puedeAtacar = false;
                    } else if (casillas[casilla - 2].getEstructura() != null && casillas[casilla - 2].getEstructura().getTipo().equals("Player1") && puedeAtacar) {
                        Gdx.app.log("Minion P2 ataca estructura: ", "");
                        minionAtacaEstructura(casillas[casilla - 1].getMinion(), casillas[casilla - 2].getEstructura());
                        puedeAtacar = false;
                    }
                } else if (puedeDesplazarse) {//si estaba vacía se mueve hacia adelante
                    Gdx.app.log("Se puede mover el minion: ", casillas[casilla - 1].getMinion().getName() + ",de la casilla " + casilla);
                    casillas[casilla - 2].setMinion(casillas[casilla - 1].getMinion());
                    casillas[casilla - 1].setMinion(null);
                    casilla--;
                    setX(getX() - (580f / relacionx) / (casillas.length));/*se adelanta su posición x, se hace sobre 600f
                    para que el movimiento de la última casilla no sea sobre la base enemiga y deje 40f pixeles de
                    margen*/
                    lSalud.setPosition(getX() + tamanox / 3f / relacionx, getY() + tamanoy / relaciony);
                    puedeDesplazarse = false;
                }
            }
        }
    }

    /**
     * Método que permite que un minion ataque al enemigo de enfrente
     *
     * @param atacante
     * @param atacado
     * @param eliminarMinionDeCasilla
     */
    public void minionAtacaMinion(ActorMinion atacante, ActorMinion atacado, Casilla eliminarMinionDeCasilla[]) {
        hit.play();
        atacado.setSalud(atacado.getSalud() - atacante.getAtaque());
        atacado.setTextoLabel(atacado.getAtaque() + "-" + atacado.getSalud());
        Gdx.app.log("Datos ataque minions: ", "Player m ataca " + atacante.getPropietario() + "Player m atacado" + atacado.getPropietario() + " salud" + atacado.getSalud() + " casilla " + atacado.getCasilla());
        if (atacado.getSalud() < 1) {
            defeat.play();
            Gdx.app.log("Datos ataque minions: ", "Player m atacado eliminado" + atacado.getPropietario());
            atacado.remove();
            atacado.getLabelSalud().remove();
            eliminarMinionDeCasilla[atacado.getCasilla() - 1].setMinion(null);//se mata el minion
        }
    }

    /**
     * Método que permite que un minion ataque a la estructura de enfrente
     *
     * @param atacante
     * @param estructura
     */
    public void minionAtacaEstructura(ActorMinion atacante, ActorEstructura estructura) {
        hit.play();
        if (estructura.getSalud() > atacante.getAtaque()) {
            //Gdx.app.log("Ataque del minion: ",""+atacante.getAtaque());
            estructura.setSalud(estructura.getSalud() - atacante.getAtaque());
        } else {
            estructura.setSalud(0);
        }
        //Gdx.app.log("Salud de la estructura atacada: ",""+casillaEstructura.getEstructura().getSalud());
        estructura.setTextoLabel("" + estructura.getSalud());
    }
}