package ieening.datastructure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.datastructure.MyLinkedBST;
import com.ieening.datastructure.MyBinarySearchTree;

public class MyLinkedBSTTest {
    private MyBinarySearchTree<Integer, Integer> myBinarySearchTree;

    @BeforeEach
    public void setUpEach() {
        myBinarySearchTree = new MyLinkedBST<Integer, Integer>();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67}", // 正常
    })
    public void testPutAndGet(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        int size = 0;
        for (String key : jObject.keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getInt(key));
            assertThat(++size, equalTo(myBinarySearchTree.size()));
        }
        for (String key : jObject.keySet()) {
            assertThat(jObject.getInt(key), equalTo(myBinarySearchTree.get(Integer.parseInt(key))));
        }
        // 不存在时
        assertThat(null, equalTo(myBinarySearchTree.get(-111)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":{7:\"8\",1:\"1\",0:\"1\",101:\"Exception\"}}", // 正常
    })
    public void testCeiling(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        for (String key : jObject.getJSONObject("asserts").keySet()) {
            try {
                int expected = jObject.getJSONObject("asserts").getInt(key);
                assertThat(expected, equalTo(myBinarySearchTree.ceiling(Integer.parseInt(key))));
            } catch (JSONException e) {
                assertThrows(NoSuchElementException.class, () -> {
                    myBinarySearchTree.ceiling(Integer.parseInt(key));
                });
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":{8:\"8\",6:\"5\",101:\"100\",0:\"Exception\"}}", // 正常
    })
    public void testFloor(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        for (String key : jObject.getJSONObject("asserts").keySet()) {
            try {
                int expected = jObject.getJSONObject("asserts").getInt(key);
                assertThat(expected, equalTo(myBinarySearchTree.floor(Integer.parseInt(key))));
            } catch (JSONException e) {
                assertThrows(NoSuchElementException.class, () -> {
                    myBinarySearchTree.floor(Integer.parseInt(key));
                });
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":100}", // 正常
            "{\"bst\":{},\"asserts\":\"Exception\"}", // 为空
    })
    public void testMax(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }

        try {
            int expected = jObject.getInt("asserts");
            assertThat(expected, equalTo(myBinarySearchTree.max()));
        } catch (JSONException e) {
            assertThrows(NoSuchElementException.class, () -> {
                myBinarySearchTree.max();
            });
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":1}", // 正常
            "{\"bst\":{},\"asserts\":\"Exception\"}", // 为空
    })
    public void testMin(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }

        try {
            int expected = jObject.getInt("asserts");
            assertThat(expected, equalTo(myBinarySearchTree.min()));
        } catch (JSONException e) {
            assertThrows(NoSuchElementException.class, () -> {
                myBinarySearchTree.min();
            });
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":{8:true,6:false,null:\"Exception\"}}", // 正常
    })
    public void testContains(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        for (String key : jObject.getJSONObject("asserts").keySet()) {
            try {
                boolean expected = jObject.getJSONObject("asserts").getBoolean(key);
                assertThat(expected, equalTo(myBinarySearchTree.contains(Integer.parseInt(key))));
            } catch (JSONException e) {
                assertThrows(IllegalArgumentException.class, () -> {
                    myBinarySearchTree.contains(Integer.parseInt(key));
                });
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":{3:8,-1:\"Exception\",10:\"Exception\"}}",
    })
    public void testSelect(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        for (String key : jObject.getJSONObject("asserts").keySet()) {
            try {
                int expected = jObject.getJSONObject("asserts").getInt(key);
                assertThat(expected, equalTo(myBinarySearchTree.select(Integer.parseInt(key))));
            } catch (JSONException e) {
                assertThrows(IllegalArgumentException.class, () -> {
                    myBinarySearchTree.select(Integer.parseInt(key));
                });
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67},\"asserts\":{8:3,9:4,-1:0,101:8}}",
    })
    public void testRank(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        for (String key : jObject.getJSONObject("asserts").keySet()) {
            int expected = jObject.getJSONObject("asserts").getInt(key);
            assertThat(expected, equalTo(myBinarySearchTree.rank(Integer.parseInt(key))));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":5}",
    })
    public void testHeight(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        assertThat(jObject.getInt("expected"), equalTo(myBinarySearchTree.height()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":4}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":56}",
    })
    public void testDeleteMin(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        myBinarySearchTree.deleteMin();
        assertThat(jObject.getInt("expected"), equalTo(myBinarySearchTree.min()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":87}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":23232}",
    })
    public void testDeleteMax(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        myBinarySearchTree.deleteMax();
        assertThat(jObject.getInt("expected"), equalTo(myBinarySearchTree.max()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"delete\":87}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"delete\":23232}",
    })
    public void testDelete(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        int deleteKey = jObject.getInt("delete");
        assertThat(true, equalTo(myBinarySearchTree.contains(deleteKey)));
        myBinarySearchTree.delete(deleteKey);
        assertThat(false, equalTo(myBinarySearchTree.contains(deleteKey)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[1, 4, 5, 8, 10, 11, 67, 87, 100]}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[12, 56, 192, 444, 897, 2839, 6788, 23232, 90000]}",
    })
    public void testKeys(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        Iterator<Integer> keysIterator = myBinarySearchTree.keys().iterator();
        int[] actual = new int[myBinarySearchTree.size()];
        int index = 0;
        while (keysIterator.hasNext()) {
            actual[index++] = keysIterator.next();
        }
        assertThat(jObject.getJSONArray("expected").toList().toArray(), equalTo(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[11, 1, 100, 4, 67, 5, 87, 8, 10]}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[90000, 12, 56, 444, 192, 897, 6788, 2839, 23232]}",
    })
    public void testLevelOrder(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        Iterator<Integer> keysIterator = myBinarySearchTree.levelOrder().iterator();
        int[] actual = new int[myBinarySearchTree.size()];
        int index = 0;
        while (keysIterator.hasNext()) {
            actual[index++] = keysIterator.next();
        }
        assertThat(jObject.getJSONArray("expected").toList().toArray(),
                equalTo(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[11, 1, 4, 5, 8, 10, 100, 67, 87]}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[90000, 12, 56, 444, 192, 897, 6788, 2839, 23232]}",
    })
    public void testPreOrder(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        Iterator<Integer> keysIterator = myBinarySearchTree.preOrder().iterator();
        int[] actual = new int[myBinarySearchTree.size()];
        int index = 0;
        while (keysIterator.hasNext()) {
            actual[index++] = keysIterator.next();
        }
        assertThat(jObject.getJSONArray("expected").toList().toArray(),
                equalTo(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[1, 4, 5, 8, 10, 11, 67, 87, 100]}",
    })
    public void testMiddleOrder(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        Iterator<Integer> keysIterator = myBinarySearchTree.middleOrder().iterator();
        int[] actual = new int[myBinarySearchTree.size()];
        int index = 0;
        while (keysIterator.hasNext()) {
            actual[index++] = keysIterator.next();
        }
        assertThat(jObject.getJSONArray("expected").toList().toArray(),
                equalTo(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[10, 8, 5, 4, 1, 87, 67, 100, 11]}",
    })
    public void testPostOrder(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        Iterator<Integer> keysIterator = myBinarySearchTree.postOrder().iterator();
        int[] actual = new int[myBinarySearchTree.size()];
        int index = 0;
        while (keysIterator.hasNext()) {
            actual[index++] = keysIterator.next();
        }
        assertThat(jObject.getJSONArray("expected").toList().toArray(),
                equalTo(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[11, 1, 4, 5, 8, 10, 100, 67, 87]}",
            "{\"bst\":{},\"expected\":[]}",

    })
    public void testDepthFirstOrder(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        assertThat(StreamSupport.stream(jObject.getJSONArray("expected").spliterator(), false).toArray(),
                equalTo(StreamSupport.stream(myBinarySearchTree.depthFirstOrder().spliterator(), false).toArray()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{}}",
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11}}"
    })
    public void testComparisonAndHashing(String parameterJsonString) {
        MyBinarySearchTree<Integer, Integer> equalBST = new MyLinkedBST<Integer, Integer>() {
        };

        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
            equalBST.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }

        assertThat(myBinarySearchTree, equalTo(equalBST));
        assertThat(myBinarySearchTree.hashCode(), equalTo(equalBST.hashCode()));

        MyBinarySearchTree<Integer, Integer> differentBST = new MyLinkedBST<Integer, Integer>() {
        };
        differentBST.put(-1, -1);
        assertThat(false, equalTo(myBinarySearchTree.equals(differentBST)));
        assertThat(false, equalTo(myBinarySearchTree.hashCode() == differentBST.hashCode()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":['S','E','A','R','C','H','X','M','P','L'],\"file\":\"normal_order\"}",
            "{\"bst\":['A','C','E','H','L','M','P','R','S','X'],\"file\":\"ascend_order\"}"
    })
    public void testDraw(String parameterJsonString) {
        MyLinkedBST<Character, Integer> bst = new MyLinkedBST<>();

        JSONObject jObject = new JSONObject(parameterJsonString);
        for (Object key : jObject.getJSONArray("bst").toList()) {
            bst.put(((String) key).charAt(0), (int) (((String) key).charAt(0)));
        }

        bst.draw("src\\test\\java\\ieening\\output\\linked_bst_" + jObject.getString("file") + ".png");
    }
}
