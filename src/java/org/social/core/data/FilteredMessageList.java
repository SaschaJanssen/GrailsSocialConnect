package org.social.core.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.social.grails.network.Message;

public class FilteredMessageList {

    private CopyOnWriteArrayList<Message> negativeList = null;
    private CopyOnWriteArrayList<Message> positiveList = null;

    public FilteredMessageList() {
        negativeList = new CopyOnWriteArrayList<Message>();
        positiveList = new CopyOnWriteArrayList<Message>();
    }

    public void addAll(FilteredMessageList filteredMessageList) {
        negativeList.addAll(filteredMessageList.getNegativeList());
        addAllToPositiveList(filteredMessageList.getPositivList());
    }

    public void addAllToPositiveList(List<Message> messages) {
        positiveList.addAll(messages);
    }

    public int size() {
        return countNegativeMessages() + countPositivMessages();
    }

    public List<Message> getNegativeList() {
        return negativeList;
    }

    public List<Message> getPositivList() {
        return positiveList;
    }

    public int countNegativeMessages() {
        return negativeList.size();
    }

    public int countPositivMessages() {
        return positiveList.size();
    }

    public void setPositiveList(List<Message> messages) {
        positiveList.addAll(messages);
    }

    public void setNegativeList(List<Message> messages) {
        negativeList.addAll(messages);
    }
}
