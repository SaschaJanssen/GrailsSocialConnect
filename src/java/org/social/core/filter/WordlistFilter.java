package org.social.core.filter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.util.UtilLucene;
import org.social.core.util.UtilValidate;

public class WordlistFilter {

    private static Logger log = LoggerFactory.getLogger(WordlistFilter.class);

    private static Set<String> wordlist = loadInputData();
    private static WordlistFilter filterInstance = new WordlistFilter();

    public static WordlistFilter getInstance() {
        return filterInstance;
    }

    private static Set<String> loadInputData() {
        if (log.isDebugEnabled()) {
            log.debug("Initialize wordlist filter - loading data");
        }

        Set<String> foodwordlist = writeStreamInStringSet("grails-app/conf/wordlists/FoodEng");

        return foodwordlist;
    }

    private static Set<String> writeStreamInStringSet(String wordlistFile) {
        Set<String> foodwordlist = new HashSet<String>();

        InputStream wordlistInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            wordlistInputStream = new FileInputStream(wordlistFile);
            inputStreamReader = new InputStreamReader(wordlistInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String lineInFile;
            while ((lineInFile = bufferedReader.readLine()) != null) {
                if (UtilValidate.isNotEmpty(lineInFile)) {
                    foodwordlist.add(lineInFile);
                }
            }
        } catch (IOException e) {
            log.error("Can't read wordlist: ", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (wordlistInputStream != null) {
                    wordlistInputStream.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }

        return foodwordlist;
    }

    public boolean matchesWordList(String phrase) {
        boolean contains = false;

        List<String> tokanizedPhrase = UtilLucene.tokenizeString(new StandardAnalyzer(Version.LUCENE_36), phrase);
        for (String token : tokanizedPhrase) {
            contains = wordlist.contains(token);
            if (contains) {
                break;
            }
        }
        return contains;
    }
}
