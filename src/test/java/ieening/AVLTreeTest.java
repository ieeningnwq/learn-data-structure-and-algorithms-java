package ieening;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ieening.AVLTree;
import com.ieening.AbstractMyBinarySearchTree;
import com.ieening.MyBinarySearchTree;

public class AVLTreeTest {
    private AVLTree<Integer, Integer> myBinarySearchTree;

    @BeforeEach
    public void setUpEach() {
        myBinarySearchTree = new AVLTree<>();
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
    public void testContainsKey(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        for (String key : jObject.getJSONObject("asserts").keySet()) {
            try {
                boolean expected = jObject.getJSONObject("asserts").getBoolean(key);
                assertThat(expected, equalTo(myBinarySearchTree.containsKey(Integer.parseInt(key))));
            } catch (JSONException e) {
                assertThrows(IllegalArgumentException.class, () -> {
                    myBinarySearchTree.containsKey(Integer.parseInt(key));
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
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":3}",
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
        assertThat(true, equalTo(myBinarySearchTree.containsKey(deleteKey)));
        myBinarySearchTree.delete(deleteKey);
        assertThat(false, equalTo(myBinarySearchTree.containsKey(deleteKey)));
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
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[11, 4, 87, 1, 8, 67, 100, 5, 10]}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[897, 56, 23232, 12, 444, 6788, 90000, 192, 2839]}",
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
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[11, 4, 1, 8, 5, 10, 87, 67, 100]}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[897, 56, 12, 444, 192, 23232, 6788, 2839, 90000]}",
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
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[12, 56, 192, 444, 897, 2839, 6788, 23232, 90000]}",
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
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"expected\":[1, 5, 10, 8, 4, 67, 100, 87, 11]}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"expected\":[12, 192, 444, 56, 2839, 6788, 90000,23232,897]}",
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
            "{\"bst\":{}}",
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11}}"
    })
    public void testComparisonAndHashing(String parameterJsonString) {
        MyBinarySearchTree<Integer, Integer> equalBST = new AVLTree<>();

        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
            equalBST.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }

        assertThat(myBinarySearchTree, equalTo(equalBST));
        assertThat(myBinarySearchTree.hashCode(), equalTo(equalBST.hashCode()));

        MyBinarySearchTree<Integer, Integer> differentBST = new AbstractMyBinarySearchTree<Integer, Integer>() {
        };
        differentBST.put(-1, -1);
        assertThat(false, equalTo(myBinarySearchTree.equals(differentBST)));
        assertThat(false, equalTo(myBinarySearchTree.hashCode() == differentBST.hashCode()));
    }

    @SuppressWarnings("rawtypes")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"bst\":{4:4,8:8,10:10,5:5,1:1,100:100,87:87,67:67,11:11},\"fileName\":\"first.jpg\"}",
            "{\"bst\":{897:4,2839:8,192:10,12:5,56:1,6788:100,444:87,23232:67,90000:11},\"fileName\":\"second.jpg\"}"
    })
    public void testDraw(String parameterJsonString) {
        JSONObject jObject = new JSONObject(parameterJsonString);
        for (String key : jObject.getJSONObject("bst").keySet()) {
            myBinarySearchTree.put(Integer.parseInt(key), jObject.getJSONObject("bst").getInt(key));
        }
        String filePath = "src\\test\\java\\ieening\\output\\" + jObject.getString("fileName");
        ((AbstractMyBinarySearchTree) myBinarySearchTree).draw(filePath);
    }
}
