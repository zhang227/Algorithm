/* Given two strings s and t which consist of only lowercase letters.
String t is generated by random shuffling string s and then add one more letter at a random position.
Find the letter that was added in t.
*/


public class FindDifferentChar {
    
    // 较巧妙、较快的方法：用char 'a'到'z'的ASCII序号来做。搞一个26个slot的int数组，然后加加减减
    //
    public char findTheDifference_ByCharASCII(String s, String t) {
        
        int[] alpha = new int[26];
        
        for (char c : s.toCharArray())
            // 这里很巧妙，用 c - 'a' 来通过char c的ASCII序号而得到c在int数组中应该对应的位置（0 - 25）
            alpha[ c - 'a' ]++;

        for (char c : t.toCharArray()) {
           
            // 简略表达法，--在前 表示先减了再说
            if (--alpha[c - 'a'] < 0)
                return c;
        }
        return 0;
    }
    
    
    // 很巧妙、较快的方法：用位运算“按位或”来做，同理于“找那个出现了两次的int”的问题的方法
    // 注意把 char 作为 二进制数 来操作的思想
    //
    public char findTheDifference_ByBitXOR(String s, String t) {
        
        char targetChar = 0; // 把 char 作为 二进制数
        
        for (char c : s.toCharArray()) {
            targetChar ^= c;
        }
        for (char c : t.toCharArray()) {
            targetChar ^= c;
        }
        return targetChar; // 最后return的时候会自动把二进制数转回为char
    }
   
    
    // 最没有华彩的方法：用 HashMap
    //
    public char findTheDifference_ByHashMap(String s, String t) {
        
        HashMap<Character, Integer> hashMap_S = new HashMap<Character, Integer>();
        
        for (char c : s.toCharArray())
        {
            if (hashMap_S.containsKey(c))
                hashMap_S.put(c, hashMap_S.get(c)+1);
            else
                hashMap_S.put(c, 1);
        }
        
        for (char c : t.toCharArray())
        {
            if (hashMap_S.containsKey(c) == false)
                return c;
            else
            {
                hashMap_S.put(c, hashMap_S.get(c)-1);
                
                if (hashMap_S.get(c) == -1)
                    return c;
            }
        }
        
        // input string incorrect
        return " ".toCharArray()[0];
    }
}
