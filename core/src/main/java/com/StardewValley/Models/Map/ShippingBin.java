package com.StardewValley.Models.Map;

import java.io.Serializable;

import java.util.ArrayList;

public class ShippingBin extends TileObject implements Serializable{
    private ArrayList<Double> refundAmount;
    private int level = 0;
    {
        refundAmount = new ArrayList<>();
        refundAmount.add(1.0);
        refundAmount.add(1.25);
        refundAmount.add(1.5);
        refundAmount.add(2.0);
    }
    public ShippingBin() {
        level = 0;
    }

    public double getRefund(){
        return refundAmount.get(level);
    }
    public ArrayList<Double> getRefundAmount() {
        return refundAmount;
    }

    public int getLevel() {
        return level;
    }
}
