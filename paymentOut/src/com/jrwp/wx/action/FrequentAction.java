package com.jrwp.wx.action;

import com.jrwp.core.help.DoResult;
import com.jrwp.core.help.DoResultType;
import com.jrwp.wx.dao.FrequentContactsMapper;
import com.jrwp.wx.entity.FrequentContacts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("wx/frequent")
public class FrequentAction {
    @Resource
    private FrequentContactsMapper frequentContactsMapper;

    @RequestMapping("saveFrquence")
    @ResponseBody
    public DoResult saveFrquence(FrequentContacts frequentContacts) {
        DoResult result = new DoResult();
        try {
//            frequentContacts.setOpenid("oJJFXsxcPZAdTiWLuucLAHibZ0KM");
            frequentContactsMapper.insertSelective(frequentContacts);
            result.setStateType(DoResultType.success);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStateType(DoResultType.fail);
        }
        return result;
    }

    @RequestMapping("isSave")
    @ResponseBody
    public DoResult isSave(String cardnumber) {
        DoResult result = new DoResult();
        try {
            if (cardnumber != null) {
                FrequentContacts frequentContacts = frequentContactsMapper.getByCardNumber(cardnumber);
                if (frequentContacts != null) {
                    //保存过
                    result.setStateValue(1);
                } else {
                    result.setStateValue(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStateValue(-1);
        }
        return result;
    }

    @RequestMapping("getFrequentContacts")
    @ResponseBody
    public List<FrequentContacts> getFrequentContacts(String openid) {
        List<FrequentContacts> frequentContacts = null;
        try {
//            openid = "oJJFXsxcPZAdTiWLuucLAHibZ0KM";
            frequentContacts = frequentContactsMapper.getByOpenid(openid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return frequentContacts;
    }

    @RequestMapping("getByid")
    @ResponseBody
    public FrequentContacts getByid(int id) {
        FrequentContacts frequentContacts = null;
        try {
            frequentContacts = frequentContactsMapper.getByid(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return frequentContacts;
    }
}
