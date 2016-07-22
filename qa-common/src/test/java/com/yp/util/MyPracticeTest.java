package com.yp.util;

import org.junit.Test;

import java.util.*;

public class MyPracticeTest {


    @Test
    public void testBinarySearch() throws Exception {
        int[] array = {1, 3, 5, 7, 9, 11};
        int first = 0;
        int last = array.length - 1;
        int middle = (first + last) / 2;
        int search = 11;

        while (first <= last) {
            if (array[middle] < search)
                first = middle + 1;
            else if (array[middle] == search) {
                System.out.println(search + " found at location " + (middle + 1) + ".");
                break;
            } else {
                last = middle - 1;
            }
            middle = (first + last) / 2;
        }

        if (first > last) {
            System.out.println(search + " is not present in the list.\n");
        }
    }

    @Test
    public void testStringValidation() throws Exception {
        String text = "(11)[222]{}";
        boolean status = validateString(text);
        System.out.println(status);
    }

    private boolean validateString(String text) {
        Stack stack = new Stack();

        for (int i = 0; i < text.length() - 1; i++) {
            if (String.valueOf(text.charAt(i)).equalsIgnoreCase("(") || String.valueOf(text.charAt(i)).equalsIgnoreCase("{") || String.valueOf(text.charAt(i)).equalsIgnoreCase("[")) {
                stack.push(text.charAt(i));
            } else if (String.valueOf(text.charAt(i)).equalsIgnoreCase(")")) {
                if (!String.valueOf(stack.pop()).equalsIgnoreCase("(")) {
                    return false;
                }
            } else if (String.valueOf(text.charAt(i)).equalsIgnoreCase("}")) {
                if (!String.valueOf(stack.pop()).equalsIgnoreCase("{")) {
                    return false;
                }
            } else if (String.valueOf(text.charAt(i)).equalsIgnoreCase("]")) {
                if (!String.valueOf(stack.pop()).equalsIgnoreCase("[")) {
                    return false;
                }
            }
        }

        return stack.empty();
    }

    @Test
    public void test2Sum() throws Exception {
        int target = 18;
        int[] x = {2, 7, 11, 15};
        int[] result = new int[2];

//        for (int i = 0; i < x.length; i++) {
//            for (int j = i + 1; j < x.length; j++) {
//                if (x[i] + x[j] == target) {
//                    result[0] = i + 1;
//                    result[1] = j + 1;
//                }
//            }
//        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < x.length; i++) {
            if (map.containsKey(x[i])) {
                int index = map.get(x[i]);
                result[0] = index + 1;
                result[1] = i + 1;
                break;
            } else {
                map.put(target - x[i], i);
            }
        }

        System.out.println(Arrays.toString(result));
    }

    @Test
    public void testCountDup() throws Exception {
        int[] x = {1, 2, 3, 1, 4, 5, 1, 2, 3, 3, 4, 5, 5};

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : x) {
            Integer count = map.get(num);
            if (count == null) {
                map.put(num, 1);
            } else {
                map.put(num, ++count);
            }
        }

        for (int i = 0; i < map.size(); i++) {
            System.out.println("number: " + (i + 1) + " has count: " + map.get(i + 1));
        }
    }

    @Test
    public void testQuickSort() throws Exception {
        int[] x = {9, 2, 7, 1, 10};

        int low = 0;
        int high = x.length - 1;
        quickSort(x, low, high);
        System.out.print(Arrays.toString(x));
    }

    private void quickSort(int[] arr, int low, int high) {
        if (arr == null && arr.length == 0) {
            return;
        }

        if (low >= high) {
            return;
        }

        int middle = low + (high - low) / 2;
        int pivot = arr[middle];

        int i = low;
        int j = high;

        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }

            while (arr[j] > pivot) {
                j--;
            }

            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }

            if (low < j) {
                quickSort(arr, low, j);
            }

            if (high > i) {
                quickSort(arr, i, high);
            }
        }
    }

    @Test
    public void testPalindromic() throws Exception {
        String[] numbers = {"11", "21", "181", "12321"};

        for (String number : numbers) {
            Stack stack = new Stack();

            for (int j = 0; j <= number.length() - 1; j++) {
                stack.push(number.charAt(j));
            }

            String palindromic = "";
            while (!stack.empty()) {
                palindromic += stack.pop();
            }

            if (palindromic.equalsIgnoreCase(number)) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        }
    }

    @Test
    public void testSummation() throws Exception {
        int number = 10;
        Stack stack = new Stack();
        int sum = 0;

        for (int i = 1; i <= number; i++) {
            stack.push(i);
        }

        while (!stack.empty()) {
            int value = (int) stack.pop();
            sum += value;
        }

        System.out.print(sum);
    }

    @Test
    public void testStringReverse() throws Exception {
        String text = "hello world";

        for (int i = text.length() - 1; i >= 0; i--) {
            System.out.print(text.charAt(i));
        }
    }

    @Test
    public void testStackStringReverse() throws Exception {
        String text = "hello world";
        Stack stack = new Stack();
        String output = "";

        for (int i = 0; i < text.length(); i++) {
            stack.add(text.charAt(i));
        }

        while (!stack.empty()) {
            output += stack.pop();
        }

        System.out.print(output);
    }

    @Test
    public void testRemoveFromLinkedList() throws Exception {
        LinkedList list = new LinkedList();
        list.addFirst("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");
        list.subList(1, 3).clear();

        System.out.println(list);
    }

    @Test
    public void testLinkedList() throws Exception {
        LinkedList list = new LinkedList();
        list.addFirst("A");
        list.add("B");
        list.add("C");

        System.out.println(list);
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        System.out.println(list.get(1));
    }

    @Test
    public void testFindDup() throws Exception {
        String a = "apple";
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= a.length() - 1; i++) {
            list.add(String.valueOf(a.charAt(i)));
        }

        boolean notFoundDup = true;
        while (notFoundDup) {
            for (int j = 0; j < list.size() - 1; j++) {
                String current = list.get(j);
                String next = list.get(j + 1);

                if (current.equalsIgnoreCase(next)) {
                    notFoundDup = false;
                    break;
                }
            }
        }

        System.out.print(notFoundDup);
    }

    @Test
    public void testBubbleSort() throws Exception {
        int[] num = {2, 5, 0, 1};
        boolean flag = true;
        int temp;

        while (flag) {
            flag = false;
            for (int i = 0; i < num.length - 1; i++) {
                if (num[i] > num[i + 1]) {
                    temp = num[i];
                    num[i] = num[i + 1];
                    num[i + 1] = temp;
                    flag = true;
                }
            }
        }

        System.out.print(Arrays.toString(num));
    }

    @Test
    public void testInsertionSort() throws Exception {
        int[] num = {2, 5, 0, 1};
        int temp;

        for (int i = 1; i < num.length; i++) {
            for (int j = i; j > 0; j--) {
                if (num[j] < num[j - 1]) {
                    temp = num[j];
                    num[j] = num[j - 1];
                    num[j - 1] = temp;
                }
            }
        }

        System.out.print(Arrays.toString(num));
    }

    @Test
    public void testArraySort() throws Exception {
        Integer[] array = {0, 1, 1, 0, 1, 2, 3, 3, 4, 6, 6, 6, 6, 6, 4, 4, 5, 5, 7, 7, 8, 9, 10};
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : array) {
            Integer count = map.get(num);
            if (count == null) {
                map.put(num, 1);
            } else {
                map.put(num, ++count);
            }
        }

        int startIndex = 0;
        int endIndex;
        int[] newArray = new int[array.length];
        for (int j : map.keySet()) {
            int v = map.get(j);
            endIndex = v + startIndex;
            for (int k = startIndex; k < endIndex; k++) {
                newArray[k] = j;
                startIndex = k + 1;
            }
        }

        System.out.print(Arrays.toString(newArray));
    }
}