package com.test.game;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
// Вражеский корабль расширяет корабль
class EnemyShip extends Ship {
//получаем позицыю размер скорость щит лазер ширина высота скорость время Между Выстрелами
    public EnemyShip(float xCentre, float yCentre,
                     float width, float height,
                     float movementSpeed, int shield,
                     float laserWidth, float laserHeight,
                     float laserMovementSpeed, float timeBetweenShots,
//              корабль щит лазер        
                     TextureRegion shipTextureRegion,
                     TextureRegion shieldTextureRegion,
                     TextureRegion laserTextureRegion) {
//    	метод получает из класса выше 
        super(xCentre, yCentre, width, height, movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, shieldTextureRegion, laserTextureRegion);
    }

    @Override
//    метод класс лазер 
    public Laser[] fireLasers() {
//    	выделить память на 2 
        Laser[] laser = new Laser[2];
//       1 лазер  заполняем  позицыя по х и у размер скорость изображение 
        laser[0] = new Laser(xPosition + width * 0.18f, yPosition - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);
//        второй лазер со второго крыла 
        laser[1] = new Laser(xPosition + width * 0.82f, yPosition  - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);
//время С Момента Последнего Выстрела
        timeSinceLastShot = 0;

        return laser;
    }

    @Override
//    рисуем корабль 
    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
//        если щит больше 0 ресуем щит 
        if (shield > 0) {
            batch.draw(shieldTextureRegion, xPosition, yPosition-height*0.2f, width, height);
        }
    }
}