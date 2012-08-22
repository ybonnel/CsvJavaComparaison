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
package fr.ybonnel.supercsv;

import fr.ybonnel.common.CommonCsvSample;
import fr.ybonnel.common.Dog;
import fr.ybonnel.common.DogValid;
import fr.ybonnel.common.ObjetCsv;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import org.apache.commons.lang.NotImplementedException;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.IsIncludedIn;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ybonnel
 */
public class SuperCsvSample extends CommonCsvSample {
    @Override
    public List<Dog> getDogs(InputStream stream) throws IOException {
        List<Dog> dogs = new ArrayList<Dog>();

        ICsvBeanReader inFile = new CsvBeanReader(new InputStreamReader(stream), CsvPreference.STANDARD_PREFERENCE);
        final String[] header = inFile.getCSVHeader(true);
        Dog dog;
        while( (dog = inFile.read(Dog.class, header)) != null) {
            dogs.add(dog);
        }
        inFile.close();
        return dogs;
    }

    @Override
    public void writeFile(List<Dog> dogs, File file) throws IOException {
        throw new NotImplementedException();
    }


    public static final CellProcessor[] userProcessorsBench = new CellProcessor[] {
            null,
            new ParseBool(),
            new ParseInt(),
            new ParseDouble(),
            null,
            null,
            new ParseBool(),
            new ParseInt(),
            new ParseDouble(),
            null,
            null,
            new ParseBool(),
            new ParseInt(),
            new ParseDouble(),
            null,
            null,
            new ParseBool(),
            new ParseInt(),
            new ParseDouble(),
            null
    };

    @Override
    public void readObjetCsv(InputStream stream, boolean display) throws IOException {

        ICsvBeanReader inFile = new CsvBeanReader(new InputStreamReader(stream), CsvPreference.STANDARD_PREFERENCE);

        final String[] header = inFile.getCSVHeader(true);
        ObjetCsv objetCsv;
        while((objetCsv = inFile.read(ObjetCsv.class, header,userProcessorsBench)) != null) {
            if (display) {
                System.out.println(objetCsv.toString());
            }
        }

        inFile.close();
    }


    public static final CellProcessor[] userProcessors = new CellProcessor[] {
            new NotNull(),
            new IsIncludedIn(new HashSet<Object>(DogValid.POSSIBLE_RACES)),
            null
    };

    @Override
    public List<DogValid> readDogsValid(InputStream stream) throws IOException {
        List<DogValid> dogs = new ArrayList<DogValid>();

        ICsvBeanReader inFile = new CsvBeanReader(new InputStreamReader(stream), CsvPreference.STANDARD_PREFERENCE);

        final String[] header = inFile.getCSVHeader(true);
        DogValid dog;
        while((dog = inFile.read(DogValid.class, header, userProcessors)) != null) {
            dogs.add(dog);
        }

        inFile.close();

        return dogs;
    }

    public static void main(String[] args) throws IOException, InterruptedException, CsvErrorsExceededException {
        SuperCsvSample sample = new SuperCsvSample();
        long time = sample.readDogs();
        System.out.println("Lecture d'un csv simple : " + time + "µs");
        time = sample.readComplexDogs();
        System.out.println("Lecture d'un csv complexe : " + time + "µs");
        sample.validateCsv();
        System.out.println("Bench moyen");
        sample.benchMoyen();
        System.out.println("Bench gros");
        sample.benchGros();
    }
}
