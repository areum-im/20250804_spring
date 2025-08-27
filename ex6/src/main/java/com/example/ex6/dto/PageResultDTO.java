package com.example.ex6.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
// list.html 페이지에서 사용할 페이지의 정보들을 담은 객체
public class PageResultDTO<DTO, EN> {
  private List<DTO> dtoList; // 해당 페이지의 목록
  private int totalPage; // 10개씩 했을 때 총 페이지수
  private int page; // 요청한 페이지 번호
  private int size; // 한페이지당 나올 갯수
  private int start, end; // 페이지네이션의 시작번호, 끝번호
  private boolean prev, next; // 다음, 이전 버튼에 대한 정보
  private List<Integer> pageList; // 페이지 번호 목록 :: page list

  // Function(EN, DTO) fn -> fn : Entity를 받아서 DTO로 변환해주는 함수
  public PageResultDTO(Page<EN> page, Function<EN, DTO> fn) {

    // page의 목록에 대한 리스트를 List타입으로 변경함.
    // 1. page.stream() -> Page<EN> 안에 있는 엔티티들을 스트림으로 꺼냄
    // 2. .map(fn) -> 스트림의 각 엔티티(EN)를 fn(변환 함수)에 넣어 DTO로 변환
    // 3. .collect(collectors.toList()) -> 변환된 DTO들을 리스트로 모음
    // => dtoList에는 Entity 대신 DTO 객체들의 리스트가 담김
    dtoList = page.stream().map(fn).collect(Collectors.toList());

    // Page객체로 부터 전체 페이지 개수 정보 구하기
    totalPage = page.getTotalPages(); // 실제 총 페이지수

    // Page의 pageable 객체를 통해서 나머지 정보를 완성
    makePageInfo(page.getPageable());
  }

  private void makePageInfo(Pageable pageable) {
    // JPA의 Pageable은 0부터 시작함 즉, 첫 페이지가 0
    // 우리는 사용자에게 1부터 보여줘야 하니까 +1
    // => 사용자가 보는 현재 페이지 번호
    page = pageable.getPageNumber() + 1;

    // 한 페이지에 몇 개를 보여줄지 크기를 가져옴
    // ex) size = 10 : 한 페이지에 10개
    size = pageable.getPageSize();

    //페이지네이션의 시작 번호, 페이지네이션을 10개 단위로 묶어서 보여주려는 계산
    // ex) 현재 7페이지를 보고 있다면 -> tempEnd = 10
    // ex) 현재 11페이지를 보고 있다면 -> tempEnd = 20
    // Math.ceil(page/10.0) : 현재 페이지를 10으로 나눈 후 올림
    int tempEnd = (int)(Math.ceil(page/10.0))*10;

    // 그룹의 시작 번호
    // ex) tempEnd = 10 -> start 1
    // ex) tempEnd = 20 -> start 11
    start = tempEnd - 9;

    // 시작 번호가 1보다 크면 Prev 버튼 활성화
    // Prev 버튼은 이전 묶음이 있을 때만 보여줘야 함
    // start는 현재 묶음의 시작 번호
    // ex) 현재 묶음이 1~10이면 start == 1, 이전 묶음이 없음 -> prev = false
    // ex) 현재 묶음이 11~20이면 start == 11 -> 이전 묶음이 있음 -> prev = true
    // 즉, 시작번호가 1보다 크다 = 첫 묶음이 아니다 = 이전 묶음이 있다 -> Prev 켜라
    prev = start > 1;

    // 이번에 보여줄 마지막 번호(end)는 tempEnd와 totalPage 중에서 더 작은 값으로 정해라
    // 왜 ? : 계산상 이번 묶음의 끝이 20이어도, 실제 전체 페이지가 13이면 13까지만 있으니까 13까지만 보여줘야 함
    end = tempEnd < totalPage ? tempEnd : totalPage;

    // 이번 묶음의 끝 후보(tempEnd)보다 전체 페이지가 더 많으면 Next 버튼을 보여줘라
    // 즉, 뒤에 더 페이지가 남아있으면 Next = true, 아니면 false
    next = tempEnd < totalPage;

    //페이지 번호 목록
    // ex) start = 11, end = 20 -> [11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
    // ex) start = 11, end = 13 -> [11, 12, 13]
    pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
  }

}
