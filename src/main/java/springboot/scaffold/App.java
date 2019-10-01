package springboot.scaffold;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class App {

    private static final String OUTPUT_DIR = ".\\src\\main\\java";

    private static final String PACKAGE_CONTROLLER = "\\app\\controller\\";
    private static final String PACKAGE_SERVICE = "\\domain\\service\\";
    private static final String PACKAGE_REPOSITORY = "\\domain\\repository\\";
    private static final String PACKAGE_MODEL = "\\domain\\model\\";

    private static VelocityContext context;

    private static Resource resource;

    static {
        Velocity.setProperty("resource.loaders", "class");
        Velocity.setProperty("resource.loader.class.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
    }

    public static void main(String[] args) {
        try {

            resource = new Resource(args);

            context = new VelocityContext();
            context.put("resource", resource);

            String baseDir = OUTPUT_DIR + File.separator
                    + resource.getPackageName().replace(".", File.separator);

            createFile("model", baseDir + PACKAGE_MODEL, resource.getResourceClass());
            createFile("repository", baseDir + PACKAGE_REPOSITORY,
                    resource.getResourceClass() + "Repository");
            createFile("service", baseDir + PACKAGE_SERVICE,
                    resource.getResourceClass() + "Service");
            createFile("controller", baseDir + PACKAGE_CONTROLLER,
                    resource.getResourceClass() + "Controller");

        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
            System.out.println("ex）-p jp.co.sample -c User[id:Long,name:String]");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ファイルを作成します。すでに存在する場合は作成しません。
     * 
     * @param vm        テンプレートファイル名
     * @param output    出力先
     * @param className クラス名
     * @throws IOException
     */
    private static void createFile(String vm, String output, String className) throws IOException {

        Files.createDirectories(Paths.get(output));

        String filePath = output + File.separator + className + ".java";

        if (Files.exists(Paths.get(filePath))) {
            return;
        }

        try (FileWriter fw = new FileWriter(filePath)) {
            Velocity.getTemplate("templates/" + vm + ".vm", "UTF-8").merge(context, fw);
            fw.flush();
        }
    }
}
