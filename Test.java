import java.util.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.*;
import static java.lang.System.out;

public class Test {

    public static void main(String[] args) throws Throwable {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        URI uri = URI.create("jar:file:/home/alex/zipfs/pythonProject/test2.zip");

        try (FileSystem zipfs  = FileSystems.newFileSystem(uri, env)) {
            Path externalTxtFile = Paths.get("./A.txt");
            Path pathInZip = zipfs.getPath("test.txt");

            out.println(pathInZip);

            #Files.copy(externalTxtFile, pathInZip, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}