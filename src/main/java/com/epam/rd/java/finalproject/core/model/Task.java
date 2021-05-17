package com.epam.rd.java.finalproject.core.model;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    private int id;
    private String descShort;
    private String descFull;
    private boolean status;
    private List<Record> teasRecords;
}
