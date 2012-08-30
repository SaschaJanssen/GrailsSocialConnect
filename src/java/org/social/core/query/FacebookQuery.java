package org.social.core.query;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.social.core.data.CustomerNetworkKeywords;
import org.social.core.util.UtilProperties;
import org.social.core.util.UtilValidate;

public class FacebookQuery extends Query {

    private final String searchUrl;

    private String query;
    private String since;
    private String type;
    private String language;
    private String limit = "1500";

    public FacebookQuery(CustomerNetworkKeywords networkKeywords) {
        super(networkKeywords);

        setQuery(networkKeywords.getQueryForNetwork());
        searchUrl = UtilProperties.getPropertyValue("grails-app/conf/network/fb.properties", "searchUrl");
    }

    private void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void setSince(String since) {
        this.since = since;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getSearchUrl() {
        return searchUrl;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String constructQuery() {
        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            encodedQuery = query;
        }

        StringBuilder queryBuilder = new StringBuilder(searchUrl);
        queryBuilder.append("?");
        queryBuilder.append("q=");
        queryBuilder.append(encodedQuery);
        queryBuilder.append("&type=");
        queryBuilder.append(UtilValidate.isNotEmpty(type) ? type : "post");

        if (UtilValidate.isNotEmpty(since)) {
            queryBuilder.append("&since=");
            queryBuilder.append(since);
        }

        queryBuilder.append("&limit=");
        queryBuilder.append(limit);

        if (UtilValidate.isNotEmpty(language)) {
            queryBuilder.append("&locale=");
            queryBuilder.append(language);
        }

        return queryBuilder.toString();
    }

    @Override
    public String getSince() {
        return since;
    }

}
