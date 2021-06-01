package com.test.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

class Laser {

	//положение и размеры
    float xPosition, yPosition;  
    float width, height;

    //laser physical characteristics
//    скорость движения
    float movementSpeed; //world units per second

    //graphics
//    изображение 
    TextureRegion textureRegion;
//получаем параметры в конструкторе 
    public Laser(float xCentre, float yBottom, float width, float height, float movementSpeed, TextureRegion textureRegion) {
        this.xPosition = xCentre - width/2;
        this.yPosition = yBottom;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }
// ресуем изображение фото позиция размер 
    public void draw(Batch batch) {
        batch.draw(textureRegion, xPosition, yPosition, width, height);
    }
    
    public Rectangle getBoundingBox() {
        return new Rectangle(xPosition, yPosition, width, height);
    
}
}
