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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ybonnel
 */
public abstract class CommonStep1 {


    public static InputStream getCsvFile() {
        return CommonStep1.class.getResourceAsStream("/dogs.csv");
    }

    public abstract List<Dog> getDogs(InputStream stream) throws IOException;

    public long readDogs() throws IOException {
        long startTime = System.nanoTime();

        for (Dog dog : getDogs(getCsvFile())) {
            System.out.println(dog);
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
