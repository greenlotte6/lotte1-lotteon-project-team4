package kr.co.lotteon.service;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.repository.FaqRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
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

        // 수정 항목만 덮어쓰기
        existing.setType1(faq.getType1());
        existing.setType2(faq.getType2());
        existing.setTitle(faq.getTitle());
        existing.setContent(faq.getContent());
        existing.setCateIcon(faq.getCateIcon());
        existing.setMgmt(faq.getMgmt());
        existing.setUploadDate(LocalDate.now());

        // 💡 hits는 변경하지 않고 그대로 둔다
        faqRepository.save(existing);
    }

    public void deleteAllByIds(List<Integer> ids) {
        faqRepository.deleteAllByIdInBatch(ids);
    }

    public void deleteFaq(int id) {
        faqRepository.deleteById(id);
    }
    public List<Faq>getFilteredFaqs(String type1, String type2) {
        if (type1 != null && !type1.isEmpty() && type2 != null && !type2.isEmpty()) {
            return faqRepository.findByType1AndType2(type1, type2);
        } else if (type1 != null && !type1.isEmpty()) {
            return faqRepository.findByType1(type1);
        } else {
            return faqRepository.findAll();
        }
    }


}
