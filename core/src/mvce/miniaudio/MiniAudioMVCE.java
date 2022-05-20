package mvce.miniaudio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import games.rednblack.miniaudio.MASound;
import games.rednblack.miniaudio.MiniAudio;

public class MiniAudioMVCE extends ApplicationAdapter {
    SpriteBatch batch;
    Color imgColor = Color.WHITE;
    Texture img;

    MiniAudio miniAudio;
    MASound sound;
    Object androidContextAssets;

    boolean stressTest = true;

    public MiniAudioMVCE() {
    }

    public MiniAudioMVCE(Object androidContextAssets) {
        this.androidContextAssets = androidContextAssets;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        miniAudio = new MiniAudio();
        miniAudio.setupAndroid(androidContextAssets);
        sound = miniAudio.createSound("example.mp3");
        sound.setLooping(true);
        sound.play();

        // click left and right side of screen to start and stop miniAudio engine
        // not relevant if stress test is enabled
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    miniAudio.stopEngine();
                    imgColor = Color.BLUE;
                } else {
                    miniAudio.startEngine();
                    imgColor = Color.RED;
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.setColor(imgColor);
        batch.draw(img, 0, 0);
        batch.end();

        if (stressTest) {
            // works fine on desktop, but will eventually crash on android with MAResult -1
            miniAudio.stopEngine();
            miniAudio.startEngine();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        sound.dispose();
        miniAudio.dispose();
    }
}
