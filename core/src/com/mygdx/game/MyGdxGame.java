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
	Touchpad touchpad;
	Touchpad.TouchpadStyle touchpadStyle;
	Skin touchpadSkin;
	Drawable touchBackground;
	Drawable touchKnob;

	private float accumulator = 0;
	Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		//the camera here doesn't care what the resolution is, this is not pixels (?). I think
		// everything relies on this, so it's all relative to this thing
		stage = new Stage(new StretchViewport(17, 10));

		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		// We will use the default LibGdx logo for this example, but we need a
		//sprite since it's going to move


		// Create a physics world, the heart of the simulation.  The Vector
		//passed in is gravity
		world = new World(new Vector2(0, -98f), true);

		//following is touchpad code
		//todo: delete touchpad, use buttons instead
		touchpadSkin = new Skin();
		//Set background image
		touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
		//Set knob image
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

		MyActor actor = new MyActor();
		theGround ground = new theGround();




		// after testing all the stuff, put touchpad below actor for drawing order
		stage.addActor(ground);
		stage.addActor(actor);


		Gdx.input.setInputProcessor(stage);

		// makes a body with a fixture
		MyActor.makeBody(world, stage.getCamera());
		theGround.makeTheGround(world, stage.getCamera());

		debugRenderer = new Box2DDebugRenderer();
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
		stage.getBatch().draw(touchpad, 4, 4);
		touchpad.setBounds(2, 1, 4, 4);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		debugRenderer.render(world, stage.getCamera().combined);
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