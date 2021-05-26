package server.model.domain.faq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.model.validator.FAQValidator;

import static org.junit.jupiter.api.Assertions.*;

public class FAQTest
{
  private FAQ faq;

  @BeforeEach
  void setUp() {
    faq = new FAQ(1, "question", "answer", Category.GENERAL);
  }

  @Test
  void setQuestionNull() {
    assertThrows(IllegalArgumentException.class, ()-> {
      FAQValidator.validateEditFAQ("", faq.getAnswer(), faq.getCategory());
    });
  }

  @Test
  void setAnswerNull() {
    assertThrows(IllegalArgumentException.class, ()-> {
      FAQValidator.validateEditFAQ(faq.getQuestion(), "", faq.getCategory());
    });
  }

  @Test
  void setCategoryNull() {
  assertThrows(IllegalArgumentException.class, ()-> {
    FAQValidator.validateEditFAQ(faq.getQuestion(), faq.getAnswer(), null);
    });
  }

}
