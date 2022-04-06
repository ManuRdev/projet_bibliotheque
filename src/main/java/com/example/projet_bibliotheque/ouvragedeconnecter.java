package com.example.projet_bibliotheque;

public class ouvragedeconnecter {

    private String titrec;
    private String auteurc;
    private String présentationc;
    private String parutionc;
    private String colonnec;
    private String rangéec;

    public ouvragedeconnecter(String titrec, String auteurc, String présentationc, String parutionc, String colonnec, String rangéec) {
        this.titrec = titrec;
        this.auteurc = auteurc;
        this.présentationc = présentationc;
        this.parutionc = parutionc;
        this.colonnec = colonnec;
        this.rangéec = rangéec;
    }

    public String getTitrec() {
        return titrec;
    }

    public String getAuteurc() {
        return auteurc;
    }

    public String getPrésentationc() {
        return présentationc;
    }

    public String getParutionc() {
        return parutionc;
    }

    public String getColonnec() {
        return colonnec;
    }

    public String getRangéec() {
        return rangéec;
    }

    public void setTitrec(String titrec) {
        this.titrec = titrec;
    }

    public void setAuteurc(String auteurc) {
        this.auteurc = auteurc;
    }

    public void setPrésentationc(String présentationc) {
        this.présentationc = présentationc;
    }

    public void setParutionc(String parutionc) {
        this.parutionc = parutionc;
    }

    public void setColonnec(String colonnec) {
        this.colonnec = colonnec;
    }

    public void setRangéec(String rangéec) {
        this.rangéec = rangéec;
    }
}
