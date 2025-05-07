package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private int pid;
    private String img_file_1;
    private String img_file_2;
    private String img_file_3;
    private String detaile_file_1;
    private String pname;
    private String description;
    private int price;
    private String discount;
    private int point;
    private int stock;
    private String company;
    private String hits;
    private String mgmt;
    private int category_id;
    private String brand;
    private LocalDate p_created_at;
    private LocalDate p_updates_at;
    private String maker;
    private int delivery_free;
    private int category_cate_id;
    private int point_rate;
    private int cart_item_item_id;
    private int productCompliance_compliance_id;

}
