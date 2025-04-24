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

}
