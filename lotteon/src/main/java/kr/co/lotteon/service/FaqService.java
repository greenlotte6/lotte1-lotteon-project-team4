package kr.co.lotteon.service;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.repository.FaqRepository;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FaqService {
    private final FaqRepository faqRepository;
    private final ListableBeanFactory listableBeanFactory;

    public FaqService(FaqRepository faqRepository, ListableBeanFactory listableBeanFactory) {
        this.faqRepository = faqRepository;
        this.listableBeanFactory = listableBeanFactory;
    }

    public void saveFaq(Faq faq) {
        faq.setUploadDate(LocalDate.now());
        faq.setHits(0);
        faqRepository.save(faq);
    }

    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    public Faq getFaqById(int id) {
        return faqRepository.findById(id).orElseThrow();
    }

    public void updateFaq(Faq faq) {
        Faq existing = faqRepository.findById(faq.getFaqId())
                .orElseThrow(() -> new RuntimeException("FAQ not found"));

        existing.setType1(faq.getType1());
        existing.setType2(faq.getType2());
        existing.setTitle(faq.getTitle());
        existing.setContent(faq.getContent());
        existing.setCateIcon(faq.getCateIcon());
//        existing.setMgmt(faq.getMgmt());
        existing.setUploadDate(LocalDate.now());

        faqRepository.save(existing);
    }

    public void deleteAllByIds(List<Integer> ids) {
        faqRepository.deleteAllByIdInBatch(ids);
    }

    public void deleteFaq(int id) {
        faqRepository.deleteById(id);
    }

    // 수정: getFilteredFaqs → LIKE 검색으로 변경
    public List<Faq>getFilteredFaqs(String type1, String type2) {
        if (type1 != null && !type1.isEmpty() && type2 != null && !type2.isEmpty()) {
            return faqRepository.findByType1AndType2Like(type1, type2);
        } else if (type1 != null && !type1.isEmpty()) {
            return faqRepository.findByType1(type1);
        } else {
            return faqRepository.findAll();
        }
    }

    public List<String> getSubTypesByType1(String category) {
        return faqRepository.findDistinctType2ByType1(category);
    }

    public List<Faq> getFaqsByCategory(String category) {
        return faqRepository.findByType1(category);
    }

    public List<Faq> getFaqsByCategory2(String category) {
        return faqRepository.findByType2(category);
    }

    public List<Faq> getFaqsByCategoryAndSubType(String type1, String type2) {
        if (type1 != null && !type1.isEmpty() && type2 != null && !type2.isEmpty()) {
            return faqRepository.findByType1AndType2Like(type1, type2);
        } else if (type1 != null && !type1.isEmpty()) {
            return faqRepository.findByType1(type1);
        } else {
            return faqRepository.findAll();
        }
    }
}
