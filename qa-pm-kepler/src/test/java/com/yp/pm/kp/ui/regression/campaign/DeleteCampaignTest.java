package com.yp.pm.kp.ui.regression.campaign;

import com.yp.pm.kp.BaseUITest;
import com.yp.pm.kp.enums.model.CampaignStatusEnum;
import com.yp.pm.kp.model.domain.Account;
import com.yp.pm.kp.model.domain.Budget;
import com.yp.pm.kp.model.domain.Campaign;
import com.yp.pm.kp.ui.CampaignPage;
import com.yp.pm.kp.ui.MyAgencyPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class DeleteCampaignTest extends BaseUITest {

    @Test
    public void testDeleteCampaign() throws Exception {
        Account account = createAccount(agencyId, "auto_" + System.currentTimeMillis(), "America/Los_Angeles");
        Budget budget = createBudget(account.getAccountId(), 100, "standard");
        Campaign newCampaign  = createCampaign(account.getAccountId(),budget.getBudgetId()).get(0);

        MyAgencyPage agencyPage = login();
        CampaignPage campaignPage = agencyPage.navigateToCampaignPage(agencyId, account.getAccountId());
        campaignPage.selectCampaign(newCampaign.getCampaignName());
        campaignPage.clickDelete();
        campaignPage.loadingStatus();
        assertConditionTrue(!campaignPage.getCampaignTableText(2, 3).contains(newCampaign.getCampaignName()));

        Campaign campaign = getCampaignByName(account.getAccountId(), newCampaign.getCampaignName());
        assertObjectNotNull(campaign.getCampaignId());
        assertObjectEqual(campaign.getCampaignName(), newCampaign.getCampaignName());
        assertObjectEqual(campaign.getAccountId(), account.getAccountId());
        assertObjectEqual(campaign.getCampaignStatus().toString(), CampaignStatusEnum.DELETED.toString());
    }
}