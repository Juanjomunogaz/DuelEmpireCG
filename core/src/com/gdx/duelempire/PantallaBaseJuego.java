package com.gdx.duelempire;

import com.badlogic.gdx.Screen;
/**
 * Created by juanjo on 03/05/2017.
 */
//al usar esta clase únicamente para heredar de ella, se define como abstracta
public abstract class PantallaBaseJuego implements Screen {

    private DuelEmpire_CardGame juego;

    public PantallaBaseJuego(DuelEmpire_CardGame juego){
        this.juego =juego;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
