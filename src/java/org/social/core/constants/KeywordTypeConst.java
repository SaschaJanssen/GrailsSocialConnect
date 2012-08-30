package org.social.core.constants;

public enum KeywordTypeConst {
    QUERY, HASH, MENTIONED, PAGE;

    public String getName() {
        return name();
    }

    public boolean isKeywordType(KeywordTypeConst keywordType) {
        return getName().equals(keywordType.name());
    }
}
