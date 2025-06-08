import java.util.*;

public class AlienDictionary {
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        int[] inDegree = new int[26];

        for (String word : words)
            for (char c : word.toCharArray())
                graph.putIfAbsent(c, new HashSet<>());

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i], w2 = words[i+1];
            int len = Math.min(w1.length(), w2.length());
            boolean found = false;

            for (int j = 0; j < len; j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    if (graph.get(w1.charAt(j)).add(w2.charAt(j))) {
                        inDegree[w2.charAt(j) - 'a']++;
                    }
                    found = true;
                    break;
                }
            }

            if (!found && w1.length() > w2.length()) return "";
        }

        Queue<Character> queue = new LinkedList<>();
        for (char c : graph.keySet()) {
            if (inDegree[c - 'a'] == 0)
                queue.offer(c);
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char curr = queue.poll();
            sb.append(curr);
            for (char nei : graph.get(curr)) {
                if (--inDegree[nei - 'a'] == 0)
                    queue.offer(nei);
            }
        }

        return sb.length() == graph.size() ? sb.toString() : "";
    }

    public static void main(String[] args) {
        AlienDictionary ad = new AlienDictionary();
        System.out.println(ad.alienOrder(new String[]{"wrt","wrf","er","ett","rftt"})); // Output: "wertf"
    }
}
