package server.mediator;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

public interface RemoteFAQModel extends RemoteSubject<Object,Object>
{
  void addFAQ(String question, String answer, Category category, Administrator creator) throws RemoteException;
  void editFAQ(FAQ faq, String question, String answer, Category category) throws RemoteException;
  void removeFAQ(String question, String answer) throws RemoteException;
  FAQList getFAQList() throws RemoteException;
}
