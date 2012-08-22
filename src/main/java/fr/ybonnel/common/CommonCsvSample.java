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

import fr.ybonnel.beanfiles.BeanFilesCsvSample;
import fr.ybonnel.csvengine.CsvEngineSample;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import fr.ybonnel.jcsv.JCsvSample;
import fr.ybonnel.jsefa.JSefaSample;
import fr.ybonnel.supercsv.SuperCsvSample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
    public static InputStream getRequiredCsvFile() {
        return CommonCsvSample.class.getResourceAsStream("/dogsRequired.csv");
    }
    public static InputStream getRaceCsvFile() {
        return CommonCsvSample.class.getResourceAsStream("/dogsRace.csv");
    }

    private List<Dog> currentDogs;

    public abstract List<Dog> getDogs(InputStream stream) throws IOException, CsvErrorsExceededException;

    private long readDogs(InputStream stream, boolean display) throws IOException, CsvErrorsExceededException {
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

    public long readDogs() throws IOException, CsvErrorsExceededException {
        return readDogs(getCsvFile(), true);
    }


    public long readComplexDogs() throws IOException, CsvErrorsExceededException {
        return readDogs(getComplexCsvFile(), true);
    }


    public abstract void writeFile(List<Dog> dogs, File file) throws IOException;

    public long writeDogs() throws IOException {
        long startTime = System.nanoTime();

        File file = File.createTempFile("dogs", "csv");
        writeFile(currentDogs, file);

        long endTime = System.nanoTime();

        System.out.println("Contenu du fichier");
        BufferedReader bufReader = new BufferedReader(new FileReader(file));
        String ligne;
        while ((ligne =bufReader.readLine()) != null) {
            System.out.println(ligne);
        }

        return endTime - startTime;
    }

    public abstract void readObjetCsv(InputStream stream, boolean display) throws IOException, CsvErrorsExceededException;

    public long benchIter(File file) throws IOException, CsvErrorsExceededException {
        InputStream stream = new FileInputStream(file);
        long startTime = System.currentTimeMillis();
        readObjetCsv(stream, false);
        long elapsedTime = (System.currentTimeMillis() - startTime);
        return elapsedTime;
    }

    public void bench(String file) throws InterruptedException, IOException, CsvErrorsExceededException {

        // Attention il faut lancer GenerationFichierCsv.main avant.

        Thread.sleep(1000);
        gestionMemoire();
        Thread.sleep(1000);
        long sum = 0;
        long min = Long.MAX_VALUE;
        long max = 0;
        long time = benchIter(new File(file));
        long sumConsoMemoire = 0;
        gestionMemoire();
        for (int count = 1; count <= NB_ITER; count++) {
            time = benchIter(new File(file));
            sumConsoMemoire += gestionMemoire();
            sum += time;
            if (min > time) {
                min = time;
            }
            if (max < time) {
                max = time;
            }
            Thread.sleep(1000);
        }
        long moyenne = sum / NB_ITER;
        long consoMoyenne = sumConsoMemoire / NB_ITER / 1024 / 1024;

        System.out.println("Bench " + file + " : ");
        System.out.println("\tminimum : " + min + "ms");
        System.out.println("\tmaximum : " + max + "ms");
        System.out.println("\tmoyenne : " + moyenne + "ms");
        System.out.println("\tConsomation mémoire moyenne : " + consoMoyenne + "Mo");
    }

    public void benchMoyen() throws InterruptedException, IOException, CsvErrorsExceededException {
        // Attention il faut lancer GenerationFichierCsv.main avant.
        bench("fichierMoyen.csv");
    }

    public void benchGros() throws InterruptedException, IOException, CsvErrorsExceededException {
        // Attention il faut lancer GenerationFichierCsv.main avant.
        bench("fichierGros.csv");
    }

    public static long gestionMemoire() {
        // Mémoire totale allouée
        long totalMemory = Runtime.getRuntime().totalMemory();
        // Mémoire utilisée
        long oldCurrentMemory = totalMemory - Runtime.getRuntime().freeMemory();
        System.gc();
        // Mémoire totale allouée
        totalMemory = Runtime.getRuntime().totalMemory();
        // Mémoire utilisée
        long currentMemory = totalMemory - Runtime.getRuntime().freeMemory();
        return oldCurrentMemory - currentMemory;

    }

    public abstract List<DogValid> readDogsValid(InputStream stream) throws IOException, CsvErrorsExceededException;

    public void validateCsv() throws IOException, CsvErrorsExceededException {
        readDogsValid(getCsvFile());
        try {
            readDogsValid(getRequiredCsvFile());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            readDogsValid(getRaceCsvFile());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws CsvErrorsExceededException, IOException, InterruptedException {
        System.out.println("Bench de BeanFiles");
        BeanFilesCsvSample beanFilesCsvSample = new BeanFilesCsvSample();
        beanFilesCsvSample.readObjetCsv(CommonCsvSample.class.getResourceAsStream("/testBench.csv"), true);
        beanFilesCsvSample.benchMoyen();
        beanFilesCsvSample.benchGros();

        System.out.println("Bench de JCsv");
        JCsvSample jCsvSample = new JCsvSample();
        jCsvSample.readObjetCsv(CommonCsvSample.class.getResourceAsStream("/testBench.csv"), true);
        jCsvSample.benchMoyen();
        jCsvSample.benchGros();

        System.out.println("Bench de Jsefa");
        JSefaSample jSefaSample = new JSefaSample();
        jSefaSample.readObjetCsv(CommonCsvSample.class.getResourceAsStream("/testBench.csv"), true);
        jSefaSample.benchMoyen();
        jSefaSample.benchGros();

        System.out.println("Bench de SuperCsv");
        SuperCsvSample superCsvSample = new SuperCsvSample();
        superCsvSample.readObjetCsv(CommonCsvSample.class.getResourceAsStream("/testBench.csv"), true);
        superCsvSample.benchMoyen();
        superCsvSample.benchGros();

        System.out.println("Bench de CsvEngine");
        CsvEngineSample csvEngineSample = new CsvEngineSample();
        csvEngineSample.readObjetCsv(CommonCsvSample.class.getResourceAsStream("/testBench.csv"), true);
        csvEngineSample.benchMoyen();
        csvEngineSample.benchGros();
    }
}
