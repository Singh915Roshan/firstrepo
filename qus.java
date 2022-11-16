2. Two Sum II – Input array is sorted
Code it now: Coming soon! Difficulty: Medium, Frequency: N/A
Question:
Similar to Question [1. Two Sum], except that the input array is already sorted in
ascending order.
Solution:
Of course we could still apply the [Hash table] approach, but it costs us O(n) extra space,
plus it does not make use of the fact that the input is already sorted.
O(n log n) runtime, O(1) space – Binary search:
For each element x, we could look up if target – x exists in O(log n) time by applying
binary search over the sorted array. Total runtime complexity is O(n log n).
public int[] twoSum(int[] numbers, int target) {
// Assume input is already sorted.
for (int i = 0; i < numbers.length; i++) {
int j = bsearch(numbers, target - numbers[i], i + 1);
if (j != -1) {
return new int[] { i + 1, j + 1 };
}
}
throw new IllegalArgumentException("No two sum solution");
}
private int bsearch(int[] A, int key, int start) {
int L = start, R = A.length - 1;
while (L < R) {
int M = (L + R) / 2;
if (A[M] < key) {
L = M + 1;
} else {
R = M;
}
}
return (L == R && A[L] == key) ? L : -1;
}
O(n) runtime, O(1) space – Two pointers:
Let’s assume we have two indices pointing to the ith and jth elements, Ai and Aj
respectively. The sum of Ai and Aj could only fall into one of these three possibilities:
i. Ai + Aj > target. Increasing i isn’t going to help us, as it makes the sum even
bigger. Therefore we should decrement j.
ii. Ai + Aj < target. Decreasing j isn’t going to help us, as it makes the sum even
smaller. Therefore we should increment i.
iii. Ai + Aj == target. We have found the answer.
7
public int[] twoSum(int[] numbers, int target) {
// Assume input is already sorted.
int i = 0, j = numbers.length - 1;
while (i < j) {
int sum = numbers[i] + numbers[j];
if (sum < target) {
i++;
} else if (sum > target) {
j--;
} else {
return new int[] { i + 1, j + 1 };
}
}
throw new IllegalArgumentException("No two sum solution");
}
8
3. Two Sum III – Data structure design
Code it now: Coming soon! Difficulty: Easy, Frequency: N/A
Question:
Design and implement a TwoSum class. It should support the following operations: add
and find.
add(input) – Add the number input to an internal data structure.
find(value) – Find if there exists any pair of numbers which sum is equal to the value.
For example,
add(1); add(3); add(5); find(4)  true; find(7)  false
Solution:
add – O(n) runtime, find – O(1) runtime, O(n2) space – Store pair sums in hash table:
We could store all possible pair sums into a hash table. The extra space needed is in the
order of O(n2). You would also need an extra O(n) space to store the list of added
numbers. Each add operation essentially go through the list and form new pair sums that
go into the hash table. The find operation involves a single hash table lookup in O(1)
runtime.
This method is useful if the number of find operations far exceeds the number of add
operations.
add – O(log n) runtime, find – O(n) runtime, O(n) space – Binary search + Two
pointers:
Maintain a sorted array of numbers. Each add operation would need O(log n) time to
insert it at the correct position using a modified binary search (See Question [48. Search
Insert Position]). For find operation we could then apply the [Two pointers] approach in
O(n) runtime.
add – O(1) runtime, find – O(n) runtime, O(n) space – Store input in hash table:
A simpler approach is to store each input into a hash table. To find if a pair sum exists,
just iterate through the hash table in O(n) runtime. Make sure you are able to handle
duplicates correctly.
9
public class TwoSum {
private Map<Integer, Integer> table = new HashMap<>();
public void add(int input) {
int count = table.containsKey(input) ? table.get(input) : 0;
table.put(input, count + 1);
}
public boolean find(int val) {
for (Map.Entry<Integer, Integer> entry : table.entrySet()) {
int num = entry.getKey();
int y = val - num;
if (y == num) {
// For duplicates, ensure there are at least two individual numbers.
if (entry.getValue() >= 2) return true;
} else if (table.containsKey(y)) {
return true;
}
}
return false;
}
}
10
4. Valid Palindrome
Code it now: https://oj.leetcode.com/problems/valid-palindrome/ Difficulty: Easy, Frequency: Medium
Question:
Given a string, determine if it is a palindrome, considering only alphanumeric characters
and ignoring cases.
For example,
"A man, a plan, a canal: Panama" is a palindrome.
"race a car" is not a palindrome.
Example Questions Candidate Might Ask:
Q: What about an empty string? Is it a valid palindrome?
A: For the purpose of this problem, we define empty string as valid palindrome.
Solution:
O(n) runtime, O(1) space:
The idea is simple, have two pointers – one at the head while the other one at the tail.
Move them towards each other until they meet while skipping non-alphanumeric
characters.
Consider the case where given string contains only non-alphanumeric characters. This is
a valid palindrome because the empty string is also a valid palindrome.
public boolean isPalindrome(String s) {
int i = 0, j = s.length() - 1;
while (i < j) {
while (i < j && !Character.isLetterOrDigit(s.charAt(i))) i++;
while (i < j && !Character.isLetterOrDigit(s.charAt(j))) j--;
if (Character.toLowerCase(s.charAt(i))
!= Character.toLowerCase(s.charAt(j))) {
return false;
}
i++; j--;
}
return true;
}