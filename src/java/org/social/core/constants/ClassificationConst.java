package org.social.core.constants;

public enum ClassificationConst {

    SENTIMENT, RELIABILITY;

    public enum Sentiment {
        NOT_CLASSIFIED, POSITIVE, NEGATIVE, NEUTRAL;

        public boolean isSameAs(String classification) {
            return this.name().equals(classification);
        }
    };

    public enum Reliability {
        NOT_CLASSIFIED, RELIABLE, NOT_RELIABLE;
    };
}
