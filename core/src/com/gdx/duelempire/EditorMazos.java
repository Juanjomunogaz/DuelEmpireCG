package com.gdx.duelempire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by juanjo on 14/06/2017.
 */

public class EditorMazos extends PantallaBaseJuego {
    private Music musica;
    //Variables que gestionan la vista de la pantalla
    private OrthographicCamera camera;
    private FitViewport viewp;
    Image fondo;
    private Texture tfondo;
    private TextButton btncarta1, btncarta2, btncarta3, btncarta4, btncarta5, btncarta6,
            btnfinalizar, btnBorraryVolver;
    private Label info;
    /*Variable que  contiene los datos y formatos necesarios para escribir y establecer gráficos para
    los labels y los botones de la interfaz*/
    private Skin skin;
    private Stage stage;
    //Dimensiones del dispositivo
    private float ancho, alto;
    //Contador de cartas elegidas
    int i;

    DuelEmpire_CardGame juego;

    public EditorMazos(DuelEmpire_CardGame juego) {
        super(juego);
        this.juego = juego;
        musica = juego.getManager().get("music/MenuMusic.mp3");
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        camera = new OrthographicCamera();
        ancho = Gdx.graphics.getWidth();
        alto = Gdx.graphics.getHeight();
    }


    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("Datos camara:", "Anchura " + ancho + " |Largura " + alto);
        tfondo = new Texture("backgrounds/fondoMenu.png");
        fondo = new Image(tfondo);
        fondo.setSize(ancho, alto);
        i = 0;
        borrarMazo();//Se borra el mazo al entrar al editor
        instanciarBotones();

        camera.setToOrtho(false, ancho, alto);
        camera.update();
        camera.position.set(ancho / 2f, alto / 2f, 0);
        camera.update();
        viewp = new FitViewport(ancho, alto, camera);
        Gdx.app.log("Datos camara2:", "Anchura vp" + camera.viewportWidth + " |Largura vp" + camera.viewportHeight);
        stage = new Stage(viewp);
        stage.addActor(fondo);
        stage.addActor(btncarta1);
        stage.addActor(btncarta2);
        stage.addActor(btncarta3);
        stage.addActor(btncarta4);
        stage.addActor(btncarta5);
        stage.addActor(btncarta6);
        stage.addActor(btnfinalizar);
        stage.addActor(btnBorraryVolver);
        stage.addActor(info);
        //Se activa la entrada de datos
        Gdx.input.setInputProcessor(stage);

        //Según la plataforma en la que se esté corriendo
        switch (Gdx.app.getType()) {
            case Desktop:
                Gdx.graphics.setWindowedMode(640, 480);/*Se debe establecer por defecto la pantalla con las
        dimensiones 640fx480f cuando se va a cambiar de pantalla para no descuadrar la pantalla que se abre*/
                break;
            case Android:
                // Nada en principio
                break;
        }
        musica.setVolume(0.8f);
        musica.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Despues de la funcion anterior es necesario ejecutar esta para limpiar el buffer de bits

        stage.act();
        stage.draw();
        if (!musica.isPlaying() && juego.partida == null) {
            musica.play();
        }
        if (i >= 30) {
            btnfinalizar.setDisabled(false);
            btnfinalizar.setColor(Color.GREEN);
            btncarta1.setDisabled(true);
            btncarta2.setDisabled(true);
            btncarta3.setDisabled(true);
            btncarta4.setDisabled(true);
            btncarta5.setDisabled(true);
            btncarta6.setDisabled(true);
            info.setText("Selecciona cartas hasta llegar a las 30 \nque completan el mazo. Van  "
                    + i + " de 30");
        } else if (i > 0) {
            info.setText("Selecciona cartas hasta llegar a las 30 \nque completan el mazo. Van  "
                    + i + " de 30");
        }
    }

    @Override
    public void hide() {
        musica.setVolume(0);
        musica.stop();
        stage.dispose();
    }

    @Override
    public void dispose() {
        musica.setVolume(0);
        musica.stop();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("inf: ", "Alto:" + height + " Ancho:" + width);
        ancho = width;
        alto = height;
        camera.setToOrtho(false, ancho, alto);
        camera.update();
        camera.position.set(ancho / 2f, alto / 2f, 0);
        stage.getViewport().update(width, height, true);
    }

    /**
     *
     */
    public void instanciarBotones() {
        info = new Label("\nBorra tu actual mazo o selecciona\ncartas hasta llegar a las 30 que completan\nel mazo.\n" +
                "(Luego las robas en el mismo orden)\nVan  "
                + i + " de 30", skin);
        info.setPosition(ancho / 2f, (alto / 20f) * 16f);

        btnfinalizar = new TextButton("GUARDAR MAZO", skin);
        btnfinalizar.setSize(ancho / 5f, alto / 8f);
        btnfinalizar.setPosition(ancho / 9f, (alto / 20f) * 17f);
        btnfinalizar.setDisabled(true);
        btnfinalizar.setColor(Color.RED);
        btnfinalizar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.flush();//se guardan los cambios de manera persistente
                juego.setScreen(juego.menuPrincipal);
            }
        });

        btncarta1 = new TextButton("Guerrero r", skin);
        btncarta1.setSize(ancho / 5f, alto / 8f);
        btncarta1.setPosition(ancho / 5f, (alto / 20f) * 13f);
        btncarta1.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.putInteger("C" + i, 1);
                Gdx.app.log("anadido 1", "");
                i++;
            }
        });

        btncarta2 = new TextButton("Titan de Fuego", skin);
        btncarta2.setSize(ancho / 5f, alto / 8f);
        btncarta2.setPosition(ancho / 1.6f, (alto / 20f) * 13f);
        btncarta2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.putInteger("C" + i, 2);
                Gdx.app.log("anadido 2", "");
                i++;
            }
        });

        btncarta3 = new TextButton("Super At", skin);
        btncarta3.setSize(ancho / 5f, alto / 8f);
        btncarta3.setPosition(ancho / 5f, (alto / 20f) * 8f);
        btncarta3.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.putInteger("C" + i, 3);
                Gdx.app.log("anadido 3", "");
                i++;
            }
        });

        btncarta4 = new TextButton("Bombazo", skin);
        btncarta4.setSize(ancho / 5f, alto / 8f);
        btncarta4.setPosition(ancho / 1.6f, (alto / 20f) * 8f);
        btncarta4.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.putInteger("C" + i, 4);
                Gdx.app.log("anadido 4", "");
                i++;
            }
        });

        btncarta5 = new TextButton("Mina de oro", skin);
        btncarta5.setSize(ancho / 5f, alto / 8f);
        btncarta5.setPosition(ancho / 5f, (alto / 20f) * 3f);
        btncarta5.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.putInteger("C" + i, 5);
                Gdx.app.log("anadido 5", "");
                i++;
            }
        });

        btncarta6 = new TextButton("Energizante", skin);
        btncarta6.setSize(ancho / 5f, alto / 8f);
        btncarta6.setPosition(ancho / 1.6f, (alto / 20f) * 3f);
        btncarta6.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.mazoGuardado.putInteger("C" + i, 6);
                Gdx.app.log("anadido 6", "" + juego.mazoGuardado.getInteger("C" + i));
                i++;
            }
        });

        btnBorraryVolver = new TextButton("BORRAR MAZO Y JUGAR CON UNO ALEATORIO", skin);
        btnBorraryVolver.setSize(ancho / 1.5f, alto / 12f);
        btnBorraryVolver.setPosition(ancho / 8f, (alto / 20f));
        btnBorraryVolver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("anadido 6", "" + juego.mazoGuardado.getInteger("C" + i));
                for (int u = 0; u < 30; u++) {
                    try {
                        juego.mazoGuardado.remove("C" + u);
                    } catch (Exception e) {
                        Gdx.app.log("No existe la posicion " + u + " guardada", "");
                    }
                }
                switch (Gdx.app.getType()) {
                    case Desktop:
                        Gdx.graphics.setWindowedMode(640, 480);/*Se debe establecer por defecto la pantalla con las
                    dimensiones 640fx480f cuando se va a cambiar de pantalla para no descuadrar la pantalla que se abre*/
                        break;
                    case Android:
                        // Nada en principio
                        break;
                }
                //y volvemos al menu
                juego.setScreen(juego.menuPrincipal);
            }
        });

        //escalado de los campos en función del dispositivo
        switch (Gdx.app.getType()) {
            case Android:
                btnfinalizar.getLabel().setFontScale(2);
                btncarta1.getLabel().setFontScale(2);
                btncarta2.getLabel().setFontScale(2);
                btncarta3.getLabel().setFontScale(2);
                btncarta4.getLabel().setFontScale(2);
                btncarta5.getLabel().setFontScale(2);
                btncarta6.getLabel().setFontScale(2);
                btnBorraryVolver.getLabel().setFontScale(1.5f);
                info.setFontScale(1);
                break;
            case Desktop:
                btnfinalizar.getLabel().setFontScale(1);
                btncarta1.getLabel().setFontScale(1);
                btncarta2.getLabel().setFontScale(1);
                btncarta3.getLabel().setFontScale(1);
                btncarta4.getLabel().setFontScale(1);
                btncarta5.getLabel().setFontScale(1);
                btncarta6.getLabel().setFontScale(1);
                btnBorraryVolver.getLabel().setFontScale(1);
                info.setFontScale(0.7f);
                break;
        }
    }

    /**
     * Método que limpia el mazo. Usado al abrir el editor de mazos
     */
    public void borrarMazo() {
        try {
            for (int i = 0; i < 30; i++) {
                Gdx.app.log("borrando", "" + juego.mazoGuardado.getInteger("C" + i) + " i " + i);
                juego.mazoGuardado.remove("C" + i);
            }
        } catch (Exception e) {
            Gdx.app.log("fallo borrando", "" + juego.mazoGuardado.getInteger("C" + i));
        }
        juego.mazoGuardado.flush();//se guardan los cambios de manera persistente
    }
}
