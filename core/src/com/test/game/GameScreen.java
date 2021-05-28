package com.test.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
//    текстура 
    private TextureAtlas textureAtlas;
//    фон
//    часть тестуры фоновые области текстур
    private TextureRegion[] backgrounds;
//    высота фона в мировых единицах измерения
    private float backgroundHeight;
    
    
//    Регион текстур Регион текстур игроков, регион текстур игроков, регион текстур игроков, регион текстур врагов,
    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
    enemyShipTextureRegion, enemyShieldTextureRegion,
    playerLaserTextureRegion, enemyLaserTextureRegion;

    //timing
//    смещение фона 
    private float[] backgroundOffsets = {0, 0, 0, 0};
//    максимальная Скорость Прокрутки фона
    private float backgroundMaxScrollingSpeed;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    
    
    
    private Ship playerShip;
    private Ship enemyShip;

    GameScreen() {
//камера работа с проекциями 
    	camera = new OrthographicCamera();
//    	видовой экран
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        
        
      //настройка атласа текстур
        textureAtlas = new TextureAtlas("images.atlas");
        
//        фон имеет 4 изображение 
      //настройка фона
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

//скорость прокрутки по высое 
        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;
        
        
        //инициализация областей текстуры
        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_blue");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true);

        playerLaserTextureRegion= textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion= textureAtlas.findRegion("laserRed03");
        
        //настройка игровых объектов
        playerShip = new Ship(2, 3, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT/4,
                playerShipTextureRegion, playerShieldTextureRegion);
        enemyShip = new Ship(2, 1, 10, 10,
                WORLD_WIDTH/2, WORLD_HEIGHT*3/4,
               enemyShipTextureRegion, enemyShieldTextureRegion);
        
//    ресует
        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
//    	происходит рисование 
        batch.begin();

        //scrolling background
        renderBackground(deltaTime);
        //вражеские корабли
        enemyShip.draw(batch);
        //корабль игрока
        playerShip.draw(batch);

        batch.end();
        }
//        создать фон 
    private void renderBackground(float deltaTime) {
//смещение фона  время умноженое на максимально время прокрутки делим на время 
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed /8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed /4 ;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed /2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer нарисуйте каждый фоновый слой
//        пробежать 
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
//        	если высота меньше 
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
//            метод где происходит отрисовка кртинки по номеру расположение 
//       минус  смещения фона по высоте 
            batch.draw(backgrounds[layer],
                  0,
                    -backgroundOffsets[layer],
                    WORLD_WIDTH, WORLD_HEIGHT);
//            следующая 
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
