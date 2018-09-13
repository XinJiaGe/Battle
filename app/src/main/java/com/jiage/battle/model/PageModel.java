package com.jiage.battle.model;

import android.widget.Toast;

import com.jiage.battle.common.SDActivityManager;

public class PageModel {

    private int pi = 1;
    private int pc = 0;
    private int ps;
    private int total;

    public int getPs() {
        return ps;
    }

    public void setPs(int page_size) {
        this.ps = page_size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int data_total) {
        this.total = data_total;
    }

    public int getPi() {
        return pi;
    }

    public void setPi(int page) {
        this.pi = page;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int page_total) {
        this.pc = page_total;
    }

    public boolean increment() {
        pi++;
        if (pi > pc) {
            pi--;
            return false;
        } else {
            return true;
        }
    }

    public void resetPage() {
        pi = 1;
    }

    public void update(PageModel model) {
        if (model != null) {
            this.pi = model.getPi();
            this.pc = model.getPc();
        }
    }

    public void update(int pi, int pc, int ps, int total, boolean toast) {
        updateToast(pi, pc, ps, total, toast);
    }

    public void update(int pi, int pc, int ps, int total) {
        update(pi, pc, ps, total, false);
    }

    public void updateToast(int pi, int pc, int ps, int total, boolean toast) {
        setPi(pi);
        setPc(pc);
        setPs(ps);
        setTotal(total);
        if (total == 0 && toast) {
            Toast.makeText(SDActivityManager.getInstance().getLastActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
        }
    }
}
