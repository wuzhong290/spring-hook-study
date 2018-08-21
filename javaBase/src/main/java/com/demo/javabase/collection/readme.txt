快速失败迭代器 java.util.*
在迭代时不允许修改集合
迭代时被修改抛出ConcurrentModificationException异常
使用原集合遍历集合元素
迭代器不要求额外的内存
示例：ArrayList, Vector, HashMap


安全失败迭代器  java.util.concurrent.*
在迭代时允许修改集合
迭代时集合被修改不抛出异常
使用原集合的副本遍历集合元素
迭代器需要额外的内存克隆集合对象
示例：ConcurrentHashMap