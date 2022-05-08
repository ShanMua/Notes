# 事务隔离级别

事务的**隔离性**是指在高并发环境中，**并发事务**之间是相互隔离的，事务的执行的互不干扰的。在标准SQL规范中定义了四种事务的隔离级别，分别是**读未提交(READ UNCOMMITTED)、读已提交(READ COMMITTED)、可重复读(REPEATABLE READ)和可串行化(SERIALIZABLE)**。

## 读未提交

在读未提交隔离级别中，某一事务A在执行过程中更新了某一数据，但还未提交或者未回滚的情况下，另一事务B能访问到事务A更新后的数据。读取到其他事务未提交的数据称为脏读。

![read_uncommit](https://raw.githubusercontent.com/ShanMua/Notes/main/2022/transaction_isolation_level/read_uncommit.png)

演示：

在客户端1中新建演示表t_id，该表只有一个id字段：

```mysql
create table t_id(
	id bigint not null auto_increment,
    primary key(id)
);
```

查看当前MySQL事务隔离界别并将其修改为read uncommitted：

```mysql
select @@tx_isolation;
set session transaction isolation level read uncommitted;
flush privileges;
```

在表t_id为空的情况下，开始执行一个事务A，插入4条记录，但不让事务A结束：

```mysql
begin;
insert into t_id() values();
insert into t_id() values();
insert into t_id() values();
insert into t_id() values();
```

```mysql
select * from t_id;

客户端1结果：
 >> 1
 >> 2
 >> 3
 >> 4
```

```mysql
select * from t_id;

客户端2结果：
 >> 1
 >> 2
 >> 3
 >> 4
```

执行插入数据后，在当前客户端1查询表t_id可以查出4条记录(1,2,3,4)。此时打开行一个客户端，查询表t_id任然可以查出4条记录(1,2,3,4)，而这四条记录并未提交，此时就已经产生了脏读(该演示完成后进行回滚)。

## 读已提交

读已提交和读未提交的区别在于：读已提交只允许访问已提交的数据。读已提交解决了脏读问题，但读已提交还存在不可重复读的问题。

![read_commit](https://raw.githubusercontent.com/ShanMua/Notes/main/2022/transaction_isolation_level/read_commit.png)

演示：

查看当前MySQL事务隔离界别并将其修改为read committed：

```mysql
select @@tx_isolation;
set global transaction isolation level read committed;
flush privileges;
```

在客户端1中开启事务A并插入4条数据，但不让事务A结束：

```mysql
begin;
insert into t_id() values();
insert into t_id() values();
insert into t_id() values();
insert into t_id() values();
```

```mysql
select * from t_id;

客户端1结果:
 >> 5
 >> 6
 >> 7
 >> 8
```

```mysql
select * from t_id;

客户端2结果:
 >> 空
```

事务A在执行了插入语句后，在客户端1可以查看到插入的数据(5,6,7,8)，但在客户端2中查不出数据，只有在事务A提交后，客户端2才能查出数据(5,6,7,8)，说明已提交只允许访问已提交的数据，且不会产生脏读的情况。

现在，在客户端2中开启一个事务B查询表t_id:

```mysql
begin;
select * from t_id;

客户端2结果:
 >> 5
 >> 6
 >> 7
 >> 8
```

在客户端1中开启事务A，将id=5的记录修改成id=9，并提交事务A:

```mysql
begin;
update t_id set id = 9 where id = 5;
commit;
```

继续在客户端2的事务B查询表t_id:

```mysql
select * from t_id;

客户端2结果:
 >> 6
 >> 7
 >> 8
 >> 9
```

事务B查询出了刚刚事务A的更新结果，id=9。说明在同一个事务(事务B)中，重复读取导的数据(因为被事务A修改过)是不一样的，这种问题就是不可重复读。

## 可重复读

可重复读隔离级别下，能够保证同一事务重复读取某一数据得到的结果是一致的，不会出现不可重复读的问题。但可重复读隔离级别会出现幻读。幻读指的是当某个事物A读取某个范围内的记录时，另一个事物B在该范围内插入了新的记录，那之前的事务A再次读取该范围的时候就会产生幻行(多出新插入的行)。

演示：

查看当前MySQL事务隔离界别并将其修改为read committed：

```mysql
select @@tx_isolation;
set global transaction isolation level repeatable read;
flush privileges;
```

在客户端1中开启事务A查询t_id表内id在[5, 15]内的记录：

```mysql
begin;
select * from t_id where id between 5 and 15;

客户端1结果:
 >> 6
 >> 7
 >> 8
 >> 9
```

此时，开启另一个客户端2，并执行事务B并提交

```mysql
begin;
insert into t_id(id) values(10);
commit;
```

客户端1的事务A再次查询t_id表内id在[5, 15]内的记录：

```mysql
select * from t_id where id between 5 and 15;

客户端1结果:
 >> 6
 >> 7
 >> 8
 >> 9
```

在可重复读隔离级别下，应该会出现幻读的情况，也就是这次查询会将id=10的记录也查询出来，但是实际查询结果却没有，这是因为MySQL通过MVCC解决了幻读的问题。

## 可串行化

可串行化是最高隔离级别，该级别下事务串行执行，读取每行数据时都会加锁，避免了幻读问题，但也是并发性能最低的隔离级别，实际场景中很少应用。

|          隔离级别          | 脏读 | 不可重复度 | 幻读 | 加锁读 |
| :------------------------: | :--: | :--------: | :--: | :----: |
| 读未提交(READ UNCOMMITTED) |  ✔   |     ✔      |  ✔   |   ❌    |
|  读已提交(READ COMMITTED)  |  ❌   |     ✔      |  ✔   |   ❌    |
| 可重复读(REPEATABLE READ)  |  ❌   |     ❌      |  ✔   |   ❌    |
|   可串行化(SERIALIZABLE)   |  ❌   |     ❌      |  ❌   |   ✔    |

