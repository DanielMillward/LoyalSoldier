package com.mygdx.game;
// testing
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;



public class MyGdxGame extends ApplicationAdapter {

	Sprite sprite;
	Texture img;
	World world;
	MyActor actor;
	private OrthographicCamera camera;
	private Stage stage;
	private SpriteBatch batch;
	private Touchpad touchpad;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private float accumulator = 0;

	@Override
	public void create() {
		stage = new Stage(new StretchViewport(640, 480));

		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		// We will use the default LibGdx logo for this example, but we need a
		//sprite since it's going to move


		// Create a physics world, the heart of the simulation.  The Vector
		//passed in is gravity
		world = new World(new Vector2(0, -98f), true);


		//Create a touchpad skin
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

		//Create a Stage and add TouchPad
		MyActor actor = new MyActor();
		theGround ground = new theGround();
		stage.addActor(touchpad);
		stage.addActor(actor);
		stage.addActor(ground);
		Gdx.input.setInputProcessor(stage);

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
		stage.draw();
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