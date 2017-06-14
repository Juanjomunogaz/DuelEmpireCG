package com.gdx.duelempire.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by juanjo on 17/05/2017.
 */

/**
 * Clase objeto de tipo estructura
 */
public class ActorEstructura extends Actor {
    private String tipo;
    private Texture base;
    private float relacionx, relaciony;
    private Label lSalud; // label asociado
    private int salud;

    public Label getLrecurso() {
        return lSalud;
    }
    public int getSalud() {
        return salud;
    }
    public void setSalud(int salud) {
        this.salud = salud;
    }

    public String getTipo() {
        return tipo;
    }

    //tipo: Player1 / Player2
    public ActorEstructura(Texture base,String tipo, Skin skin, float relacionx, float relaciony) {
        salud = 100;
        this.base = base;
        this.relaciony=relaciony;
        this.relacionx=relacionx;
        this.tipo=tipo;
        if(tipo.equals("Player1")){
            setName("base1");
        }else{
            setName("base2");
        }
        lSalud = new Label("PS ", skin);
        lSalud.setColor(Color.GOLD);
        switch(Gdx.app.getType()) {
            case Android:
                lSalud.setFontScale(2);
                break;
            case Desktop:
                lSalud.setFontScale(1);
                break;
        }
        if (tipo.equals("Player1")) {
            this.setPosition(5f / relacionx, 220f / relaciony);
            lSalud.setPosition(20f/relacionx, 320f/relaciony);
        }else{
            this.setPosition(575f / relacionx, 220f / relaciony);
            lSalud.setPosition(590f/relacionx, 320f/relaciony);
        }
        this.setTextoLabel(""+salud);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        batch.draw(base, getX(), getY(), 60f/relacionx, 100f/relaciony);
    }

    public void setTextoLabel(String texto){
        lSalud.setText(texto);
    }

}
