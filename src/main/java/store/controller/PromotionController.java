package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;
import store.domain.promotion.Range;
import store.utils.Converter;
import store.utils.Parser;

public class PromotionController {

    public List<Promotion> setPromotions(List<String> information) {
        List<Promotion> promotions = new ArrayList<Promotion>();

        for (String promotionString : information.subList(1, information.size())) {
            List<String> fields = Parser.splitWithCommaDelimiter(promotionString);
            promotions.add(setPromotion(fields));
        }

        return promotions;
    }


    private Promotion setPromotion(List<String> input) {
        String name = input.get(0);
        String buy = input.get(1);
        String get = input.get(2);
        String start = input.get(3);
        String end = input.get(4);

        BuyGet buyGet = new BuyGet(Parser.stringToInt(buy), Parser.stringToInt(get));
        Range range = new Range(Converter.stringToLocalDate(start), Converter.stringToLocalDate(end));

        return new Promotion(name, buyGet, range);
    }

}
