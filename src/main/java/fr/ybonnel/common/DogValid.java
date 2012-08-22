package fr.ybonnel.common;

import com.googlecode.jcsv.annotations.MapToColumn;
import fr.ybonnel.csvengine.annotation.CsvColumn;
import fr.ybonnel.csvengine.annotation.CsvFile;
import fr.ybonnel.csvengine.annotation.CsvValidation;
import fr.ybonnel.csvengine.validator.ValidateException;
import fr.ybonnel.csvengine.validator.ValidatorCsv;
import org.jsefa.common.validator.ValidationError;
import org.jsefa.common.validator.ValidationResult;
import org.jsefa.common.validator.Validator;
import org.jsefa.common.validator.ValidatorConfiguration;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

import java.util.HashSet;
import java.util.Set;

@CsvDataType
@CsvFile
public class DogValid {
    @CsvColumn(value = "name", mandatory = true)
    @CsvField(pos = 0, required = true)
    private String name;
    @CsvValidation(ValidatorRace.class)
    @CsvColumn(value = "race", mandatory = true)
    @CsvField(pos = 1, required = true, validatorType = ValidatorRaceJsefa.class)
    private String race;
    @CsvColumn("proprietary")
    @CsvField(pos = 2)
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



    public final static Set<String> POSSIBLE_RACES = new HashSet<String>() {{
        this.add("Bichon Maltais");
        this.add("Cocker");
        this.add("Fox Terrier");
        this.add("Berger Allemand");
    }};

    public static class ValidatorRaceJsefa implements Validator {
        @Override
        public ValidationResult validate(Object o) {
            if (!POSSIBLE_RACES.contains(o)) {
                return ValidationResult.create(ValidationError.create("ERROR", "The race \"" + o + "\" isn't correct"));
            }
            return null;
        }

        public static ValidatorRaceJsefa create(ValidatorConfiguration config) {
            return new ValidatorRaceJsefa();
        }
    }

    public static class ValidatorRace extends ValidatorCsv {
        @Override
        public void validate(String field) throws ValidateException {
            if (!POSSIBLE_RACES.contains(field)) {
                throw new ValidateException("The race \"" + field + "\" isn't correct");
            }
        }
    }
}
