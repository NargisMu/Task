import java.util.*;

public class Main {
//добавить исключения
    public static void main(String[] args)  {
        List<String> list= new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        // разделяем последовательности по ]
        String[] string = scan.next().split("]");
        String[] s = new String[]{"{", "}", "(", ")"};
        boolean interrupt = true;
        while (interrupt) {
            outer: for (String value : string) {
                for (String item : s) {
                        if (value.contains(item)) {
                            System.out.println("Недопустимые символы");
                            interrupt = false;
                            break outer;
                        }
                    }
                for(int i= 0; i< value.length(); i++) {
                    if (Character.UnicodeBlock.of(value.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                        interrupt = false;
                        System.out.println("Недопустимые символы");
                        break outer;
                    }
                }
                list.add(value);
                if(list.size()== string.length) interrupt = false;
            }
        }
            searchSequence(list);
    }

    public static void searchSequence(List<String> list){
        HashMap<Integer, Integer> repeat = new HashMap<>();
        HashMap<Integer, String> sequence = new HashMap<>();
        List<Integer> number = new ArrayList<>();
        List<Integer> numberB = new ArrayList<>();
        int number1, number2, number3;
        int count = 0;
        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        list.removeIf(string -> string.equals(""));
        // поиск внутренних скобок и запись числа
        for(int i = 0; i < list.size();i++) {
            char[] s = list.get(i).toCharArray();
            if (Character.isDigit(s[0])) {
                if (s[1] == '[') {
                    number1 = Integer.parseInt(String.valueOf(s[0]));
                    number.add(number1);
                    repeat.put(i, number1);
                    count++;
                    if (list.get(i).length() > 3) {
                        if (s[3] != '[') {
                            for (char c : s) {
                                if (c != '[' && !Character.isDigit(c)) {
                                    str1 = str1 + c;
                                    sequence.put(i, str1);
                                }
                            }
                            str1 = "";
                        }
                    } else {
                        for (int j = 2; j < s.length; j++) {
                            str4 = str4 + s[j];
                            sequence.put(i, str4);
                        }
                        str4 = "";
                    }
                    //repeat.put(1, number1);
                    if (list.get(i).length() > 3 && s[3] == '[') {
                        number2 = Integer.parseInt(String.valueOf(s[2]));
                        number3 = number1 * number2;
                        repeat.put(i, number3);
                        count++;
                        for (char c : s) {
                            if (c != '[' && !Character.isDigit(c)) {
                                str2 = str2 + c;
                                sequence.put(i, str2);
                            }
                        }
                        str2 = "";
                    }
                    numberB.add(i, count);
                    count = 0;
                }
            }
            if (i >= 1){
                if (!Character.isDigit(s[0]) && numberB.get(i - 1) != 2) {
                    repeat.put(i, 1);

                    for (char c : s) {
                        if (c != '[' && !Character.isDigit(c)) {
                            str3 = str3 + c;
                            sequence.put(i, str3);
                            numberB.add(i,count);
                        }
                    }
                    str3 = "";
                }
            }
            if (i >= 1 && numberB.get(i-1)==2) {
                if (!list.get(i).contains("[")) {
                    repeat.put(i, number.get(i-1));
                    numberB.add(i,count);
                } else if (list.get(i).contains("[")){
                    repeat.put(i, number.get(i-1) * number.get(i));
                    numberB.add(i,count++);
                }
                for(char c : s){
                    if (c != '[' && !Character.isDigit(c)) {
                        str3 = str3 + c;
                        sequence.put(i, str3);
                    }
                }
                str3 = "";
            }
            s = null;
        }
        print(sequence, repeat);
    }
    public static void print(HashMap<Integer, String> sequence, HashMap<Integer, Integer> repeat) {
        String value4;
        for(Map.Entry<Integer, String> pair: sequence.entrySet()){
            for(Map.Entry<Integer, Integer> pair2: repeat.entrySet()) {
                int key1 = pair.getKey();
                String value1 = pair.getValue();
                int key2 = pair2.getKey();
                int value2 = pair2.getValue();
                if(key1==key2) {
                    value4 = new String(new char[value2]).replace("\0", value1);
                    System.out.print(value4);
                    value4 = "";
                }
            }
        }
    }
}
