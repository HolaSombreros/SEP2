package server.model.domain.faq;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FAQContent implements Serializable {
    private Map<FAQType, FAQList> content;
    
    public FAQContent() {
        content = new HashMap<>();
    }
    
    public Map<FAQType, FAQList> getContent() {
        return content;
    }
    
    public void add(FAQType type, FAQ faq) {
        FAQList list = content.get(type);
        if (list == null) {
            list = new FAQList();
            list.add(faq);
            content.put(type, list);
        }
        else {
            list.add(faq);
        }
    }
    
    public void remove(FAQType type, FAQ faq) {
        FAQList list = content.get(type);
        if (list == null) {
            throw new IllegalArgumentException("FAQ type of " + type.toString() + " was not found!");
        }
        else {
            if (list.contains(faq)) {
                list.remove(faq);
            }
            else {
                throw new IllegalArgumentException("No such FAQ found...");
            }
        }
    }
    
    public int size() {
        return content.size();
    }
}
