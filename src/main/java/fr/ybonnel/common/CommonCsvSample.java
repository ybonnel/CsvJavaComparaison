/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     ybonnel - initial API and implementation
 */
package fr.ybonnel.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ybonnel
 */
public abstract class CommonCsvSample {


    public static final int NB_ITER = 5;

    public static InputStream getCsvFile() {
        return CommonCsvSample.class.getResourceAsStream("/dogs.csv");
    }
    public static InputStream getComplexCsvFile() {
        return CommonCsvSample.class.getResourceAsStream("/dogsComplex.csv");
    }

    private List<Dog> currentDogs;

    public abstract List<Dog> getDogs(InputStream stream) throws IOException;

    private long readDogs(InputStream stream, boolean display) throws IOException {
        long startTime = System.nanoTime();

        currentDogs = getDogs(stream);
        long endTime = System.nanoTime();
        if (display) {
            for (Dog dog : currentDogs) {
                System.out.println(dog);
            }
        }

        return (endTime - startTime)/1000;
    }

    public long readDogs() throws IOException {
        return readDogs(getCsvFile(), true);
    }


    public long readComplexDogs() throws IOException {
        return readDogs(getComplexCsvFile(), true);
    }


    public abstract void writeFile(List<Dog> dogs, File file) throws IOException;

    public long writeDogs() throws IOException {
        long startTime = System.nanoTime();

        File file = File.createTempFile("dogs", "csv");
        writeFile(currentDogs, file);

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public abstract void readObjetCsv(InputStream stream) throws IOException;

    public long benchIter(File file) throws IOException {
        InputStream stream = new FileInputStream(file);
        long startTime = System.currentTimeMillis();
        readObjetCsv(stream);
        long elapsedTime = (System.currentTimeMillis() - startTime);
        return elapsedTime;
    }

    public void benchMoyen() throws InterruptedException, IOException {

        // Attention il faut lancer GenerationFichierCsv.main avant.

        Thread.sleep(30000);
        gestionMemoire();
        Thread.sleep(30000);
        long sum = 0;
        long min = Long.MAX_VALUE;
        long max = 0;
        for (int count = 1; count <= NB_ITER; count++) {
            long time = benchIter(new File("fichierMoyen.csv"));
            System.out.println("Temps de l'itération : " + time + "ms");
            gestionMemoire();
            sum += time;
            if (min > time) {
                min = time;
            }
            if (max < time) {
                max = time;
            }
            Thread.sleep(30000);
        }
        long moyenne = sum / NB_ITER;

        System.out.println("Bench moyen : ");
        System.out.println("\tminimum : " + min + "ms");
        System.out.println("\tmaximum : " + max + "ms");
        System.out.println("\tmoyenne : " + moyenne + "ms");
    }

    public static void gestionMemoire() {
        // Mémoire totale allouée
        long totalMemory = Runtime.getRuntime().totalMemory();
        // Mémoire utilisée
        long currentMemory = totalMemory - Runtime.getRuntime().freeMemory();
        System.out.println("Mémoire avant gc : " + (currentMemory / 1024) + "ko/" + (totalMemory / 1024) + "ko");
        System.gc();
        // Mémoire totale allouée
        totalMemory = Runtime.getRuntime().totalMemory();
        // Mémoire utilisée
        currentMemory = totalMemory - Runtime.getRuntime().freeMemory();
        System.out.println("Mémoire après gc : " + (currentMemory / 1024) + "ko/" + (totalMemory / 1024) + "ko");
    }
}
