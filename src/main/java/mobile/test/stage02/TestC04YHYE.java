package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageYZZZ;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

public class TestC04YHYE extends TestBase {

private PageYZZZ mPage = PageManager.getPage(PageYZZZ.class);
	
	@BeforeClass
	public void before() {
		ViewNavigator.navigate("银证转账", mPage);
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testYZZZ(Map<String, String> param) {
		
		mPage.doSetBank(param.get("银行名称"), param.get("币种"));
		mPage.doBtnCYE();
		mPage.doInputYHPwd(param.get("银行密码"));
		
		Alert alert = mPage.getAlert();
		alert.doAccept();
		String actual1 = "";
		if (alert.exists()){	
			AssertUtil.assertEquals(param.get("验证1"),actual1);
		}else{
			actual1 =  mPage.doGetYHYE();
			AssertUtil.assertEquals(param.get("验证1"),actual1);
		}
		
	}
}
