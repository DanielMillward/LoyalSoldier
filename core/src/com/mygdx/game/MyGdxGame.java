package com.mygdx.game;
// testing
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.MyActor.PLAYER_HEIGHT;
import static com.mygdx.game.MyActor.PLAYER_SPEED;
import static com.mygdx.game.MyActor.PLAYER_WIDTH;


public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	Sprite sprite;
	Texture img;
	static World world;
	private OrthographicCamera camera;
	static Stage stage;

	Touchpad touchpad;
	Touchpad.TouchpadStyle touchpadStyle;
	Skin touchpadSkin;
	Drawable touchBackground;
	Drawable touchKnob;
	private float accumulator = 0;
	Box2DDebugRenderer debugRenderer;

	private Stage UIstage;
	private SpriteBatch UIbatch;
	private Skin skin;
	private TextButton leftButton;
	private TextButton rightButton;
	static TextButton upButton;
	static TextButton shootButton;

	float myTimer = 0f;
	ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	ArrayList<Body> bodyList = new ArrayList<Body>();
	HashMap<Sprite, Body> doubleList = new HashMap<Sprite, Body>();

	Sprite bulletThing;
	bullet newBullet;


	int MAX_VELOCITY = 2;
	static int direction = 1;
	Body body;
	static int upVelocity = 0;
	public static final int gravity = -10;
	static int upSpeed = upVelocity + gravity;
	static float moving;
	theGround ground;
	static MyActor actor;
	public static ArrayList<theGround> wallSpriteList = new ArrayList<theGround>();
	public static ArrayList<Rectangle> wallList = new ArrayList<Rectangle>();
	public static ArrayList<bullet> bulletList = new ArrayList<bullet>();

int checkerInt;
long otherCheckerInt;

	@Override

	public void create() {
		//the camera here doesn't care what the resolution is, this is not pixels (?). I think
		// everything relies on this, so it's all relative to this thing
		batch = new SpriteBatch();
		stage = new Stage(new StretchViewport(17, 10));
		UIstage = new Stage(new StretchViewport(170, 100));
		Gdx.input.setInputProcessor(stage);

		UIbatch = new SpriteBatch();
		world = new World(new Vector2(0, -98f), true);
		world.setContactListener(new B2dContactListener());
		// We will use the default LibGdx logo for this example, but we need a
		//sprite since it's going to move


		ground = new theGround();
		actor = new MyActor();

		MyActor.makeBody(world, stage.getCamera());
		theGround.makeTheGround(world, stage.getCamera());


		final Vector2 vel = MyActor.body.getLinearVelocity();
		final Vector2 pos = MyActor.body.getPosition();
		// Create a physics world, the heart of the simulation.  The Vector
		//passed in is gravity


		//

		//
		//following is touchpad code
		//todo: delete touchpad, use buttons instead
		touchpadSkin = new Skin();

				touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
				touchpadSkin.add("touchKnob", new Texture("block.png"));
		//Create TouchPad Style
		touchpadStyle = new Touchpad.TouchpadStyle();
		//Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		//Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		//Create new TouchPad with the created style
		//must be in pixels, so dont change this probably
		touchpad = new Touchpad(4, touchpadStyle);


//

		//
// this is the textbutton skin
		//todo: move this to its own class
		skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		//textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		leftButton = new TextButton("GO LEFT", textButtonStyle);
		leftButton.setPosition(5, 10); //** Button location **//
		leftButton.setHeight(15); //** Button Height **//
		leftButton.setWidth(20); //** Button Width **//
		leftButton.getLabel().setFontScale(0.3f);
		/*leftButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("DFM", "Pressed"); /*//** Usually used to start Game, etc. **//*/
				if (vel.x > -MAX_VELOCITY) {
					MyActor.body.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
				}

				return true;
			}
		});*/

		rightButton = new TextButton("GO RIGHT", textButtonStyle);
		rightButton.setPosition(30, 10); //** Button location **//
		rightButton.setHeight(15); //** Button Height **//
		rightButton.setWidth(20); //** Button Width **//
		rightButton.getLabel().setFontScale(0.3f); //
		/*rightButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("DFM", "Pressed"); /*//** Usually used to start Game, etc. **//*/
				if (vel.x < MAX_VELOCITY) {
					MyActor.body.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
				}

				return true;
			}
		});*/

		upButton = new TextButton("GO UP", textButtonStyle);
		upButton.setPosition(110, 10); //** Button location **//
		upButton.setHeight(15); //** Button Height **//
		upButton.setWidth(20); //** Button Width **//
		upButton.getLabel().setFontScale(0.3f);

		shootButton = new TextButton("SHOOT", textButtonStyle);
		shootButton.setPosition(135, 10); //** Button location **//
		shootButton.setHeight(15); //** Button Height **//
		shootButton.setWidth(20); //** Button Width **//
		shootButton.getLabel().setFontScale(0.3f);

		// after testing all the stuff, put touchpad below actor for drawing order
		wallSpriteList.add(ground);
		stage.addActor(ground);
		stage.addActor(actor);
		//stage.addActor(bullet);

		UIstage.addActor(leftButton);
		UIstage.addActor(rightButton);
		UIstage.addActor(upButton);
		UIstage.addActor(shootButton);


		Gdx.input.setInputProcessor(UIstage);

		// makes a body with a fixture

		for (theGround wall : wallSpriteList) {
			Rectangle rectangle = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
			wallList.add(rectangle);
		}
	}

	public void jump() {
		upVelocity = 20;
	}

	@Override
	public void render() {
		moving = 0;
		// Advance the world, by the amount of time that has elapsed since the
		//last frame
		// Generally in a real game, don't do this in the render loop, as you are
		//tying the physics
		// update rate to the frame rate, and vice versa


		// Now update the sprite position accordingly to it's now updated
		//Physics body
		//actor.setPosition(MyActor.body.getPosition().x, MyActor.body.getPosition().y);

		// You know the rest...
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bullet newBullet;

		if (otherCheckerInt == 0){
			actor.setPosition(9, 9);
	}
	otherCheckerInt++;
		if (upButton.isPressed()) {
			jump();
		}
		if (leftButton.isPressed()) {
			moving = 0.02f * -1 * PLAYER_SPEED;
			direction = -1;
		}
		if (rightButton.isPressed()) {
			moving = 0.02f * PLAYER_SPEED;
			direction = 1;
		}

		stage.act(Gdx.graphics.getDeltaTime());

		batch.setProjectionMatrix(stage.getCamera().combined);

		if (shootButton.isPressed()) {

			if (myTimer > 1) {
				bullet aNewBullet = new bullet(direction);
				bulletList.add(aNewBullet);
				stage.addActor(aNewBullet);
			}

			myTimer = myTimer + Gdx.graphics.getDeltaTime();
		}

		if (checkerInt >= 60) {
			int check = 0;
			Rectangle ghost = new Rectangle(actor.getX() + MyGdxGame.moving, actor.getY() + upSpeed, PLAYER_WIDTH, PLAYER_HEIGHT);
			for (Rectangle wall : wallList) {
				if (!ghost.overlaps(wall)) {
					check = check + 1;
				}
			}
			if (check == wallList.size()) {
				actor.setX(actor.getX() + MyGdxGame.moving);
				actor.setY(actor.getY() + upSpeed);
			}
			check = 0;
			moving = 0;
			ghost = null;

			for (bullet bullet : bulletList) {
				for (Rectangle wall : wallList) {
					if (wall.contains(bullet.getX() + bullet.bulletDirection, bullet.getY())) {
						bullet.remove();
					} else {
						bullet.setX(bullet.getX() + bullet.bulletDirection);
					}
				}
			}
			checkerInt = 0;
		}
		batch.begin();
		stage.draw();
		UIstage.draw();
		batch.end();
		if (myTimer > 1) {
			System.out.println("DFM" + actor.getX() + " , " + actor.getY());
			myTimer = 0;
		}
		myTimer = myTimer + Gdx.graphics.getDeltaTime();
		moving = 0;
		checkerInt++;
	}


	// todo: find out what else to dispose of
	@Override
	public void dispose() {
//		img.dispose();
		world.dispose();
		theGround.groundBox.dispose();

	}

}