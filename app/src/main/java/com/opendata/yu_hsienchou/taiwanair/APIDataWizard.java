package com.opendata.yu_hsienchou.taiwanair;

import java.util.ArrayList;

/**
 * 通用 API 資料精靈
 * @// TODO: 2018/11/4 用於所有的外部連結API
 * @author Yu-Hsien Chou  (小周)
 */
public interface APIDataWizard {
    public ArrayList<AirDataModel> getModel();
    public String getResult();
}
