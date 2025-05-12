package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

//    private final OrdersRepository ordersRepository;
//
//    public void findById(PageRequestDTO pageRequestDTO) {
//
//        Pageable pageable = pageRequestDTO.getPageable("no");
//
//        Page<Orders> ordersPage = ordersRepository.findByOid(pageable);
//
//
//    }

}
