package amazon;

import java.util.HashMap;
import java.util.Map;

/*
* 146. LRU Cache

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
Implement the LRUCache class:
•	LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
•	int get(int key) Return the value of the key if the key exists, otherwise return -1.
•	void put(int key, int value) Update the value of the key if the key exists. Otherwise,
* add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation,
* evict the least recently used key.
*
The functions get and put must each run in O(1) average time complexity.

Example 1:
Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4

Constraints:
•	1 <= capacity <= 3000
•	0 <= key <= 104
•	0 <= value <= 105
•	At most 2 * 105 calls will be made to get and put.
*/
/*
* ：哈希表 + 双向链表 LinkedHashMap : Hash + double linked list
算法
LRU 缓存机制可以通过哈希表辅以双向链表实现，我们用一个哈希表和一个双向链表维护所有在缓存中的键值对。
双向链表按照被使用的顺序存储了这些键值对，靠近头部的键值对是最近使用的，而靠近尾部的键值对是最久未使用的。
哈希表即为普通的哈希映射（HashMap），通过缓存数据的键映射到其在双向链表中的位置。

这样以来，我们首先使用哈希表进行定位，找出缓存项在双向链表中的位置，随后将其移动到双向链表的头部，
* 即可在 O(1)O(1) 的时间内完成 get 或者 put 操作。具体的方法如下：

对于 get 操作，首先判断 key 是否存在：
如果 key 不存在，则返回 -1−1；
如果 key 存在，则 key 对应的节点是最近被使用的节点。通过哈希表定位到该节点在双向链表中的位置，
* 并将其移动到双向链表的头部，最后返回该节点的值。

对于 put 操作，首先判断 key 是否存在：
如果 key 不存在，使用 key 和 value 创建一个新的节点，在双向链表的头部添加该节点，并将 key 和该节点添加进哈希表中。
* 然后判断双向链表的节点数是否超出容量，如果超出容量，则删除双向链表的尾部节点，并删除哈希表中对应的项；
如果 key 存在，则与 get 操作类似，先通过哈希表定位，再将对应的节点的值更新为 value，并将该节点移到双向链表的头部。

上述各项操作中，访问哈希表的时间复杂度为 O(1)O(1)，在双向链表的头部添加节点、在双向链表的尾部删除节点的复杂度也为 O(1)O(1)。
* 而将一个节点移到双向链表的头部，可以分成「删除该节点」和「在双向链表的头部添加节点」两步操作，都可以在 O(1)O(1) 时间内完成。

Tips:
* 在双向链表的实现中，使用一个伪头部（dummy head）和伪尾部（dummy tail）标记界限，
* 这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在。
*/
//below is the implementation with LinkedHashMap directly, but it doesn't explain the algo
/*
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int m_cacheSize;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRUCache(int initialCapacity,
                    float loadFactor,
                    int cacheSize) {
        super(initialCapacity, loadFactor, true);
        m_cacheSize = cacheSize;
    }

    public LRUCache(int initialCapacity,
                    int cacheSize) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, cacheSize);
    }

    public LRUCache(int cacheSize) {
        this(DEFAULT_INITIAL_CAPACITY, cacheSize);
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > m_cacheSize;
    }

    public static <K, V> LRUCache<K, V> newInstance(int cacheSize) {
        return new LRUCache<>(cacheSize);
    }
}
*/
//时间复杂度：对于 put 和 get 都是O(1)。
//空间复杂度：O(capacity)，因为哈希表和双向链表最多存储 capacity+1 个元素。

public class LRUCache {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev = null;
        DLinkedNode next = null;

        public DLinkedNode() {
        }

        public DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<>();
    private int size = 0;
    private int capacity;
    private DLinkedNode head = new DLinkedNode();
    private DLinkedNode tail = new DLinkedNode();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            DLinkedNode newNode = new DLinkedNode(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                DLinkedNode tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */