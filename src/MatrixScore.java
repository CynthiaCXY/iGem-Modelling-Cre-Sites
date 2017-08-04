import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by xintongyu on 03/08/2017.
 */
public class MatrixScore {

    static String data = ReadGenome();

    static int[][] frequencyCount = ReadSequences();
    static double[][] scoreMatrix = CalculateScoreMatrix();

    public static int[][] ReadSequences() {
        int[][] result = new int[4][13];
        Charset charset = Charset.forName("US-ASCII");
        Path path = FileSystems.getDefault().getPath("src", "CreBindingSite.txt");
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < 13; i ++) {
                    if (line.charAt(i) == 'A') {
                        result[0][i]++;
                    } else if (line.charAt(i) == 'C') {
                        result[1][i]++;
                    } else if (line.charAt(i) == 'T') {
                        result[2][i]++;
                    } else {
                        result[3][i]++;
                    }
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return result;
    }


    public static String ReadGenome() {
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

    public static double[][] CalculateScoreMatrix() {
        double[][] result = new double[4][14];
        for (int i = 0; i < 13; i++) {
            int sum = frequencyCount[0][i] + frequencyCount[1][i] + frequencyCount[2][i] + frequencyCount[3][i];
            for (int j = 0; j < 4; j++){
                result[j][i] = (double) frequencyCount[j][i] / sum;
            }
        }
        return result;
    }

    public static double CalculateSequenceScore(String s) {
        double score = 0;
        for (int i = 0; i < 13; i++) {
            if (s.charAt(i) == 'A') {
                score += scoreMatrix[0][i];
            } else if (s.charAt(i) == 'C') {
                score += scoreMatrix[1][i];
            } else if (s.charAt(i) == 'T') {
                score += scoreMatrix[2][i];
            } else {
                score += scoreMatrix[3][i];
            }
        }
        return score;
    }


    public static double[] ScanDNA(double threshold) {
        double[] figures = new double[4];

        double maxScore = (double) Integer.MIN_VALUE;
        double minScore = (double) Integer.MAX_VALUE;
        double sum = 0;
        double count = 0;

        int previousIndex = -13;

        for (int i = 0; i < data.length() - 12; i++) {
            //for (int i = 0; i < 10; i++) {

            String sub = data.substring(i, i+13);
            //System.out.println(sub);
            double score = CalculateSequenceScore(sub);
            //System.out.println(score);
            sum += score;
            if (score > maxScore) maxScore = score;
            if (score < minScore) minScore = score;
            if (score > threshold && (i - previousIndex) > 12) {
                count++;
                previousIndex = i;
            }
        }

        figures[0] = maxScore;
        figures[1] = minScore;
        figures[2] = sum/(data.length() - 12);
        figures[3] = count;
        return figures;
    }

    public static void main(String[] args) {

        double[] output = ScanDNA(6);

        System.out.println("Max score: " + output[0]);
        System.out.println("Min score: " + output[1]);
        System.out.println("Average score: " + output[2]);
        System.out.println("13bp above 6: " + output[3]);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(scoreMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
