package com.xiaban.qqzone.service.impl;

import com.xiaban.qqzone.dao.HostReplyDAO;
import com.xiaban.qqzone.pojo.HostReply;
import com.xiaban.qqzone.service.HostReplyService;

public class HostReplyServiceImpl implements HostReplyService {

    private HostReplyDAO hostReplyDAO;
    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        return hostReplyDAO.getHostReplyByReplyId(replyId);
    }
    @Override
    public void delHostReply(Integer id) {
        hostReplyDAO.delHostReply(id);
    }
}
