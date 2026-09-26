import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String str = "hello everyone";

        HashMap<Character, Integer> map = new HashMap<>();

        for (char ch : str.toCharArray()) {
            if (ch == ' ') {
                continue;
            }

            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        System.out.println(map);
    }
}
