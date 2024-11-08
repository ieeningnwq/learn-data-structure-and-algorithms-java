package com.ieening.datastructure;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.imageio.ImageIO;

public abstract class AbstractMyBinarySearchTree<K, V> implements MyBinarySearchTree<K, V> {
    // MARK:Fields

    /**
     * 根结点，如果树为空，那么根结点为 {@code null}
     */
    MyBinarySearchTree.TreeNode<K, V> root;

    /**
     * 比较器，如果使用自然排序，那么可以置为 {@code null}
     */
    final Comparator<? super K> comparator;

    // MARK:Constructors

    public AbstractMyBinarySearchTree() {
        this(null);
    }

    public AbstractMyBinarySearchTree(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    // MARK:Query Operations

    @Override
    public TreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void setRoot(TreeNode<K, V> treeNode) {
        root = treeNode;
    }

    @Override
    public int size() {
        return size(getRoot());
    }

    int size(TreeNode<K, V> treeNode) {
        return treeNode == null ? 0 : treeNode.getSize();
    }

    @Override
    public int height() {
        return height(getRoot());
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
        return size() == 0 && getRoot() == null;
    }

    @Override
    public V get(K key) {
        checkKeyNotNull(key);
        TreeNode<K, V> treeNode = get(getRoot(), key);
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

    void checkKeyNotNull(K key) {
        if (key == null)
            throw new IllegalArgumentException("prohibit to operate with a null key");
    }

    @Override
    public K ceiling(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();

        TreeNode<K, V> x = ceiling(getRoot(), key);
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

    void checkIsEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("BST is empty");
    }

    @Override
    public boolean contains(K key) {
        checkKeyNotNull(key);
        return get(key) != null;
    }

    @Override
    public K floor(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();

        TreeNode<K, V> x = floor(getRoot(), key);
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

        return max(getRoot()).getKey();
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

        return min(getRoot()).getKey();

    }

    TreeNode<K, V> min(TreeNode<K, V> treeNode) {
        if (treeNode.getLeftChild() == null)
            return treeNode;
        else
            return min(treeNode.getLeftChild());
    }

    @Override
    public K select(int rank) {
        checkRank(rank);

        return select(getRoot(), rank);
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
        return rank(getRoot(), key);
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
            setRoot(put(getRoot(), key, value));
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
        throw new UnsupportedOperationException("Unimplemented method 'createTreeNode'");
    }

    /**
     * 将指定键值对存入 BST 中，采用递归的方法
     * 
     * @param treeNode 根结点
     * @param key      键
     * @param value    值
     * @return BST 根结点
     */
    TreeNode<K, V> put(TreeNode<K, V> treeNode, K key, V value) {
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

        setRoot(deleteMin(getRoot()));
    }

    TreeNode<K, V> deleteMin(TreeNode<K, V> treeNode) {
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

        setRoot(deleteMax(getRoot()));
    }

    TreeNode<K, V> deleteMax(TreeNode<K, V> treeNode) {
        if (treeNode.getRightChild() == null)
            return treeNode.getLeftChild();
        treeNode.setRightChild(deleteMax(treeNode.getRightChild()));

        return afterNodeDelete(treeNode);
    }

    @Override
    public void delete(K key) {
        checkKeyNotNull(key);
        checkIsEmpty();

        setRoot(delete(getRoot(), key));
    }

    TreeNode<K, V> delete(TreeNode<K, V> treeNode, K key) {
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
        keys(getRoot(), queue, lo, hi);
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

        queue.enqueue(getRoot());
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
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        middleOrder(getRoot(), keys);
        return keys;
    }

    private void middleOrder(TreeNode<K, V> treeNode, MyQueue<K> keys) {
        if (Objects.nonNull(treeNode)) {
            middleOrder(treeNode.getLeftChild(), keys);
            keys.enqueue(treeNode.getKey());
            middleOrder(treeNode.getRightChild(), keys);
        }
    }

    public Iterable<K> depthFirstOrder() {
        MyList<K> keys = new MyLinkedList<>();
        depthFirstOrder(getRoot(), keys);
        return keys;
    }

    private void depthFirstOrder(TreeNode<K, V> treeNode, MyList<K> keys) {
        if (Objects.nonNull(treeNode)) {
            keys.add(treeNode.getKey());
            depthFirstOrder(treeNode.getLeftChild(), keys);
            depthFirstOrder(treeNode.getRightChild(), keys);
        }
    }

    @Override
    public Iterable<K> postOrder() {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        postOrder(getRoot(), keys);
        return keys;
    }

    private void postOrder(TreeNode<K, V> treeNode, MyQueue<K> keys) {
        if (Objects.nonNull(treeNode)) {
            postOrder(treeNode.getLeftChild(), keys);
            postOrder(treeNode.getRightChild(), keys);
            keys.enqueue(treeNode.getKey());
        }
    }

    @Override
    public Iterable<K> preOrder() {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        preOrder(getRoot(), keys);
        return keys;
    }

    private void preOrder(TreeNode<K, V> treeNode, MyQueue<K> keys) {
        if (Objects.nonNull(treeNode)) {
            keys.enqueue(treeNode.getKey());
            preOrder(treeNode.getLeftChild(), keys);
            preOrder(treeNode.getRightChild(), keys);
        }
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
            h += get(getRoot(), k.next()).hashCode();
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
            if (!(m.contains(key) && Objects.equals(get(key), m.get(key))))
                return false;
        }
        return true;
    }

    // MARK:Visible

    public void draw(String filePath) {
        draw(1024, 1024, filePath, getRoot());
    }

    public void draw(String filePath, TreeNode<K, V> treeNode) {
        draw(1024, 1024, filePath, treeNode);
    }

    public void draw(int width, int height, String filePath, TreeNode<K, V> treeNode) {
        MyMap<TreeNode<K, V>, double[]> coordinatesMap = new MyHashMap<>();
        setCoordinates(treeNode, 0.9, coordinatesMap, size(treeNode));

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());// 填充整个屏幕
        graphics.setColor(Color.BLACK);

        drawLines(treeNode, coordinatesMap, graphics, bufferedImage);
        drawNodes(treeNode, coordinatesMap, 0.032, graphics, bufferedImage);

        saveImage(filePath, bufferedImage);
    }

    void saveImage(String filePath, BufferedImage bufferedImage) {
        File file = new File(filePath);
        try {
            ImageIO.write(bufferedImage, filePath.substring(filePath.lastIndexOf('.') + 1), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawNodes(TreeNode<K, V> treeNode, MyMap<TreeNode<K, V>, double[]> coordinatesMap, double nodeRadius,
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
    void setCoordinates(TreeNode<K, V> treeNode, double distance,
            MyMap<TreeNode<K, V>, double[]> coordinatesMap, int size) {
        if (treeNode == null)
            return;

        setCoordinates(treeNode.getLeftChild(), distance - 0.05, coordinatesMap, size);
        double[] coordinates = coordinatesMap.getOrDefault(treeNode, new double[2]);
        coordinates[0] = (0.5 + coordinatesMap.size()) / size;
        coordinates[1] = 1 - (distance - 0.05);
        coordinatesMap.put(treeNode, coordinates);
        setCoordinates(treeNode.getRightChild(), distance - 0.05, coordinatesMap, size);
    }

    void drawLines(TreeNode<K, V> treeNode, MyMap<TreeNode<K, V>, double[]> coordinatesMap, Graphics2D graphics,
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
