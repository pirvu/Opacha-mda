package yio.tro.opacha.teavm;

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.teavm.tooling.TeaVMTargetType;
import org.teavm.tooling.TeaVMTool;
import org.teavm.vm.TeaVMOptimizationLevel;

public class TeaVMBuilder {

    public static void main(String[] args) throws IOException {
        TeaBuildConfiguration config = new TeaBuildConfiguration();
        config.assetsPath.add(new AssetFileHandle("../assets"));
        config.assetFilter = (file, isDirectory, op) -> !file.endsWith(".DS_Store");
        config.webappPath = new File("build/dist").getCanonicalPath();
        config.targetType = TeaVMTargetType.JAVASCRIPT;
        config.htmlTitle = "Opacha-mda";
        TeaBuilder.config(config);

        TeaVMTool tool = new TeaVMTool();
        tool.setOptimizationLevel(TeaVMOptimizationLevel.ADVANCED);
        tool.setMainClass(TeaVMLauncher.class.getName());
        tool.setObfuscated(true);
        TeaBuilder.build(tool);

        // Replace the default page with one that handles mobile viewport and canvas scaling
        Files.copy(new File("webapp/index.html").toPath(),
                new File(config.webappPath, "webapp/index.html").toPath(),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
