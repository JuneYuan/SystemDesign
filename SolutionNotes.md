# Solution Notes

### P502 Mini Cassandra

首先这里肯定需要保存数据库一张表的信息。 NoSQL 数据库的表一般是这个样子：

![](http://ww4.sinaimg.cn/mw690/6b9392ddgw1fa2cz1058jj20ti03twfd.jpg)

直接根据这个图，去构造一个 `MiniCassandra` 类的字段的话，应该是怎样呢？首先能想到的就是一个 `List` ，逐行存放每一条记录（这不是 SQL 里面的表么？所以呢， SQL 数据库的最根本数据结构是个什么呢？待考证……）。这时，我们又需要用一个内部类来表示“每一条记录”，它的设计大体是这样的：

```
class Record {
	String rowKey;
	TreeMap<Integer, String> cells;  // Integer 代表 columnKey, String 代表 columnValue
}
```

为什么没有看到图中 `row1` 对应的字段呢？这是因为 row1 所包含的信息，作为 `List<Record>` 中元素的下表索引就好了，不必显式地用一个字段去存储。实际要插入一条记录时，首先计算出来 `rowKey` 的 hash 散列值，作为这条记录要插入的位置下标，然后构造一条记录插入 `List<Record>` 即可。

但是这样有个问题：`hash(rowKey)` 得到的散列值理论上是随机的，所以 `List<Record>` 也就应该被初始化为一个非常大的容量，然而我们不太可能这么去做。结论呢？——就是舍弃 `List` 的数据结构，采用 `HashMap<Integer, TreeMap<Integer, String>>`, 其中外层的 `HashMap` 表示一个 `rowKey` 到它后边同一行的所有单元格（每个单元格其实就是一个 `columnKey - columnValue pair` ）所组成的整体的映射关系；而内层的 `TreeMap` 则表示了一个 `columnKey` 到一个 `columnValue` 的映射关系。这样确实是可以的。

### P519 Consistent Hashing

问题：求从一台机器加到n台后，区间分布和对应的机器编号？（`每个区间内的值代表了数据模n以后的结果，每个区间对应着一个机器，机器负责……区间到底代表什么？？？`题目没有明说，从运行效果来看，返回结果是不必按区间先后有序的）

分析：

手工模拟一下可以发现，直接迭代求解就可以了：从最开始的一台机器做起，一台一台地加，每加一台，都得到一个新的区间分布，直到求得第 n 个区间分布，返回结果即可。就是一个递推求解的过程。按照这个思路可以直接写出代码。

题目要求返回值为 `List<List<>>` 类型，里层的 `List` 其实只是想用来表示一个区间。考虑到书写 `List` 代码时必须实例化对象并接连写好几个 `add()` 操作的麻烦，这里干脆先用一个内部类 `Machine` 代替之，随后转为 `List` 类型即可。这样代码写起来更清楚，可读性也更好。

+ Brute-Unordered

 返回结果不用维护 List 内元素顺序的话，很好做，就是迭代着、每次找到最长的一个区间，分出来一半内容给新加入的机器即可。这样复杂度是 `O(N^2)` 的，因为每加入一台机器都要扫描整个区间寻找最长的那个。

+ Brute-Ordered

 返回结果维护了 List 内元素顺序的话，意味着插入元素时得先选定位置再插入。这样复杂度也还是 `O(N^2)`. 因为每次加入一台机器，需要扫描整个区间，并移动一部分元素（最坏情况下为全部），扫描区间和移动元素都是 `O(N)` 的复杂度，相加还是 `O(N)`.

 就排序而言可以有 `O(N·logN)` 的算法，所以瓶颈在于按位置插入元素。
 
+ PriorityQueue

 舍弃扫描整个区间寻找插入位置的方法，还可以更快——用堆。整个过程是：（维护两个内容相同但元素顺序不同的数据结构， `PriorityQueue` 和 `List`）每加入一台新机器，先通过 `PriorityQueue.remove()` 得到要分割的区间，分出一半给新机器，然后 `PriorityQueue` 和 `List` 分别增加这个新节点。重复此步骤直到第 n 台机器。最后对 `List` 排个序即可返回。
 
 **可是为什么这种写法用时 11793 ms, 前两种反而是 9527 ms 和 9764 ms 呢？？？**
 
+ PriorityQueue with cache

 既然是递推求解的一个过程，那么自然可以把递推过程中的中间结果都记录下来，或者说是，预先计算好所有可能请求到的中间结果，每次请求时直接读取结果出来。


下面是 [LintCode 官网](http://www.jiuzhang.com/solutions/consistent-hashing/)给出的参考代码（部分），可读性就不是那么好——

```
	for (int i = 1; i < n; ++i) {
    List<Integer> new_machine = new ArrayList<Integer>();
    int index = 0;
    for (int j = 1; j < i; ++j) {
        if (results.get(j).get(1) - results.get(j).get(0) + 1 >
            results.get(index).get(1) - results.get(index).get(0) + 1)
            index = j;
    }
	//
    int x = results.get(index).get(0);
    int y = results.get(index).get(1);
    results.get(index).set(1, (x + y) / 2);
    //
    new_machine.add((x + y) / 2 + 1);
    new_machine.add(y);
    new_machine.add(i + 1);
    results.add(new_machine);
}
```

### P538 Memcache

由题意，很显然是要用 HashMap 的。问题在于，怎么把 time 这个信息给结合进来。一个办法就是：把有效时间区间和原来的 value 一起，作为新的 value （也就是搞一个 Wrapper）, 那么 API 中的每一个操作，就都是在操作这个 Wrapper 了。