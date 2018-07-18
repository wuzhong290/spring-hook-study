package com.demo.javabase;
import com.alibaba.fastjson.annotation.JSONField;

public class T_Prize
{
    private String PrizeID;
    private String PrizeName;
    private int Stock;
    private int Limit;
    private float Probability;

    @JSONField(name="PrizeID")
    public String getPrizeID()
    {
        return this.PrizeID;
    }

    public void setPrizeID(String prizeID)
    {
        this.PrizeID = prizeID;
    }

    @JSONField(name="PrizeName")
    public String getPrizeName()
    {
        return this.PrizeName;
    }

    public void setPrizeName(String prizeName)
    {
        this.PrizeName = prizeName;
    }

    @JSONField(name="Stock")
    public int getStock()
    {
        return this.Stock;
    }

    public void setStock(int stock)
    {
        this.Stock = stock;
    }

    @JSONField(name="Limit")
    public int getLimit()
    {
        return this.Limit;
    }

    public void setLimit(int limit)
    {
        this.Limit = limit;
    }

    @JSONField(name="Probability")
    public float getProbability()
    {
        return this.Probability;
    }

    public void setProbability(float probability)
    {
        this.Probability = probability;
    }
}
