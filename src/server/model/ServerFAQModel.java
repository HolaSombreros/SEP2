package server.model;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface ServerFAQModel extends LocalSubject<FAQ, FAQ>
{
  void addFAQ(String question, String answer, Category category, Administrator creator) throws RemoteException;
  void editFAQ(String oldQuestion, String oldAnswer, String question, String answer, Category category) throws RemoteException;
  void removeFAQ(String question, String answer) throws RemoteException;
  FAQList getFAQList();
}
