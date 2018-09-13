package com.jiage.battle.util;


import com.jiage.battle.entity.ImageModel;

import java.util.Comparator;

public class ContentComparator implements Comparator<ImageModel> {

    @Override
    public int compare(ImageModel o1, ImageModel o2) {
        // 将 null 排到最后
        if (o1 == null) {
            return 1;
        }
        if (o2 == null || !(o2 instanceof ImageModel)) {
            return -1;
        }

        long key1 = o1.getDate_added();
        long key2 = o2.getDate_added();
        return key1 > key2 ? 1 : key1 < key2 ? -1 : 0;
    }
}
