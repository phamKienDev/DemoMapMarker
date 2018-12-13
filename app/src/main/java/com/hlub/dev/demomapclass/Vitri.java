package com.hlub.dev.demomapclass;

public class Vitri {
    private long id;
    private String title;
    private long kinhdo;
    private long vido;

    public Vitri(long id, String title, long kinhdo, long vido) {
        this.id = id;
        this.title = title;
        this.kinhdo = kinhdo;
        this.vido = vido;
    }

    public Vitri() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(long kinhdo) {
        this.kinhdo = kinhdo;
    }

    public long getVido() {
        return vido;
    }

    public void setVido(long vido) {
        this.vido = vido;
    }
}
