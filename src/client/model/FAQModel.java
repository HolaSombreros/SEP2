package client.model;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface FAQModel extends LocalSubject<FAQ,FAQ>
{
  void addFAQ(String question, String answer, Category category, Administrator creator);
  void editFAQ(String oldQuestion, String oldAnswer, String question, String answer, Category category);
  void removeFAQ(String question, String answer);
  FAQList getFAQList();

}
