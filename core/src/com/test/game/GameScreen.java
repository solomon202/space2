package com.test.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
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
//    выделяем память для фона чтобы можно было пробежатся по классам 
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
//    создаем лист лазер 
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;

    GameScreen() {
//камера работа с проекциями 
    	camera = new OrthographicCamera();
//    	видовой экран
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        
        
      //настройка атласа текстур
//        выделяем память для текстур 
        textureAtlas = new TextureAtlas("images.atlas");
        
       //фон имеет 4 изображение 
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
        //set up game objects
//        передаем в констркутор 
        playerShip = new PlayerShip(WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10,
                2, 3,
                0.4f, 4, 45, 0.5f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);

        enemyShip = new EnemyShip(WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 4,
                10, 10,
                2, 1,
                0.3f, 5, 50, 0.8f,
                enemyShipTextureRegion, enemyShieldTextureRegion ,enemyLaserTextureRegion);
//создали лазеры 
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        
//    ресует
        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
//    	происходит рисование 
        batch.begin();
        
        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        //scrolling background
        renderBackground(deltaTime);
        //вражеские корабли
        enemyShip.draw(batch);
        //корабль игрока
        playerShip.draw(batch);
      //лазеры
      //создание новых лазеров
      //лазеры игроков
        if (playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLasers();
            for (Laser laser: lasers) {
                playerLaserList.add(laser);
            }
        }
      //вражеские лазеры
        if (enemyShip.canFireLaser()) {
            Laser[] lasers = enemyShip.fireLasers();
            for (Laser laser: lasers) {
                enemyLaserList.add(laser);
            }
        }

      //нарисуйте лазеры
      //удаление старых лазеров
//        вставляем в переменную сылку на метод  метод 
//        список лазеров
        ListIterator<Laser> iterator = playerLaserList.listIterator();
//        Этот метод добавляет элемент в коллекцию на следующую позицию после элемента, полученного с помощью метода next(). boolean hasNext() 
//        Этот метод проверяет, есть ли в коллекции следующий элемент.
//        hasNext(): возвращает true, если в коллекции имеется следующий элемент, иначе возвращает false
        while(iterator.hasNext()) {
//        	next(): возвращает текущий элемент и переходит к следующему, если такого нет, то генерируется исключение NoSuchElementException
            Laser laser = iterator.next();
//            рисует лазер 
            laser.draw(batch);
//            скорость лазера у + время  на умноженую частоту кадра 
            // двигаем лазер 
            laser.yPosition += laser.movementSpeed*deltaTime;
           // если высота по у больше 
            if (laser.yPosition > WORLD_HEIGHT) {
           //удаляем  итератор 
            	iterator.remove();
            }
        }
//        лазер противника 
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition -= laser.movementSpeed*deltaTime;
            if (laser.yPosition + laser.height < 0) {
                iterator.remove();
            }
        }

        //explosions

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
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer],
                    WORLD_WIDTH, backgroundHeight);
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
