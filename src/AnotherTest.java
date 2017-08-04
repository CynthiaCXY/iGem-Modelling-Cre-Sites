import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

/**
 * Created by xintongyu on 31/07/2017.
 */
public class AnotherTest{

    static String data = Read();

    static String sequence = "gaattc";

    public static String Read() {
        Charset charset = Charset.forName("US-ASCII");
        Path path = FileSystems.getDefault().getPath("src", "test.txt");
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return content.toString();
    }


    public static boolean possibleSite(String sub) {
        int difference = 0;
        for (int j = 0; j < 6; j++){
            if(sub.charAt(j) != sequence.charAt(j)) {
                difference++;
                if (difference > 1) return false;
            }
        }
        return true;
    }

    public static int CheckSites() {
        int totalSites = 0;

        HashSet<String> checkList = new HashSet<>();

        // counting overlay
        for (int i = 0; i < data.length() - 6; i++) {
            String sub = data.substring(i, i+6);
            //System.out.println(sub);
            if (checkList.contains(sub)) {
                totalSites++;
            } else if (possibleSite(sub)) {
                totalSites++;
                checkList.add(sub);
            }
        }
        return totalSites;
    }

    public static void main(String args[]) {
        System.out.println(CheckSites());
    }
}
