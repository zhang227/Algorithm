/* Given a list of numbers, return all possible permutations.
Notice: You can assume that there is no duplicate numbers in the list.

Example:
For nums = [1,2,3], the permutations are:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
] */

class Solution {
    /**
     * @param nums: A list of integers.
     * @return: A list of permutations.
     */
     
    // Recursion 方法
    // Ref: http://www.jiuzhang.com/solutions/permutations/
    public List<List<Integer>> permute(int[] nums) {
        
        ArrayList<List<Integer>> result = new ArrayList<List<Integer>>();
        
        // 注意！
        // nums 为空时，结果是空的 List of Lists
        // nums 不为空但长度为0时，结果是长度为 1 的List of Lists，其内容为一个空List
        if (nums == null) {
            return result;
        }
        else if (nums.length == 0) {
            result.add(new ArrayList<Integer>());
            return result;
        }
        
        findPermutations(nums, new ArrayList<Integer>(), result);
        return result;
    }
    private void findPermutations(int[] nums,
                                  ArrayList<Integer> curList,
                                  ArrayList<List<Integer>> result) {
                                      
        if (curList.size() == nums.length) {
            // 注意！！一定要复制！！
            // 因为 curList 在其他分支里还会被反复利用！！不是到这里就使命终结了！！
            result.add(new ArrayList<Integer>(curList));
            return;
        }
              
        // 整个算法的精华在这里！把每个数都轮流放进去！！！
        // 然后用ArrayList.contains 函数来判断是否有过这个数了！！！
        for (int n : nums) {
            // ArrayList 也有 contains 函数！不光是 HashSet 有！
            if (!curList.contains(n)) {
                curList.add(n);
                findPermutations(nums, curList, result);
                curList.remove(curList.size() - 1);
            }
        }                       
    }
  
  
    // Non-Recursion 方法
    // Ref: https://discuss.leetcode.com/topic/6377/my-ac-simple-iterative-java-python-solution
    /* the basic idea is, to permute n numbers, we can add the nth number into the resulting List<List<Integer>> from the n-1 numbers, 
     in every possible position.
     For example, if the input num[] is {1,2,3}: First, add 1 into the initial List<List<Integer>> (let's call it "answer").
     Then, 2 can be added in front or after 1. So we have to copy the List<Integer> in answer (it's just {1}), 
     add 2 in position 0 of {1}, then copy the original {1} again, and add 2 in position 1. 
     Now we have an answer of {{2,1},{1,2}}. There are 2 lists in the current answer.
     Then we have to add 3. first copy {2,1} and {1,2}, add 3 in position 0; then copy {2,1} and {1,2}, and add 3 into position 1, 
     then do the same thing for position 3. Finally we have 2*3=6 lists in answer, which is what we want. */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        
        if (nums == null) {
            return results;
        }
        else if (nums.length == 0) {
            results.add(new ArrayList<Integer>());
            return results;
        }
        
        int len = nums.length;
        ArrayList<Integer> firstList = new ArrayList<>();
        firstList.add(nums[0]);
        results.add(firstList);
        
        for (int i = 1; i < len; i++) {
            int curValue = nums[i];
            List<List<Integer>> tmpResults = new ArrayList<List<Integer>>();
            
            for (List<Integer> curList : results) {
                int curListLen = curList.size();
                
                for (int j = 0; j <= curListLen; j++) {
                    List<Integer> tmpList = new ArrayList<>(curList);
                    tmpList.add(j, curValue);
                    tmpResults.add(tmpList);
                }
            }
            results = tmpResults;
        }
        return results;
    }
  
}