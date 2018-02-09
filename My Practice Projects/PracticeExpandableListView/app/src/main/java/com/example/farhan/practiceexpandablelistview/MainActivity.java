package com.example.farhan.practiceexpandablelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    CustomExpandableListViewAdapter expLvAdapter;
    List<String> itemHeader;
    HashMap<String, List<String>> itemChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.lvExp);
        initData();
        expLvAdapter = new CustomExpandableListViewAdapter(this, itemHeader, itemChild);
        expandableListView.setAdapter(expLvAdapter);
    }

    private void initData() {
        itemHeader = new ArrayList<>();
        itemChild = new HashMap<>();

        itemHeader.add("Animals");
        itemHeader.add("Fruits");
        itemHeader.add("Vegetables");
        itemHeader.add("Nuts");

        List<String> animals = new ArrayList<>();
        animals.add("Loin");
        animals.add("Tiger");
        animals.add("Cheetah");
        animals.add("Liger");
        animals.add("A");

        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Graphs");
        fruits.add("Guava");
        fruits.add("B");

        List<String> vegetables = new ArrayList<>();
        vegetables.add("Cabbage");
        vegetables.add("Carrot");
        vegetables.add("Chili");
        vegetables.add("Lemons");
        vegetables.add("C");

        List<String> nuts = new ArrayList<>();
        nuts.add("peanuts");
        nuts.add("Almond");
        nuts.add("ChesNut");
        nuts.add("CocoNuts");
        nuts.add("D");

        itemChild.put(itemHeader.get(0), animals);
        itemChild.put(itemHeader.get(1), fruits);
        itemChild.put(itemHeader.get(2), vegetables);
        itemChild.put(itemHeader.get(3), nuts);
    }
}
