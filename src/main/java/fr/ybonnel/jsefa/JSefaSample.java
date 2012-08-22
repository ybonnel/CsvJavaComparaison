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
package fr.ybonnel.jsefa;

import fr.ybonnel.common.CommonCsvSample;
import fr.ybonnel.common.Dog;
import fr.ybonnel.common.DogValid;
import fr.ybonnel.common.ObjetCsv;
import fr.ybonnel.csvengine.exception.CsvErrorsExceededException;
import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.common.converter.provider.SimpleTypeConverterProvider;
import org.jsefa.common.lowlevel.filter.FilterResult;
import org.jsefa.common.lowlevel.filter.LineFilter;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ybonnel
 */
public class JSefaSample extends CommonCsvSample {
    @Override
    public List<Dog> getDogs(InputStream stream) throws IOException {
        CsvConfiguration config = new CsvConfiguration();
        config.setFieldDelimiter(',');
        Deserializer deserializer = CsvIOFactory.createFactory(config, Dog.class).createDeserializer();

        List<Dog> dogs = new ArrayList<Dog>();

        deserializer.open(new InputStreamReader(stream));
        while (deserializer.hasNext()) {
            dogs.add(deserializer.<Dog>next());
        }
        deserializer.close(true);

        return dogs;
    }

    @Override
    public void writeFile(List<Dog> dogs, File file) throws IOException {
        CsvConfiguration config = new CsvConfiguration();
        config.setFieldDelimiter(',');
        Serializer serializer = CsvIOFactory.createFactory(config, Dog.class).createSerializer();

        serializer.open(new FileWriter(file));
        for (Dog dog : dogs) {
            serializer.write(dog);
        }
        serializer.close(true);
    }

    @Override
    public void readObjetCsv(InputStream stream, boolean display) throws IOException {
        CsvConfiguration config = new CsvConfiguration();
        config.setFieldDelimiter(',');
        config.setLineFilter(new LineFilter() {
            @Override
            public FilterResult filter(String s, int i, boolean b, boolean b1) {
                if (i == 1) {
                    return FilterResult.FAILED;
                }
                return FilterResult.PASSED;
            }
        });
        config.getSimpleTypeConverterProvider().registerConverterType(Double.class, ObjetCsv.DoubleConverter.class);
        config.getSimpleTypeConverterProvider().registerConverterType(Boolean.class, ObjetCsv.BooleanConverter.class);
        Deserializer deserializer = CsvIOFactory.createFactory(config, ObjetCsv.class).createDeserializer();


        deserializer.open(new InputStreamReader(stream));
        while (deserializer.hasNext()) {
            ObjetCsv objetCsv =deserializer.<ObjetCsv>next();
            if (display) {
                System.out.println(objetCsv.toString());
            }
        }
        deserializer.close(true);

    }

    @Override
    public List<DogValid> readDogsValid(InputStream stream) {
        CsvConfiguration config = new CsvConfiguration();
        config.setFieldDelimiter(',');
        Deserializer deserializer = CsvIOFactory.createFactory(config, DogValid.class).createDeserializer();

        List<DogValid> dogs = new ArrayList<DogValid>();

        deserializer.open(new InputStreamReader(stream));
        while (deserializer.hasNext()) {
            dogs.add(deserializer.<DogValid>next());
        }
        deserializer.close(true);

        return dogs;
    }

    public static void main(String[] args) throws IOException, InterruptedException, CsvErrorsExceededException {
        JSefaSample sample = new JSefaSample();
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
