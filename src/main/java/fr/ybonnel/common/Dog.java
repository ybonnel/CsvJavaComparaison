package fr.ybonnel.common;

import com.googlecode.jcsv.annotations.MapToColumn;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

@CsvDataType
public class Dog {
    @CsvField(pos = 0)
    @MapToColumn( column = 0)
    private String name;
    @CsvField(pos = 1)
    @MapToColumn( column = 1)
    private String race;
    @CsvField(pos = 2)
    @MapToColumn( column = 2)
    private String proprietary;

    public void setName(String name) {
        this.name = name;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setProprietary(String proprietary) {
        this.proprietary = proprietary;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public String getProprietary() {
        return proprietary;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", proprietary='" + proprietary + '\'' +
                '}';
    }
}
