## JVM内存结构

<img src="E:\github\Notes\2022\jvm_memory\runtime_memory_structure.png" alt="runtime_memory_structure" style="zoom:15%;" />

### 程序计数器

程序计数器相当于当前线程执行字节码的行号指示器。如果线程正在执行一个java方法，则程序计数器记录的是正在执行的字节码指令。

### 堆

参数：-Xmx、-Xms

### 方法区（非堆）

方法区是线程共享的内存区域，它用于存储类型信息、常量、静态变量、即时编译器产生的代码缓存等。

### 栈

参数：-Xss

### 直接内存

## JVM内存模型









- [JSR133中文版.pdf](http://ifeve.com/wp-content/uploads/2014/03/JSR133%E4%B8%AD%E6%96%87%E7%89%88.pdf)

- [17.4. Memory Model](https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.4)

- [JVM内部结构详解](https://github.com/cncounter/translation/blob/926502b537cb440f7371907d67bf93db01f77653/tiemao_2017/12_JVMInternals/12_JVMInternals.md)

