package gordon.study.concurrent.geekbang.u28immutability;

public class InheritanceSpoilsImmutability extends ImmutabilitySample {

    public InheritanceSpoilsImmutability(String... name) {
        super(name);
    }

    public InheritanceSpoilsImmutability addStudent(String name) {
        super.getStudents().add(name);
        return this;
    }

    public static void main(String[] args) {
        InheritanceSpoilsImmutability instance = new InheritanceSpoilsImmutability("Linus", "Doug Lee");
        InheritanceSpoilsImmutability instance2 = instance.addStudent("Dijikstra");
        System.out.println(instance2.getStudents());
        System.out.println(instance.getStudents()); // instance 变了
    }
}
