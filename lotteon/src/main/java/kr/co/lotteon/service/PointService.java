package kr.co.lotteon.service;

import kr.co.lotteon.dto.PointDTO;
import kr.co.lotteon.entity.Point;
import kr.co.lotteon.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointService {

//    private final PointRepository pointRepository;
//    private final ModelMapper modelMapper;
//
//    public List<PointDTO> UserJoinPointAll() {
//        List<Point> points = pointRepository.UserJoinPointAll();
//
//        log.info("points: {}", points);
//
//        return points.stream()
//                .map(point -> modelMapper.map(point, PointDTO.class))
//                .collect(Collectors.toList());
//
//    }

}
