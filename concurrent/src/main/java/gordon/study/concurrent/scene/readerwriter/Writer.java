package gordon.study.concurrent.scene.readerwriter;

import java.util.concurrent.Callable;

public class Writer implements Callable {

    private File file;

    public Writer(File file) {
        this.file = file;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 10000; i++) {
            file.write(i);
        }
        return null;
    }
}