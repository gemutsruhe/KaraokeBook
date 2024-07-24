package com.karaoke.karaokebook.data.cell;

import com.google.gson.annotations.SerializedName;
import com.karaoke.karaokebook.data.model.Folder;

public class FolderCellData implements CellData {
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private String name;
    @SerializedName("parent")
    private int parent;

    public FolderCellData(int id, String name, int parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public FolderCellData(Folder folder) {
        this.id = folder.id;
        this.name = folder.name;
        this.parent = folder.parent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
