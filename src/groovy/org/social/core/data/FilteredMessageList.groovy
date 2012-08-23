package org.social.core.data

import java.util.concurrent.CopyOnWriteArrayList

import org.social.core.network.Message

class FilteredMessageList {

    def CopyOnWriteArrayList<Message> negativeList = null
    def CopyOnWriteArrayList<Message> positiveList = null

    def FilteredMessageList() {
        negativeList = new CopyOnWriteArrayList<Message>()
        positiveList = new CopyOnWriteArrayList<Message>()
    }

    def void addAll(FilteredMessageList filteredMessageList) {
        negativeList.addAll(filteredMessageList.getNegativeList())
        addAllToPositiveList(filteredMessageList.getPositivList())
    }

    def void addAllToPositiveList(List<Message> messages) {
        positiveList.addAll(messages)
    }

    def int size() {
        return countNegativeMessages() + countPositivMessages()
    }

    def List<Message> getNegativeList() {
        return negativeList
    }

    def List<Message> getPositivList() {
        return positiveList
    }

    def int countNegativeMessages() {
        return negativeList.size()
    }

    def int countPositivMessages() {
        return positiveList.size()
    }

    def void setPositiveList(List<Message> messages) {
        positiveList.addAll(messages)
    }

    def void setNegativeList(List<Message> messages) {
        negativeList.addAll(messages)
    }
}
