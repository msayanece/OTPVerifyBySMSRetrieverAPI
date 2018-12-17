package com.android.rnd.newwayotpverify.newwayotpverifysdk;

import com.google.gson.annotations.Expose;

public class GeneralResponse {
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
