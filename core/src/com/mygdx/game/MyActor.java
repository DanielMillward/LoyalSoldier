package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.mygdx.game.MyGdxGame.upSpeed;


/**
 * Created by Daniel on 12/17/2017.
 */

public class MyActor extends Actor {
    static BodyDef bodyDef;
    static FixtureDef fixtureDef;
    static Body body;
    static PolygonShape groundBox;
    public static final int PLAYER_SPEED = 2;
    public static final int PLAYER_WIDTH = 2;
    public static final int PLAYER_HEIGHT = 2;
    Sprite sprite = new Sprite(new Texture(Gdx.files.internal("Player.png")), 9, 9, PLAYER_WIDTH, PLAYER_HEIGHT);

    public MyActor() {

    }

    public static void makeBody(World world, Camera camera) {

        bodyDef = new BodyDef();
        bodyDef.type = DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(9, 9);
// Create our body in the world using our body definition
        body = world.createBody(bodyDef);
        // Create a fixture definition to apply our shape to
        fixtureDef = new FixtureDef();
        groundBox = new PolygonShape();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.35f;
        fixtureDef.restitution = 0.1f; // Make it bounce a little bit
// Create a polygon shape
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(1.0f, 1.0f);
// Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        batch.draw(texture,body.getPosition().x, body.getPosition().y);

        sprite.setBounds(sprite.getX(), sprite.getY(), PLAYER_WIDTH, PLAYER_HEIGHT);
/*        for (int i = 0; i <= 8; i++) {
            int check = 0;
            Rectangle ghost = new Rectangle(sprite.getX() + MyGdxGame.moving, sprite.getY() + upSpeed, PLAYER_WIDTH, PLAYER_HEIGHT);
            for (Rectangle wall : MyGdxGame.wallList) {
                if (!ghost.overlaps(wall)) {
                    check = check + 1;
                }
            }
            if (check == MyGdxGame.wallList.size()) {
                sprite.setX(sprite.getX() + MyGdxGame.moving);
                sprite.setY(sprite.getY() + upSpeed);
            }
            check = 0;
            MyGdxGame.moving = 0;
            ghost = null;
        }*/


        // Set origin center for the sprite to guarantee proper rotation with physicsBody.
        sprite.draw(batch);
//        sprite.setPosition(body.getPosition().x - 15, body.getPosition().y - 25);
    }
}
