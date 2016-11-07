# Solution Notes

### P519 Consistent Hashing

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
