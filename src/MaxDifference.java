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
public class MaxDifference {

    static String data = Read();

    //static char[] dataArray = data.toCharArray();

    static String one = "ATAACTTCGTATA";
    static String two = "TATTGAAGCATAT";

    public static String Read() {
        Charset charset = Charset.forName("US-ASCII");
        Path path = FileSystems.getDefault().getPath("src", "EcoliGenome.txt");
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


    public static boolean possibleSite(String a, String b) {
        int difference = 0;
        for (int j = 0; j < 13; j++){
            if(a.charAt(j) != b.charAt(j)) {
                difference++;
                if (difference > 5) return false;
            }
        }
        return true;
    }

    public static int CheckSites() {
        int totalSites = 0;

        HashSet<String> checkList = new HashSet<>();
        int i = 0;
        while (i < data.length() - 12) {
            String sub = data.substring(i, i+13);
            if (checkList.contains(sub)) {
                totalSites++;
                i += 13;
            } else if (possibleSite(sub, one) || possibleSite(sub, two)) {
                totalSites++;
                checkList.add(sub);
                i += 13;
            } else {
                i++;
            }
        }
        return totalSites;
    }

    public static void main(String args[]) {
        System.out.println(CheckSites());
    }
}
