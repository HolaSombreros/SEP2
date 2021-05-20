package server.mediator;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQ;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

public interface RemoteFAQModel extends RemoteSubject<FAQ,FAQ>
{
  void addFAQ(String question, String answer, Category category, Administrator creator) throws RemoteException;
  FAQList getFAQList() throws RemoteException;
  void removeFAQ(String question, String answer) throws RemoteException;
}
