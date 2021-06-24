# hi-spring

이동욱님 교재로 따라치며 배우는 스프링 입문  
[저자 레포지토리](https://github.com/jojoldu/freelec-springboot2-webservice)
/ [교재 링크 ](https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=218568947)

## 3~6장 프로젝트

* CRUD 가능한 게시판
* 소셜로그인 가능
* 로그인 여부 / 글 작성 여부에 따른 서로 다른 권한 부여

## Spring 웹 계층

![Spring 웹 계층](https://blog.kakaocdn.net/dn/bFruEV/btqAUv4HJLQ/H5TVBjqkKc5KBgD4Vdyvkk/img.png)

- Web Layer : 컨트롤러, 뷰템플릿 영역
- Service Layer : 트랜잭션, 도메인 간 순서 보장의 역할. @Service, @Transactional 사용됨.
- Repository Layer : DB에 접근. 기존의 DAO
- DTO : 계층 간 데이터 교환을 위한 객체. 뷰 템플릿으로 넘기는 값이나, Repository Layer에서 넘겨주는 거.
- Domain : 비즈니스 로직 그 자체.

```java
class ServiceTest {
    /**
     * 모든 로직이 서비스클래스 내부에서 처리됨.
     * 즉 서비스계층이 무의미하며, 객체는 단순히 데이터 덩어리 역할만 하게됨.
     */
    @Transactional
    public Order cancelOrderTransactionalScript(int orderId) {
        // DB에서 주문, 결제, 배송정보 조회
        OrdersDto order = ordersDao.selectOrders(orderId);
        BillingDto billing = billingDao.selectBilling(orderId);
        DeliveryDto delivery = deliveryDao.selectDelivery(orderId);

        // 배송취소 로직
        String deliveryStatus = delivery.getStatus();

        if ("IN_PROGRESS".equals(deliveryStatus)) {
            delivery.setStatus("CANCEL");
            deliveryDao.update(delivery);
        }

        // 각 테이블에 반영
        order.setStatus("CANCEL");
        ordersDao.update(order);

        billing.setStatus("CANCEL");
        deliveryDao.update(billing);

        return order;

    }

    /**
     * 각각의 도메인이 각자 본인의 취소 입네트 처리를 하며,
     * 서비스 메소드는 트랜잭션, 도메인의 순서만 보장해줌.
     */
    @Transactional
    public Order cancelOrderDDD(int orderId) {
        // 조회하는건 똑같은데
        Orders order = ordersRepository.findById(orderId);
        Billing billing = billingRepository.findByOrderId(orderId);
        Delivery delivery = deliveryRepository.findByOrderId(orderId);

        // 각 도메인에 있는 cancel을 적용함.
        delivery.cancel();
        order.cancel();
        billing.cancel();
        
        return order;
    }
}
```

