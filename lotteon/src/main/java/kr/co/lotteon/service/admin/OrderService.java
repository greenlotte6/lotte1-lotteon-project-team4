package kr.co.lotteon.service.admin;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.entity.Delivery;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.Products;
import kr.co.lotteon.repository.DeliveryRepository;
import kr.co.lotteon.repository.OrdersRepository;
import kr.co.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 주문현황 조회
    public PageResponseDTO<OrdersDTO> findById(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Tuple> ordersPage = ordersRepository.findByOid(pageable);

        List<OrdersDTO> ordersDTOS = ordersPage.getContent().stream().map(tuple -> {

                    Orders orders = tuple.get(0, Orders.class);
                    String uid = tuple.get(1, String.class);
                    String uname = tuple.get(2, String.class);
                    String pname = tuple.get(3, String.class);
                    Integer quantity = tuple.get(4, Integer.class);

                    OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);
                    ordersDTO.setUsers_uid(uid);
                    ordersDTO.setUname(uname);
                    ordersDTO.setPname(pname);
                    ordersDTO.setQuantity(quantity);
                    return ordersDTO;
                }).collect(Collectors.toList());

        // 전체 게시물 갯수
        int total = (int) ordersPage.getTotalElements();

        return PageResponseDTO
                .<OrdersDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(ordersDTOS)
                .total(total)
                .build();

    }

    // 주문 현황 검색
    public PageResponseDTO<OrdersDTO> searchList(PageRequestDTO pageRequestDTO) {

        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = pageRequestDTO.getPageableNotSort();

        Page<Orders> ordersPage;

        if ("oid".equals(searchType)) {
            ordersPage = ordersRepository.findByOidContaining(keyword, pageable);
        } else if ("uid".equals(searchType)) {
            ordersPage = ordersRepository.findByUsers_UidContaining(keyword, pageable);
        } else if ("uname".equals(searchType)) {
            ordersPage = ordersRepository.findByUsers_UnameContaining(keyword, pageable);
        } else {
            ordersPage = ordersRepository.findAll(pageable);
        }

        List<OrdersDTO> ordersDTOList = ordersPage.getContent().stream()
                .map(order -> {
                    OrdersDTO ordersDTO = modelMapper.map(order, OrdersDTO.class);
                    ordersDTO.setUsers_uid(order.getUsers().getUid());
                    ordersDTO.setUname(order.getUsers().getUname());
                    ordersDTO.setPname(order.getOrderItems().stream()
                            .map(orderItem -> orderItem.getProducts().getPname())
                            .collect(Collectors.joining(",")));
                    return ordersDTO;
                }).collect(Collectors.toList());

        int total = (int) ordersPage.getTotalElements();

        return PageResponseDTO
                .<OrdersDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .dtoList(ordersDTOList)
                .build();

    }

    // 배송 정보 입력 데이터 불러오기
    public OrdersDTO deliveryDetail(int oid) {
        Optional<Orders> optOrders = ordersRepository.findById(oid);
        Delivery delivery = deliveryRepository.findByOrders_oid(oid);

        if (optOrders.isPresent()) {
            Orders orders = optOrders.get();
            OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);
            ordersDTO.setOid(orders.getOid());
            ordersDTO.setRecipient(orders.getRecipient());
            ordersDTO.setZip(orders.getUsers().getZip());
            ordersDTO.setAddr1(orders.getUsers().getAddr1());
            ordersDTO.setAddr2(orders.getUsers().getAddr2());
            ordersDTO.setDelivery_company(delivery.getDelivery_company());
            ordersDTO.setDelivery_num(delivery.getDelivery_num());
            ordersDTO.setOther(delivery.getOther());

            log.info("ordersDTO {}", ordersDTO);


            return ordersDTO;
        } else {
            throw new NoSuchElementException("주문 번호를 찾을 수 없습니다." + oid);
        }

    }

    // 배송 정보 등록하기
    public OrdersDTO modifyDelivery(int oid, String delivery_company, String delivery_num) {
        Optional<Orders> optOrders = ordersRepository.findById(oid);
        Delivery delivery = deliveryRepository.findByOrders_oid(oid);

        if (optOrders.isPresent()) {
            Orders orders = optOrders.get();
            delivery.setDelivery_company(delivery_company);
            delivery.setDelivery_num(delivery_num);
            ordersRepository.save(orders);
            deliveryRepository.save(delivery);

            OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);
            ordersDTO.setDelivery_company(delivery.getDelivery_company());
            ordersDTO.setDelivery_num(delivery.getDelivery_num());

            return ordersDTO;
        } else {
            throw new NoSuchElementException("주문 번호를 찾을 수 없습니다" + oid);
        }
    }

    // 주문상세 보기
    public void orderDetail(int oid) {
        Optional<Orders> optOrders = ordersRepository.findById(oid);

        /// ///////////////////////
        // 조인해서 주문상세 조회하기



        
        if (optOrders.isPresent()) {
            Orders orders = optOrders.get();
            OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);
            ordersDTO.setOid(orders.getOid());

        }
    }

}
