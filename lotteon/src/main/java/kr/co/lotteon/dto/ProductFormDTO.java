package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFormDTO {

    // 기본 정보
    private int pid;
    private String pname;
    private String description;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private String company;
    private String hits;
    private String brand;
    private String maker;
    private int delivery_free;
    private int point_rate;
    private int productCompliance_compliance_id;

    // 카테고리 정보
    private int category1_id;
    private int category2_id;

    // 이미지 파일
    private MultipartFile img_file_1; // 상품 목록 이미지
    private MultipartFile img_file_2; // 상품 메인 이미지
    private MultipartFile img_file_3; // 상품 상세 이미지
    private MultipartFile detaile_file_1; // 상품 상세 설명 이미지

    private String img_file_1_name;
    private String img_file_2_name;
    private String img_file_3_name;
    private String detaile_file_1_name;


    // 옵션 정보
    private List<OptionForm> options;

    // 상품 정보 고시
    private String status;
    private String tax;
    private String receipt;
    private String biz_type;
    private String origin;

    // OptionForm.java (내부 클래스)
    @Data
    public static class OptionForm {
        private String optionName;
        private List<String> optionItems;
    }

}
