package com.example.virussafeagro.uitilities;

import java.util.ArrayList;
import java.util.List;

public class DataComparison {

    public static boolean checkTwoListHaveSameItems(List<String> list1, List<String> list2) {
        if (list1 == list2) {
            return false;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        List<Integer> checkedList1IndexList = new ArrayList<>();
        List<Integer> checkedList2IndexList = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for(int i2 = 0; i2 < list2.size(); i2++) {
                if (list1.get(i1).equals(list2.get(i2)) && (!checkedList1IndexList.contains(i1) || !checkedList2IndexList.contains(i2))) {
                    checkedList1IndexList.add(i1);
                    checkedList2IndexList.add(i2);
                }
            }
        }
        return (checkedList1IndexList.size() == list1.size()) && (checkedList2IndexList.size() == list2.size());
    }
}
