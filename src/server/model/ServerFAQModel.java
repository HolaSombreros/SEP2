package server.model;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.subject.LocalSubject;

public interface ServerFAQModel extends LocalSubject<FAQ, FAQ>
{
  void addFAQ(String question, String answer, Category category, Administrator creator);
  FAQList getFAQList();
}
