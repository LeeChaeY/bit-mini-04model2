package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

//	@Test
	public void testAddPurchase() throws Exception {

		Purchase purchase = new Purchase();
		purchase.setBuyer(new User("testUserId"));
		purchase.setPurchaseProd(new Product(10010));
		purchase.setPaymentOption("0");
		purchase.setReceiverName("testReceiverName");
		purchase.setReceiverPhone("010-1111-2222");
		purchase.setDivyAddr("testDivyAddr");
		purchase.setDivyRequest("testDivyRequest");
		purchase.setDivyDate("2023-07-07");

		purchaseService.addPurchase(purchase);

		purchase = purchaseService.getPurchase(10010);

		//==> console 확인
		System.out.println(purchase);

		//==> API 확인
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals(10010, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals('0', purchase.getPaymentOption());
		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-1111-2222", purchase.getReceiverPhone());
		Assert.assertEquals("testDivyAddr", purchase.getDivyAddr());
		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals('2', purchase.getTranCode());
		Assert.assertEquals("2023-07-07 00:00:00.0", purchase.getDivyDate());

		//		Assert.assertEquals(1, userService.removeUser(user.getUserId()));
	}

//	@Test
	public void testGetPurchase() throws Exception {

		Purchase purchase = new Purchase();

		purchase = purchaseService.getPurchase(10011);
		
		//==> console 확인
		System.out.println(purchase);

		//==> API 확인
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals(10010, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("0", purchase.getPaymentOption());
		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-1111-2222", purchase.getReceiverPhone());
		Assert.assertEquals("testDivyAddr", purchase.getDivyAddr());
		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals("2", purchase.getTranCode());
		Assert.assertEquals("2023-07-07", purchase.getDivyDate());
	}

//	@Test
	public void testUpdateUser() throws Exception{

		Purchase purchase = purchaseService.getPurchase(10011);
		Assert.assertNotNull(purchase);

//		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
//		Assert.assertEquals(10010, purchase.getPurchaseProd().getProdNo());
//		Assert.assertEquals("0", purchase.getPaymentOption());
//		Assert.assertEquals("testReceiverName", purchase.getReceiverName());
//		Assert.assertEquals("010-1111-2222", purchase.getReceiverPhone());
//		Assert.assertEquals("testDivyAddr", purchase.getDivyAddr());
//		Assert.assertEquals("testDivyRequest", purchase.getDivyRequest());
//		Assert.assertEquals("2", purchase.getTranCode());
//		Assert.assertEquals("2023-08-24", purchase.getDivyDate());

		purchase.setPaymentOption("1");
		purchase.setReceiverName("changeReceiverName");
		purchase.setReceiverPhone("010-8888-9999");
		purchase.setDivyAddr("changeDivyAddr");
		purchase.setDivyRequest("changeDivyRequest");
		purchase.setDivyDate("2023-07-07");

		purchaseService.updatePurchase(purchase);

		purchase = purchaseService.getPurchase(10001);
		Assert.assertNotNull(purchase);

		//==> console 확인
		System.out.println(purchase);

		//==> API 확인
		Assert.assertEquals("testUserId", purchase.getBuyer().getUserId());
		Assert.assertEquals(10010, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("1", purchase.getPaymentOption());
		Assert.assertEquals("changeReceiverName", purchase.getReceiverName());
		Assert.assertEquals("010-8888-9999", purchase.getReceiverPhone());
		Assert.assertEquals("changeDivyAddr", purchase.getDivyAddr());
		Assert.assertEquals("changeDivyRequest", purchase.getDivyRequest());
		Assert.assertEquals("2", purchase.getTranCode());
		Assert.assertEquals("2023-08-24", purchase.getDivyDate());
	}

	//==>  주석을 풀고 실행하면....
//		 @Test
	public void testGetPurchaseListAll() throws Exception{
		String userId = "testUserId";

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String,Object> map = purchaseService.getPurchaseList(search, userId);

		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());

		//==> console 확인
		//System.out.println(list);

		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchKeyword("");
		map = purchaseService.getPurchaseList(search, userId);

		list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());

		//==> console 확인
		//System.out.println(list);

		totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
	}

//		 @Test
	public void testGetPurchaseListByDate() throws Exception{
		String userId = "testUserId";

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("2023-08-01,2023-08-30");
		Map<String,Object> map = purchaseService.getPurchaseList(search, userId);

		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());

		//==> console 확인
		//System.out.println(list);

		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("1");
		search.setSearchKeyword("2023-08-01,2023-08-02");
		map = purchaseService.getPurchaseList(search, userId);

		list = (List<Object>)map.get("list");
		Assert.assertEquals(1, list.size());

		//==> console 확인
		//System.out.println(list);

		totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
	}

		 @Test
	public void testGetPurchaseListBySysdate() throws Exception{
		String userId = "testUserId";

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("2");
		search.setSearchKeyword("2023-08-01,SYSDATE");
		Map<String,Object> map = purchaseService.getPurchaseList(search, userId);

		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());

		//==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("2");
		search.setSearchKeyword("2023-08-01,2023-08-02");
		map = purchaseService.getPurchaseList(search, userId);

		list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());

		//==> console 확인
		System.out.println(list);

		totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
	}	 


}