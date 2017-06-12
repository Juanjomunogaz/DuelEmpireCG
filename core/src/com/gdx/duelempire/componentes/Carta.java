package com.gdx.duelempire.componentes;

/**
 * Created by juanjo on 02/06/2017.
 */

/**
 * La clase carta es la encargada de reunir en un ente todos los componentes de un objeto
 * carta (pudiendo ser de tipo hechizo o minion). Guarda los datos siguientes:
 * - El ID del objeto.
 * - Las estadisticas de la carta (ataque/defensa/movilidad).
 * - El efecto de la carta.
 * - La imagen asociada a la carta.
 */
public class Carta {
    private String id, tipo, efecto;//id: identificador único de cada carta. tipo: hechizo o minion
    private int ataque, defensa;
    private float movilidad;//movilidad medida en casillas/segundo
    private float ratioAtaque;
    private int[] coste;//representa el coste de oro y mana de la carta coste[0]=oro, coste[1]=mana
    private String rutaTextura;
    private float tamXminion, tamYminion;

    public String getId() {
        return id;
    }

    public String getEfecto() {
        return efecto;
    }

    public String getTipo() {
        return tipo;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public float getMovilidad() {
        return movilidad;
    }

    public void setMovilidad(int movilidad) {
        this.movilidad = movilidad;
    }

    public float getRatioAtaque() {
        return ratioAtaque;
    }

    public void setRatioAtaque(float ratioAtaque) {
        this.ratioAtaque = ratioAtaque;
    }

    public int[] getCoste() {
        return coste;
    }

    public String getRutaTextura() {
        return rutaTextura;
    }

    public float getTamXminion() {
        return tamXminion;
    }

    public float getTamYminion() {
        return tamYminion;
    }

    /**
     * Constructor vacio para representar que no hay carta
     */
    public Carta() {
        this.coste = new int[]{-1, -1};// ya que una carta no puede ser de coste negativo, se va a identificar así
        // cuando representemos que no hay carta en la mano del jugador
    }

    /**
     * Constructor de hechizo
     *
     * @param id
     * @param efecto
     * @param coste
     */
    public Carta(String id, String efecto, int[] coste) {
        this.tipo = "hechizo";
        this.id = id;
        this.efecto = efecto;
        this.coste = coste;
        //diferencio con valores negativos de stats a los hechizos
        this.ataque = -1;
        this.defensa = -1;
        this.movilidad = -1;
    }

    /**
     * Constructor de minion
     * @param id
     * @param efecto
     * @param rutaTextura
     * @param tamXminion
     * @param tamYminion
     * @param coste
     * @param ataque
     * @param defensa
     * @param ratioAtaque
     * @param movilidad
     */
    public Carta(String id, String efecto, String rutaTextura, float tamXminion, float tamYminion, int[] coste, int ataque, int defensa,float ratioAtaque, float movilidad) {
        this.tipo = "minion";
        this.id = id;
        this.efecto = efecto;
        this.ataque = ataque;
        this.defensa = defensa;
        this.movilidad = movilidad;
        this.ratioAtaque = ratioAtaque;
        this.rutaTextura = rutaTextura;
        this.tamXminion = tamXminion;
        this.tamYminion = tamYminion;
        this.coste = coste;
    }
}
