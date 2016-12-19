package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageJJDTDS;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * 基金定投测试
 */
public class TestD04JJDT extends TestBase {
	private PageJJDTDS mPage = PageManager.getPage(PageJJDTDS.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("基金定投", mPage);
		mPage.doSwitch("基金定投");
		mPage.doAdd();
	}

	@AfterClass
	public void after() {
		mPage.getToolBar().doBack();
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testJJDT(Map<String, String> param) {
		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);

		// 输入每期定投金额
		mPage.doInputNumber(param.get("金额"));

		// 选择扣款周期
		mPage.doChooseTime(param.get("扣款周期"), param.get("扣款日"));

		// 获取开始日期和结束日期
		String begintime = mPage.doGetBeginTime();
		String endtime = mPage.doGetEndTime();
		mPage.doTrade();

		// 风险提示框处理
		mPage.doHandleRiskAlert();

		// 获取验证点1
		String vCheckPoint1 = param.get("验证1");
		vCheckPoint1 = vCheckPoint1.replace("{BeginTime}", begintime).replace("{EndTime}", endtime);

		// 获取弹出框1
		Alert vAlert = mPage.getAlert();
		String vActualCheckPoint1 = vAlert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		vAlert.doAccept();

		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = vAlert.doGetText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		vAlert.doAccept();
		if(param.get("类型").equals("正例")){
			// 参数中加入委托编号
			String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
			param.put("委托编号", vNo);
		}
	}
}
