package gordon.study.concurrent.scene.readerwriter;

import java.util.concurrent.Callable;

public class Reader implements Callable {

    private File file;

    public Reader(File file) {
        this.file = file;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 10000; i++) {
            file.read();
        }
        return null;
    }
}