package yio.tro.opacha.teavm;

import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import org.teavm.jso.JSBody;
import yio.tro.opacha.PlatformType;
import yio.tro.opacha.YioGdxGame;

public class TeaVMLauncher {

    // The game is designed for portrait phone screens. On wider windows (desktop, tablets
    // in landscape) the canvas is limited to this width/height ratio and centered.
    private static final double MAX_ASPECT_RATIO = 0.6;
    private static final double MAX_PIXEL_RATIO = 3;


    public static void main(String[] args) {
        double windowWidth = getWindowWidth();
        double windowHeight = getWindowHeight();
        double cssHeight = windowHeight;
        double cssWidth = Math.min(windowWidth, windowHeight * MAX_ASPECT_RATIO);
        double pixelRatio = Math.max(1, Math.min(MAX_PIXEL_RATIO, getDevicePixelRatio()));

        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");
        // Game layout is computed once on startup, so it gets a fixed resolution.
        // index.html scales the canvas with CSS to keep it fitted to the window.
        config.width = (int) Math.round(cssWidth * pixelRatio);
        config.height = (int) Math.round(cssHeight * pixelRatio);
        config.antialiasing = true;
        config.preloadListener = assetLoader -> assetLoader.loadScript("freetype.js");

        YioGdxGame.platformType = PlatformType.web;
        new TeaApplication(new YioGdxGame(), config);
    }


    @JSBody(script = "return window.innerWidth;")
    private static native double getWindowWidth();


    @JSBody(script = "return window.innerHeight;")
    private static native double getWindowHeight();


    @JSBody(script = "return window.devicePixelRatio || 1;")
    private static native double getDevicePixelRatio();
}
