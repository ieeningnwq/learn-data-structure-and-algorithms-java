package com.ieening.datastructure;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class MyPriorityQueue<E> implements MyQueue<E> {
    // MARK:Fields

    // 初始化元素数组大小
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    // 元素数组
    private Object[] data;

    /**
     * 优先级队列中元素个数
     */
    private int size = 0;

    // 比较器，如果未指定 <em>null</em>，使用自然排序

    private final Comparator<? super E> comparator;

    // MARK:Constructors
    /**
     * 指定初始容量以及比较器构造 {@code MyPriorityQueue}
     *
     * @param initialCapacity 优先级队列初始容量
     * @param comparator      指定比较器，如果为 {@code null} 则使用自然排序
     * @throws IllegalArgumentException 如果 {@code initialCapacity} 小于 1
     */
    public MyPriorityQueue(int initialCapacity,
            Comparator<? super E> comparator) {
        // Note: This restriction of at least one is not actually needed,
        // but continues for 1.5 compatibility
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.data = new Object[initialCapacity];
        this.comparator = comparator;
    }

    /**
     * 使用默认容量，指定比较器构造优先级队列
     *
     * @param comparator 指定比较器，如果为 {@code null} 则使用自然排序
     */
    public MyPriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    /**
     * 指定容量，采取元素自然排序构造优先级队列
     *
     * @param initialCapacity 优先级队列初始化容量
     * @throws IllegalArgumentException 如果 {@code initialCapacity} 小于1
     */
    public MyPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * 采取默认初始化容量以及自然排序构造优先级队列
     */
    public MyPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    public MyPriorityQueue(Object[] objs) {
        initElementsFromArray(objs);

        comparator = null;

        heapify();
    }

    private void initElementsFromArray(Object[] objs) {
        // 防止数组中有 null 值
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                throw new NullPointerException();
            }
        }

        Object[] a = new Object[objs.length];
        a = Arrays.copyOf(objs, objs.length, Object[].class);
        data = a;
        size = data.length;
    }

    public MyPriorityQueue(Object[] objs, Comparator<? super E> comparator) {
        this.comparator = comparator;
        heapify();
    }

    /**
     * 比较两个元素
     * 
     * @param e1 第一个元素
     * @param e2 第二个元素
     * @return 比较的值，返回一个负数、0或者整数，如果<em>e1</em> 小于、等于或者大于<em>e2</em>
     * @throws NullPointerException 如果 <em>e1</em>或者<em>e2</em>为{@code null}
     * @throws ClassCastException   如果不能比较
     */
    @SuppressWarnings("unchecked")
    protected int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<? super E>) e1).compareTo(e2);
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<E> {

        // create a new pq
        private MyPriorityQueue<E> copy;

        public HeapIterator() {
            copy = new MyPriorityQueue<>(toArray());
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy.dequeue();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(E element) {
        if (element == null)
            throw new NullPointerException();
        int i = size;
        if (i >= data.length) {
            grow(i + 1);
        }
        size = i + 1;
        if (i == 0) {
            data[0] = element;
        } else {
            siftUp(i, element);
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int index, E element) {
        while (index > 0) {
            int parentIndex = (index - 1) >>> 1;
            Object parent = data[parentIndex];
            if (compare(element, (E) parent) >= 0) {
                break;
            }
            data[index] = parent;
            index = parentIndex;
        }
        data[index] = element;
    }

    private void grow(int minCapacity) {
        int oldCapacity = data.length;
        // Double size if small; else grow by 50%
        int newCapacity = oldCapacity + ((oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1));
        data = Arrays.copyOf(data, newCapacity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() {
        if (isEmpty())
            return null;
        int s = --size;
        E result = (E) data[0];
        E lastE = (E) data[s];
        data[s] = null;
        if (s != 0) {
            siftDown(0, lastE);
        }
        return result;
    }

    /**
     * Removes a single instance of the specified element from this queue,
     * if it is present. More formally, removes an element {@code e} such
     * that {@code o.equals(e)}, if this queue contains one or more such
     * elements. Returns {@code true} if and only if this queue contained
     * the specified element (or equivalently, if this queue changed as a
     * result of the call).
     *
     * @param o element to be removed from this queue, if present
     * @return {@code true} if this queue changed as a result of the call
     */
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        } else {
            removeAt(index);
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private void removeAt(int index) {
        int s = --size;
        if (s == index)// 删除最后一个元素
            data[index] = null;
        else {
            E moved = (E) data[s];
            data[s] = null; // 队尾置 null
            siftDown(index, moved);
            if (data[index] == moved) {
                siftUp(index, moved);
            }
        }
    }

    private int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++)
                if (o.equals(data[i]))
                    return i;
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        return isEmpty() ? null : (E) data[0];
    }

    @Override
    public void clear() {
        for (int i = 0; i < data.length; i++)
            data[i] = null;
        size = 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        final int size = this.size;
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }
        System.arraycopy(data, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @SuppressWarnings("unchecked")
    private void heapify() {
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            siftDown(i, (E) data[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int index, E element) {
        int half = size >>> 1;
        while (index < half) {// 循环直到一个非叶子节点
            int childIndex = (index << 1) + 1;
            Object child = data[childIndex];
            int rightChildIndex = childIndex + 1;
            if (rightChildIndex < size && compare((E) child, (E) data[rightChildIndex]) > 0) {
                child = data[childIndex = rightChildIndex];
            }
            if (compare(element, (E) child) <= 0) {
                break;
            }
            data[index] = child;
            index = childIndex;
        }
        data[index] = element;
    }

    // MARK:Visible

    /**
     * 返回当前结点 index 的左结点索引，如果超出 size ，返回 -1
     * 
     * @param index 当前结点索引
     * @return 左节点索引，如果超出 size，返回 -1
     */
    private int getLeftChildIndex(Integer index) {
        int child = (index << 1) + 1;
        return child < size ? child : -1;
    }

    /**
     * 返回当前结点 index 的右结点索引，如果超出 size ，返回 -1
     * 
     * @param index 当前结点索引
     * @return 右节点索引，如果超出 size，返回 -1
     */
    private int getRightChildIndex(Integer index) {
        int child = (index << 1) + 2;
        return child < size ? child : -1;
    }

    public void draw(String filePath) {
        draw(1024, 1024, filePath, 0);
    }

    public void draw(String filePath, int rootIndex) {
        draw(1024, 1024, filePath, rootIndex);
    }

    /**
     * 以 rootIndex 为根结点树的结点个数
     * 
     * @param rootIndex 根结点索引
     * @return 结点个数
     */
    private int size(int rootIndex) {
        if (rootIndex == -1)
            return 0;
        int left_level = 0, right_level = 0;
        int left_sub_tree_index = rootIndex, right_sub_tree_index = rootIndex;

        while (left_sub_tree_index != -1) {
            left_level++;
            left_sub_tree_index = getLeftChildIndex(left_sub_tree_index);
        }

        while (right_sub_tree_index != -1) {
            right_level++;
            right_sub_tree_index = getRightChildIndex(right_sub_tree_index);
        }

        if (left_level == right_level) {
            return (1 << left_level) - 1;
        }
        return 1 + size(getLeftChildIndex(rootIndex)) + size(getRightChildIndex(rootIndex));
    }

    public void draw(int width, int height, String filePath, int rootIndex) {
        MyMap<Integer, double[]> coordinatesMap = new MyHashMap<>();
        setCoordinates(rootIndex, 0.85, coordinatesMap, size(rootIndex));

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());// 填充整个屏幕
        graphics.setColor(Color.BLACK);

        drawLines(rootIndex, coordinatesMap, graphics, bufferedImage);
        drawNodes(rootIndex, coordinatesMap, 0.032, graphics, bufferedImage);
        drawArray(graphics, bufferedImage, 0.06);

        saveImage(filePath, bufferedImage);
    }

    private void drawArray(Graphics2D graphics, BufferedImage bufferedImage, double nodeSideWidth) {
        int x_begin = (int) (bufferedImage.getWidth() * 0.05), y_begin = (int) (bufferedImage.getHeight() * 0.05),
                sideWidth = (int) (nodeSideWidth * bufferedImage.getWidth());
        for (int i = 0; i < size; i++) {
            // 绘制方格
            graphics.setColor(Color.black);
            graphics.draw(new Rectangle(i * sideWidth + x_begin, y_begin, sideWidth, sideWidth));
            // 设置字体信息
            graphics.setFont(new Font("SansSerif", Font.PLAIN, 16));
            FontMetrics fontMetrics = graphics.getFontMetrics();
            double ws = fontMetrics.stringWidth(String.valueOf(data[i]));
            double hs = fontMetrics.getDescent();
            // 绘制值
            graphics.drawString(String.valueOf(data[i]), (float) ((i + 0.5) * sideWidth + x_begin - ws / 2.0),
                    (float) (y_begin + 0.5 * sideWidth + hs));
            // 绘制索引
            graphics.drawString(String.valueOf(i), (float) ((i + 0.5) * sideWidth + x_begin - ws / 2.0),
                    (float) (y_begin + 1.3 * sideWidth + hs));
        }
    }

    void saveImage(String filePath, BufferedImage bufferedImage) {
        File file = new File(filePath);
        try {
            ImageIO.write(bufferedImage, filePath.substring(filePath.lastIndexOf('.') + 1), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawNodes(int index, MyMap<Integer, double[]> coordinatesMap, double nodeRadius,
            Graphics2D graphics,
            BufferedImage bufferedImage) {
        if (index == -1)
            return;

        drawNodes(getLeftChildIndex(index), coordinatesMap, nodeRadius, graphics, bufferedImage);

        // 计算坐标
        double xs = coordinatesMap.get(index)[0] * bufferedImage.getWidth();
        double ys = coordinatesMap.get(index)[1] * bufferedImage.getHeight();
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
        ws = fontMetrics.stringWidth(String.valueOf(data[index]));
        hs = fontMetrics.getDescent();
        graphics.drawString(String.valueOf(data[index]), (float) (xs - ws / 2.0), (float) (ys + hs));
        // 绘制 索引值
        graphics.drawString(String.valueOf(index), (float) (xs - ws / 2.0), (float) (ys + 1.5 * nodeRadius * bufferedImage.getHeight()+hs));

        drawNodes(getRightChildIndex(index), coordinatesMap, nodeRadius, graphics, bufferedImage);
    }

    /**
     * 计算各个结点坐标
     * 
     * @param treeNode       树的根结点
     * @param distance       在画布上，纵轴上的百分比位置，比如：0.9，就是从上往下，距离上边界 0.1
     * @param coordinatesMap 保存结点坐标信息
     */
    void setCoordinates(int index, double distance,
            MyMap<Integer, double[]> coordinatesMap, int size) {
        if (index == -1)
            return;

        setCoordinates(getLeftChildIndex(index), distance - 0.05, coordinatesMap, size);
        double[] coordinates = coordinatesMap.getOrDefault(index, new double[2]);
        coordinates[0] = (0.5 + coordinatesMap.size()) / size;
        coordinates[1] = 1 - (distance - 0.05);
        coordinatesMap.put(index, coordinates);
        setCoordinates(getRightChildIndex(index), distance - 0.05, coordinatesMap, size);
    }

    void drawLines(int index, MyMap<Integer, double[]> coordinatesMap, Graphics2D graphics,
            BufferedImage bufferedImage) {
        if (index == -1) {
            return;
        }

        drawLines(getLeftChildIndex(index), coordinatesMap, graphics, bufferedImage);

        if (getLeftChildIndex(index) != -1) {
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(index)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(index)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(getLeftChildIndex(index))[0],
                    bufferedImage.getHeight() * coordinatesMap.get(getLeftChildIndex(index))[1]));
        }
        if (getRightChildIndex(index) != -1) {
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(index)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(index)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(getRightChildIndex(index))[0],
                    bufferedImage.getHeight() * coordinatesMap.get(getRightChildIndex(index))[1]));
        }
        drawLines(getRightChildIndex(index), coordinatesMap, graphics, bufferedImage);
    }
}
