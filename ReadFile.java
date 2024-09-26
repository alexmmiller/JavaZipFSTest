import java.util.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.*;
import static java.lang.System.out;
import java.util.stream.*;

public class ReadFile {

    public static void main(String[] args) throws Throwable {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        URI uri = URI.create("jar:file:/home/alex/zipfs/pythonProject/test2.zip");

        try (FileSystem zipfs  = FileSystems.newFileSystem(uri, env)) {
            Path pathInZip = zipfs.getPath("test.txt");

            Stream<String> fileStream = Files.lines(pathInZip);
            fileStream.forEach(s -> out.println(s));
        }
    }

}