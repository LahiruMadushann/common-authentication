package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.entity.ShopID;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IncreaseMatchingStoreNumber {
    List<String> increasePrefectureList = Arrays.asList(
            "北海道", "茨城", "埼玉", "千葉", "東京", "神奈川",
            "静岡", "愛知", "大阪", "兵庫", "広島", "福岡, 佐賀県, 長崎県"
    );
    // remove these prefectures (佐賀県, 長崎県) in production
    public boolean increaseMatchingStoreNumber(String prefecture) {
        return increasePrefectureList.contains(prefecture) ||
                increasePrefectureList.stream().anyMatch(prefecture::contains) ||
                increasePrefectureList.stream().anyMatch(p -> p.contains(prefecture));
    }

    public boolean changeIntroductionValue(List<ShopID> shopIDs){
        if(shopIDs.isEmpty()) return false;
        int nonExclusiveCount = 0;
        for(ShopID shopID : shopIDs){
            if (shopID.getAssessedEx().equals("NON_EX")){
                nonExclusiveCount++;
            }
        }
        return nonExclusiveCount > 2;
    }
    public final Long IntroductionValue = 3000L;
}
