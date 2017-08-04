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
public class Test{

    static String data = Read();

    static String front = "ttgaca";
    static String end = "tataat";

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
        int differenceOne = 0;
        int differenceTwo = 0;
        for (int j = 0; j < 6; j++){
            if(sub.charAt(j) != front.charAt(j)) {
                differenceOne++;
                if (differenceOne > 3) return false;
            }
        }
        for (int j = 0; j < 6; j++){
            if(sub.charAt(sub.length()-6+j) != end.charAt(j)) {
                differenceTwo++;
                if (differenceTwo > 3) return false;
            }
        }
        return true;
    }

    public static int CheckSites() {
        int totalSites = 0;

        HashSet<String> checkList = new HashSet<>();

        // not counting overlay
        int i = 0;
        while (i < data.length() - 30) {
            if (possibleSite(data.substring(i, i + 27))) {
                //System.out.println(data.substring(i, i+28));
                totalSites++;
                i += 27;
            } else if (possibleSite(data.substring(i, i + 28))) {
                //System.out.println(data.substring(i, i+28));
                totalSites++;
                i += 28;
            } else if (possibleSite(data.substring(i, i + 29))) {
                //System.out.println(data.substring(i, i+29));
                totalSites++;
                i += 29;
            } else if (possibleSite(data.substring(i, i + 30))) {
                //System.out.println(data.substring(i, i+30));
                totalSites++;
                i += 30;
            } else if (possibleSite(data.substring(i, i + 31))) {
                //System.out.println(data.substring(i, i+30));
                totalSites++;
                i += 31;
            } else i++;
        }

        // counting overlay
//        for (int i = 0; i < data.length() - 30; i++) {
//            for (int k = 27; k < 32; k++) {
//                String sub = data.substring(i, i+k);
//                //System.out.println(sub);
//                if (checkList.contains(sub)) {
//                    totalSites++;
//                    //i += k;
//                } else if (possibleSite(sub)) {
//                    totalSites++;
//                    checkList.add(sub);
//                    //i += k;
//                }
//            }
//        }


        return totalSites;
    }

    public static void main(String args[]) {
        System.out.println(CheckSites());
    }
}
