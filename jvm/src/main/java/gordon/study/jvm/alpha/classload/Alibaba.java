package gordon.study.jvm.alpha.classload;

public class Alibaba {

    public static int k = 0;
    public static Alibaba t1 = new Alibaba("t1");
    public static Alibaba t2 = new Alibaba("t2");
    public static int i = print("i");
    public static int n = 99;

    private int a = 0;
    public int j = print("j");

    {
        print("构造块");
    }

    static {
        print("静态块");
    }

    public Alibaba(String str) {
        System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
        ++i;
        ++n;
    }

    public static int print(String str) {
        System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
        ++n;
        return ++i;
    }

    public static void main(String args[]) {
        Alibaba t = new Alibaba("init");
    }
}

/*
// Alibaba类加载，连接（准备阶段全是默认值）
// Alibaba类初始化，遇到t1，进行对象初始化（Alibaba类初始化暂停）。t1对象初始化j，执行构造块，执行构造函数。
1:j   i=0    n=0
2:构造块   i=1    n=1
3:t1   i=2    n=2
// t2对象初始化。
4:j   i=3    n=3
5:构造块   i=4    n=4
6:t2   i=5    n=5
// i初始化，n初始化，静态块初始化。Alibaba类初始化结束。
7:i   i=6    n=6
8:静态块   i=7    n=99
// t对象初始化
9:j   i=8    n=100
10:构造块   i=9    n=101
11:init   i=10    n=102
 */