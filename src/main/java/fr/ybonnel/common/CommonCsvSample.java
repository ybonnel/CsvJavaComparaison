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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ybonnel
 */
public abstract class CommonCsvSample {


    public static InputStream getCsvFile() {
        return CommonCsvSample.class.getResourceAsStream("/dogs.csv");
    }

    private List<Dog> currentDogs;

    public abstract List<Dog> getDogs(InputStream stream) throws IOException;

    public long readDogs() throws IOException {
        long startTime = System.nanoTime();

        currentDogs = getDogs(getCsvFile());
        for (Dog dog : currentDogs) {
            System.out.println(dog);
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public abstract void writeFile(List<Dog> dogs, File file) throws IOException;

    public long writeDogs() throws IOException {
        long startTime = System.nanoTime();

        File file = File.createTempFile("dogs", "csv");
        writeFile(currentDogs, file);

        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
