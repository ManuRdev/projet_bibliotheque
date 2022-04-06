package com.example.projet_bibliotheque;

public class ouvrage {
    private String titrec;
    private String auteurc;
    private String présentationc;
    private String parutionc;
    private Integer colonnec;
    private Integer rangéec;
    private String resumec;
    private String urlc;
    private String etatc;

    public ouvrage(String titrec, String auteurc, String présentationc, String parutionc, Integer colonnec, Integer rangéec, String resumec, String urlc, String etatc) {
        this.titrec = titrec;
        this.auteurc = auteurc;
        this.présentationc = présentationc;
        this.parutionc = parutionc;
        this.colonnec = colonnec;
        this.rangéec = rangéec;
        this.resumec = resumec;
        this.urlc = urlc;
        this.etatc = etatc;
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

    public Integer getColonnec() {
        return colonnec;
    }

    public Integer getRangéec() {
        return rangéec;
    }

    public String getResumec() {
        return resumec;
    }

    public String getUrlc() {
        return urlc;
    }

    public String getEtatc() {
        return etatc;
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

    public void setColonnec(Integer colonnec) {
        this.colonnec = colonnec;
    }

    public void setRangéec(Integer rangéec) {
        this.rangéec = rangéec;
    }

    public void setResumec(String resumec) {
        this.resumec = resumec;
    }

    public void setUrlc(String urlc) {
        this.urlc = urlc;
    }

    public void setEtatc(String etatc) {
        this.etatc = etatc;
    }
}

