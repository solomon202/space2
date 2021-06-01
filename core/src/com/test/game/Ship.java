package com.test.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

 abstract class Ship {

    //характеристики судна
//	скорость движения
    float movementSpeed;  //единицы измерения 
//    щит
    int shield;

    //положение и размер
    float xPosition, yPosition; //lower-left corner
    float width, height;
//    Прямоугольная ограничивающая рамка;
    Rectangle boundingBox;
    
    
    //laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

//конструктор получает данные инцилиацию папраметров каробля и сылки наклас корабль и щит 
    public Ship(float xCentre, float yCentre,
            float width, float height,
            float movementSpeed, int shield,
            float laserWidth, float laserHeight, float laserMovementSpeed,
            float timeBetweenShots,
            TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
            TextureRegion laserTextureRegion) {
    this.movementSpeed = movementSpeed;
    this.shield = shield;

    this.xPosition = xCentre - width / 2;
    this.yPosition = yCentre - height / 2;
    this.width = width;
    this.height = height;
    this.boundingBox = new Rectangle(xPosition,yPosition,width,height);

    this.laserWidth = laserWidth;
    this.laserHeight = laserHeight;
    this.laserMovementSpeed = laserMovementSpeed;
    this.timeBetweenShots = timeBetweenShots;
    this.shipTextureRegion = shipTextureRegion;
    this.shieldTextureRegion = shieldTextureRegion;
    this.laserTextureRegion = laserTextureRegion;
}
    
    
    public void update(float deltaTime) {
    	boundingBox.set(xPosition,yPosition,width,height); 
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser() {
        return (timeSinceLastShot - timeBetweenShots >= 0);
        
    }
    
    
    public abstract Laser[] fireLasers();
    public boolean intersects(Rectangle otherRectangle) {
        return boundingBox.overlaps(otherRectangle);
    }
    
    
    
    public void hit(Laser laser) {
        if (shield > 0) {
            shield--;
        }
    }
    
//рисуем получаем ссылку длЯ метода 
    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if (shield > 0) {
            batch.draw(shieldTextureRegion, xPosition, yPosition, width, height);
        }
    }
}
