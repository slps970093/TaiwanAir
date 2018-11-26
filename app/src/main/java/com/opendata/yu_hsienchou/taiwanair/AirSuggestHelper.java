package com.opendata.yu_hsienchou.taiwanair;

/**
 * 空氣建議助手
 * @see http://www.tnepb.gov.tw/AIR_PM25.htm
 *
 */
public class AirSuggestHelper {

    /**
     * 取得等級分類
     * @param value
     * @return String
     */
    public String getCategory(double value){
        if( (int) value >= 0 && (int) value <= 35){
            return "低";
        }
        if( (int) value >= 36 && (int) value <= 53 ){
            return "中";
        }
        if( (int) value >= 54 && (int) value <= 70){
            return "高";
        }
        return "非常高";
    }

}
