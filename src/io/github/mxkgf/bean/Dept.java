package io.github.mxkgf.bean;

import java.util.Objects;

public class Dept {
    private int deptno;
    private String dname;
    private String loc;

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dept dept)) return false;
        return getDeptno() == dept.getDeptno() && Objects.equals(getDname(), dept.getDname()) && Objects.equals(getLoc(), dept.getLoc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeptno(), getDname(), getLoc());
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
