package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.mygdx.game.MyGdxGame.direction;
import static com.mygdx.game.MyGdxGame.world;

/**
 * Created by Daniel Millward on 12/22/2017.
 */

public class bullet extends Actor{
static Body body;
static FixtureDef fixtureDef;
static PolygonShape groundBox;
    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

    Sprite sprite = new Sprite(new Texture(pixmap));
    int bulletDirection;


// Set our body's starting position in the world

// Create our body in the world using our body definition

        // Create a fixture definition to apply our shape to



    bullet(int direction){
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        bulletDirection = direction;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setBounds(MyGdxGame.actor.getX(), MyGdxGame.actor.getY(), 0.1f, 0.1f);
        sprite.draw(batch);
    }


}
