import java.util.*;
import java.util.stream.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.*;
import static java.lang.System.out;

public class Test {

    public static void main(String[] args) throws Throwable {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        String zip_path = args[0];
        String rand_files_dir_name = args[1];

        Stream<Path> file_stream = Files.list(Paths.get(rand_files_dir_name));
        int file_count = file_stream.collect(Collectors.toList()).size();

        ArrayList<Integer> runtimes = new ArrayList<Integer>();

        URI uri = URI.create("jar:file:" + zip_path);
        boolean deleted = Files.deleteIfExists(Paths.get(zip_path));
        out.println(deleted);

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Path rand_dir = zipfs.getPath(rand_files_dir_name);
            Files.createDirectory(rand_dir);

            for(int i = 1; i <= file_count; i++) {
                long startTime = System.nanoTime();

                String rand_file = rand_files_dir_name + "/" + Integer.toString(i) + "_rand.txt";

                Path externalTxtFile = Paths.get(rand_file);
                Path pathInZip = zipfs.getPath(rand_file);
                Files.copy(externalTxtFile, pathInZip, StandardCopyOption.REPLACE_EXISTING);

                long endTime = System.nanoTime();

                int runtime = (int)(endTime - startTime)/1000000;

                out.println(Integer.toString(i) + " -- " + Integer.toString(runtime) + "ms");

                runtimes.add(runtime);
            }
        }

        double runtimeSum = 0;
        for(int i = 0; i < runtimes.size(); i++) {
            runtimeSum += runtimes.get(i);
        }

        out.print("Avg: " + Double.toString(runtimeSum / runtimes.size()));
    }
}