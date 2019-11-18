package gordon.study.concurrent.scene.readerwriter;

public class Wrong implements File {

    private int num;


    @Override
    public void write(int num) throws InterruptedException {
        this.num = num;
    }

    @Override
    public int read() throws InterruptedException {
        return num;
    }


    public static void main(String[] args) throws Exception {
        Wrong file = new Wrong();
        file.run();
    }
}