package org.social.core.data

import org.social.core.constants.KeywordType
import org.social.core.user.Keyword

class CustomerNetworkKeywords {

    def final Map<KeywordType, String> networkKeywords

    def CustomerNetworkKeywords(List<Keyword> keywordListForNetwork) {
        networkKeywords = mapKeywords(keywordListForNetwork)
    }

    def String getHashForNetwork() {
        String hash = ""

        if (networkKeywords.containsKey(KeywordType.HASH)) {
            hash = networkKeywords.get(KeywordType.HASH)
        }

        return hash
    }

    def String getMentionedForNetwork() {
        String mentioned = ""

        if (networkKeywords.containsKey(KeywordType.MENTIONED)) {
            mentioned = networkKeywords.get(KeywordType.MENTIONED)
        }

        return mentioned
    }

    def String getQueryForNetwork() {
        String query = ""

        if (networkKeywords.containsKey(KeywordType.QUERY)) {
            query = networkKeywords.get(KeywordType.QUERY)
        }

        return query
    }

    def Map<KeywordType, String> mapKeywords(List<Keyword> keywordsForCustomer) {
        Map<KeywordType, String> mappedKeywords = new HashMap<KeywordType, String>()

        keywordsForCustomer.each { keyword ->
            String keywordType = keyword.keywordType

            if (KeywordType.HASH.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.HASH, keyword.getKeyword())
            } else if (KeywordType.MENTIONED.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.MENTIONED, keyword.getKeyword())
            } else if (KeywordType.QUERY.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.QUERY, keyword.getKeyword())
            } else if (KeywordType.PAGE.isKeywordType(keywordType)) {
                mappedKeywords.put(KeywordType.PAGE, keyword.getKeyword())
            }
        }

        return mappedKeywords
    }

    public String getPage() {
        String page = ""

        if (networkKeywords.containsKey(KeywordType.PAGE)) {
            page = networkKeywords.get(KeywordType.PAGE)
        }

        return page
    }
}
