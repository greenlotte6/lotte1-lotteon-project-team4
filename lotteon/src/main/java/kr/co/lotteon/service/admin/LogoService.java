package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.LogoDTO;
import kr.co.lotteon.entity.Copyright;
import kr.co.lotteon.entity.Logo;
import kr.co.lotteon.repository.CopyrightRepository;
import kr.co.lotteon.repository.LogoInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LogoService {
    private final LogoInfoRepository logoRepository;

    public void registerLogo(LogoDTO dto) throws IOException {
        String uploadPath = System.getProperty("user.dir") + "/uploads/";

        // 1. 기존 로고 정보 가져오기 (없으면 새로 생성)
        Logo logo = logoRepository.findById(1).orElse(new Logo());
        logo.setLogoId(1);

        // 2. 기존 파일 삭제 후 새 파일 업로드
        if (dto.getHeaderFileUpload() != null && !dto.getHeaderFileUpload().isEmpty()) {
            deleteFile(uploadPath + logo.getHeaderFile()); // 기존 삭제
            String filename = dto.getHeaderFileUpload().getOriginalFilename();
            dto.getHeaderFileUpload().transferTo(new File(uploadPath + filename));
            logo.setHeaderFile(filename);
        }

        if (dto.getFooterFileUpload() != null && !dto.getFooterFileUpload().isEmpty()) {
            deleteFile(uploadPath + logo.getFooterFile());
            String filename = dto.getFooterFileUpload().getOriginalFilename();
            dto.getFooterFileUpload().transferTo(new File(uploadPath + filename));
            logo.setFooterFile(filename);
        }

        if (dto.getFaviconUpload() != null && !dto.getFaviconUpload().isEmpty()) {
            deleteFile(uploadPath + logo.getFavicon());
            String filename = dto.getFaviconUpload().getOriginalFilename();
            dto.getFaviconUpload().transferTo(new File(uploadPath + filename));
            logo.setFavicon(filename);
        }

        logoRepository.save(logo);
    }

    private void deleteFile(String fullPath) {
        File oldFile = new File(fullPath);
        if (oldFile.exists()) {
            oldFile.delete();
        }
    }

    public Logo getInfo(int id) {
        return logoRepository.findById(id).orElse(null);
    }
}
