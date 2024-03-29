package org.social.core.filter.classifier.bayes;

/**
 * Simple interface defining the method to calculate the feature probability.
 * 
 * @param <T>
 *            The feature class.
 * @param <K>
 *            The category class.
 */
public interface IFeatureProbability<T, K> {

    public float featureProbability(T feature, K category);

}
