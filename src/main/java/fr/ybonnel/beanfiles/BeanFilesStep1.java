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
import fr.ybonnel.common.Dog;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ybonnel
 */
public class BeanFilesStep1 {

    private static InputStream getCsvFile() {
        return BeanFilesStep1.class.getResourceAsStream("/dogs.csv");
    }

    public static void main(String[] args) throws IOException {
        InputStream stream = getCsvFile();
        CSVReaderIterator<Dog> readerIterator = new CSVReaderIterator<Dog>(Dog.class, stream);
        stream.close();
        for (Dog dog : readerIterator) {
            System.out.println(dog);
        }
    }
}
