package gordon.study.snippet.reflection.model;

public class RefModelB extends RefModelA {

    @Override
    public void methodA () {

    }

    @Override
    protected void methodC() {
        System.out.println("in RefModelA methodC");
    }

}
