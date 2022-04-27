package github.setsnuaa.core.proxy;

import java.util.*;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/4/27 18:56
 * @description:
 */
public class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        ArrayList<TreeNode> ret = deleteLevel(root, (ArrayList<Integer>) list);
        for (TreeNode tmp : ret) {
            System.out.println(tmp.val);
        }
    }

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static ArrayList<TreeNode> deleteLevel(TreeNode root, ArrayList<Integer> a) {
        ArrayList<TreeNode> ans = new ArrayList<>();
        if (root == null || a.size() == 0) {
            ans.add(root);
            return ans;
        }

        int level = 1;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<List<TreeNode>> list = new ArrayList<>();
        List<TreeNode> tmp1 = new ArrayList<>();
        tmp1.add(root);
        list.add(tmp1);
        while (!queue.isEmpty()) {
            int cnt = queue.size();
            int sum = 0;
            List<TreeNode> tmp = new ArrayList<>();
            while (sum < cnt) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                    tmp.add(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    tmp.add(node.right);
                }
                sum++;
            }
            list.add(tmp);
        }
        HashSet<Integer> set = new HashSet<>();
        for (int num : a) {
            set.add(num);
            if (num > 1) {
                for (TreeNode tmp : list.get(num - 2)) {
                    tmp.left = null;
                    tmp.right = null;
                }
            }
        }
        if (!set.contains(1)) ans.add(root);
        for (int num : a) {
            if (num < list.size()) {
                for (TreeNode node : list.get(num)) {
                    ans.add(node);
                }
            }
        }
        return ans;
    }
}
