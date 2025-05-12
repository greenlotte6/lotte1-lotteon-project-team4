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

    // 공통: 모든 FAQ 조회
    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    // 공통: FAQ 상세 조회
    public Faq getFaqById(int id) {
        return faqRepository.findById(id).orElseThrow();
    }

    // 공통: 카테고리로 FAQ 조회
    public List<Faq> getFaqsByCategory(String category) {
        return faqRepository.findByType1(category);
    }

    // 공통: 서브 카테고리로 FAQ 조회
    public List<Faq> getFaqsByCategory2(String category) {
        return faqRepository.findByType2(category);
    }

    // 공통: 카테고리와 서브 카테고리로 FAQ 조회
    public List<Faq> getFaqsByCategoryAndSubType(String type1, String type2) {
        if (type1 != null && !type1.isEmpty() && type2 != null && !type2.isEmpty()) {
            return faqRepository.findByType1AndType2Like(type1, type2);
        } else if (type1 != null && !type1.isEmpty()) {
            return faqRepository.findByType1(type1);
        } else {
            return faqRepository.findAll();
        }
    }

    // 공통: 카테고리에 따른 서브 카테고리 목록 조회
    public List<String> getSubTypesByType1(String category) {
        return faqRepository.findDistinctType2ByType1(category);
    }

    // 관리자: FAQ 저장
    public void saveFaq(Faq faq) {
        faq.setUploadDate(LocalDate.now());
        faq.setHits(0);
        faqRepository.save(faq);
    }

    // 관리자: FAQ 수정
    public void updateFaq(Faq faq) {
        Faq existing = faqRepository.findById(faq.getFaqId())
                .orElseThrow(() -> new RuntimeException("FAQ not found"));

        existing.setType1(faq.getType1());
        existing.setType2(faq.getType2());
        existing.setTitle(faq.getTitle());
        existing.setContent(faq.getContent());
        existing.setCateIcon(faq.getCateIcon());
        existing.setUploadDate(LocalDate.now());

        faqRepository.save(existing);
    }

    // 관리자: FAQ 삭제
    public void deleteFaq(int id) {
        faqRepository.deleteById(id);
    }

    // 관리자: 여러 FAQ 삭제
    public void deleteAllByIds(List<Integer> ids) {
        faqRepository.deleteAllByIdInBatch(ids);
    }

    // 관리자/사용자: 필터링된 FAQ 목록 조회
    public List<Faq> getFilteredFaqs(String type1, String type2) {
        if (type1 != null && !type1.isEmpty() && type2 != null && !type2.isEmpty()) {
            return faqRepository.findByType1AndType2Like(type1, type2);
        } else if (type1 != null && !type1.isEmpty()) {
            return faqRepository.findByType1(type1);
        } else {
            return faqRepository.findAll();
        }
    }
}
