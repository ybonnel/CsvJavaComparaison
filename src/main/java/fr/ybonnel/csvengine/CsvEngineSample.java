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
package fr.ybonnel.csvengine;

import fr.ybonnel.common.CommonCsvSample;
import fr.ybonnel.common.Dog;
import fr.ybonnel.common.DogValid;
import fr.ybonnel.common.ObjetCsv;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import fr.ybonnel.csvengine.model.InsertObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author ybonnel
 */
public class CsvEngineSample extends CommonCsvSample {
    @Override
    public List<Dog> getDogs(InputStream stream) throws IOException, CsvErrorsExceededException {
        CsvEngine engine = new CsvEngine(Dog.class);
        return engine.parseInputStream(stream, Dog.class).getObjects();
    }

    @Override
    public void writeFile(List<Dog> dogs, File file) throws IOException {
        CsvEngine engine = new CsvEngine(Dog.class);
        engine.writeFile(new FileWriter(file), dogs, Dog.class);
    }

    CsvEngine engine = new CsvEngine(ObjetCsv.class);

    @Override
    public void readObjetCsv(InputStream stream, final boolean display) throws IOException, CsvErrorsExceededException {

        engine.parseFileAndInsert(new InputStreamReader(stream), ObjetCsv.class, new InsertObject<ObjetCsv>() {
            @Override
            public void insertObject(ObjetCsv object) {
               if (display) {
                   System.out.println(object.toString());
               }
            }
        });
    }

    @Override
    public List<DogValid> readDogsValid(InputStream stream) throws CsvErrorsExceededException {
        CsvEngine engine = new CsvEngine(DogValid.class);
        return engine.parseInputStream(stream, DogValid.class).getObjects();
    }

    public static void main(String[] args) throws IOException, InterruptedException, CsvErrorsExceededException {
        CsvEngineSample sample = new CsvEngineSample();
        long time = sample.readDogs();
        System.out.println("Lecture d'un csv simple : " + time + "µs");
        time = sample.readComplexDogs();
        System.out.println("Lecture d'un csv complexe : " + time + "µs");
        time = sample.writeDogs();
        System.out.println("Écriture d'un fichier csv : " + time + "µs");
        sample.validateCsv();
        System.out.println("Bench moyen");
        sample.benchMoyen();
        System.out.println("Bench gros");
        sample.benchGros();
    }
}
