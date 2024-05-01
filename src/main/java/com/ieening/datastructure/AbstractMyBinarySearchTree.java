package com.ieening.datastructure;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AbstractMyBinarySearchTree<K, V> implements MyBinarySearchTree<K, V> {
    // MARK:Fields

    /**
     * 根结点，如果树为空，那么根结点为 {@code null}
     */
    MyBinarySearchTree.TreeNode<K, V> root;

    /**
     * 比较器，如果使用自然排序，那么可以置为 {@code null}
     */
    private final Comparator<? super K> comparator;

    class MyBinarySearchTreeNode implements MyBinarySearchTree.TreeNode<K, V> {
        private K key; // 排序的键
        private V value; // 存储的值
        private MyBinarySearchTree.TreeNode<K, V> left, right; // 左右子树
        private int size;
        private int height;

        MyBinarySearchTreeNode(K key, V value) {
            this(key, value, 1, 0);
        }

        MyBinarySearchTreeNode(K key, V value, int size, int height) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.height = height;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public TreeNode<K, V> getLeftChild() {
            return left;
        }

        @Override
        public void setLeftChild(
                TreeNode<K, V> treeNode) {
            left = treeNode;
        }

        @Override
        public TreeNode<K, V> getRightChild() {
            return right;
        }

        @Override
        public void setRightChild(
                MyBinarySearchTree.TreeNode<K, V> treeNode) {
            right = treeNode;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj instanceof MyBinarySearchTree.TreeNode) {
                MyBinarySearchTree.TreeNode<?, ?> other = (MyBinarySearchTree.TreeNode<?, ?>) obj;
                if (size == other.getSize() && height == other.getHeight() && Objects.equals(key, other.getKey())
                        && Objects.equals(value, other.getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = size * height;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

    }

    // MARK:Constructors
    public AbstractMyBinarySearchTree() {
        this(null);
    }

    public AbstractMyBinarySearchTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    // MARK:Query Operations
    @Override
    public int size() {
        return size(root);
    }

    private int size(TreeNode<K, V> treeNode) {
        return treeNode == null ? 0 : treeNode.getSize();
    }

    @Override
    public int height() {
        return height(root);
    }

    /**
     * 获取指定节点高度
     * 
     * @param treeNode 指定结点
     * @return 结点高度
     */
    int height(TreeNode<K, V> treeNode) {
        // 空节点高度为 -1 ，叶节点高度为 0
        return treeNode == null ? -1 : treeNode.getHeight();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0 && root == null;
    }

    @Override
    public V get(K key) {
        checkKeyNotNull(key);

        TreeNode<K, V> treeNode = get(root, key);
        return treeNode == null ? null : treeNode.getValue();
    }

    private TreeNode<K, V> get(TreeNode<K, V> treeNode, K key) {
        if (treeNode == null)
            return null;
        int cmp = compare(key, treeNode.getKey());
        if (cmp < 0)
            return get(treeNode.getLeftChild(), key);
        else if (cmp > 0)
            return get(treeNode.getRightChild(), key);
        else
            return treeNode;
    }

    private void checkKeyNotNull(K key) {
        if (key == null)
            throw new IllegalArgumentException("prohibit to operate with a null key");
    }

    @Override
    public K ceiling(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();

        TreeNode<K, V> x = ceiling(root, key);
        if (x == null)
            throw new NoSuchElementException("argument to ceiling() is too large");
        else
            return x.getKey();
    }

    private TreeNode<K, V> ceiling(TreeNode<K, V> treeNode, K key) {
        if (treeNode == null)
            return null;
        int cmp = compare(key, treeNode.getKey());
        if (cmp == 0) {
            return treeNode;
        }
        if (cmp < 0) {
            TreeNode<K, V> node = ceiling(treeNode.getLeftChild(), key);
            if (node != null)
                return node;
            else
                return treeNode;
        }
        return ceiling(treeNode.getRightChild(), key);
    }

    private void checkIsEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("BST is empty");
    }

    @Override
    public boolean containsKey(K key) {
        checkKeyNotNull(key);
        return get(key) != null;
    }

    @Override
    public K floor(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();

        TreeNode<K, V> x = floor(root, key);
        if (x == null) {
            throw new NoSuchElementException("argument to floor() is too small");
        } else
            return x.getKey();
    }

    private TreeNode<K, V> floor(TreeNode<K, V> treeNode, K key) {
        if (treeNode == null)
            return null;
        int cmp = compare(key, treeNode.getKey());
        if (cmp == 0) {
            return treeNode;
        }
        if (cmp > 0) {
            TreeNode<K, V> node = floor(treeNode.getRightChild(), key);
            if (node != null) {
                return node;
            } else
                return treeNode;
        }
        return floor(treeNode.getLeftChild(), key);
    }

    @Override
    public K max() {
        checkIsEmpty();

        return max(root).getKey();
    }

    private TreeNode<K, V> max(TreeNode<K, V> treeNode) {
        if (treeNode.getRightChild() != null)
            return max(treeNode.getRightChild());
        else
            return treeNode;
    }

    @Override
    public K min() {
        checkIsEmpty();

        return min(root).getKey();

    }

    private TreeNode<K, V> min(TreeNode<K, V> treeNode) {
        if (treeNode.getLeftChild() == null)
            return treeNode;
        else
            return min(treeNode.getLeftChild());
    }

    @Override
    public K select(int rank) {
        checkRank(rank);

        return select(root, rank);
    }

    private K select(TreeNode<K, V> treeNode, int rank) {
        if (treeNode == null)
            return null;
        int leftSize = size(treeNode.getLeftChild());
        if (leftSize > rank)
            return select(treeNode.getLeftChild(), rank);
        else if (leftSize < rank)
            return select(treeNode.getRightChild(), rank - leftSize - 1);
        else
            return treeNode.getKey();
    }

    private void checkRank(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument rank  is invalid: " + rank);
        }
    }

    @Override
    public int rank(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();
        return rank(root, key);
    }

    private int rank(TreeNode<K, V> treeNode, K key) {
        if (treeNode == null)
            return 0;
        int cmp = compare(key, treeNode.getKey());
        if (cmp < 0)
            return rank(treeNode.getLeftChild(), key);
        else if (cmp > 0)
            return 1 + size(treeNode.getLeftChild()) + rank(treeNode.getRightChild(), key);
        else
            return size(treeNode.getLeftChild());

    }

    // MARK:Modification Operations

    @Override
    public void put(K key, V value) {
        checkKeyNotNull(key);

        if (value == null)
            delete(key);
        else
            root = put(root, key, value);
    }

    void updateSize(TreeNode<K, V> treeNode) {
        // size大小等于 左子树size+右子树size + 1
        treeNode.setSize(1 + size(treeNode.getLeftChild()) + size(treeNode.getRightChild()));

    }

    void updateHeight(TreeNode<K, V> treeNode) {
        // 节点高度等于最高子树高度 + 1
        treeNode.setHeight(
                Math.max(height(treeNode.getLeftChild()), height(treeNode.getRightChild())) + 1);

    }

    TreeNode<K, V> afterNodePut(TreeNode<K, V> treeNode) {
        updateSize(treeNode);
        updateHeight(treeNode);
        return treeNode;
    }

    TreeNode<K, V> createTreeNode(K key, V value) {
        return new MyBinarySearchTreeNode(key, value);
    }

    /**
     * 将指定键值对存入 BST 中，采用递归的方法
     * 
     * @param treeNode 根结点
     * @param key      键
     * @param value    值
     * @return BST 根结点
     */
    private TreeNode<K, V> put(TreeNode<K, V> treeNode, K key, V value) {
        if (treeNode == null)
            return createTreeNode(key, value);

        int cmp = compare(key, treeNode.getKey());
        if (cmp < 0) {
            treeNode.setLeftChild(put(treeNode.getLeftChild(), key, value));
        } else if (cmp > 0) {
            treeNode.setRightChild(put(treeNode.getRightChild(), key, value));
        } else {
            treeNode.setValue(value);
        }

        return afterNodePut(treeNode);
    }

    @Override
    public void deleteMin() {
        checkIsEmpty();

        root = deleteMin(root);
    }

    private TreeNode<K, V> deleteMin(TreeNode<K, V> treeNode) {
        if (treeNode.getLeftChild() == null)
            return treeNode.getRightChild();
        treeNode.setLeftChild(deleteMin(treeNode.getLeftChild()));

        return afterNodeDelete(treeNode);
    }

    TreeNode<K, V> afterNodeDelete(TreeNode<K, V> treeNode) {
        // size大小等于 左子树size+右子树size + 1
        updateSize(treeNode);
        // 节点高度等于最高子树高度 + 1
        updateHeight(treeNode);

        return treeNode;
    }

    @Override
    public void deleteMax() {
        checkIsEmpty();

        root = deleteMax(root);
    }

    private TreeNode<K, V> deleteMax(TreeNode<K, V> treeNode) {
        if (treeNode.getRightChild() == null)
            return treeNode.getLeftChild();
        treeNode.setRightChild(deleteMax(treeNode.getRightChild()));

        return afterNodeDelete(treeNode);
    }

    @Override
    public void delete(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();

        root = delete(root, key);
    }

    private TreeNode<K, V> delete(TreeNode<K, V> treeNode, K key) {
        if (treeNode == null)
            return null;

        int cmp = compare(key, treeNode.getKey());
        if (cmp < 0)
            treeNode.setLeftChild(delete(treeNode.getLeftChild(), key));
        else if (cmp > 0)
            treeNode.setRightChild(delete(treeNode.getRightChild(), key));
        else {
            if (treeNode.getRightChild() == null) // 如果没有右节点
                return treeNode.getLeftChild();
            if (treeNode.getLeftChild() == null) // 如果没有左节点
                return treeNode.getRightChild();
            // 左右结点都存在
            TreeNode<K, V> node = treeNode;
            treeNode = min(node.getRightChild());
            treeNode.setRightChild(deleteMin(node.getRightChild()));
            treeNode.setLeftChild(node.getLeftChild());
        }

        return afterNodeDelete(treeNode);
    }

    // MARK:View

    @Override
    public Iterable<K> keys() {
        if (isEmpty()) {
            return new MyResizingArrayQueue<>();
        }
        return keys(min(), max());
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        checkKeyNotNull(lo);
        checkKeyNotNull(hi);

        MyQueue<K> queue = new MyResizingArrayQueue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(TreeNode<K, V> treeNode, MyQueue<K> queue, K lo, K hi) {
        if (treeNode == null)
            return;
        int cmpLo = compare(lo, treeNode.getKey());
        int cmpHi = compare(hi, treeNode.getKey());
        if (cmpLo < 0)
            keys(treeNode.getLeftChild(), queue, lo, hi);
        if (cmpLo <= 0 && cmpHi >= 0)
            queue.enqueue(treeNode.getKey());
        if (cmpHi > 0)
            keys(treeNode.getRightChild(), queue, lo, hi);
    }

    @Override
    public Iterable<K> levelOrder() {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyQueue<TreeNode<K, V>> queue = new MyResizingArrayQueue<>();

        queue.enqueue(root);
        while (!queue.isEmpty()) {
            TreeNode<K, V> currentNode = queue.dequeue();
            if (currentNode == null)
                continue;
            keys.enqueue(currentNode.getKey());
            queue.enqueue(currentNode.getLeftChild());
            queue.enqueue(currentNode.getRightChild());
        }
        return keys;
    }

    @Override
    public Iterable<K> middleOrder() {
        return middleOrder(root);
    }

    private Iterable<K> middleOrder(TreeNode<K, V> treeNode) {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyStack<TreeNode<K, V>> stack = new MyResizingArrayStack<>();

        while (true) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.getLeftChild();
            }
            if (stack.isEmpty())
                break;
            treeNode = stack.pop();
            keys.enqueue(treeNode.getKey());
            treeNode = treeNode.getRightChild();
        }
        return keys;
    }

    @Override
    public Iterable<K> postOrder() {
        return postOrder(root);
    }

    private Iterable<K> postOrder(TreeNode<K, V> treeNode) {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyStack<TreeNode<K, V>> stack = new MyResizingArrayStack<>();
        TreeNode<K, V> lastVisit = null;

        while (true) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.getLeftChild();
            }
            if (stack.isEmpty())
                break;
            treeNode = stack.peek();
            if (treeNode.getRightChild() == null || treeNode.getRightChild() == lastVisit) {
                lastVisit = stack.pop();
                keys.enqueue(lastVisit.getKey());
                treeNode = null;
            } else {
                treeNode = treeNode.getRightChild();
            }
        }
        return keys;
    }

    @Override
    public Iterable<K> preOrder() {
        return preOrder(root);
    }

    private Iterable<K> preOrder(TreeNode<K, V> treeNode) {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyStack<TreeNode<K, V>> stack = new MyResizingArrayStack<>();

        while (treeNode != null) {
            keys.enqueue(treeNode.getKey());
            if (treeNode.getRightChild() != null) {
                stack.push(treeNode.getRightChild());
            }
            if (treeNode.getLeftChild() != null) {
                stack.push(treeNode.getLeftChild());
            }
            treeNode = stack.isEmpty() ? null : stack.pop();
        }
        return keys;
    }

    /**
     * 比较两个 {@code key}
     * 
     * @param k1 第一个{@code key}
     * @param k2 第二个{@code key}
     * @return 比较的值，返回一个负数、0或者整数，如果<em>k1</em> 小于、等于或者大于<em>k2</em>
     * @throws NullPointerException 如果 <em>k1</em>或者<em>k2</em>为{@code null}
     * @throws ClassCastException   如果不能比较
     */
    @SuppressWarnings("unchecked")
    protected int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        return ((Comparable<? super K>) k1).compareTo(k2);
    }

    // MARK:Comparison and hashing
    @Override
    public int hashCode() {
        int h = 0;
        Iterator<K> k = keys().iterator();
        while (k.hasNext())
            h += get(root, k.next()).hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof MyBinarySearchTree))
            return false;
        @SuppressWarnings("unchecked")
        MyBinarySearchTree<K, V> m = (MyBinarySearchTree<K, V>) obj;
        if (m.size() != size() || m.height() != height())
            return false;
        Iterator<K> i = keys().iterator();
        while (i.hasNext()) {
            K key = i.next();
            if (!(m.containsKey(key) && Objects.equals(get(key), m.get(key))))
                return false;
        }
        return true;
    }

    // MARK:Visible

    public void draw(String filePath) {
        draw(1024, 1024, filePath);
    }

    public void draw(int width, int height, String filePath) {
        MyMap<TreeNode<K, V>, double[]> coordinatesMap = new MyHashMap<>();
        setCoordinates(root, 0.9, coordinatesMap);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());// 填充整个屏幕
        graphics.setColor(Color.BLACK);

        drawLines(root, coordinatesMap, graphics, bufferedImage);
        drawNodes(root, coordinatesMap, 0.032, graphics, bufferedImage);

        saveImage(filePath, bufferedImage);
    }

    private void saveImage(String filePath, BufferedImage bufferedImage) {
        File file = new File(filePath);
        try {
            ImageIO.write(bufferedImage, filePath.substring(filePath.lastIndexOf('.') + 1), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawNodes(TreeNode<K, V> treeNode, MyMap<TreeNode<K, V>, double[]> coordinatesMap, double nodeRadius,
            Graphics2D graphics,
            BufferedImage bufferedImage) {
        if (treeNode == null) {
            return;
        }

        drawNodes(treeNode.getLeftChild(), coordinatesMap, nodeRadius, graphics, bufferedImage);

        // 计算坐标
        double xs = coordinatesMap.get(treeNode)[0] * bufferedImage.getWidth();
        double ys = coordinatesMap.get(treeNode)[1] * bufferedImage.getHeight();
        double ws = 2 * nodeRadius * bufferedImage.getWidth();
        double hs = 2 * nodeRadius * bufferedImage.getHeight();
        // Clear the node circle area
        graphics.setColor(Color.white);
        graphics.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        // 绘制圆
        graphics.setColor(Color.black);
        graphics.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        // 绘制 key
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 16));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        ws = fontMetrics.stringWidth(String.valueOf(treeNode.getKey()));
        hs = fontMetrics.getDescent();
        graphics.drawString(String.valueOf(treeNode.getKey()), (float) (xs - ws / 2.0), (float) (ys + hs));

        drawNodes(treeNode.getRightChild(), coordinatesMap, nodeRadius, graphics, bufferedImage);
    }

    /**
     * 计算各个结点坐标
     * 
     * @param treeNode       树的根结点
     * @param distance       在画布上，纵轴上的百分比位置，比如：0.9，就是从上往下，距离上边界 0.1
     * @param coordinatesMap 保存结点坐标信息
     */
    private void setCoordinates(TreeNode<K, V> treeNode, double distance,
            MyMap<TreeNode<K, V>, double[]> coordinatesMap) {
        if (treeNode == null)
            return;

        setCoordinates(treeNode.getLeftChild(), distance - 0.05, coordinatesMap);
        double[] coordinates = coordinatesMap.getOrDefault(treeNode, new double[2]);
        coordinates[0] = (0.5 + coordinatesMap.size()) / size();
        coordinates[1] = 1 - (distance - 0.05);
        coordinatesMap.put(treeNode, coordinates);
        setCoordinates(treeNode.getRightChild(), distance - 0.05, coordinatesMap);
    }

    private void drawLines(TreeNode<K, V> treeNode, MyMap<TreeNode<K, V>, double[]> coordinatesMap, Graphics2D graphics,
            BufferedImage bufferedImage) {
        if (treeNode == null) {
            return;
        }

        drawLines(treeNode.getLeftChild(), coordinatesMap, graphics, bufferedImage);

        if (treeNode.getLeftChild() != null) {
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(treeNode)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(treeNode.getLeftChild())[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode.getLeftChild())[1]));
        }
        if (treeNode.getRightChild() != null) {
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(treeNode)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(treeNode.getRightChild())[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode.getRightChild())[1]));
        }

        drawLines(treeNode.getRightChild(), coordinatesMap, graphics, bufferedImage);
    }
}
