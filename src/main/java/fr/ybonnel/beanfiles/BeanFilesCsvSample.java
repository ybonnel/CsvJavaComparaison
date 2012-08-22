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
package fr.ybonnel.beanfiles;

import com.googlecode.beanfiles.csv.CSVReaderIterator;
import fr.ybonnel.common.CommonCsvSample;
import fr.ybonnel.common.Dog;
import org.apache.commons.lang.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ybonnel
 */
public class BeanFilesCsvSample extends CommonCsvSample {

    public List<Dog> getDogs(InputStream stream) throws IOException {
        CSVReaderIterator<Dog> readerIterator = new CSVReaderIterator<Dog>(Dog.class, stream);
        stream.close();
        List<Dog> dogs = new ArrayList<Dog>();
        for (Dog dog : readerIterator) {
            dogs.add(dog);
        }
        return dogs;
    }

    @Override
    public void writeFile(List<Dog> dogs, File file) throws IOException {
        throw new NotImplementedException();
    }

    public static void main(String[] args) throws IOException {
        new BeanFilesCsvSample().readDogs();
    }
}
