package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//игровой экран реалезует экран 
class GameScreen implements Screen {

    //screen
    private Camera camera;
//    видовой экран
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
//    фон
    private Texture[] backgrounds;
    private float backgroundHeight;

    //timing
//    смещение фона 
    private float[] backgroundOffsets = {0, 0, 0, 0};
//    максимальная Скорость Прокрутки фона
    private float backgroundMaxScrollingSpeed;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    GameScreen() {
//камера работа с проекциями 
    	camera = new OrthographicCamera();
//    	видовой экран
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        
//        фон имеет 4 изображение 
        backgrounds = new Texture[4];
        backgrounds[0] = new Texture("Starscape00.png");
        backgrounds[1] = new Texture("Starscape01.png");
        backgrounds[2] = new Texture("Starscape02.png");
        backgrounds[3] = new Texture("Starscape03.png");
//скорость прокрутки по высое 
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;
//    ресует
        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
//    	происходит рисование 
        batch.begin();

        //scrolling background
        renderBackground(deltaTime);

        batch.end();
        }
//        создать фон 
    private void renderBackground(float deltaTime) {
//смещение фона  время умноженое на максимально время прокрутки 
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],
                    0,
                    -backgroundOffsets[layer],
                    WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer],
                    0,
                    -backgroundOffsets[layer] + WORLD_HEIGHT,
                    WORLD_WIDTH, WORLD_HEIGHT);
        }
    }

    //маштабировать 
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
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
    public void show() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
