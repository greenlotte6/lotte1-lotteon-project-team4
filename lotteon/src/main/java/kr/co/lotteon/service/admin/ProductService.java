package kr.co.lotteon.service.admin;

import jakarta.persistence.EntityNotFoundException;
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
import java.util.*;
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

    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageableNotSort();
        String keyword = pageRequestDTO.getKeyword();
        String searchType = pageRequestDTO.getSearchType();

        Page<Products> pageProduct;
        if ("pid".equals(searchType)) {
            pageProduct = productRepository.findByPidContaining(keyword, pageable);
        } else if ("pname".equals(searchType)) {
            pageProduct = productRepository.findByPnameContaining(keyword, pageable);
        } else if ("company".equals(searchType)) {
            pageProduct = productRepository.findByCompanyContaining(keyword, pageable);
        } else {
            pageProduct = productRepository.findAll(pageable);
        }

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

    @Transactional
    public void deleteProduct(List<Integer> pid) {
        productComplianceRepository.deleteByProductsPidIn(pid);
        productRepository.deleteAllByIdInBatch(pid);
    }

    @Transactional
    public void registerProduct(ProductFormDTO form) throws IOException {
        Category category = categoryRepository.findById((long) form.getCategory2_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + form.getCategory2_id()));

        Products product = Products.builder()
                .pid(form.getPid())
                .pname(form.getPname())
                .description(form.getDescription())
                .price(form.getPrice())
                .discount(form.getDiscount())
                .point(form.getPoint())
                .stock(form.getStock())
                .company(form.getCompany())
                .hits(form.getHits())
                .mgmt("null")
                .brand(form.getBrand())
                .p_created_at(LocalDate.now())
                .p_updates_at(LocalDate.now())
                .maker(form.getMaker())
                .delivery_free(form.getDelivery_free())
                .category(category)
                .point_rate(form.getPoint_rate())
                .build();

        String baseDir = System.getProperty("user.dir") + "/uploads/";
        String imgFile1 = saveFile(form.getImg_file_1(), baseDir);
        String imgFile2 = saveFile(form.getImg_file_2(), baseDir);
        String imgFile3 = saveFile(form.getImg_file_3(), baseDir);
        String detailFile1 = saveFile(form.getDetaile_file_1(), baseDir);

        product.setImg_file_1(imgFile1);
        product.setImg_file_2(imgFile2);
        product.setImg_file_3(imgFile3);
        product.setDetaile_file_1(detailFile1);

        productRepository.save(product);

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
        if (file == null || file.isEmpty()) return null;
        File directory = new File(baseDir);
        if (!directory.exists()) directory.mkdirs();

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File destFile = new File(baseDir, fileName);
        file.transferTo(destFile);
        return fileName;
    }

    public ProductFormDTO modifyView(int pid) {
        Products products = productRepository.findById(pid).orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다."));
        Category category = products.getCategory();
        Category parentCategory = category.getParent();

        List<ProductOption> options = productOptionRepository.findByProducts(products);
        List<ProductFormDTO.OptionForm> optionForms = new ArrayList<>();

        for (ProductOption option : options) {
            List<ProductOptionItem> optionItems = productOptionItemRepository.findByProductOption(option);
            ProductFormDTO.OptionForm optionForm = new ProductFormDTO.OptionForm();
            optionForm.setOptionName(option.getOption_name());
            optionForm.setOptionItems(optionItems.stream().map(ProductOptionItem::getItem_name).toList());
            optionForms.add(optionForm);
        }

        ProductCompliance productCompliance = productComplianceRepository.findByProducts(products);
        if (productCompliance == null) {
            throw new EntityNotFoundException("해당 데이터를 찾을 수 없습니다.");
        }

        ProductFormDTO productFormDTO = new ProductFormDTO();
        productFormDTO.setPid(products.getPid());
        productFormDTO.setPname(products.getPname());
        productFormDTO.setDescription(products.getDescription());
        productFormDTO.setCompany(products.getCompany());
        productFormDTO.setPrice(products.getPrice());
        productFormDTO.setDiscount(products.getDiscount());
        productFormDTO.setPoint(products.getPoint());
        productFormDTO.setStock(products.getStock());
        productFormDTO.setDelivery_free(products.getDelivery_free());
        productFormDTO.setBrand(products.getBrand());
        productFormDTO.setCategory2_id(Math.toIntExact(category.getCateId()));
        if (parentCategory != null) {
            productFormDTO.setCategory1_id(Math.toIntExact(parentCategory.getCateId()));
        }
        productFormDTO.setStatus(productCompliance.getStatus());
        productFormDTO.setTax(productCompliance.getTax());
        productFormDTO.setReceipt(productCompliance.getReceipt());
        productFormDTO.setBiz_type(productCompliance.getBiz_type());
        productFormDTO.setOrigin(productCompliance.getOrigin());
        productFormDTO.setOptions(optionForms);

        return productFormDTO;
    }

    @Transactional
    public void modifyProduct(int pid, ProductFormDTO productFormDTO) {
        Products products = productRepository.findById(pid).orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다."));

        products.setPname(productFormDTO.getPname());
        products.setDescription(productFormDTO.getDescription());
        products.setCompany(productFormDTO.getCompany());
        products.setPrice(productFormDTO.getPrice());
        products.setDiscount(productFormDTO.getDiscount());
        products.setPoint(productFormDTO.getPoint());
        products.setStock(productFormDTO.getStock());
        products.setDelivery_free(productFormDTO.getDelivery_free());
        products.setBrand(productFormDTO.getBrand());

        Category newCategory = categoryRepository.findById((long) productFormDTO.getCategory2_id())
                .orElseThrow(() -> new EntityNotFoundException("카테고리 존재하지 않음"));
        products.setCategory(newCategory);

        productRepository.save(products);

        List<ProductOption> existingOptions = productOptionRepository.findByProducts(products);
        for (ProductOption option : existingOptions) {
            productOptionItemRepository.deleteByProductOption(option);
        }
        productOptionRepository.deleteAll(existingOptions);

        for (ProductFormDTO.OptionForm optionForm : productFormDTO.getOptions()) {
            ProductOption newOption = ProductOption.builder()
                    .products(products)
                    .option_name(optionForm.getOptionName())
                    .build();
            productOptionRepository.save(newOption);

            for (String item : optionForm.getOptionItems()) {
                ProductOptionItem newItem = ProductOptionItem.builder()
                        .productOption(newOption)
                        .item_name(item)
                        .build();
                productOptionItemRepository.save(newItem);
            }
        }

        ProductCompliance compliance = productComplianceRepository.findByProducts(products);
        if (compliance != null) {
            compliance.setStatus(productFormDTO.getStatus());
            compliance.setTax(productFormDTO.getTax());
            compliance.setReceipt(productFormDTO.getReceipt());
            compliance.setBiz_type(productFormDTO.getBiz_type());
            compliance.setOrigin(productFormDTO.getOrigin());
            productComplianceRepository.save(compliance);
        }
    }
}
