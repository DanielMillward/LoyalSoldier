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



public class MyGdxGame extends ApplicationAdapter {

	Sprite sprite;
	Texture img;
	World world;
	private OrthographicCamera camera;
	private Stage stage;
	private SpriteBatch batch;
<<<<<<< HEAD
	Touchpad touchpad;
	Touchpad.TouchpadStyle touchpadStyle;
	Skin touchpadSkin;
	Drawable touchBackground;
	Drawable touchKnob;
=======
	private Touchpad touchpad;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;
>>>>>>> parent of 892114c... adjust to Box2d coords + fiddle with trackpad
	private float accumulator = 0;

	private Stage UIstage;
	private SpriteBatch UIbatch;
	private Skin skin;
	private TextButton leftButton;
	private TextButton rightButton;

	MyActor actor = new MyActor();
	theGround ground = new theGround();

	Vector2 vel = MyActor.body.getLinearVelocity();
	Vector2 pos = MyActor.body.getPosition();
	int MAX_VELOCITY = 2;




	@Override
	public void create() {
<<<<<<< HEAD
		//the camera here doesn't care what the resolution is, this is not pixels (?). I think
		// everything relies on this, so it's all relative to this thing
		stage = new Stage(new StretchViewport(17, 10));
		UIstage = new Stage(new StretchViewport(170, 100));
=======
		stage = new Stage(new StretchViewport(640, 480));

>>>>>>> parent of 892114c... adjust to Box2d coords + fiddle with trackpad
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		UIbatch = new SpriteBatch();
		// We will use the default LibGdx logo for this example, but we need a
		//sprite since it's going to move


		// Create a physics world, the heart of the simulation.  The Vector
		//passed in is gravity
		world = new World(new Vector2(0, -98f), true);


<<<<<<< HEAD
		//

		//
		//following is touchpad code
		//todo: delete touchpad, use buttons instead
=======
		//Create a touchpad skin
>>>>>>> parent of 892114c... adjust to Box2d coords + fiddle with trackpad
		touchpadSkin = new Skin();
		//Set background image
		touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
		//Set knob image
		touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
		//Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		//Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		//Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		//Create new TouchPad with the created style
		touchpad = new Touchpad(10, touchpadStyle);
		//setBounds(x,y,width,height)
		touchpad.setBounds(15, 15, 200, 200);

<<<<<<< HEAD


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
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		leftButton = new TextButton("GO LEFT", textButtonStyle);
		leftButton.setPosition(5, 10); //** Button location **//
		leftButton.setHeight(15); //** Button Height **//
		leftButton.setWidth(20); //** Button Width **//
		leftButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("DFM", "Pressed"); //** Usually used to start Game, etc. **//
				if (vel.x > -MAX_VELOCITY) {
					MyActor.body.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
				}

				return true;
			}
		});

		rightButton = new TextButton("GO LEFT", textButtonStyle);
		rightButton.setPosition(5, 35); //** Button location **//
		rightButton.setHeight(15); //** Button Height **//
		rightButton.setWidth(20); //** Button Width **//
		rightButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("DFM", "Pressed"); //** Usually used to start Game, etc. **//
				if (vel.x < MAX_VELOCITY) {
					MyActor.body.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
				}

				return true;
			}
		});

		// after testing all the stuff, put touchpad below actor for drawing order
		stage.addActor(ground);
		stage.addActor(actor);

		UIstage.addActor(leftButton);
		UIstage.addActor(rightButton);


		Gdx.input.setInputProcessor(UIstage);
=======
		//Create a Stage and add TouchPad
		MyActor actor = new MyActor();
		theGround ground = new theGround();
		stage.addActor(touchpad);
		stage.addActor(actor);
		stage.addActor(ground);
		Gdx.input.setInputProcessor(stage);
>>>>>>> parent of 892114c... adjust to Box2d coords + fiddle with trackpad

		// makes a body with a fixture
		MyActor.makeBody(world, stage.getCamera());
		theGround.makeTheGround(world, stage.getCamera());


	}

	@Override
	public void render() {

		// Advance the world, by the amount of time that has elapsed since the
		//last frame
		// Generally in a real game, dont do this in the render loop, as you are
		//tying the physics
		// update rate to the frame rate, and vice versa


		// Now update the sprite position accordingly to it's now updated
		//Physics body
		//actor.setPosition(MyActor.body.getPosition().x, MyActor.body.getPosition().y);

		// You know the rest...
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		UIstage.draw();
		stage.draw();
<<<<<<< HEAD
		touchpad.setBounds(2, 1, 4, 4);
=======
>>>>>>> parent of 892114c... adjust to Box2d coords + fiddle with trackpad
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());

		// stepping physics
		doPhysicsStep(Gdx.graphics.getDeltaTime());

	}

	// this is code for the physics step
	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= 1/45f) {
			world.step(1/45f, 6, 2);
			accumulator -= 1/45f;
		}
	}

	@Override
	public void dispose() {
		img.dispose();
		world.dispose();
		theGround.groundBox.dispose();

	}

}