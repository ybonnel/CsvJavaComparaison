package fr.ybonnel.common;

public class Dog {
    private String name;
    private String race;
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

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", proprietary='" + proprietary + '\'' +
                '}';
    }
}
