秦金卫kimmking: 
    为什么redis使用skiplist而不是b+ tree？

弟君: 
    B➕树本质优势是
        1 支持范围查询
        2 b➕树非叶子节点不存储数据，一次可以load更多的索引节点，所以可以减少磁盘IO次数
        redis是kv型的内存数据库，所以不适用
    老师这样理解对么

秦金卫kimmking: 
    bingo，要点就是B+树是针对磁盘+内存的，如果是纯内存的skiplist更有效
