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

        String zip_path = args[0];
        String rand_files_dir_name = args[1];

        URI uri = URI.create("jar:file:" + zip_path);

        ArrayList<Integer> runtimes = new ArrayList<Integer>();

        try (FileSystem zipfs  = FileSystems.newFileSystem(uri, env)) {
            Stream<Path> file_stream = Files.list(zipfs.getPath(rand_files_dir_name));
            int file_count = file_stream.collect(Collectors.toList()).size();
            out.println("Files: " + Integer.toString(file_count));

            for(int i = 1; i <= file_count; i++) {
                long startTime = System.nanoTime();
                Path pathInZip = zipfs.getPath(rand_files_dir_name + "/" + Integer.toString(i) + "_rand.txt");

                byte[] allBytes = Files.readAllBytes(pathInZip);
                long endTime = System.nanoTime();

                int runtime = (int)(endTime - startTime)/1000000;
                runtimes.add(runtime);

                out.println(Integer.toString(i) + " -- " + Integer.toString(runtime) + "ms");
            }
        }

        double runtimeSum = 0;
        for(int i = 0; i < runtimes.size(); i++) {
            runtimeSum += runtimes.get(i);
        }

        out.print("Avg: " + Double.toString(runtimeSum / runtimes.size()));
    }

}