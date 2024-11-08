# java-convenience-store-precourse
- - -
- 구매자의 **할인 혜택**, **재고 상황**을 고려하여 **최종 결제 금액을 계산 , 안내**하는 결제 시스템
- 영수증 **출력 후 추가 구매 진행 ,종료** 선택
추가 고려 사항
- 재고 관리
  - 재고 체크 - 구매 전
  - 재고 상태 유지 - 구매 후 
    - 프로모션 제품[1]
    - 일반 제품[2]
- 프로모션 할인
  - 기간
  - N + 1 형태
  - 동일 상품에 여러 프로모션 적용 x
  - 프로모션 적용 가능 모델 알림 
  - 부분적 정가 결제 알림 (프로모션 재고가 부족한 경우)
- 멤버쉽 할인
  - 프로그램 미적용 재고 30% 
  - 프로모션 적용 후 남은 금액 멤버쉽
  - 최대 한도 8000원
- 영수증
  - 구매 상품 : 상품명 , 수량, 가격
  - 증정 상품 : 무료 제공 상품 목록
  - 금액 정보 
    - 총 구매 : 수량 , 금액
    - 행사 : 할인
    - 멤버쉽 : 할인
    - 내실돈 : 최종 결제
- - - 
👷기능 요구사항 👷
- - -
## Utils
- [x] `Validator`
  - [x] `nullOrEmptyValidator` : null, 빈 값을 검증
  - [x] `purchaseInputFormatValidator` : 구매할 상품과 수량 형식이 올바르지 않은 경우
  - [x] `duplicatedNameValidator` : 중복된 상품명이 입력되면 예외처리 
  - [X] `YesOrNoValidator` : Y,N 제외 입력 예외처리
- [x] `Parser`
  - [x] `productAndStockParser` : 상품명, 수량을 파싱
## Domain
- [x] `Product` 
  - [x] `updateStock` : 수량을 업데이트
  - [x] `iStockAvailable` : 물량을 체크
  - [x] `stockValidator` : 생성 시 수량 검증
  - [x] `priceValidator` : 생성 시 가격 검증
- [x] `Range` : `Promotion` 의 기간 정보를 다루는 클래스 
  - [x] `isValidRange` : 기간이 유효한지 체크
- [x] `BuyGet` : `Promotion` 의 증정 정보를 다루는 클래스
  - [x] `calculateBuyStock` : 입력받은 수량에 따라 가장 많은 프로모션을 받기 위해 구매해야 할 수량을 계산
  - [x] `isPromotionAvailable` : 입력 수량이 프로모션 적용 가능한지 체크
- [x] `Promotion`
  - [x] `checkDate` : `Range` 클래스의 `isValidRange` 를 호출 
  - [x] `getBuyCount`: `BuyGet` 클래스의 `calculateBuyStock` 를 호출 
  - [x] `calculateFreeStock` : 구매한 수량에 따라 받게되는 promotion 상품 수량을 계산
- [x] `Receipt` 
  - [x] `List<Product> products`
  - [x] `canUseMembership` : 멤버쉽 사용 가능한지 점검
  - [x] `calculatePrice` : 토탈 가격,프로모션 할인 가격을 계산
  - [x] `applyMembership` : 멤버쉽을 적용
- [x] `Store`
  - [x] `List <product>`,` List <promotion>`
  - [x] `findProduct` : 편의점 내 상품을 반환한다.
  - [x] `findPromotionProduct` : 편의점 내 프로모션 상품을 반환한다.
  - [x] `isValidStock` : 구매 가능한 수량인지 체크
  - [x] `isProductExists` : 상품이 존재하는 지 체크 
  - [x] `getProductStock` : 상품 별 수량을 가져오는 메서드 (private)
  - [ ] `canApplyPromotion` : 프로모션 상품이 존재하는 경우 프로모션 적용 가능한지 체크
  - [ ] `buyPromoItemNoDiscount` : 프로모션 상품을 프로모션 없이 구매
  - [ ] `checkStock` : 재고 체크
  - [ ] `productAndStockValidator` : List<Product> 돌면서 검증 : 프러덕트 클래스 내부 메서드
#### getter 는 생략
## View
- [ ] `InputView`
- [ ] `OutputView`
- - -

- [] 모두 구현 후 도메인, 컨트롤러 분리할 수 있으면 분리