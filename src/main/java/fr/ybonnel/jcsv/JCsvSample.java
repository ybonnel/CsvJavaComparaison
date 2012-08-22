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
package fr.ybonnel.jcsv;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.writer.CSVEntryConverter;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;
import fr.ybonnel.common.CommonCsvSample;
import fr.ybonnel.common.Dog;
import fr.ybonnel.common.DogValid;
import fr.ybonnel.common.ObjetCsv;
import org.apache.commons.lang.NotImplementedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * @author ybonnel
 */
public class JCsvSample extends CommonCsvSample {
    @Override
    public List<Dog> getDogs(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream);

        ValueProcessorProvider provider = new ValueProcessorProvider();
        CSVEntryParser<Dog> entryParser = new AnnotationEntryParser<Dog>(Dog.class, provider);
        CSVReader<Dog> csvDogReader = new CSVReaderBuilder<Dog>(reader)
                .entryParser(entryParser)
                .strategy(new CSVStrategy(',', '"', '#', true, true)).build();

        return csvDogReader.readAll();
    }

    @Override
    public void writeFile(List<Dog> dogs, File file) throws IOException {

        CSVEntryConverter<Dog> entryConverter = new CSVEntryConverter<Dog>() {
            @Override
            public String[] convertEntry(Dog dog) {
                String[] columns = new String[3];

                columns[0] = dog.getName();
                columns[1] = dog.getRace();
                columns[2] = dog.getProprietary();

                return columns;
            }
        };
        CSVWriter<Dog> csvDogWriter = new CSVWriterBuilder<Dog>(new FileWriter(file))
                .entryConverter(entryConverter)
                .strategy(new CSVStrategy(',', '"', '#', true, true))
                .build();
        csvDogWriter.writeAll(dogs);
        csvDogWriter.close();
    }

    @Override
    public void readObjetCsv(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream);

        ValueProcessorProvider provider = new ValueProcessorProvider();
        CSVEntryParser<ObjetCsv> entryParser = new AnnotationEntryParser<ObjetCsv>(ObjetCsv.class, provider);
        CSVReader<ObjetCsv> csvReader = new CSVReaderBuilder<ObjetCsv>(reader)
                .entryParser(entryParser)
                .strategy(new CSVStrategy(',', '"', '#', true, true))
                .build();
        ObjetCsv objet;
        while ((objet = csvReader.readNext()) != null) {
            // do nothing
        }
    }

    @Override
    public List<DogValid> readDogsValid(InputStream stream) {
        throw new NotImplementedException();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JCsvSample sample = new JCsvSample();
        long time = sample.readDogs();
        System.out.println("Lecture d'un csv simple : " + time + "µs");
        time = sample.readComplexDogs();
        System.out.println("Lecture d'un csv complexe : " + time + "µs");
        time = sample.writeDogs();
        System.out.println("Écriture d'un fichier csv : " + time + "µs");
        System.out.println("Bench moyen");
        sample.benchMoyen();
        System.out.println("Bench gros");
        sample.benchGros();
    }
}
