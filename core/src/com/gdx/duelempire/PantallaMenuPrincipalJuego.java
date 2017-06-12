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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by juanjo on 03/05/2017.
 */

/**
 * Menú principal de la aplicación, que da la opción de acceder a las damás pantallas del juego
 */
public class PantallaMenuPrincipalJuego extends PantallaBaseJuego {
    private Music musica;
    //Variables que gestionan la vista de la pantalla
    private OrthographicCamera camera;
    private FitViewport viewp;
    //Textutas e imagenes que se usarán de fondo
    private Texture tfondo, tlogo;
    Image fondo, logo;
    //Botones
    private TextButton btnNuevaPartidaVScpu, btnNuevaPartidaVSplayer, btnEditorMazos;
    /*Variable que  contiene los datos y formatos necesarios para escribir y establecer gráficos para
    los labels y los botones de la interfaz*/
    private Skin skin;
    //Escenario que contiene y gestiona todos los componenetes de la pantalla
    private Stage stage;
    //Dimensiones del dispositivo
    private float ancho, alto;

    DuelEmpire_CardGame juego;

    /**
     * Constructor de la pantalla principal
     *
     * @param juego
     */
    public PantallaMenuPrincipalJuego(DuelEmpire_CardGame juego) {
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
        Gdx.app.log("Datos camara:", "Anchura " + ancho + " |Largura " + alto);
        tfondo = new Texture("backgrounds/fondoMenu.png");
        fondo = new Image(tfondo);
        fondo.setSize(ancho, alto);
        tlogo = new Texture("backgrounds/logoJuego.png");
        logo = new Image(tlogo);
        logo.setPosition(ancho - ancho / 1.1f, alto / 2f);
        logo.setSize(ancho / 1.2f, alto / 2f);
        instanciarBotones();
        camera.setToOrtho(false, ancho, alto);
        camera.update();
        camera.position.set(ancho / 2f, alto / 2f, 0);
        camera.update();
        viewp = new FitViewport(ancho, alto, camera);
        Gdx.app.log("Datos camara2:", "Anchura vp" + camera.viewportWidth + " |Largura vp" + camera.viewportHeight);
        stage = new Stage(viewp);
        stage.addActor(fondo);
        stage.addActor(logo);
        stage.addActor(btnNuevaPartidaVScpu);
        stage.addActor(btnNuevaPartidaVSplayer);
        stage.addActor(btnEditorMazos);
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
        //Se activa la recogida de datos por pantalla para el escenario
        Gdx.input.setInputProcessor(stage);
        //Gdx.graphics.setResizable(false);
        juego.partida = null;//se limpia la partida anterior al volver al menú
        musica.setVolume(0.8f);
        musica.play();
    }

    @Override
    public void hide() {
        musica.stop();
        stage.dispose();
    }

    @Override
    public void dispose() {
        musica.stop();
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Despues de la funcion anterior es necesario ejecutar esta para limpiar el buffer de bits

        stage.act();
        stage.draw();
        if (!musica.isPlaying() && juego.partida == null) {
            musica.play();
        }
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
        btnNuevaPartidaVScpu = new TextButton("Nueva Partida", skin);
        btnNuevaPartidaVScpu.setSize(ancho / 2f, alto / 8f);
        btnNuevaPartidaVScpu.setPosition(ancho / 4f, (alto / 20f) * 7f);
        btnNuevaPartidaVScpu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (Gdx.app.getType()) {
                    case Desktop:
                        Gdx.graphics.setWindowedMode(640, 480);/*Se debe establecer por defecto la pantalla con las
        dimensiones 640fx480f cuando se va a cambiar de pantalla para no descuadrar la pantalla que se abre*/
                        break;
                    case Android:
                        //
                        break;
                }
                juego.partida = new PantallaPartidaJuego(juego);
                juego.setScreen(juego.partida);
                musica.stop();
            }
        });

        btnNuevaPartidaVSplayer = new TextButton("Partida online", skin);
        btnNuevaPartidaVSplayer.setSize(ancho / 2f, alto / 8f);
        btnNuevaPartidaVSplayer.setDisabled(true);
        btnNuevaPartidaVSplayer.setColor(Color.DARK_GRAY);
        btnNuevaPartidaVSplayer.setPosition(ancho / 4f, (alto / 20f) * 4f);
        btnNuevaPartidaVSplayer.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        btnEditorMazos = new TextButton("Editor de mazos", skin);
        btnEditorMazos.setSize(ancho / 2f, alto / 8f);
        btnEditorMazos.setDisabled(true);
        btnEditorMazos.setColor(Color.DARK_GRAY);
        btnEditorMazos.setPosition(ancho / 4f, alto / 20f);
        btnEditorMazos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        //escalado de los campos en función del dispositivo
        switch (Gdx.app.getType()) {
            case Android:
                btnNuevaPartidaVScpu.getLabel().setFontScale(4);
                btnNuevaPartidaVSplayer.getLabel().setFontScale(4);
                btnEditorMazos.getLabel().setFontScale(4);
                break;
            case Desktop:
                btnNuevaPartidaVScpu.getLabel().setFontScale(2);
                btnNuevaPartidaVSplayer.getLabel().setFontScale(2);
                btnEditorMazos.getLabel().setFontScale(2);
                break;
        }
    }
}
