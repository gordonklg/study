/**
 Given an absolute path for a file (Unix-style), simplify it. Or in other words, convert it to the canonical path.

 In a UNIX-style file system, a period . refers to the current directory. Furthermore, a double period .. moves the directory up a level.

 Note that the returned canonical path must always begin with a slash /, and there must be only a single slash / between two directory names.
 The last directory name (if it exists) must not end with a trailing /. Also, the canonical path must be the shortest string representing the absolute path.

 Example 1:
 Input: "/home/"
 Output: "/home"
 Explanation: Note that there is no trailing slash after the last directory name.

 Example 2:
 Input: "/../"
 Output: "/"
 Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.

 Example 3:
 Input: "/home//foo/"
 Output: "/home/foo"
 Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.

 Example 4:
 Input: "/a/./b/../../c/"
 Output: "/c"

 Example 5:
 Input: "/a/../../b/../c//.//"
 Output: "/c"

 Example 6:
 Input: "/a//b////c/d//././/.."
 Output: "/a/b/c"
 */
package gordon.study.algorithm.leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class MLeet071SimplifyPath {

    public static void main(String[] args) {
        MLeet071SimplifyPath inst = new MLeet071SimplifyPath();
        System.out.println(inst.simplifyPath(""));
        System.out.println(inst.simplifyPath("/../"));
        System.out.println(inst.simplifyPath("/../abc"));
        System.out.println(inst.simplifyPath("/a/./b/../../c/"));
        System.out.println(inst.simplifyPath("/a//b////c/d//././/.."));
        System.out.println(inst.simplifyPath("/a/../../b/../c//.//"));
    }

    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] tokens = path.split("/");
        for (String token : tokens) {
            token = token.trim();
            if (token.equals(".")) {
                // do nothing
            } else if (token.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (token.length() > 0) {
                stack.push(token);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append("/").append(stack.removeLast());
        }
        return sb.length() > 0 ? sb.toString() : "/";
    }
}
