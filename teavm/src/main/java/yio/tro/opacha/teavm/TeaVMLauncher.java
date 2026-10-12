package yio.tro.opacha.teavm;

import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import yio.tro.opacha.PlatformType;
import yio.tro.opacha.YioGdxGame;

public class TeaVMLauncher {

    public static void main(String[] args) {
        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");
        config.width = 0; // 0 = fill the browser window
        config.height = 0;
        config.preloadListener = assetLoader -> assetLoader.loadScript("freetype.js");

        YioGdxGame.platformType = PlatformType.web;
        new TeaApplication(new YioGdxGame(), config);
    }
}
