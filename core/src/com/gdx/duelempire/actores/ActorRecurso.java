package com.gdx.duelempire.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by juanjo on 24/05/2017.
 */

public class ActorRecurso extends Actor {
    private Texture base;
    private float relacionx, relaciony;// representan la escala respecto a la pantalla
    private Label lrecurso; // label asociado
    private String tipo;

    public Label getLrecurso() {
        return lrecurso;
    }

    //tipo: oro/ mana
    public ActorRecurso(Texture base, String tipo, Skin skin, float relacionx, float relaciony) {
        this.base = base;
        this.tipo=tipo;
        this.relaciony=relaciony;
        this.relacionx=relacionx;
        if (tipo.equals("Oro")) {
            lrecurso = new Label("Oro :", skin);
            lrecurso.setPosition(570f/relacionx, 430f/relaciony);
            this.setPosition(540 / relacionx, 430 / relaciony);
        }else{
            lrecurso = new Label("Mana :", skin);
            lrecurso.setPosition(460f/relacionx, 430f/relaciony);
            this.setPosition(430 / relacionx, 430 / relaciony);
        }
        switch(Gdx.app.getType()) {
            case Android:
                lrecurso.setFontScale(2);
                break;
            case Desktop:
                lrecurso.setFontScale(1);
                break;
        }
        //this.setTextoLabel("50");//recursos iniciales de oro y mana
    }

    //usado para actualizar el actor
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        batch.draw(base, getX(), getY(), 30f/relacionx, 30f/relaciony);
    }

    public void setTextoLabel(String texto){
        lrecurso.setText(texto);
    }
}
