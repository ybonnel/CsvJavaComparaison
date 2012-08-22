package fr.ybonnel.common;

import com.googlecode.jcsv.annotations.MapToColumn;
import fr.ybonnel.csvengine.adapter.AdapterBoolean;
import fr.ybonnel.csvengine.adapter.AdapterDouble;
import fr.ybonnel.csvengine.adapter.AdapterInteger;
import fr.ybonnel.csvengine.annotation.CsvColumn;
import fr.ybonnel.csvengine.annotation.CsvFile;
import org.jsefa.common.converter.SimpleTypeConverter;
import org.jsefa.common.converter.SimpleTypeConverterConfiguration;
import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

@CsvDataType
@CsvFile
public class ObjetCsv {

    @CsvField(pos = 0)
    @MapToColumn(column = 0)
    @CsvColumn( "a1" )
    private String a1;
    @CsvField(pos = 1)
    @MapToColumn(column = 1)
    @CsvColumn( value = "a2", adapter = AdapterBoolean.class)
    private Boolean a2;
    @CsvField(pos = 2)
    @MapToColumn(column = 2)
    @CsvColumn( value = "a3" , adapter = AdapterInteger.class)
    private Integer a3;
    @CsvField(pos = 3)
    @MapToColumn(column = 3)
    @CsvColumn( value = "a4" , adapter = AdapterDouble.class)
    private Double a4;
    @CsvField(pos = 4)
    @MapToColumn(column = 4)
    @CsvColumn( "a5" )
    private String a5;
    @CsvField(pos = 5)
    @MapToColumn(column = 5)
    @CsvColumn( "a6" )
    private String a6;
    @CsvField(pos = 6)
    @MapToColumn(column = 6)
    @CsvColumn( value = "a7" , adapter = AdapterBoolean.class)
    private Boolean a7;
    @CsvField(pos = 7)
    @MapToColumn(column = 7)
    @CsvColumn( value = "a8" , adapter = AdapterInteger.class)
    private Integer a8;
    @CsvField(pos = 8)
    @MapToColumn(column = 8)
    @CsvColumn( value = "a9" , adapter = AdapterDouble.class)
    private Double a9;
    @CsvField(pos = 9)
    @MapToColumn(column = 9)
    @CsvColumn( "a10" )
    private String a10;
    @CsvField(pos = 10)
    @MapToColumn(column = 10)
    @CsvColumn( "a11" )
    private String a11;
    @CsvField(pos = 11)
    @MapToColumn(column = 11)
    @CsvColumn( value = "a12", adapter = AdapterBoolean.class )
    private Boolean a12;
    @CsvField(pos = 12)
    @MapToColumn(column = 12)
    @CsvColumn( value = "a13", adapter = AdapterInteger.class )
    private Integer a13;
    @CsvField(pos = 13)
    @MapToColumn(column = 13)
    @CsvColumn( value = "a14", adapter = AdapterDouble.class )
    private Double a14;
    @CsvField(pos = 14)
    @MapToColumn(column = 14)
    @CsvColumn( "a15" )
    private String a15;
    @CsvField(pos = 15)
    @MapToColumn(column = 15)
    @CsvColumn( "a16" )
    private String a16;
    @CsvField(pos = 16)
    @MapToColumn(column = 16)
    @CsvColumn( value = "a17", adapter = AdapterBoolean.class )
    private Boolean a17;
    @CsvField(pos = 17)
    @MapToColumn(column = 17)
    @CsvColumn( value = "a18", adapter = AdapterInteger.class )
    private Integer a18;
    @CsvField(pos = 18)
    @MapToColumn(column = 18)
    @CsvColumn( value = "a19", adapter = AdapterDouble.class )
    private Double a19;
    @CsvField(pos = 19)
    @MapToColumn(column = 19)
    @CsvColumn( "a20" )
    private String a20;

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public void setA2(Boolean a2) {
        this.a2 = a2;
    }

    public void setA3(Integer a3) {
        this.a3 = a3;
    }

    public void setA4(double a4) {
        this.a4 = a4;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    public void setA7(Boolean a7) {
        this.a7 = a7;
    }

    public void setA8(Integer a8) {
        this.a8 = a8;
    }

    public void setA9(double a9) {
        this.a9 = a9;
    }

    public void setA10(String a10) {
        this.a10 = a10;
    }

    public void setA11(String a11) {
        this.a11 = a11;
    }

    public void setA12(Boolean a12) {
        this.a12 = a12;
    }

    public void setA13(Integer a13) {
        this.a13 = a13;
    }

    public void setA14(double a14) {
        this.a14 = a14;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    public void setA16(String a16) {
        this.a16 = a16;
    }

    public void setA17(Boolean a17) {
        this.a17 = a17;
    }

    public void setA18(Integer a18) {
        this.a18 = a18;
    }

    public void setA19(double a19) {
        this.a19 = a19;
    }

    public void setA20(String a20) {
        this.a20 = a20;
    }

    @Override
    public String toString() {
        return "ObjetCsv{" +
                "a1='" + a1 + '\'' +
                ", a2=" + a2 +
                ", a3=" + a3 +
                ", a4=" + a4 +
                ", a5='" + a5 + '\'' +
                ", a6='" + a6 + '\'' +
                ", a7=" + a7 +
                ", a8=" + a8 +
                ", a9=" + a9 +
                ", a10='" + a10 + '\'' +
                ", a11='" + a11 + '\'' +
                ", a12=" + a12 +
                ", a13=" + a13 +
                ", a14=" + a14 +
                ", a15='" + a15 + '\'' +
                ", a16='" + a16 + '\'' +
                ", a17=" + a17 +
                ", a18=" + a18 +
                ", a19=" + a19 +
                ", a20='" + a20 + '\'' +
                '}';
    }

    public static class DoubleConverter implements SimpleTypeConverter {

        @Override
        public String toString(Object o) {
            return o.toString();
        }

        @Override
        public Object fromString(String s) {
            return Double.valueOf(s);
        }

        public static DoubleConverter create(SimpleTypeConverterConfiguration config) {
            return new DoubleConverter();
        }
    }

    public static class BooleanConverter implements SimpleTypeConverter {

        @Override
        public String toString(Object o) {
            return ((Boolean) o) ? "1" : "0";
        }

        @Override
        public Object fromString(String s) {
            return "1".equals(s);
        }

        public static BooleanConverter create(SimpleTypeConverterConfiguration config) {
            return new BooleanConverter();
        }
    }
}
