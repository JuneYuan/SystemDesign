# Solution Notes

### P519 Consistent Hashing

**需要重新整理一下思路，从大的 idea 到小的细节**

1. 这里返回值定义的是 `List<List<Integer>>` 类型，其实内层的 `List<>` 只是记录了一个机器的信息而已，几乎不涉及 `List` 这种数据结构的特有操作，所以不妨使用一个内部类 `class Machine` 来记录各个属性，这样代码写起来更清楚，可读性也更好。下面是 [LintCode 官网](http://www.jiuzhang.com/solutions/consistent-hashing/)给出的参考代码（部分），可读性就不是那么好——  
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
1. 本来以为返回的结果应该是按区间先后有序的，题目没有明说，思考算法时考虑了这个问题。从运行效果来看，题目是没有要求的。
1. 对了，新插入节点（机器）区间右边界出现错误！错误原因：被分割的区间有可能是奇数长度，而原先计算边界时把分割后的两部分当成了相等长度。

1. 考虑进行预处理，一次性计算出所有 `n` 对应的结果，此后直接读取即可。
2. 每次新加入一台 Machine, 需要做这几件事情
 + 计算把哪个机器的区间匀给它
 + 修改该机器的区间右边界
 + 插入新 Machine
3. 计算匀出区间来的机器，可以通过维护一个 PriorityQueue 来做，每次直接取堆顶元素就是区间 span 最大的那台机器了。
4. Machine 需要哪些属性呢？
 + int id  // 1, 2, 3, ...
 + int leftBound, rightBound
 + int span
5. `PriorityQueue` 的作用域如何设置？——应该比 `for` 循环内每次的计算结果高一级，在 `compute()` 预处理函数里均可访问。
6. List 嵌套 List 是个大麻烦，在这个问题上纠缠了好几个小时，尤其一上来还想的是做一个 `List<List<List<Integer>>>`, 额，还是一步步来比较好，问题分模块，函数拆成子函数。
