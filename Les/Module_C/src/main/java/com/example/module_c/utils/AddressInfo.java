package com.example.module_c.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddressInfo {
    private String filePath;
    private ArrayList<String[]> allInfo;

    public AddressInfo(String filePath) {
        this.filePath = filePath;
        allInfo = getDataOrigin(this.filePath);
    }

    private ArrayList<String[]> getDataOrigin(String filePath) {
        this.filePath = filePath;
        ArrayList<String[]> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                String[] string = str.split(",");
                allInfo.add(string);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<String> getProvinces() {
        Set<String> buf = new HashSet<>();
        for (String[] str : this.allInfo) {
            buf.add(str[0]);
        }
        ArrayList<String> strings = new ArrayList<>();
        for (String s : buf) {
            strings.add(s);
        }
        return strings;
    }

    public ArrayList<String> getCitiesByProvince(String province) {
        ArrayList<String> cities = new ArrayList<>();
        for (String[] str : this.allInfo) {
            if (str[0].equals(province)) {
                cities.add(str[1]);
            }
        }
        return cities;
    }

    public ArrayList<String> getCountyByCity(String province, String city) {
        ArrayList counties = new ArrayList();
        for (String[] str : this.allInfo) {
            if (str[0].equals(province) && str[1].equals(city)) {
                counties.add(str[2]);
            }
        }
        return counties;
    }

}
