package com.gdx.duelempire;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Clase principal del programa, contiene los componentes más pesados de cargar y las distintas
 * pantallas con las que cuenta el juego
 */
public class DuelEmpire_CardGame extends Game {
    private AssetManager manager;
    //Se declaran las pantallas que tendrá el juego
    public PantallaMenuPrincipalJuego menuPrincipal;
    public PantallaPartidaJuego partida;
    //Variable encargada de recoger toda la música y sonidos del juego
    public AssetManager getManager() {
        return manager;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("music/HeroicBattleMusic.mp3", Music.class);
        manager.load("music/MenuMusic.mp3", Music.class);
        manager.load("music/DrawCard.mp3", Sound.class);
        manager.load("music/summonMinion.mp3", Sound.class);
        manager.load("music/summonSpell.mp3", Sound.class);
        manager.load("music/defeat.mp3", Sound.class);
        manager.load("music/hit.mp3", Sound.class);
        manager.finishLoading();//sincrono

        partida = null;
        menuPrincipal = new PantallaMenuPrincipalJuego(this);
        setScreen(menuPrincipal);
    }

}
