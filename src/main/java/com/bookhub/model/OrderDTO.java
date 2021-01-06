package com.bookhub.model;

import java.util.List;

public class OrderDTO {
    private int addrId;
    private List<PurchaseDTO> data;

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public List<PurchaseDTO> getData() {
        return data;
    }

    public void setData(List<PurchaseDTO> data) {
        this.data = data;
    }
}
