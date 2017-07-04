/* Given three arrays sorted in ascending order. Pull one number from each array to form a coordinate <x,y,z> in a 3D space. 
Find the coordinates of the points that is k-th closest to <0,0,0>.
We are using euclidean distance here.
Assumptions:
The three given arrays are not null or empty
K >= 1 and K <= a.length * b.length * c.length
Return:
a size 3 integer list, the first element should be from the first array, 
the second element should be from the second array and the third should be from the third array

Examples:
A = {1, 3, 5}, B = {2, 4}, C = {3, 6}
The closest is <1, 2, 3>, distance is sqrt(1 + 4 + 9)
The 2nd closest is <3, 2, 3>, distance is sqrt(9 + 4 + 9)  */


/* 思路：min heap。heap里的每一个元素是含有3个index。在heap里排列的标准是距离原点的距离从小到大。
initial state: 3个index：i = 0, j = 0, l = 0
expansion / generation rule: expand <i, j, l>
    1. generate <i+1, j, l>
    2. generate <i, j+1, l>
    3. generate <i, j, l+1>
termination condition: pop k 次，然后跟踪最后一次即第k次pop出来的那个，就是我们要的答案
    
去重：比如，<2, 2, 1> can be generated by <1, 2, 1> or <2, 1, 1>
所以可以用 HashSet 来存所有visited过的list，每个list存3个整数即3个indexes。
注意 ！！！ 这里要用 List ！！！ 不要用 Array ！！！ 具体见下文。

时间：O(k logk)
因为要从heap里pop k次，每一次需要时间 logk。  */

// 我的方法，代码看起来比较长，但其实思路很朴素，很简明
public class Solution {
  
  public List<Integer> closest(int[] a, int[] b, int[] c, int k) {
    List<Integer> result = new ArrayList<>();
    
    PriorityQueue<List<Integer>> indexTriplets = new PriorityQueue<>(k, new Comparator<List<Integer>>(){
      @Override
      public int compare(List<Integer> l1, List<Integer> l2) {
        long d1 = distance(l1, a, b, c);
        long d2 = distance(l2, a, b, c);
        if (d1 == d2) {
          return 0;
        }
        return d1 < d2 ? -1 : 1;
      }
    });
    
    // 特别 特别 特别 注意 ！！！
    // HashSet 和 HashMap 里，如果要装几个同类元素所组成的Collection，要用 List ！！！ 不要用 Array ！！！
    // 因为：
    //
    // Arrays use the default identity-based Object.hashCode() implementation and 
    // there's no way you can override that. 
    // So don't use Arrays as keys in a HashMap/HashSet ！！！
    //
    // the equals() method of List had already been overridden ！！！
    // so we don't need to worry about it ！！！
    // it is comparing the actual elements' values in the List
    Set<List<Integer>> visited = new HashSet<>();
    
    List<Integer> curIndexes = new ArrayList<>();
    curIndexes.add(0);
    curIndexes.add(0);
    curIndexes.add(0);
      
    indexTriplets.offer(curIndexes);
    visited.add(curIndexes);
    
    int remain = k;
    while (remain > 0) {
        
      curIndexes = indexTriplets.poll();
      int i1 = curIndexes.get(0);
      int i2 = curIndexes.get(1);
      int i3 = curIndexes.get(2);
      
      List<Integer> newIndexes = new ArrayList<Integer>();
      newIndexes.add(i1 + 1);
      newIndexes.add(i2);
      newIndexes.add(i3);
      if (i1 + 1 < a.length && !visited.contains(newIndexes)) {
        indexTriplets.offer(newIndexes);
        visited.add(newIndexes);
      }
      
      newIndexes = new ArrayList<Integer>();
      newIndexes.add(i1);
      newIndexes.add(i2 + 1);
      newIndexes.add(i3);
      if (i2 + 1 < b.length && !visited.contains(newIndexes)) {
        indexTriplets.offer(newIndexes);
        visited.add(newIndexes);
      }
      
      newIndexes = new ArrayList<Integer>();
      newIndexes.add(i1);
      newIndexes.add(i2);
      newIndexes.add(i3 + 1);
      if (i3 + 1 < c.length && !visited.contains(newIndexes)) {
        indexTriplets.offer(newIndexes);
        visited.add(newIndexes);
      }
      
      remain --;
    }
    
    result.add(a[curIndexes.get(0)]);
    result.add(b[curIndexes.get(1)]);
    result.add(c[curIndexes.get(2)]);
    return result;
  }
  
  private long distance(List<Integer> indexes, int[] a, int[] b, int[] c) {
    long dist = 0;
    dist += a[indexes.get(0)] * a[indexes.get(0)];
    dist += b[indexes.get(1)] * b[indexes.get(1)];
    dist += c[indexes.get(2)] * c[indexes.get(2)];
    return dist;
  }
}