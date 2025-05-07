package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionItemRepository productOptionItemRepository;
    private final ProductComplianceRepository productComplianceRepository;
    private final ModelMapper modelMapper;

    // 상품 목록 조회 및 페이징 처리
    public PageResponseDTO<ProductDTO> productList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Products> pageProduct = productRepository.findAll(pageable);

        List<ProductDTO> productDTOList = pageProduct.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        int total = (int) pageProduct.getTotalElements();

        return PageResponseDTO
                .<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(productDTOList)
                .total(total)
                .build();

    }

    // 상품 목록 검색 및 페이징 처리
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Products> pageProduct = productRepository.findAll(pageable);

        List<ProductDTO> productDTOList = pageProduct.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

        int total = (int) pageProduct.getTotalElements();

        return PageResponseDTO
                .<ProductDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(productDTOList)
                .total(total)
                .build();

    }

    // 상품 선택 삭제
    @Transactional
    public void deleteProduct(List<Integer> pid) {
        productComplianceRepository.deleteByProductsPidIn(pid);
        productRepository.deleteAllByIdInBatch(pid);
    }

    // 상품 등록
    @Transactional
    public void registerProduct(ProductFormDTO form) throws IOException {
        // 1. 카테고리 조회
        Category category = categoryRepository.findById((long) form.getCategory_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + form.getCategory_id()));

        // 2. Products 엔티티 생성 및 저장
        Products product = Products.builder()
                .pid(form.getPid())
                .pname(form.getPname())
                .description(form.getDescription())
                .price(form.getPrice())
                .discount(form.getDiscount())
                .point(form.getPoint())
                .stock(form.getStock())
                .company(form.getCompany())
                .hits(form.getHits()) // 조회수 나중에 수정
                .brand(form.getBrand())
                .p_created_at(LocalDate.now()) // @CreationTimestamp 대신 직접 설정
                .p_updates_at(LocalDate.now())
                .maker(form.getMaker())
                .delivery_free(form.getDelivery_free())
                .category(category)
                .point_rate(form.getPoint_rate())
                .build();


        // 이미지 파일 처리 및 저장
        String baseDir = System.getProperty("user.dir") + "/uploads/"; // 프로젝트폴더/uploads/
        String imgFile1 = saveFile(form.getImg_file_1(), baseDir);
        String imgFile2 = saveFile(form.getImg_file_2(), baseDir);
        String imgFile3 = saveFile(form.getImg_file_3(), baseDir);
        String detailFile1 = saveFile(form.getDetaile_file_1(), baseDir);

        product.setImg_file_1(imgFile1);
        product.setImg_file_2(imgFile2);
        product.setImg_file_3(imgFile3);
        product.setDetaile_file_1(detailFile1);
        product.setCompany(form.getCompany());

        productRepository.save(product);

        // 3. ProductOption 및 ProductOptionItem 저장
        if (form.getOptions() != null) {
            for (ProductFormDTO.OptionForm optionForm : form.getOptions()) {
                ProductOption productOption = ProductOption.builder()
                        .products(product)
                        .option_name(optionForm.getOptionName())
                        .build();
                productOptionRepository.save(productOption);

                if (optionForm.getOptionItems() != null) {
                    for (String item : optionForm.getOptionItems()) {
                        ProductOptionItem optionItem = ProductOptionItem.builder()
                                .productOption(productOption)
                                .item_name(item)
                                .build();
                        productOptionItemRepository.save(optionItem);
                    }
                }
            }
        }

        // 4. ProductCompliance 저장
        ProductCompliance compliance = ProductCompliance.builder()
                                                            .products(product)
                                                            .status(form.getStatus())
                                                            .tax(form.getTax())
                                                            .receipt(form.getReceipt())
                                                            .biz_type(form.getBiz_type())
                                                            .origin(form.getOrigin())
                                                            .build();
        productComplianceRepository.save(compliance);
    }

    private String saveFile(MultipartFile file, String baseDir) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        File directory = new File(baseDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 저장 디렉토리 없으면 생성
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File destFile = new File(baseDir, fileName);
        file.transferTo(destFile);
        return fileName;
//        if (file == null || file.isEmpty()) {
//            return null; // 파일이 없으면 null 반환
//        }
//        String uuid = UUID.randomUUID().toString();
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        File destFile = new File(baseDir, fileName);
//        file.transferTo(destFile);
//        return fileName;
    }

//            Products products = Products.builder()
//                    .pid(productDTO.getPid())
//                    .img_file_1(productDTO.getImg_file_1())
//                    .img_file_2(productDTO.getImg_file_2())
//                    .img_file_3(productDTO.getImg_file_3())
//                    .detaile_file_1(productDTO.getDetaile_file_1())
//                    .pcode(productDTO.getPcode())
//                    .pname(productDTO.getPname())
//                    .description(productDTO.getDescription())
//                    .price(productDTO.getPrice())
//                    .discount(productDTO.getDiscount())
//                    .point(productDTO.getPoint())
//                    .stock(productDTO.getStock())
//                    .company(productDTO.getCompany())
//                    .hits(productDTO.getHits())
//                    .mgmt(productDTO.getMgmt())
//                    .category_id(productDTO.getCategory_id())
//                    .brand(productDTO.getBrand())
//                    .p_created_at(LocalDate.now())
//                    .p_updates_at(LocalDate.now())
//                    .maker(productDTO.getMaker())
//                    .delivery_free(productDTO.getDelivery_free())
//                    .category(productDTO.getCategory_cate_id())
//                    .poiont_rate(productDTO.getPoiont_rate())
//                    .cart_item_item_id(productDTO.getCart_item_item_id())
//                    .build();
//
//            productRepository.save(products);
}
