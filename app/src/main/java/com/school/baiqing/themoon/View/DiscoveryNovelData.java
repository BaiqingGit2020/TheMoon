package com.school.baiqing.themoon.View;

import java.io.Serializable;
import java.util.List;

/**
 * 发现页小说数据
 *
 * @author Feng Zhaohao
 * Created on 2019/12/20
 */
public class DiscoveryNovelData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String listname = "TheMoon";
    private List<String> novelNameList;
    private List<String> coverUrlList;

    public DiscoveryNovelData() {
    }
    public DiscoveryNovelData( List<String> novelNameList,List<String> coverUrlList) {
        this.coverUrlList = coverUrlList;
        this.novelNameList = novelNameList;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public String getListname() {
        return listname;
    }

    public List<String> getNovelNameList() {
        return novelNameList;
    }

    public void setNovelNameList(List<String> novelNameList) {
        this.novelNameList = novelNameList;
    }

    public List<String> getCoverUrlList() {
        return coverUrlList;
    }

    public void setCoverUrlList(List<String> coverUrlList) {
        this.coverUrlList = coverUrlList;
    }

    @Override
    public String toString() {
        return "DiscoveryNovelData{" +
                "novelNameList=" + novelNameList +
                ", coverUrlList=" + coverUrlList +
                '}';
    }
}
