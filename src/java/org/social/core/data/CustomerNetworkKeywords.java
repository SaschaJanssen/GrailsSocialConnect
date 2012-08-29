package org.social.core.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.social.core.constants.KeywordTypeConst;
import org.social.grails.user.Keyword;

public class CustomerNetworkKeywords {

    private final Map<KeywordTypeConst, String> networkKeywords;

    public CustomerNetworkKeywords(List<Keyword> keywordListForNetwork) {
        networkKeywords = mapKeywords(keywordListForNetwork);
    }

    public String getHashForNetwork() {
        String hash = "";

        if (networkKeywords.containsKey(KeywordTypeConst.HASH)) {
            hash = networkKeywords.get(KeywordTypeConst.HASH);
        }

        return hash;
    }

    public String getMentionedForNetwork() {
        String mentioned = "";

        if (networkKeywords.containsKey(KeywordTypeConst.MENTIONED)) {
            mentioned = networkKeywords.get(KeywordTypeConst.MENTIONED);
        }

        return mentioned;
    }

    public String getQueryForNetwork() {
        String query = "";

        if (networkKeywords.containsKey(KeywordTypeConst.QUERY)) {
            query = networkKeywords.get(KeywordTypeConst.QUERY);
        }

        return query;
    }

    private Map<KeywordTypeConst, String> mapKeywords(List<Keyword> keywordsForCustomer) {
        Map<KeywordTypeConst, String> mappedKeywords = new HashMap<KeywordTypeConst, String>();

        for (Keyword keyword : keywordsForCustomer) {

            String keywordType = keyword.getKeywordTypeId();

            if (KeywordTypeConst.HASH.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordTypeConst.HASH, keyword.getKeyword());
            } else if (KeywordTypeConst.MENTIONED.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordTypeConst.MENTIONED, keyword.getKeyword());
            } else if (KeywordTypeConst.QUERY.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordTypeConst.QUERY, keyword.getKeyword());
            } else if (KeywordTypeConst.PAGE.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordTypeConst.PAGE, keyword.getKeyword());
            }
        }

        return mappedKeywords;
    }

    public String getPage() {
        String page = "";

        if (networkKeywords.containsKey(KeywordTypeConst.PAGE)) {
            page = networkKeywords.get(KeywordTypeConst.PAGE);
        }

        return page;
    }
}
